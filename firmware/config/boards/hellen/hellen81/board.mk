# Combine the related files for a specific platform and MCU.

# Target ECU board design
BOARDCPPSRC = $(BOARDS_DIR)/hellen/hellen81/board_configuration.cpp
BOARDINC = $(BOARDS_DIR)/hellen/hellen81

ifeq ($(LED_CRITICAL_ERROR_BRAIN_PIN),)
  LED_CRITICAL_ERROR_BRAIN_PIN = -DLED_CRITICAL_ERROR_BRAIN_PIN=GPIOH_8
endif

ifeq ($(LED_COMMUNICATION_BRAIN_PIN),)
  LED_COMMUNICATION_BRAIN_PIN = -DEFI_COMMUNICATION_PIN=GPIOH_10
endif

DDEFS += -DEFI_MAIN_RELAY_CONTROL=TRUE

# Disable serial ports on this board as UART3 causes a DMA conflict with the SD card
DDEFS += -DTS_NO_PRIMARY -DTS_NO_SECONDARY
DDEFS += -DEFI_CAN_SERIAL=TRUE
# todo debug:
DDEFS += -DDISABLE_CAN_UPDATE_DASH=TRUE

# Add them all together
DDEFS += -DEFI_USE_OSC=TRUE -DFIRMWARE_ID=\"hellen81\" $(LED_CRITICAL_ERROR_BRAIN_PIN) $(LED_COMMUNICATION_BRAIN_PIN)
#DDEFS += -DEFI_SOFTWARE_KNOCK=TRUE -DSTM32_ADC_USE_ADC3=TRUE
#DDEFS += -DEFI_ICU_INPUTS=FALSE -DHAL_TRIGGER_USE_PAL=TRUE -DHAL_VSS_USE_PAL=TRUE
DDEFS += -DEFI_ICU_INPUTS=FALSE -DHAL_TRIGGER_USE_ADC=TRUE -DHAL_VSS_USE_PAL=TRUE



TRIGGER_USE_ADC = yes

# we need fast ADC for software trigger detector
#DDEFS += -DEFI_OVERRIDE_FAST_ADC_FOR_STM32H7=TRUE -DADC_FAST_DEVICE=ADCD1 -DADC_SLOW_DEVICE=ADCD3 -DSTM32_ADC_USE_ADC3=TRUE
#DDEFS += -DADC_SLOW_DEVICE=ADCD3 -DSTM32_ADC_USE_ADC3=TRUE

DDEFS += -DEFI_OVERRIDE_FAST_ADC_FOR_STM32H7=TRUE -DADC_FAST_DEVICE=ADCD1 -DEFI_USE_ONLY_FAST_ADC=TRUE -DEFI_FASTER_UNIFORM_ADC=TRUE -DADC_MAX_CHANNELS_COUNT=16 -DADC_BUF_DEPTH_FAST=1 -DADC_BUF_NUM_AVG=1
#DDEFS += -DADC_SLOW_DEVICE=ADCD1

include $(BOARDS_DIR)/hellen/hellen-common.mk
