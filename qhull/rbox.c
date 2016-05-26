/* rbox.c

   This code needs rewriting.
*/
#define UNIX
#ifdef UNIX
char prompt[]= "\n\
-rbox- generate random points in a cube or a sphere about the origin\n\
\n\
args (any order, command line sets seed):\n\
  3000    number of points\n\
  b       add a slightly smaller cubical box around points ('r' turns off)\n\
  s       cospherical points\n\
  l       spiral distribution, available only in 3-d
  D3      dimension 3-d\n\
  W0.1    restrict to 0.1 of the surface of a sphere or cube\n\
  Z0.5    restrict to a 0.5 disk projected to sphere (default Z1.0)\n\
  Z0.5 G0.6 same as Z0.5 with a 0.6 ring (default G0.5)\n\
  r       regular points about z axis, (Z1 G0.1 gives two rings)\n\
";
#else
#define random rand
#define srandom srand
#endif
#include <ctype.h>
#include <float.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MINVALUE 0.8
#define MAXdim  100
#define RANDOM_MAX 0x7fffffffUL
#define PI 3.141593

#define fabs_(a)  ( (a) < 0 ? -(a) : (a) )
#define maximize_(maxval, val) {if ( (maxval) < (val)) (maxval)= (val);}
#define minimize_(minval, val) {if ( (minval) > (val)) (minval)= (val);}

int dim=3 , numpoints= 10,  issphere, isaxis,  isregular, iswidth, addbox, isring, isspiral, ishalfspiral;
double width, ring, radius, numregular;

