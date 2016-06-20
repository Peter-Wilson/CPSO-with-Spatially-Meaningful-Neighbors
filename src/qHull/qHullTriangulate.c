/** qHullTriangulate: 
 *	JNI <--> qHull Interface to create Delaunay triangulation for a given set of points.
 *
 * Apparently you're suppose to run qHull, not call it like this, err... nevermind.
 *
 * qHull does what it does very quickly, very stably and very well, but the coding is awful.
 * The JNI is pretty bad as well so this is unavoidably a bit of a mess.
 * 
 * Get anything slightly wrong and all you get is a SEGFAULT.
 *
 * A lot of this was copied from qdelaunay.c - the qHull standard Delaunay program.
 *
 * @author oliford <codes<at>oliford.co.uk>
 * Copyright Oliver Ford (c) 2010 
 * 
 */

#include <stdio.h>
#include <memory.h>
#include <stdlib.h>
#include "qHullJNI_QHullDelaunay.h"
#include <qhull.h>
#include <qset.h>
#include <poly.h>


//this is a string of letters
//it seems to be needed for something
// ... maybe ...
// who knows.
char hidden_options[]=" d n v H U Qb QB Qc Qf Qg Qi Qm Qr QR Qv Qx TR E V FC Fi Fo Ft Fp FV Q0 Q1 Q2 Q3 Q4 Q5 Q6 Q7 Q8 Q9 ";

JNIEXPORT jobjectArray JNICALL Java_qHullJNI_QHullDelaunay_qHullTriangulate(JNIEnv *env, jclass jobj, jobjectArray pointsArray2D, jboolean outputSummary) {

	int i;
	int dim = 3;		//qhull wants the 3D projection of the 2D points we're given
	int ismalloc = 0;	//ask qhull to not free the memory we give it
	facetT *facet;		//needed for FOREACHfacet{ }
	
	// Get number of points in incombing array
	int nPoints = (int)( (*env)->GetArrayLength(env, pointsArray2D) );

	//alocate space for 3D points array
	i = (nPoints*3) * sizeof(coordT);
	coordT *points3D = (coordT*)malloc(i);
	if (points3D == NULL) { fprintf(stderr, "Unable to allocate %i bytes for 3D copy of points\n", i); return NULL; }

	//for each point... get the coordinates array and fill in the 3D coords
	for(i=0; i < nPoints; i++){
		jdoubleArray coordArray = (jdoubleArray) (*env)->GetObjectArrayElement(env, pointsArray2D, i);
		if (coordArray == NULL) { return NULL; }

		jdouble *pointCoords = (*env)->GetDoubleArrayElements(env, coordArray, NULL);
		if (pointCoords == NULL) { return NULL; }

		points3D[i*3+0] = (coordT)pointCoords[0];
		points3D[i*3+1] = (coordT)pointCoords[1];
		points3D[i*3+2] = (coordT)0; //set by qh_setdelaunay() in a bit

		(*env)->ReleaseDoubleArrayElements(env, coordArray, pointCoords, 0);
	}
	
	
	//init qhull (with no input file or command line args)
	qh_init_A(NULL, stdout, stderr, 0, NULL);

	//fill in some err... 'options' 
	qh_option("delaunay  Qbbound-last", NULL, NULL);
	qh DELAUNAY= True;		// 'd'   
	qh SCALElast= True;		// 'Qbb' 
	qh KEEPcoplanar= True;		// 'Qc', to keep coplanars in 'p' 
	qh_checkflags(qh qhull_command, hidden_options);	//check some stuff
	qh_initflags(qh qhull_command);				// do some stuff

	// fill in the 3rd dim of points 3D with a parabolic projection
	qh_setdelaunay(dim, nPoints, points3D);

	//pass those to qhull
	qh_init_B(points3D, nPoints, dim, ismalloc);

	//drumroll please....
	qh_qhull();

	// and we're done, do some verifications and cleanup 
	qh_check_output();

	if(outputSummary){
		qh_produce_output();
	}else{
		//this is in qh_produce_output() and nothing happens if we don't run it
		qh_findgood_all(qh facet_list); 
	}



	if (qh VERIFYoutput && !qh FORCEoutput && !qh STOPpoint && !qh STOPcone)
		qh_check_points();

	
	//count the good triangles
	int nTriangles = 0;
	FORALLfacets {
		if (facet->good)
			if (facet->simplicial)
				nTriangles++;
	}
	  
	//the output array is int triangles[][]
	jobjectArray triangles;

	jclass intArrCls = (*env)->FindClass(env, "[I");
	if (intArrCls == NULL) { return NULL; } /* exception thrown */
	
	triangles = (*env)->NewObjectArray(env, nTriangles, intArrCls, NULL);
	if (triangles == NULL) { return NULL; } /* out of memory error thrown */
	
	i=0;
	FORALLfacets {
		//ignore stuff that isnt 'good' or 'simplicial' ???
		if (!facet->good || !facet->simplicial)
			continue;
	
		vertexT *vertex, **vertexp;
		int nVertices = qh_setsize(facet->vertices);

		jint tmp[nVertices];
		int j;
		jintArray iarr = (*env)->NewIntArray(env, nVertices);
		if (iarr == NULL) { return NULL; } /* out of memory error thrown */

		if(nVertices != 3)
			fprintf(stderr, "WARNING: Facet %i has %i vertices so isn't a triangle.\n", i, nVertices);

		//copy vertex indices to output array
		j=0;
		FOREACHvertex_(facet->vertices){
			tmp[j] = (jint)qh_pointid(vertex->point);
			j++;
		}

		//slap weird java arrays together
		(*env)->SetIntArrayRegion(env, iarr, 0, nVertices, tmp);
		(*env)->SetObjectArrayElement(env, triangles, i, iarr);
		(*env)->DeleteLocalRef(env, iarr);

		i++;
	}


	qh_freeqhull(False); //free some stuff, but ismalloc=0 tells it not to mess with points3D here

	//free memory with leak checks (I'm vaguely impressed!)
	int curlong, totlong;
	qh_memfreeshort(&curlong, &totlong);
	if (curlong || totlong)
		fprintf(stderr, "qhull internal warning (main): did not free %d bytes of long memory(%d pieces)\n",
				totlong, curlong);

	free(points3D); //free the points3D array (which we allocated)

	return triangles;

}

