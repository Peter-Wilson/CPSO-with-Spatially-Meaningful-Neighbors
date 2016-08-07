/* qhull - quick hull see qhull.h
   
   This file is consists of MacIntosh specific code and UNIX specific code.
   If you compile this program for the UNIX environment, keep #define UNIX,
   otherwise, delete this line.
   
   Author  : Hannu Huhdanpaa
   Internet: hannu@geom.umn.edu
   
   copyright (c) 1993, The Geometry Center
        
   02/06/93 - initial version (hh)
        
*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <ctype.h>
#include "set.h"
#include "poly.h"
#include "geom.h"
#include "qhull.h"
#include "globals.h"
#include "mem.h"
#include "io.h"
#define UNIX
extern void *buffer_begin; /* pointer to the beginning of current buffer */ 
extern int *indextable;    /* table of freelists, indexed by size */
#ifdef UNIX

char prompt[]= "\n\
qhull- convex hull in general dimension, version: 1.01\n\n\
    An   - approximate convex hull, delta value n\n\
    b    - partition the horizon points also\n\
    c    - check structure\n\
    d    - Delaunay triangulation by lifting to a paraboloid\n\
    f    - in partitioning select the facet the point is furthest above\n\
    g    - output can be used as input to geomview, available for 2-d - 4-d\n\
    i    - print the facets, each identified by which vertices it contains\n\
    o    - force output despite degenerate results\n\
    Tn   - tracing on at level n\n\n\
";

/*-------------------------------------------------
-main- processes the command line, calls qhull() to do the work, and exits
this is UNIX main(), main() for Mac version is in mac.c
*/
int main(int argc, char *argv[]) {
  fout= stdout;
  ferr= stderr;
  fin= stdin;
  initialize(argc, argv);
  qhull(NULL);
  exit(0);
} /* main */

#endif

/*-------------------------------------------------
-qhull- general dimension convex hull
inputfile != NULL if Mac version
*/
void qhull(char *inputfile) {
  pointT *points, *minx, *maxx;
  int k, numpoints;
  setT *maxpoints, *vertices;
  facetT *facetlist, *facet;
  void *buffer, *nextbuffer;
  
  if (inputfile) {  /* Mac */
    fout= fopen("qhull.output", "w");
    ferr= fopen("qhull.trace", "w");
    if (!(fin= fopen(inputfile, "r"))) {
      fprintf(ferr, "qhull error #2: input file could not be opened\n");
      fclose(fout);
      fclose(ferr);
      return;
    }
  }
  first_point= points= readpoints(&numpoints, &hull_dim);
  if (hull_dim == 1) {
    fprintf(ferr, "qhull error #3: dimension must be > 1\n");
    return;
  }
  normal_size= hull_dim * sizeof(coordT);
  constructsizetable();
  facet_points= (coordT *)memalloc(hull_dim * hull_dim * sizeof(coordT), &k);
  gm_matrix= (coordT *)memalloc(hull_dim * hull_dim * sizeof(coordT), &k);
  gm_row= (coordT **)memalloc(hull_dim * sizeof(coordT *), &k);
  newhashtable(hull_dim * hull_dim * 10);
  maxpoints= maxmin(points, numpoints, hull_dim, &minx, &maxx);
  k= hull_dim + 1;
  vertices= setnew(&k);
  setprepend(&vertices, newvertex(minx));
  setprepend(&vertices, newvertex(maxx));
  initialvertices(&vertices, maxpoints, points, numpoints, hull_dim+1);
  if (!APPROXhull)
    DELTAvalue= DISTround;
  facetlist= initialhull(vertices);
  partitionall(facetlist, vertices, points, numpoints);
  setfree(&maxpoints);
  setfree(&vertices);
  buildhull(&facetlist, numpoints);
  if (CHECKstructure || IStracing) {
    checkpoints(facetlist, points, numpoints);
    checkpolygon(facetlist);
  }else if (num_coplanar || num_nonconvex || num_nearlysingular) {
    checkorientation(facetlist, ALGORITHMfault);
    checkplanes(facetlist, ALGORITHMfault);
    checkpolygon(facetlist);
  }else
    checkorientation(facetlist, ALGORITHMfault);
  if (PRINTincidences)
    printincidences(fout, facetlist);
  else if (PRINTtriangles)
    printtriangles(fout, facetlist);
  else if (PRINToff) {
    switch(hull_dim) {
    case 2:
      printoff2(fout, facetlist);
      break;
    case 3:
      printoff3(fout, facetlist, points, numpoints);
      break;
    case 4:
      printoff4(fout, facetlist, points, numpoints);
      break;
    default:
      printfacets(fout, facetlist);
      break;
    }
  }else 
    printsummary(fout, facetlist);
  FORALLfacet_(facetlist)
    setfree(&(facet->outsideset));
  free(points);
  free(hash_table);
  for(buffer= buffer_begin; buffer; buffer= nextbuffer) {
    nextbuffer= *((void **)buffer);
    free(buffer);
  }
  free(indextable);
  trace1((ferr, "qhull: algorithm completed\n"));
  if (inputfile) { /* Mac */
    fclose(fin);
    fclose(fout);
    fclose(ferr);
  }
} /* main */


