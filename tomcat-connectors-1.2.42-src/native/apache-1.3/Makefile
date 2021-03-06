# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

srcdir=.
top_srcdir=..
top_builddir=..
APXS=/Users/shivendrasrivastava/Documents/Apache_SAPFieldglass/httpd/bin/apxs
CP=/bin/cp
APACHE_DIR=/Users/shivendrasrivastava/Documents/Apache_SAPFieldglass/httpd
MKDIR=/bin/mkdir
APXSCFLAGS= -g -O2 -DHAVE_APR  -I/Users/shivendrasrivastava/Documents/Apache_SAPFieldglass/httpd/include -I/Users/shivendrasrivastava/Documents/Apache_SAPFieldglass/httpd/include  -DHAVE_CONFIG_H
APXSCPPFLAGS=-DDARWIN -DSIGPROCMASK_SETS_THREAD_MASK -DDARWIN_10
APXSLDFLAGS=
CC=gcc
SHELL=/bin/sh

# Defaults
libexecdir=${APACHE_DIR}/libexec

JK=${top_builddir}/common/
# Defines APACHE_OBJECTS - the list of all common files
include ${top_builddir}/common/list.mk

# Apache settings, values guessed by Apache config and used to build it
# Will define libexecdir, LIBTOOL, etc
include /Users/shivendrasrivastava/Documents/Apache_SAPFieldglass/httpd/build/config_vars.mk

# Local settings ( overriding/appending to Apache's )
COMMON=common
JK_INCL=-DUSE_APACHE_MD5 -I${top_builddir}/${COMMON} -I ${top_srcdir}/${COMMON}
CFLAGS=-I/Users/shivendrasrivastava/Documents/Apache_SAPFieldglass/httpd/include  -DHAVE_CONFIG_H ${JK_INCL} ${APXSCPPFLAGS} ${APXSCFLAGS} ${EXTRA_CFLAGS} ${EXTRA_CPPFLAGS}


# Implicit rules
include ${top_srcdir}/scripts/build/rules.mk

OEXT=.lo

all: Makefile mod_jk.so
install: install_dynamic

Makefile: ${srcdir}/Makefile.in
	echo Regenerating Makefile
	( cd ..; ./config.status )

lib_jk.la: mod_jk.lo ${APACHE_OBJECTS}
	$(LIBTOOL) --mode=link $(CC) -o lib_jk.la -static -rpath ${libexecdir}/jk mod_jk.lo $(APACHE_OBJECTS)

install_static: mod_jk.a
	@echo ""
	@echo "Copying files to Apache Modules Directory..."
	-${MKDIR} ${APACHE_DIR}/src/modules/jk
	${LIBTOOL} --mode=install ${CP} $< ${APACHE_DIR}/src/modules/jk/mod_jk.a
	-${CP} Makefile.tmpl ${APACHE_DIR}/src/modules/jk
	-${CP} Makefile.libdir ${APACHE_DIR}/src/modules/jk
	-${CP} mod_jk.c ${APACHE_DIR}/src/modules/jk
	-${MKDIR} ${APACHE_DIR}/src/modules/jk/include
	-${CP} ../common/*.h ${APACHE_DIR}/src/modules/jk/include
	@echo ""
	@echo "Please be sure to re-compile Apache..."
	@echo ""
	@echo "cd ${APACHE_DIR}"
	@echo "./config.status --activate-module=src/modules/jk/libjk.a \\"
	@echo "                --enable-module=dir \\"
	@echo "                --disable-shared=dir"
	@echo "make"
	@echo ""

#################### Dynamic .so file ####################
# APXS will compile every file, this is derived from apxs

mod_jk.a: mod_jk.lo $(APACHE_OBJECTS)
	$(LIBTOOL) --mode=link ${COMPILE} $(APXSLDFLAGS) -o $@ mod_jk.lo $(APACHE_OBJECTS)

mod_jk.lo: ${srcdir}/mod_jk.c
	${LT_COMPILE}

mod_jk.la: mod_jk.lo $(APACHE_OBJECTS)
	$(LIBTOOL) --mode=link ${COMPILE} $(APXSLDFLAGS) -o $@ -module -rpath ${libexecdir} -avoid-version mod_jk.lo $(APACHE_OBJECTS)

mod_jk.so: mod_jk.la
	${top_srcdir}/scripts/build/instdso.sh SH_LIBTOOL='$(LIBTOOL)' mod_jk.la `pwd`

install_dynamic:
	@echo ""
	@echo "Installing files to Apache Modules Directory..."
	$(APXS) -i mod_jk.la
	@echo ""
	@echo "Please be sure to arrange ${APACHE_DIR}/conf/httpd.conf..."
	@echo ""

clean:
	rm -f *.o *.lo *.a *.la *.so *.so.* *.slo
	rm -rf .libs

maintainer-clean: clean

distclean: clean
