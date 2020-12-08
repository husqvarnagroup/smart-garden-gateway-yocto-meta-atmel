KBRANCH ?= "master"

require recipes-kernel/linux/linux-yocto.inc

SRCREV_machine ?= "v5.9"
SRCREV_meta ?= "bbe4e49c283637a5470445bd5e518e4921203db7"

LINUX_KERNEL_TYPE = "tiny"
KCONFIG_MODE = "--allnoconfig"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=master;destsuffix=${KMETA} \
           file://0001-ARM-at91-Add-GARDENA-smart-Gateway-AT91SAM-board.patch \
           file://0002-rtlwifi-rtl8192se-remove-duplicated-legacy_httxpower.patch \
           file://0003-rtl8xxxu-add-code-to-handle-BSS_CHANGED_TXPOWER-IEEE.patch \
           file://0004-rtl8xxxu-add-handle-for-mac80211-get_txpower.patch \
           file://0005-rtl8xxxu-Enable-RX-STBC-by-default.patch \
           file://0006-rtl8xxxu-feed-antenna-information-for-mac80211.patch \
           file://0007-rtl8xxxu-fill-up-txrate-info-for-all-chips.patch \
           file://0008-rtl8xxxu-Fix-the-reported-rx-signal-strength.patch \
           file://0009-rtl8xxxu-Fix-ampdu_action-to-get-block-ack-session-w.patch \
           file://0010-rtl8xxxu-force-resetting-basic-rate-to-all-except-CC.patch \
           file://defconfig \
          "
LINUX_VERSION ?= "5.9"

FILESEXTRAPATHS_prepend := "${THISDIR}:${THISDIR}/${BPN}-${LINUX_VERSION}:"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR_append = ".4"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

COMPATIBLE_MACHINE = "at91sam9x5"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "" ,d)}"
