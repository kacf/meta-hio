SUMMARY = "U-Boot bootloader fw_printenv/setenv utilities"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=025bf9f768cbcb1a165dbe1a110babfb"
SECTION = "bootloader"
DEPENDS = "mtd-utils"

SRCREV = "${AUTOREV}"

PROVIDES += "u-boot-fw-utils"
RPROVIDES_${PN} += "u-boot-fw-utils"

SRC_URI = "git://github.com/HIO-Project/u-boot-imx6-hio.git;branch=2014.01 \
           file://0001-Fix-Makefile-to-build-autogenerated-files-while-buil.patch"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="-L${STAGING_BASE_LIBDIR_NATIVE} -L${STAGING_LIBDIR_NATIVE}" \
                 HOSTSTRIP=true'

INSANE_SKIP_${PN} = "already-stripped"
EXTRA_OEMAKE_class_target = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} ${CFLAGS} ${LDFLAGS}" V=1'
EXTRA_OEMAKE_class-cross = 'ARCH=${TARGET_ARCH} CC="${CC} ${CFLAGS} ${LDFLAGS}" V=1'

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit uboot-config

do_compile () {
  oe_runmake ${UBOOT_MACHINE}
  oe_runmake HOSTCC=arm-poky-linux-gnueabi-gcc env
}

do_install () {
  install -d ${D}${base_sbindir}
  install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
  install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_setenv
}

SYSROOT_PREPROCESS_FUNCS_class-cross = "uboot_fw_utils_cross"
uboot_fw_utils_cross() {
        sysroot_stage_dir ${D}${bindir_cross} ${SYSROOT_DESTDIR}${bindir_cross}
}

COMPATIBLE_MACHINE = "(hio-imx6q-board|hio-imx6q-ppc4507|hio-imx6dl-board|hio-imx6dl-poe|hio-imx6dl-uart5|hio-imx6dl-ppc4507|hio-imx6dl-ppc4510|hio-imx6dl-ppc4535|hio-imx6dl-novo)"
