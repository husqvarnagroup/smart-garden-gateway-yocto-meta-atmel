FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR_append = ".0"

SRC_URI += " \
    file://rtl8192cufw_TMSC.bin \
"

do_install_append() {
    cp ${WORKDIR}/rtl8192cufw_TMSC.bin ${D}${nonarch_base_libdir}/firmware/rtlwifi/
}
