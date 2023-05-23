KBRANCH ?= "v5.10/standard/tiny/base"

LINUX_KERNEL_TYPE = "tiny"
KCONFIG_MODE = "--allnoconfig"

require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION ?= "5.10.99"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

SRCREV_machine ?= "317635e1feaecfd8aa29bc94d8d03ba873190414"
SRCREV_meta ?= "b53e11ea46f4e78ff4cb48532a11e1dbad7939b1"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR:append = ".6"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.10;destsuffix=${KMETA} \
           file://0001-toup-rtlwifi-Use-mac80211-debugfs-location.patch \
           file://0002-noup-rtlwifi-Add-debugfs-entries-for-registers.patch \
           file://0003-toup-rtl8xxxu-Add-debugfs-entries-for-registers.patch \
           file://0004-fromup-rtl8xxxu-Simplify-locking-of-a-skb-list-acces.patch \
           file://0005-fromup-rtl8xxxu-avoid-parsing-short-RX-packet.patch \
           file://0006-fromup-rtl8xxxu-disable-interrupt_in-transfer-for-81.patch \
           file://0007-fromup-rtl8xxxu-Use-lower-tx-rates-for-the-ack-packe.patch \
           file://0008-toup-rtl8xxxu-Handle-BSS_CHANGED_TXPOWER-IEEE80211_C.patch \
           file://0009-toup-rtl8xxxu-Handle-mac80211-get_txpower.patch \
           file://0010-toup-rtl8xxxu-Enable-RX-STBC-by-default.patch \
           file://0011-toup-rtl8xxxu-Feed-antenna-information-for-mac80211.patch \
           file://0012-toup-rtl8xxxu-Fill-up-txrate-info-for-all-chips.patch \
           file://0013-toup-rtl8xxxu-Fix-reported-RX-signal-strength.patch \
           file://0014-toup-rtl8xxxu-Raise-error-level-of-IQK-failures.patch \
           file://0015-toup-enable-DMA-for-gardena-UARTs.patch \
           file://0016-fromlist-wifi-rtl8xxxu-fix-authentication-timeout-du.patch \
           file://0017-temphack-rtl8xxxu-Print-corrupted-REG_RCR-values.patch \
           file://defconfig \
          "
FILESEXTRAPATHS:prepend := "${THISDIR}:${THISDIR}/${BPN}-${LINUX_VERSION}:"

COMPATIBLE_MACHINE = "at91sam9x5"


# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES:append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES:append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "" ,d)}"