/*--------------------------------------------
-rbox-  main procedure of rbox application
*/
#ifdef UNIX
void main(int argc, char **argv) {
#else
void rbox(void) {
#endif
    int i,j,k;
    int gendim;
    int boxsize;
    unsigned long randi;
    double coord[MAXdim], t;
    double maxval[MAXdim]={0.0}, minval[MAXdim]={0.0}, minmax=FLT_MAX;
    double norm, factor, randr, randmax, ranring=1.0, seed;
    double anglediff, angle, x, y;
    char command[200], *s;    
    FILE *fp;

#ifdef UNIX
    if (argc == 1) {
      printf (prompt);
        exit(1);
    }
    strcpy (command, argv[0]);
    for (i=1; i<argc; i++) {
	if (strlen (command) + strlen(argv[i]) + 1 < sizeof(command) ) {
	    strcat (command, " ");
	    strcat (command, argv[i]);
	}
        if (isdigit (argv[i][0])) {
            numpoints = atoi (argv[i]);
            continue;
	}
	if (argv[i][0] == '-')
	  (argv[i])++;
        switch (argv[i][0]) {
	  case 'b':
	    addbox= 1;
	    issphere= 1;
	    break;
	  case 'D':
	    dim = atoi (&argv[i][1]);
	    if (dim < 1
	    || dim > MAXdim) {
		fprintf (stderr, "randbox: dim %d too large or too small\n", dim);
		exit (1);
	    }
            break;
	  case 'G':
	    if (argv[i][1])
	      ring= (double) atof (&argv[i][1]);
	    else
	      ring= 0.5;
	    isring= 1;
	    break;
	  case 'r':
	    isregular= 1;
	    break;
	  case 's':
	    issphere = 1;
            break;
	  case 'l':
	    isspiral= 1;
	    break;
	  case 'W':
	    width= (double) atof (&argv[i][1]);
	    iswidth= 1;
	    break;
	  case 'Z':
	    if (argv[i][1])
	      radius= (double) atof (&argv[i][1]);
	    else
	      radius= 1.0;
	    isaxis= 1;
	    break;
	  default:
            fprintf (stderr, "randbox: unknown flag %s.  Execute 'rbox' without arguments for documentation.\n", argv[i]);
            exit (1);
	  }
      }
#endif
    if (addbox)
      boxsize= floor(ldexp(1.0,dim)+0.5);
    else
      boxsize= 0;
#ifdef UNIX
    fp= stdout;
#else
    fp= fopen("qhull.input",  "w");
#endif
    fprintf(fp,  "%d\n%d\n", dim, numpoints + ((dim == 3) && isregular) * (2 + isring * numpoints) + (!isregular && isaxis) + boxsize);
    for (s=command; *s; s++)
      seed= 11*seed + *s;
    srandom(seed);
    if (isregular) {
      if (addbox) {
	fprintf(stderr, "rbox: box option does not work with regular points\n");
	exit(1);
      }
      if (dim != 2 && dim != 3) {
	fprintf(stderr, "rbox: regular points can be used only in 2-d and 3-d\n");
	exit(1);
      }
      if (!isaxis || radius == 0.0) {
	isaxis= 1;
	radius= 1.0;
      }
      anglediff= 2* PI/numpoints;
      angle= 0.0;
      if (dim == 3) {
	fprintf (fp, "0.0 0.0 -1.0\n");
	fprintf (fp, "0.0 0.0 1.0\n");
      }
      for (i=0; i<numpoints; i++) {
	angle += anglediff;
	x= radius * cos (angle);
	y= radius * sin (angle);
	if (dim == 2) {
	  fprintf (fp, "%6.16e %6.16e\n", x, y);
	}else {
	  norm= sqrt (1.0 + x*x + y*y);
	  fprintf (fp, "%6.16e %6.16e %6.16e\n", x/norm, y/norm, 1.0/norm);
	  if (isring) {
	    x *= 1-ring;
	    y *= 1-ring;
	    norm= sqrt (1.0 + x*x + y*y);
	    fprintf (fp, "%6.16e %6.16e %6.16e\n", x/norm, y/norm, 1.0/norm);
	  }
	}
      }
      return;
    }
    if (isaxis) {
      gendim= dim-1;
      for (j=0; j<gendim; j++)
	fprintf (fp, "0.0 ");
      fprintf (fp, "-1.0 \n");
      if (!isring) {
	isring= 1;
	ring= 1.0;
      }
    }else {
      gendim= dim;
    }
    randmax= RANDOM_MAX;
    for (i=0; i<numpoints; i++) {
      norm = 0.0;
      for (j=0; j<gendim; j++) {
	randi= (unsigned long) random();
	randr= randi;
	coord[j] = randr/(randmax+1) - 0.5;  /*is normalized to 1 */
	norm += coord[j] * coord[j];
      }
      norm= sqrt (norm);
      if (isaxis) {
	randi= (unsigned long) random();
	randr= randi;
	ranring= 1.0 - ring * randr/(randmax+1);
	factor= radius * ranring / norm;
	for (j=0; j<gendim; j++)
	  coord[j]= factor * coord[j];
      }else if (isspiral) {
	if (dim != 3) {
	  fprintf(stderr, "rbox: spiral distribution is available only in 3d\n");
	  exit(1);
	}
	coord[0]= cos(2*PI*i/(numpoints - 1));
	coord[1]= sin(2*PI*i/(numpoints - 1));
	coord[2]= (double)i/(double)(numpoints - 2);
      }else if (issphere) {
	factor= 1/norm;
	if (iswidth) {
	  randi= (unsigned long) random();
	  randr= randi;
	  factor *= 1.0 - width * randr/(randmax+1);
	}
	for (j=0; j<dim; j++)
	  coord[j]= factor * coord[j];
      }
      if (isaxis && issphere) {
	coord[dim-1]= 1.0;
	norm= 1.0;
	for (j=0; j<gendim; j++)
	  norm += coord[j] * coord[j];
	norm= sqrt (norm);
	for (j=0; j<dim; j++)
	  coord[j]= coord[j] / norm;
	if (iswidth) {
	  randi= (unsigned long) random();
	  randr= randi;
	  coord[dim-1] *= 1 - width * randr/(randmax+1);
	}
      }else if (isaxis && !issphere) {  /* not very interesting */
	randi= (unsigned long) random();
	randr= randi;
	coord[dim-1] = randr/(randmax+1) - 0.5;
      }else if (iswidth && !issphere) {
	randi= (unsigned long) random();
	j= randi % gendim;
	if (coord[j] < 0)
	  coord[j]= -0.5 - coord[j] * width;
	else
	  coord[j]= 0.5 - coord[j] * width;
      }
      for (k=0; k<dim; k++) {
	maximize_(maxval[k],coord[k]);
	minimize_(minval[k],coord[k]);
	fprintf (fp, "%6.16e ", coord[k]);
      }
      fprintf (fp, "\n");
    }
    if (addbox) {
      for (k=0; k<dim; k++) {
	minimize_(minmax, maxval[k]);
	minimize_(minmax, -minval[k]);
      }
      maximize_(minmax, MINVALUE);
      for (j=0; j<boxsize; j++) {
	for (k=dim-1; k>=0; k--) {
	  if (j & ( 1 << k))
	    fprintf(fp, "%6.16e ", minmax * 0.99999);
	  else
	    fprintf(fp, "%6.16e ", -minmax * 0.99999);
	}
	fprintf (fp, "\n");
      }
    }
    return;
  } /* rbox */
