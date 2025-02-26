package com.rusefi.test;

import com.rusefi.ReaderStateImpl;
import com.rusefi.output.GetOutputValueConsumer;
import com.rusefi.util.LazyFile;
import org.junit.jupiter.api.Test;

import static com.rusefi.AssertCompatibility.assertEquals;

/**
 * @see LiveDataProcessorTest for most integrated test
 */
public class GetOutputValueConsumerTest {
    @Test
    public void generateGetOutputs() {
        String test = "struct_no_prefix ts_outputs_s\n" +
                "bit issue_294_31,\"si_example\",\"nada_example\"\n" +
                "bit enableFan1WithAc;+Turn on this fan when AC is on.\n" +
                "int hwChannel;\n" +
                "end_struct\n";
        ReaderStateImpl state = new ReaderStateImpl();

        GetOutputValueConsumer outputValueConsumer = new GetOutputValueConsumer(null, LazyFile.REAL);
        outputValueConsumer.isPtr = true;
        outputValueConsumer.conditional = "EFI_BOOST_CONTROL";
        state.readBufferedReader(test, (outputValueConsumer));
        assertEquals(
                "#if !EFI_UNIT_TEST\n" +
                        "// generated by GetOutputValueConsumer.java\n" +
                        "#include \"pch.h\"\n" +
                        "#include \"board_lookup.h\"\n" +
                        "#include \"value_lookup.h\"\n" +
                        "float getOutputValueByName(const char *name) {\n" +
                        "\tint hash = djb2lowerCase(name);\n" +
                        "\tswitch(hash) {\n" +
                        "// issue_294_31\n" +
                        "#if EFI_BOOST_CONTROL\n" +
                        "\t\tcase -1571463185:\n" +
                        "\t\t\treturn engine->outputChannels->issue_294_31;\n" +
                        "#endif\n" +
                        "// enableFan1WithAc\n" +
                        "#if EFI_BOOST_CONTROL\n" +
                        "\t\tcase -298185774:\n" +
                        "\t\t\treturn engine->outputChannels->enableFan1WithAc;\n" +
                        "#endif\n" +
                        "// hwChannel\n" +
                        "#if EFI_BOOST_CONTROL\n" +
                        "\t\tcase -709106787:\n" +
                        "\t\t\treturn engine->outputChannels->hwChannel;\n" +
                        "#endif\n" +
                        "\t}\n" +
                        "\treturn EFI_ERROR_CODE;\n" +
                        "}\n" +
                        "#endif\n", outputValueConsumer.getContent());
    }


    @Test
    public void generateGetEngineModuleOutputs() {
        String test = "struct_no_prefix fuel_pump_control_s\n" +
                "\tbit isPrime\n" +
                "\tbit engineTurnedRecently\n" +
                "\tbit isFuelPumpOn\n" +
                "\tbit ignitionOn\n" +
                "end_struct\n";
        ReaderStateImpl state = new ReaderStateImpl();

        GetOutputValueConsumer outputValueConsumer = new GetOutputValueConsumer(null, LazyFile.REAL);
        outputValueConsumer.moduleMode = true;
        outputValueConsumer.currentEngineModule = "FuelPumpController";

        state.readBufferedReader(test, (outputValueConsumer));
        assertEquals(
                "#if !EFI_UNIT_TEST\n" +
                        "// generated by GetOutputValueConsumer.java\n" +
                        "#include \"pch.h\"\n" +
                        "#include \"board_lookup.h\"\n" +
                        "#include \"value_lookup.h\"\n" +
                        "float getOutputValueByName(const char *name) {\n" +
                        "\tint hash = djb2lowerCase(name);\n" +
                        "\tswitch(hash) {\n" +
                        "// isPrime\n" +
                        "\t\tcase -1429286498:\n" +
                        "\t\t\treturn engine->module<FuelPumpController>()->isPrime;\n" +
                        "// engineTurnedRecently\n" +
                        "\t\tcase -1270448973:\n" +
                        "\t\t\treturn engine->module<FuelPumpController>()->engineTurnedRecently;\n" +
                        "// isFuelPumpOn\n" +
                        "\t\tcase -344048084:\n" +
                        "\t\t\treturn engine->module<FuelPumpController>()->isFuelPumpOn;\n" +
                        "// ignitionOn\n" +
                        "\t\tcase -381519965:\n" +
                        "\t\t\treturn engine->module<FuelPumpController>()->ignitionOn;\n" +
                        "\t}\n" +
                        "\treturn EFI_ERROR_CODE;\n" +
                        "}\n" +
                        "#endif\n", outputValueConsumer.getContent());
    }
}