/*-------------------------------------------------
-buildhull- constructs a hull by adding points to a simplex
*/
void buildhull(facetT **facetlist, int numpoints) {
  facetT *facet, **facetp, *newfacet, *newfacets;
  setT *horizon;         /* set of horizon neighbors */
  setT *interior;        /* set of visible facets for furthest */
  pointT *furthest;
  int dummy;
  
  trace1((ferr, "buildhull> facetlist %d\n", (*facetlist)->id));
  FORALLfacet_(*facetlist) {
    if (!(furthest= SETlast_(facet->outsideset, dummy)))
      continue;
    current_furthest= pointid_(furthest);
    first_newfacet= facet_id; 
    SETdellast_(facet->outsideset, dummy);  
    horizon= findhorizon(furthest, facet, &interior);
    newfacets= makecone(furthest, horizon, interior);
    FORALLnewfacet_(newfacets)
      setfacetplane(newfacet);
    partitioninterior(newfacets, interior);
    if (BESTfurthest)
      partitionhorizonpoints(newfacets, horizon);
    deleteinterior(facetlist, interior, horizon);
    setfree(&horizon);
    setfree(&interior);
  }
  trace1((ferr, "buildhull: completed the hull construction\n"));
} /* buildhull */


/*-------------------------------------------
-errexit- return to system after an error
prints useful information
*/
void errexit(facetT *facet, ridgeT *ridge, vertexT *vertex, pointT *point) {
  fprintf (ferr, "    last furthest point p%d  last new facet f%d\n", 
	   current_furthest, first_newfacet);
  errprint(facet, ridge, vertex, point);
  exit(1);
} /*errexit */


/*-------------------------------------------------
-findhorizon- find the horizon and interior for a point that is above the facet
returns:
     non-empty set of horizon facets
     interior: visible facets for the point
     sets facet->interior= True, facet->horizon= False for interior facets
     sets facet->horizon= True, facet->interior= False for horizon facets
*/
setT *findhorizon(pointT *point, facetT *facet, setT **interior) {
  facetT *neighbor, **neighborp;
  setT *horizon= NULL;
  coordT dist;
  int interiorcnt= 1, horizoncnt= 0, n;
  
  trace1((ferr, "findhorizon> for point p%d facet f%d\n",pointid_(point),facet->id));
  *interior= NULL;
  setappend(interior, facet);
  facet->interior= True; facet->horizon= False;
  facet->visitid= ++visit_id;
  SETsize_(facet->neighbors, n);
  for(n= 0; facet= SETelem_(n, facetT, *interior); n++) {
    FOREACHneighbor_(facet) {
      if (neighbor->visitid == visit_id)
	continue;
      neighbor->visitid= visit_id;
      num_visibility++;
      if ((dist= distplane(point, neighbor)) > DISTround) {
	setappend(interior, neighbor);
	interiorcnt++;
	neighbor->interior= True; neighbor->horizon= False;
      }else {
	if (dist > -DISTround) {
	  if (IStracing) 
	    fprintf(ferr, "qhull warning #1: distance from p%d to f%d = %6.16g < DISTround = %6.16g\n", pointid_(point), neighbor->id, dist, DISTround);
	  num_coplanar++;
	}
	setappend(&horizon, neighbor);
	horizoncnt++;
	neighbor->interior= False; neighbor->horizon= True;
      }
    }
  }
  if (!horizon) {
    fprintf(ferr, "qhull error #4: empty horizon\n");
    errexit(NULL, NULL, NULL, NULL);
  }
  SETsize_(horizon, n);
  if ((hull_dim - 1) * n > 2 * HASHTABLEsize) {
    newhashtable(3 * HASHTABLEsize);
  }
  trace1((ferr, "findhorizon: %d horizon facets, %d interior facets\n", 
	  horizoncnt, interiorcnt));
  return (horizon);
} /* findhorizon */


