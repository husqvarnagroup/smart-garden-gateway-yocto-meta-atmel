KBRANCH ?= "v5.10/standard/tiny/base"

LINUX_KERNEL_TYPE = "tiny"
KCONFIG_MODE = "--allnoconfig"

require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION ?= "5.10.43"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

SRCREV_machine ?= "a68fc0180ae168b5af017e9071e183e1a51e4569"
SRCREV_meta ?= "7fab6536c164fd743f17c52bc56a65867e30903a"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR_append = ".0"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.10;destsuffix=${KMETA} \
           file://0001-rtl8xxxu-add-code-to-handle-BSS_CHANGED_TXPOWER-IEEE.patch \
           file://0002-rtl8xxxu-add-handle-for-mac80211-get_txpower.patch \
           file://0003-rtl8xxxu-Enable-RX-STBC-by-default.patch \
           file://0004-rtl8xxxu-feed-antenna-information-for-mac80211.patch \
           file://0005-rtl8xxxu-fill-up-txrate-info-for-all-chips.patch \
           file://0006-rtl8xxxu-Fix-the-reported-rx-signal-strength.patch \
           file://0007-rtl8xxxu-Fix-ampdu_action-to-get-block-ack-session-w.patch \
           file://defconfig \
          "
FILESEXTRAPATHS_prepend := "${THISDIR}:${THISDIR}/${BPN}-${LINUX_VERSION}:"

COMPATIBLE_MACHINE = "at91sam9x5"


# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "" ,d)}"
