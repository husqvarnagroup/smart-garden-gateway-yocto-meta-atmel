SUMMARY = "Realtek 8192cu Wireless Driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " \
    file://os_dep/linux/os_intfs.c;endline=19;md5=72c75de415f1e8a42587d170459677e2 \
"

inherit module

COMPATIBLE_MACHINE = "at91sam9x5"

SRC_URI = "\
    git://github.com/husqvarnagroup/rtl8188cus_vendor;protocol=https;branch=main \
"

SRCREV = "733c6aba8f8c814b2f5f36280682f63a0b8f9cc5"
PV = "2021-07-23+git${SRCPV}"
PR = "r0"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "CONFIG_PLATFORM_ARM_AT91SAM9G25=y"

DEPENDS += "bc-native"
