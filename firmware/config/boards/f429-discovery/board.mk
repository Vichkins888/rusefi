HALCONFDIR = $(BOARD_DIR)

# List of all the board related files.
BOARDCPPSRC = $(BOARD_DIR)/board_configuration.cpp

# Required include directories
# STM32F429 has FSMC with SDRAM support
DDEFS += -DFIRMWARE_ID=\"stm32f429\"
IS_STM32F429 = yes
EFI_HAS_EXT_SDRAM = yes

BUNDLE_OPENOCD = yes

#LED
DDEFS += -DLED_CRITICAL_ERROR_BRAIN_PIN=Gpio::G14

DDEFS += -DSTM32_FSMC_USE_FSMC1=TRUE -DSTM32_SDRAM_USE_SDRAM2=TRUE

DDEFS += -DSTM32_USB_USE_OTG1=FALSE
DDEFS += -DSTM32_USB_USE_OTG2=TRUE
DDEFS += -DHAL_USE_SDRAM=TRUE
DDEFS += -DHAL_USE_FSMC=TRUE
DDEFS += -DSTATIC_BOARD_ID=STATIC_BOARD_ID_F429_DISCOVERY

# this is here just to assert compilation
DDEFS += -DALLOW_JUMP_WITH_IGNITION_VOLTAGE=FALSE

# Shared variables
ALLINC += $(BOARDINC)
