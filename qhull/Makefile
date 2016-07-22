# Makefile for qhull and rbox
#       make -- makes qhull and rbox 
#
# 	CC -- gcc is used as a compiler
#       CCOPTS1 -- options used when compiling objects into a module
#       CCOPTS2 -- options used to compile .c files
#       BINDIR -- directory where to copy executables
#       MANDIR -- directory where to copy manual pages
#       OBJS -- specifies the object files of qhull
#       PRINTER -- name of the printer used for hardcopies
#
#	Author:
#         Hannu Huhdanpaa
#	  The Geometry Center
#         University of Minnesota
#	  1300 South Second Street
#         Minneapolis, MN 55454
#         hannu@geom.umn.edu

CC	= gcc
CCOPTS1 = -g -O2 -c	
CCOPTS2 = -g -O2
BINDIR	= /usr/bin/
MANDIR	= /usr/man/
OBJS = qhull.o poly.o geom.o set.o globals.o mem.o io.o
PRINTER= next

all: qhull rbox

install: all
	/bin/cp qhull $(BINDIR).
	/bin/cp rbox $(BINDIR).

instman:
	/bin/cp qhull.man $(MANDIR).
	/bin/cp rbox.man $(MANDIR).
	
.c.o:
	$(CC) $(CCOPTS1) $<

clean:
	rm -f *.o *~ #* core gmon.out

cleanall: clean
	rm -f rbox qhull >/dev/null

printall:
	enscript -2r -G -P$(PRINTER) qhull.c poly.c geom.c set.c mem.c globals.c io.c

printheaders:
	enscript -2r -G -P$(PRINTER) qhull.h poly.h geom.h set.h mem.h globals.h io.h

printqhull:
	enscript -2r -G -P$(PRINTER) qhull.c

qhull: $(OBJS)
	$(CC) -O2 -g -o qhull $(OBJS) $(CCOPTS2) -lm

rbox: rbox.o
	$(CC) -o rbox rbox.o $(CCOPTS2) -lm

