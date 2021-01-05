KBRANCH ?= "v5.10/standard/tiny/base"

require recipes-kernel/linux/linux-yocto.inc

SRCREV_machine ?= "f08df324ccdbc73b9f0c2de2826a2843bcdf651b"
SRCREV_meta ?= "1afcd02eb057cfe92214867fcabc29c06b2ff10b"

LINUX_KERNEL_TYPE = "tiny"
KCONFIG_MODE = "--allnoconfig"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.10;destsuffix=${KMETA} \
           file://defconfig \
"
LINUX_VERSION ?= "5.10.5"

FILESEXTRAPATHS_prepend := "${THISDIR}:${THISDIR}/${BPN}-${LINUX_VERSION}:"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR_append = ".0"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

COMPATIBLE_MACHINE = "at91sam9x5"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
