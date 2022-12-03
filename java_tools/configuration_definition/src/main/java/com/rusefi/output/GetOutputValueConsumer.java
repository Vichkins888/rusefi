package com.rusefi.output;

import com.rusefi.ConfigField;
import com.rusefi.ReaderState;
import com.rusefi.TypesHelper;
import com.rusefi.core.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.rusefi.output.ConfigStructure.ALIGNMENT_FILL_AT;
import static com.rusefi.output.DataLogConsumer.UNUSED;
import static com.rusefi.output.GetConfigValueConsumer.FILE_HEADER;
import static com.rusefi.output.GetConfigValueConsumer.getCompareName;

@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class GetOutputValueConsumer implements ConfigurationConsumer {
    private final List<Pair<String, String>> getterPairs = new ArrayList<>();
    private final String fileName;

    public GetOutputValueConsumer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void handleEndStruct(ReaderState state, ConfigStructure structure) throws IOException {
        if (state.stack.isEmpty()) {
            PerFieldWithStructuresIterator iterator = new PerFieldWithStructuresIterator(state, structure.tsFields, "",
                    this::processOutput, ".");
            iterator.loop();
        }
    }

    private String processOutput(ReaderState readerState, ConfigField cf, String prefix) {
        if (cf.getName().contains(UNUSED) || cf.getName().contains(ALIGNMENT_FILL_AT))
            return "";

        if (cf.isArray() || cf.isFromIterate() || cf.isDirective())
            return "";
        if (!TypesHelper.isPrimitive(cf.getType()) && !TypesHelper.isBoolean(cf.getType())) {
            return "";
        }

        String userName = prefix + cf.getName();
        String javaName = "engine->outputChannels." + prefix;

        getterPairs.add(new Pair<>(userName, javaName + cf.getName()));


        return "";
    }

    @Override
    public void endFile() throws IOException {
        GetConfigValueConsumer.writeStringToFile(fileName, getContent());
    }

    public String getContent() {
        StringBuilder getterBody = new StringBuilder();
        for (Pair<String, String> pair : getterPairs) {
            getterBody.append(getCompareName(pair.first));
            getterBody.append("\t\treturn " + pair.second + ";\n");
        }

        return FILE_HEADER +
                "float getOutputValueByName(const char *name) {\n" + getterBody + GetConfigValueConsumer.GET_METHOD_FOOTER;
    }

}
