KBRANCH ?= "v4.19/standard/base"

require recipes-kernel/linux/linux-yocto.inc

SRCREV_machine ?= "4ec6f255163da37a4c83528e5835b6b9baccee63"
SRCREV_meta ?= "960be4218436fbbb3500e019f7abf02fa94e6aac"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-4.19;destsuffix=${KMETA} \
          "

KCONFIG_MODE = "--alldefconfig"

# Hardware specific settings
SRC_URI_append_at91sam9x5 = "\
    file://defconfig \
    file://dts \
"

# Distribution specific settings
# TODO: Move to distribution layer
SRC_URI_append_at91sam9x5 += "\
    file://enable_compaction.cfg \
    file://enable_console.cfg \
    file://enable_debugfs.cfg \
    file://enable_devmem.cfg \
    file://enable_executable_format_elf.cfg \
    file://enable_executable_format_script.cfg \
    file://enable_keyboard_button.cfg \
    file://enable_leds.cfg \
    file://enable_modules.cfg \
    file://enable_mtd_tests.cfg \
    file://enable_nfsroot.cfg \
    file://enable_notify.cfg \
    file://enable_panic_on_oops.cfg \
    file://enable_ppp.cfg \
    file://enable_rfkill.cfg \
    file://enable_shmem.cfg \
    file://enable_squashfs.cfg \
    file://enable_sysrq.cfg \
    file://enable_systemd_cpuquota.cfg \
    file://enable_systemd_cpushare.cfg \
    file://enable_systemd_requirements.cfg \
    file://enable_systemd_resource_control.cfg \
    file://enable_systemd_strongly_recommended.cfg \
    file://enable_systemd_undocumented.cfg \
    file://enable_tun.cfg \
    file://enable_ubi.cfg \
    file://enable_watchdog.cfg \
    file://enable_wifi_crda.cfg \
    file://set_cfq_scheduler.cfg \
    file://set_default_loglevel.cfg \
    file://disable_kernel_samples.cfg \
    file://enable_netfilter.cfg \
"

# Misc stuff (maybe not even strictly required)
SRC_URI_append_at91sam9x5 += "\
    file://enable_misc_stuff.cfg \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION ?= "4.19.61"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

COMPATIBLE_MACHINE = "at91sam9x5"

# Functionality flags
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "" ,d)}"

do_patch_append() {
    cp ${WORKDIR}/dts/* ${S}/arch/arm/boot/dts/

    if ! grep -q "gardena_smart_gateway_at91sam.dtb" "${S}/arch/arm/boot/dts/Makefile"; then
        echo '\ndtb-$(CONFIG_SOC_AT91SAM9) += gardena_smart_gateway_at91sam.dtb\n' >> \
            ${S}/arch/arm/boot/dts/Makefile
    fi
}

# TODO: how is this supposed to work? nothing in yocto calls that,
#       yet it depends on dtbs being available.
kernel_do_compile_append() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
	oe_runmake -C ${B} ${PARALLEL_MAKE} dtbs CC="${KERNEL_CC} $cc_extra " LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS}
}
