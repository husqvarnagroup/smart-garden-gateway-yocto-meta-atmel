KBRANCH ?= "v4.19/standard/base"

require recipes-kernel/linux/linux-yocto.inc

SRCREV_machine ?= "2d7c98a6748a64ca36fd1d2e60c517b16326df61"
SRCREV_meta ?= "a7cb57afb9fb9787079c28a1028d797632105e56"

KCONFIG_MODE = "--alldefconfig"
KBUILD_DEFCONFIG_at91sam9x5 ?= "at91_dt_defconfig"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-4.19;destsuffix=${KMETA} \
           file://dts \
          "

# Add meta-atmel kmeta
FILESEXTRAPATHS_prepend := "${THISDIR}:"
SRC_URI_append = " file://atmel-kmeta;type=kmeta;name=atmel-kmeta;destsuffix=atmel-kmeta"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION ?= "4.19.72"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

COMPATIBLE_MACHINE = "at91sam9x5"

# Functionality flags
KERNEL_FEATURES_append_gardena-sg-at91sam = " bsp/gardena-sg-at91sam/gardena-sg-at91sam.scc "
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "" ,d)}"

do_patch_append() {
    cp ${WORKDIR}/dts/* ${S}/arch/arm/boot/dts/

    if ! grep -q "YOCTO_DTBS" "${S}/arch/arm/boot/dts/Makefile"; then
        printf '\n# YOCTO_DTBS\ndtb-$(CONFIG_SOC_AT91SAM9) += gardena_smart_gateway_at91sam.dtb\n' >> \
            ${S}/arch/arm/boot/dts/Makefile
    fi
}
