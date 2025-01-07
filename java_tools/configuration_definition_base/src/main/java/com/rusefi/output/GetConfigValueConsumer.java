package com.rusefi.output;

import com.rusefi.ConfigField;
import com.rusefi.ReaderState;
import com.rusefi.parse.TypesHelper;
import com.rusefi.output.variables.VariableRecord;
import com.rusefi.util.LazyFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Here we generate C++ code for https://github.com/rusefi/rusefi/wiki/Lua-Scripting#getcalibrationname
 * @see GetOutputValueConsumer
 * @see GetConfigValueConsumerTest
 */
@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class GetConfigValueConsumer implements ConfigurationConsumer {
    private static final String CONFIG_ENGINE_CONFIGURATION = "config->engineConfiguration.";
    private static final String ENGINE_CONFIGURATION = "engineConfiguration.";

    public static String getHeader(Class clazz) {
        return "// generated by " + clazz.getSimpleName() + ".java\n" +
                "#include \"pch.h\"\n" +
                SdCardFieldsContent.BOARD_LOOKUP_H +
                "#include \"value_lookup.h\"\n";
    }

    private static final String GET_METHOD_HEADER =
            "float getConfigValueByName(const char *name) {\n";

    static final String GET_METHOD_FOOTER = "\treturn EFI_ERROR_CODE;\n" + "}\n";
    private static final String SET_METHOD_HEADER = "bool setConfigValueByName(const char *name, float value) {\n";
    private static final String SET_METHOD_FOOTER = "}\n";
    private final List<VariableRecord> variables = new ArrayList<>();
    private final String outputFileName;
    private final String mdOutputFileName;
    private final LazyFile.LazyFileFactory lazyFileFactory;

    private final StringBuilder mdContent = new StringBuilder();

    public GetConfigValueConsumer() {
        this(null, null, LazyFile.REAL);
    }

    public GetConfigValueConsumer(String outputFileName, String mdOutputFileName, LazyFile.LazyFileFactory lazyFileFactory) {
        this.outputFileName = outputFileName;
        this.mdOutputFileName = mdOutputFileName;
        this.lazyFileFactory = lazyFileFactory;
    }

    public static void writeStringToFile(@Nullable String fileName, String content, LazyFile.LazyFileFactory lazyFileFactory) throws IOException {
        if (fileName != null) {
            LazyFile fw = lazyFileFactory.create(fileName);
            fw.write(content);
            fw.close();
        }
    }

    @Override
    public void handleEndStruct(ReaderState state, ConfigStructure structure) throws IOException {
        if (state.isStackEmpty()) {
            PerFieldWithStructuresIterator.Strategy strategy = new PerFieldWithStructuresIterator.Strategy() {
                @Override
                public String process(ReaderState state, ConfigField cf, String prefix, int currentPosition, PerFieldWithStructuresIterator perFieldWithStructuresIterator) {
                    return processConfig(cf, prefix);
                }

                @Override
                public String getArrayElementName(ConfigField cf) {
                    return cf.getOriginalArrayName();
                }
            };
            PerFieldWithStructuresIterator iterator = new PerFieldWithStructuresIterator(state, structure.getTsFields(), "",
                    strategy, ".");
            iterator.loop(0);
        }
    }

    @Override
    public void endFile() throws IOException {
        writeStringToFile(outputFileName, getContent(), lazyFileFactory);
        writeStringToFile(mdOutputFileName, getMdContent(), lazyFileFactory);
    }

    private String processConfig(ConfigField cf, String prefix) {
        if (cf.isUnusedField())
            return "";

        if (cf.isArray() || cf.isFromIterate() || cf.isDirective())
            return "";
        if (!TypesHelper.isPrimitive(cf.getTypeName()) && !TypesHelper.isBoolean(cf.getTypeName())) {
            return "";
        }

        String userName = prefix + cf.getName();
        if (userName.startsWith(ENGINE_CONFIGURATION))
            userName = userName.substring(ENGINE_CONFIGURATION.length());

        String javaName = "config->" + prefix;
        if (javaName.startsWith(CONFIG_ENGINE_CONFIGURATION))
            javaName = "engineConfiguration->" + javaName.substring(CONFIG_ENGINE_CONFIGURATION.length());

        variables.add(new VariableRecord(userName, javaName + cf.getName(), cf.getTypeName(), null));

        mdContent.append("### " + userName + "\n");
        mdContent.append(cf.getComment() + "\n\n");


        return "";
    }

    @NotNull
    private String getAssignment(String cast, String value) {
        return "\t{\n" + "\t\t" + value + " = " + cast +
                "value;\n" +
                "\t\treturn 1;\n\t}\n";
    }

    @NotNull
    static String getCompareName(String userName) {
        return "\tif (strEqualCaseInsensitive(name, \"" + userName + "\"))\n";
    }

    public String getHeaderAndGetter() {
        return GetConfigValueConsumer.getHeader(getClass()) +
                getCompleteGetterBody();
    }

    public String getMdContent() {
        return mdContent.toString();
    }

    @NotNull
    public String getCompleteGetterBody() {
        StringBuilder switchBody = new StringBuilder();

        StringBuilder getterBody = GetOutputValueConsumer.getGetters(switchBody, variables);

        String fullSwitch = GetOutputValueConsumer.wrapSwitchStatement(switchBody);

        return GET_METHOD_HEADER +
                fullSwitch +
                getterBody + GET_METHOD_FOOTER;
    }

    public String getSetterBody() {
        StringBuilder switchBody = new StringBuilder();

        StringBuilder setterBody = new StringBuilder();
        HashMap<Integer, AtomicInteger> hashConflicts = GetOutputValueConsumer.getHashConflicts(variables);

        for (VariableRecord pair : variables) {

            String cast = TypesHelper.isFloat(pair.type) ? "" : "(int)";


            int hash = HashUtil.hash(pair.getUserName());
            String str = getAssignment(cast, pair.getFullName());
            if (hashConflicts.get(hash).get() == 1) {
                switchBody.append("\t\tcase " + hash + ":\n");
                switchBody.append(str);

            } else {

                setterBody.append(getCompareName(pair.getUserName()));
                setterBody.append(str);
            }
        }

        String fullSwitch = GetOutputValueConsumer.wrapSwitchStatement(switchBody);

        return fullSwitch + "\treturn 0;\n" + setterBody;
    }

    public String getContent() {
        return getHeaderAndGetter()
                +
                SET_METHOD_HEADER + getSetterBody() + SET_METHOD_FOOTER
                ;
    }
}