/*-------------------------------------------------
-initialhull- constructs the initial hull as a hull_dim simplex of vertices
  returns:
    facetlist for hull
    interior_point= arithmetic center of vertices
    */
facetT *initialhull(setT *vertices) {
  facetT *facet, *facetlist, *firstfacet;
  
  facetlist= createsimplex(vertices);
  interior_point= getcenter(vertices, hull_dim+1);
  firstfacet= facetlist;
  setfacetplane(firstfacet);
  if (distplane(interior_point, firstfacet) > 0)
    fliporient(facetlist);
  FORALLfacet_(facetlist)
    setfacetplane(facet); 
  checkorientation(facetlist, DATAfault);
  checkpolygon(facetlist);
  checkplanes(facetlist, DATAfault);
  trace1((ferr, "initialhull: simplex constructed\n"));
  return (facetlist);
} /* initialhull */


#ifdef UNIX

/*---------------------------------------------
-initialize- read command line arguments,
    recover qhull_command with defaults
*/
void initialize(int argc, char *argv[]) {
  int i;

  APPROXhull= False;
  BESTfurthest= False;
  BESTnewfacet= False;
  CHECKstructure= False;
  FORCEoutput= False;
  PRINToff= False;
  PRINTincidences= False;
  PRINTtriangles= False;
  IStracing= 0;
  if ((argc == 1) && isatty(0)) {
    fprintf(fout, prompt);
    exit(1);
  }
  for (i=1; i<argc; i++) {
    if (argv[i][0] == '-')
      (argv[i])++;
    switch (argv[i][0]) {
    case 'A':
      if (!isdigit(argv[i][1]))
        fprintf(stderr, "qhull warning #2: no delta value given for arg A\n");
      else {
        DELTAvalue= (realT)atof(&argv[i][1]);
        if (DELTAvalue < 0)
          fprintf(stderr, "qhull warning #3: given delta value negative\n");
        else
          APPROXhull= True;
      }
      break;
    case 'b':
      BESTfurthest= True;
      BESTnewfacet= True;
      break;
    case 'c':
      CHECKstructure= True;
      break;
    case 'd':
      DELAUNAY= True;
      break;
    case 'f':
      BESTnewfacet= True;
      break;
    case 'g':
      PRINToff= True;
      break;
    case 'i':
      PRINTincidences= True;
      break;
    case 'o':
      FORCEoutput= True;
      break;
    case 't':
      PRINTtriangles= True;
      break;
    case 'T':
      IStracing= atoi(&argv[i][1]);
      break;
    default:
      fprintf (stderr, "qhull warning #4: unknown flag %s\n", argv[i]);
      break;
    }
  }
  if (APPROXhull) {
    if (BESTnewfacet == False) {
      BESTfurthest= True;
      BESTnewfacet= True;
    }
  }
} /* initialize */

#endif

/*-------------------------------------------------
-partitionall- partitions all points into the outsidesets for facetlist
   vertices= set of vertices used by facetlist
*/
void partitionall(facetT *facetlist, setT *vertices,
		  pointT *points, int numpoints){
  setT *vertexpoints= NULL; 
  vertexT *vertex, **vertexp;
  pointT *point, *pointend;
  int upperlimit;
  
  FOREACHvertex_(vertices)
    setadd(&vertexpoints, vertex->point);
  upperlimit= setsize(vertexpoints) - 1;
  FORALLpoint_(points, numpoints)
    if (!setin(vertexpoints, point, upperlimit))
      partitionpoint(point, facetlist);
  setfree(&vertexpoints);
  trace1((ferr, "partitionall: partitioned the points into outside sets\n"));
} /* partitionall */


