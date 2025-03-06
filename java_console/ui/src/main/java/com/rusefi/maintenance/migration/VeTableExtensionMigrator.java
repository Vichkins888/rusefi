package com.rusefi.maintenance.migration;

import com.opensr5.ini.field.ArrayIniField;
import com.opensr5.ini.field.IniField;
import com.rusefi.config.FieldType;
import com.rusefi.io.UpdateOperationCallbacks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum VeTableExtensionMigrator implements IniFieldMigrator {
    INSTANCE;

    private static String VE_TABLE_FIELD_NAME = "veTable";
    private static String VE_RPM_BINS_FIELD_NAME = "veRpmBins";

    private static final FieldType VE_TABLE_TYPE = FieldType.UINT16;
    private static final FieldType VE_RPM_BINS_TYPE = FieldType.UINT16;

    private static final int VE_TABLE_ROWS = 16;
    private static final int OLD_VE_TABLE_COLS = 16;
    private static final int NEW_VE_TABLE_COLS = 24;

    private static final int VE_RPM_BINS_COLS = 1;
    private static final double VE_RPM_BINS_MULTIPLIER = 1;
    private static final String VE_RPM_BINS_DIGITS = "0";

    @Override
    public Optional<String> tryMigrateValue(
        final IniField prevField,
        final IniField newField,
        final String prevValue,
        final UpdateOperationCallbacks callbacks
    ) {
        Optional<String> result = Optional.empty();
        final String prevFieldName = prevField.getName();
        final String newFieldName = newField.getName();
        if (VE_TABLE_FIELD_NAME.equals(prevFieldName) && VE_TABLE_FIELD_NAME.equals(newFieldName)) {
            result = tryMigrateVeTable(prevField, newField, prevValue, callbacks);
        } else if (VE_RPM_BINS_FIELD_NAME.equals(prevFieldName) && VE_RPM_BINS_FIELD_NAME.equals(newFieldName)) {
            result = tryMigrateVeRpmBins(prevField, newField, prevValue, callbacks);
        }
        return result;
    }

    private Optional<String> tryMigrateVeTable(
        final IniField prevField,
        final IniField newField,
        final String prevValue,
        final UpdateOperationCallbacks callbacks
    ) {
        Optional<String> result = Optional.empty();
        final Optional<ArrayIniField> prevVeTableValidatedField = getValidatedVeTableArrayIniField(prevField, callbacks);
        final Optional<ArrayIniField> newVeTableValidatedField = getValidatedVeTableArrayIniField(newField, callbacks);
        if (prevVeTableValidatedField.isPresent() && newVeTableValidatedField.isPresent()) {
            final ArrayIniField prevVeTableField = prevVeTableValidatedField.get();
            final ArrayIniField newVeTableField = newVeTableValidatedField.get();
            final int prevVeTableFieldCols = prevVeTableField.getCols();
            final int newVeTableFieldCols = newVeTableField.getCols();
            if ((prevVeTableFieldCols == OLD_VE_TABLE_COLS) && (newVeTableFieldCols == NEW_VE_TABLE_COLS)) {
                final String[][] prevValues = prevVeTableField.getValues(prevValue);
                final String[][] newValues = new String[VE_TABLE_ROWS][NEW_VE_TABLE_COLS];
                for (int rowIdx = 0; rowIdx < VE_TABLE_ROWS; rowIdx++) {
                    // copy prev values:
                    for (int colIdx = 0; colIdx < OLD_VE_TABLE_COLS; colIdx++) {
                        newValues[rowIdx][colIdx] = prevValues[rowIdx][colIdx];
                    }
                    // propagate value from a last column to new columns:
                    for (int colIdx = OLD_VE_TABLE_COLS; colIdx < NEW_VE_TABLE_COLS; colIdx++) {
                        newValues[rowIdx][colIdx] = prevValues[rowIdx][OLD_VE_TABLE_COLS - 1];
                    }
                }
                result = Optional.of(newVeTableField.formatValue(newValues));
            }
        }
        return result;
    }

    private static Optional<ArrayIniField> getValidatedVeTableArrayIniField(
        final IniField field,
        final UpdateOperationCallbacks callbacks
    ) {
        if (!(field instanceof ArrayIniField)) {
            callbacks.logLine(String.format(
                "WARNING! `veTable` ini-field is expected to be `ArrayIniField` instead of %s",
                field.getClass().getName()
            ));
            return Optional.empty();
        }
        final ArrayIniField arrayField = (ArrayIniField) field;
        final FieldType arrayFieldType = arrayField.getType();
        if (arrayFieldType != VE_TABLE_TYPE) {
            callbacks.logLine(String.format(
                "WARNING! `veTable` ini-field is expected to be `ArrayIniField` instead of %s",
                field.getClass().getName()
            ));
            return Optional.empty();
        }
        final int arrayFieldRows = arrayField.getRows();
        if (arrayFieldRows != VE_TABLE_ROWS) {
            callbacks.logLine(String.format(
                "WARNING! `veTable` ini-field is expected to contain %d rows instead of %d",
                VE_TABLE_ROWS,
                arrayFieldRows
            ));
            return Optional.empty();
        }
        final int arrayFieldCols = arrayField.getCols();
        switch (arrayFieldCols) {
            case OLD_VE_TABLE_COLS:
            case NEW_VE_TABLE_COLS: {
                return Optional.of(arrayField);
            }
            default: {
                callbacks.logLine(String.format(
                    "WARNING! `veTable` ini-field is expected to contain %d or %d columns " +
                        "instead of %d",
                    OLD_VE_TABLE_COLS,
                    NEW_VE_TABLE_COLS,
                    arrayFieldRows
                ));
                return Optional.empty();
            }
        }
    }

    private static long chooseStep(final List<Long> bins, final long maxPossibleStep) {
        Long lastBin = null;
        Long lastStep = null;
        Long maxStep = null;
        for (final long bin: bins) {
            if (lastBin != null) {
                lastStep = bin - lastBin;
                if (lastStep <= maxPossibleStep) {
                    if ((maxStep == null) || (maxStep < lastStep)) {
                        maxStep = lastStep;
                    }
                }
            }
            lastBin = bin;
        }
        if (lastStep != null) {
            if (lastStep <= maxPossibleStep) {
                return lastStep;
            } else if (maxStep != null) {
                return maxStep;
            }
        }
        return maxPossibleStep;
    }

    private Optional<String> tryMigrateVeRpmBins(
        final IniField prevField,
        final IniField newField,
        final String prevValue,
        final UpdateOperationCallbacks callbacks
    ) {
        Optional<String> result = Optional.empty();
        final Optional<ArrayIniField> prevVeRpmBinsValidatedField = getValidatedVeRpmBinsArrayIniField(
            prevField,
            callbacks
        );
        final Optional<ArrayIniField> newVeRpmBinsValidatedField = getValidatedVeRpmBinsArrayIniField(
            newField,
            callbacks
        );
        if (prevVeRpmBinsValidatedField.isPresent() && newVeRpmBinsValidatedField.isPresent()) {
            final ArrayIniField prevVeRpmBinsField = prevVeRpmBinsValidatedField.get();
            final ArrayIniField newVeRpmBinsField = newVeRpmBinsValidatedField.get();
            final int prevVeRpmBinsFieldRows = prevVeRpmBinsField.getRows();
            final int newVeRpmBinsFieldRows = newVeRpmBinsField.getRows();
            if ((prevVeRpmBinsFieldRows == OLD_VE_TABLE_COLS) && (newVeRpmBinsFieldRows == NEW_VE_TABLE_COLS)) {
                final List<String> prevValues = Arrays.stream(prevVeRpmBinsField.getValues(prevValue))
                    .map(e -> e[0])
                    .collect(Collectors.toList());
                final List<Long> prevLongValues = prevValues.stream()
                    .map(Double::parseDouble)
                    .map(Math::round)
                    .collect(Collectors.toList());
                final long lastValue = prevLongValues.get(prevLongValues.size() - 1);
                final String max = newVeRpmBinsField.getMax();

                Optional<Long> recommendedStep = Optional.empty();
                if (max != null) {
                    final long maxPossibleStep = (long)(
                        (Double.parseDouble(max) - lastValue) / (NEW_VE_TABLE_COLS - OLD_VE_TABLE_COLS)
                    );
                    if (1 <= maxPossibleStep) {
                        recommendedStep = Optional.of(chooseStep(prevLongValues, maxPossibleStep));
                    } else {
                        callbacks.logLine(String.format(
                            "WARNING! `veRpmBins` ini-field cannot be propagated with increasing values, because max value is %s",
                            max
                        ));
                        return Optional.empty();
                    }
                } else {
                    final long valueBeforeLast = prevLongValues.get(prevLongValues.size() - 2);
                    recommendedStep = Optional.of(lastValue - valueBeforeLast);
                }
                if (recommendedStep.isPresent()) {
                    final String[][] newValues = new String[NEW_VE_TABLE_COLS][1];
                    // copy prev values:
                    for (int i = 0; i < OLD_VE_TABLE_COLS; i++) {
                        newValues[i] = new String[] { prevValues.get(i) };
                    }
                    long lastBin = lastValue;
                    // add missed bins with recommended step:
                    for (int i = OLD_VE_TABLE_COLS; i < NEW_VE_TABLE_COLS; i++) {
                        lastBin += recommendedStep.get();
                        newValues[i] = new String[] { String.format("%.1f", (double)lastBin) };
                    }
                    result = Optional.of(newVeRpmBinsField.formatValue(newValues));
                }
            } else {
                callbacks.logLine("WARNING! We failed to extend `veRpmBins` ini-field");
            }
        }
        return result;
    }

    private static Optional<ArrayIniField> getValidatedVeRpmBinsArrayIniField(
        final IniField field,
        final UpdateOperationCallbacks callbacks
    ) {
        if (!(field instanceof ArrayIniField)) {
            callbacks.logLine(String.format(
                "WARNING! `veRpmBins` ini-field is expected to be `ArrayIniField` instead of %s",
                field.getClass().getName()
            ));
            return Optional.empty();
        }
        final ArrayIniField arrayField = (ArrayIniField) field;
        final FieldType arrayFieldType = arrayField.getType();
        if (arrayFieldType != VE_RPM_BINS_TYPE) {
            callbacks.logLine(String.format(
                "WARNING! Type of `veRpmBins` ini-field is expected to be `%s` instead of `%s`",
                VE_TABLE_TYPE,
                arrayFieldType
            ));
            return Optional.empty();
        }
        final int arrayFieldCols = arrayField.getCols();
        if (arrayFieldCols != VE_RPM_BINS_COLS) {
            callbacks.logLine(String.format(
                "WARNING! `veRpmBins` ini-field is expected to contain %d columns instead of %d",
                VE_TABLE_ROWS,
                arrayFieldCols
            ));
            return Optional.empty();
        }
        final double arrayFieldMultiplier = arrayField.getMultiplier();
        if (arrayFieldMultiplier != VE_RPM_BINS_MULTIPLIER) {
            callbacks.logLine(String.format(
                "WARNING! Multiplier of `veRpmBins` ini-field is expected to be %f instead of %f",
                VE_RPM_BINS_MULTIPLIER,
                arrayFieldMultiplier
            ));
            return Optional.empty();
        }
        final String arrayFieldDigits = arrayField.getDigits();
        if (!VE_RPM_BINS_DIGITS.equals(arrayFieldDigits)) {
            callbacks.logLine(String.format(
                "WARNING! Digits of `veRpmBins` ini-field is expected to be `%s` instead of `%s`",
                VE_RPM_BINS_DIGITS,
                arrayFieldDigits
            ));
            return Optional.empty();
        }
        final int arrayFieldRows = arrayField.getRows();
        switch (arrayFieldRows) {
            case OLD_VE_TABLE_COLS:
            case NEW_VE_TABLE_COLS: {
                return Optional.of(arrayField);
            }
            default: {
                callbacks.logLine(String.format(
                    "WARNING! `veRpmBins` ini-field is expected to contain %d or %d rows " +
                        "instead of %d",
                    OLD_VE_TABLE_COLS,
                    NEW_VE_TABLE_COLS,
                    arrayFieldRows
                ));
                return Optional.empty();
            }
        }
    }
}
