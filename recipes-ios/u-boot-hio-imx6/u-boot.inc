require recipes-bsp/u-boot/u-boot-mender.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://enable-mender-boot-code.patch"

BOOTENV_SIZE = "0x2000"