/*-------------------------------------------------
-partitionhorizon- partitions the outsideset of one horizonfacet
*/
void partitionhorizon(facetT *horizonfacet, facetT *newfacets) {
  int dummy;
  pointT *point, **pointp;
  facetT *facet, *bestfacet;
  coordT dist, bestdist, currentdist;
  boolT furthestdeleted= False;
  pointT *furthest= SETlast_(horizonfacet->outsideset, dummy);
  setT *pointstodelete= NULL;
  
  FOREACHpoint_(horizonfacet->outsideset) {
    bestfacet= horizonfacet;
    num_distpart++;
    bestdist= distplane(point, horizonfacet);
    FORALLfacet_(newfacets) {
      num_distpart++;
      if ((dist= distplane(point, facet)) > bestdist) {
	bestfacet= facet;
	bestdist= dist;
      }
    }
    if (bestfacet != horizonfacet) {
      setappend(&pointstodelete, point);
      if (point == furthest)
	furthestdeleted= True;
      if (bestfacet->furthestdist < bestdist) {
	setappend(&(bestfacet->outsideset), point);
	bestfacet->furthestdist= bestdist;
      }else
	setappend2ndlast(&(bestfacet->outsideset), point);
    }
  }
  FOREACHpoint_(pointstodelete)
    setdel(&(horizonfacet->outsideset), point);
  setfree(&pointstodelete);
  if (furthestdeleted && (furthest=SETlast_(horizonfacet->outsideset, dummy))){
    num_distpart++;
    currentdist= distplane(furthest, horizonfacet);
    FOREACHpoint_(horizonfacet->outsideset) {
      num_distpart++;
      if ((dist= distplane(point, horizonfacet)) > currentdist) {
	furthest= point;
	currentdist= dist;
      }
    }
    setdel(&(horizonfacet->outsideset), furthest);
    setappend(&(horizonfacet->outsideset), furthest);
  }
  trace3((ferr, "partitionhorizon: outsideset f%d repartitioned\n", 
	  horizonfacet->id));
} /* partitionhorizon */


/*-------------------------------------------------
-partitionhorizonpoints- partitions the points of horizon facets if needed
*/
void partitionhorizonpoints(facetT *newfacets, setT *horizon){
  facetT *facet, **facetp;;
  
  FOREACHfacet_(horizon) {
    partitionhorizon(facet, newfacets);
  }
  trace2((ferr, "partitionhorizonpoints: partitioned the horizon points\n"));
} /* partitionhorizonpoints */


/*-------------------------------------------------
-partitioninterior- partitions points to the outside sets of facets
*/
void partitioninterior(facetT *facetlist, setT *interior) {
  facetT *facet, **facetp;
  pointT *point, **pointp;
  
  FOREACHfacet_(interior) {
    if (facet->outsideset) {
      FOREACHpoint_(facet->outsideset) {
	partitionpoint(point, facetlist);
      }
    }
  }
  trace1((ferr,"partitioninterior: partitioned interior points into new facets\n"));
} /* partitioninterior */


/*-------------------------------------------------
-partitionpoint- assigns point to a visible facet in facetlist
    BESTnewfacet= assigns point to facet it is furthest above
    otherwise, picks the first one 

    DELTAvalue == DISTround in non-approximate case
*/
void partitionpoint(pointT *point, facetT *facetlist) {
  coordT dist, bestdist= DELTAvalue;
  facetT *facet, *bestfacet= NULL;
  
  num_partition++;
  FORALLfacet_(facetlist) {
    num_distpart++;
    if ((dist= distplane(point, facet)) > bestdist) {
      bestfacet= facet;
      bestdist= dist;
      if (!BESTnewfacet && bestdist > DISTround)
	break;
    }
  }
  if (bestfacet) {
    if (bestdist < DISTround) {
      if (IStracing)
	fprintf(ferr, "qhull warning #5: distance from the facet < DISTround, distance: %g, DISTround: %g\n", bestdist, DISTround);
      num_coplanar++;
    }
    if (bestfacet->furthestdist < bestdist) {
      setappend(&(bestfacet->outsideset), point);
      bestfacet->furthestdist= bestdist;
    }else {
      setappend2ndlast(&(bestfacet->outsideset), point);
    }
  }
  trace4((ferr, "partitionpoint: point p%d assigned to a facet f%d\n",
	  pointid_(point), getid_(bestfacet)));
} /* partitionpoint */



