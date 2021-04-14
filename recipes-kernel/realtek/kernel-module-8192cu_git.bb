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

SRCREV = "ca44d56bc88e81b16d9df3a0ec7106342db3c45b"
PV = "2021-04-13+git${SRCPV}"
PR = "r0"

S = "${WORKDIR}/git"

DEPENDS += "bc-native"
