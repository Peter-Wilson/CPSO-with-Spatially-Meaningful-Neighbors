package org.jzy3d.plot3d.builder.delaunay.jdt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import org.jzy3d.plot3d.builder.delaunay.Triangulation;

public class Delaunay_Triangulation
  implements Triangulation
{
  private Point_dt firstP;
  private Point_dt lastP;
  private static int flipCount = 0;
  private boolean allCollinear;
  private Triangle_dt firstT;
  private Triangle_dt lastT;
  private Triangle_dt currT;
  private Triangle_dt startTriangle;
  public Triangle_dt startTriangleHull;
  private int nPoints = 0;
  private Set<Point_dt> _vertices;
  private Vector<Triangle_dt> _triangles;
  private Vector<Triangle_dt> deletedTriangles;
  private Vector<Triangle_dt> addedTriangles;
  private int _modCount = 0;
  private final static int breakCount = 100;
  private int _modCount2 = 0;
  private Point_dt _bb_min;
  private Point_dt _bb_max;
  private GridIndex gridIndex = null;
  
  public Delaunay_Triangulation()
  {
    this(new Point_dt[0]);
  }
  
  public Delaunay_Triangulation(Point_dt[] ps)
  {
    this._modCount = 0;
    this._modCount2 = 0;
    this._bb_min = null;
    this._bb_max = null;
    this._vertices = new TreeSet(Point_dt.getComparator());
    this._triangles = new Vector();
    this.deletedTriangles = null;
    this.addedTriangles = new Vector();
    this.allCollinear = true;
    for (int i = 0; (ps != null) && (i < ps.length) && (ps[i] != null); i++) {
      insertPoint(ps[i]);
    }
  }
  
  public Delaunay_Triangulation(String file)
    throws Exception
  {
    this(read_file(file));
  }
  
  public int size()
  {
    if (this._vertices == null) {
      return 0;
    }
    return this._vertices.size();
  }
  
  public int trianglesSize()
  {
    initTriangles();
    return this._triangles.size();
  }
  
  public int getModeCounter()
  {
    return this._modCount;
  }
  
  public void insertPoint(Point_dt p)
  {
    if (this._vertices.contains(p)) {
      return;
    }
    flipCount = 0;
    this._modCount += 1;
    updateBoundingBox(p);
    this._vertices.add(p);
    Triangle_dt t = insertPointSimple(p);
    if (t == null) {
      return;
    }
    Triangle_dt tt = t;
    this.currT = t;
    do
    {
      flip(tt, this._modCount);
      tt = tt.canext;
    } while ((tt != t) && (!tt.halfplane));
    if (this.gridIndex != null) {
      this.gridIndex.updateIndex(getLastUpdatedTriangles());
    }
  }
  
  public void deletePoint(Point_dt pointToDelete)
  {
    Vector<Point_dt> pointsVec = findConnectedVertices(pointToDelete, true);
    if (pointsVec == null) {
      return;
    }
    Point_dt p;
    while (pointsVec.size() >= 3)
    {
      Triangle_dt triangle = findTriangle(pointsVec, pointToDelete);
      this.addedTriangles.add(triangle);
      
      p = findDiagonal(triangle, pointToDelete);
      for (Point_dt tmpP : pointsVec) {
        if (tmpP.equals(p))
        {
          pointsVec.removeElement(tmpP);
          break;
        }
      }
    }
    deleteUpdate(pointToDelete);
    for (Triangle_dt t : this.deletedTriangles) {
      if (t == this.startTriangle)
      {
        this.startTriangle = ((Triangle_dt)this.addedTriangles.elementAt(0));
        break;
      }
    }
    this._triangles.removeAll(this.deletedTriangles);
    this._triangles.addAll(this.addedTriangles);
    this._vertices.remove(pointToDelete);
    this.nPoints = (this.nPoints + this.addedTriangles.size() - this.deletedTriangles.size());
    this.addedTriangles.removeAllElements();
    this.deletedTriangles.removeAllElements();
  }
  
  public Point_dt findClosePoint(Point_dt pointToDelete)
  {
    Triangle_dt triangle = find(pointToDelete);
    Point_dt p1 = triangle.p1();
    Point_dt p2 = triangle.p2();
    double d1 = p1.distance(pointToDelete);
    double d2 = p2.distance(pointToDelete);
    if (triangle.isHalfplane())
    {
      if (d1 <= d2) {
        return p1;
      }
      return p2;
    }
    Point_dt p3 = triangle.p3();
    
    double d3 = p3.distance(pointToDelete);
    if ((d1 <= d2) && (d1 <= d3)) {
      return p1;
    }
    if ((d2 <= d1) && (d2 <= d3)) {
      return p2;
    }
    return p3;
  }
  
  private void deleteUpdate(Point_dt pointToDelete)
  {
      
    Triangle_dt addedTriangle1;
    for (Iterator i$ = this.addedTriangles.iterator(); i$.hasNext();)
    {
      addedTriangle1 = (Triangle_dt)i$.next();
      for (Triangle_dt deletedTriangle : this.deletedTriangles) {
        if (shareSegment(addedTriangle1, deletedTriangle)) {
          updateNeighbor(addedTriangle1, deletedTriangle, pointToDelete);
        }
      }
    }
    for (Iterator i$ = this.addedTriangles.iterator(); i$.hasNext();)
    {
      addedTriangle1 = (Triangle_dt)i$.next();
      for (Triangle_dt addedTriangle2 : this.addedTriangles) {
        if ((addedTriangle1 != addedTriangle2) && (shareSegment(addedTriangle1, addedTriangle2))) {
          updateNeighbor(addedTriangle1, addedTriangle2);
        }
      }
    }
    if (this.gridIndex != null) {
      this.gridIndex.updateIndex(this.addedTriangles.iterator());
    }
  }
  
  private boolean shareSegment(Triangle_dt t1, Triangle_dt t2)
  {
    int counter = 0;
    Point_dt t1P1 = t1.p1();
    Point_dt t1P2 = t1.p2();
    Point_dt t1P3 = t1.p3();
    Point_dt t2P1 = t2.p1();
    Point_dt t2P2 = t2.p2();
    Point_dt t2P3 = t2.p3();
    if (t1P1.equals(t2P1)) {
      counter++;
    }
    if (t1P1.equals(t2P2)) {
      counter++;
    }
    if (t1P1.equals(t2P3)) {
      counter++;
    }
    if (t1P2.equals(t2P1)) {
      counter++;
    }
    if (t1P2.equals(t2P2)) {
      counter++;
    }
    if (t1P2.equals(t2P3)) {
      counter++;
    }
    if (t1P3.equals(t2P1)) {
      counter++;
    }
    if (t1P3.equals(t2P2)) {
      counter++;
    }
    if (t1P3.equals(t2P3)) {
      counter++;
    }
    if (counter >= 2) {
      return true;
    }
    return false;
  }
  
  private void updateNeighbor(Triangle_dt addedTriangle, Triangle_dt deletedTriangle, Point_dt pointToDelete)
  {
    Point_dt delA = deletedTriangle.p1();
    Point_dt delB = deletedTriangle.p2();
    Point_dt delC = deletedTriangle.p3();
    Point_dt addA = addedTriangle.p1();
    Point_dt addB = addedTriangle.p2();
    Point_dt addC = addedTriangle.p3();
    if (pointToDelete.equals(delA))
    {
      deletedTriangle.next_23().switchneighbors(deletedTriangle, addedTriangle);
      if (((addA.equals(delB)) && (addB.equals(delC))) || ((addB.equals(delB)) && (addA.equals(delC)))) {
        addedTriangle.abnext = deletedTriangle.next_23();
      } else if (((addA.equals(delB)) && (addC.equals(delC))) || ((addC.equals(delB)) && (addA.equals(delC)))) {
        addedTriangle.canext = deletedTriangle.next_23();
      } else {
        addedTriangle.bcnext = deletedTriangle.next_23();
      }
    }
    else if (pointToDelete.equals(delB))
    {
      deletedTriangle.next_31().switchneighbors(deletedTriangle, addedTriangle);
      if (((addA.equals(delA)) && (addB.equals(delC))) || ((addB.equals(delA)) && (addA.equals(delC)))) {
        addedTriangle.abnext = deletedTriangle.next_31();
      } else if (((addA.equals(delA)) && (addC.equals(delC))) || ((addC.equals(delA)) && (addA.equals(delC)))) {
        addedTriangle.canext = deletedTriangle.next_31();
      } else {
        addedTriangle.bcnext = deletedTriangle.next_31();
      }
    }
    else
    {
      deletedTriangle.next_12().switchneighbors(deletedTriangle, addedTriangle);
      if (((addA.equals(delA)) && (addB.equals(delB))) || ((addB.equals(delA)) && (addA.equals(delB)))) {
        addedTriangle.abnext = deletedTriangle.next_12();
      } else if (((addA.equals(delA)) && (addC.equals(delB))) || ((addC.equals(delA)) && (addA.equals(delB)))) {
        addedTriangle.canext = deletedTriangle.next_12();
      } else {
        addedTriangle.bcnext = deletedTriangle.next_12();
      }
    }
  }
  
  private void updateNeighbor(Triangle_dt addedTriangle1, Triangle_dt addedTriangle2)
  {
    Point_dt A1 = addedTriangle1.p1();
    Point_dt B1 = addedTriangle1.p2();
    Point_dt C1 = addedTriangle1.p3();
    Point_dt A2 = addedTriangle2.p1();
    Point_dt B2 = addedTriangle2.p2();
    Point_dt C2 = addedTriangle2.p3();
    if (A1.equals(A2))
    {
      if (B1.equals(B2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else if (B1.equals(C2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      else if (C1.equals(B2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
    }
    else if (A1.equals(B2))
    {
      if (B1.equals(A2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else if (B1.equals(C2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
      else if (C1.equals(A2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
    }
    else if (A1.equals(C2))
    {
      if (B1.equals(A2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      if (B1.equals(B2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
      if (C1.equals(A2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      else
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
    }
    else if (B1.equals(A2))
    {
      if (A1.equals(B2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else if (A1.equals(C2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      else if (C1.equals(B2))
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
    }
    else if (B1.equals(B2))
    {
      if (A1.equals(A2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else if (A1.equals(C2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
      else if (C1.equals(A2))
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
    }
    else if (B1.equals(C2))
    {
      if (A1.equals(A2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      if (A1.equals(B2))
      {
        addedTriangle1.abnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
      if (C1.equals(A2))
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      else
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
    }
    else if (C1.equals(A2))
    {
      if (A1.equals(B2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else if (A1.equals(C2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      else if (B1.equals(B2))
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
    }
    else if (C1.equals(B2))
    {
      if (A1.equals(A2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else if (A1.equals(C2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
      else if (B1.equals(A2))
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.abnext = addedTriangle1;
      }
      else
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
    }
    else if (C1.equals(C2))
    {
      if (A1.equals(A2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      if (A1.equals(B2))
      {
        addedTriangle1.canext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
      if (B1.equals(A2))
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.canext = addedTriangle1;
      }
      else
      {
        addedTriangle1.bcnext = addedTriangle2;
        addedTriangle2.bcnext = addedTriangle1;
      }
    }
  }
  
  private Point_dt findDiagonal(Triangle_dt triangle, Point_dt point)
  {
    Point_dt p1 = triangle.p1();
    Point_dt p2 = triangle.p2();
    Point_dt p3 = triangle.p3();
    if ((p1.pointLineTest(point, p3) == 1) && (p2.pointLineTest(point, p3) == 2)) {
      return p3;
    }
    if ((p3.pointLineTest(point, p2) == 1) && (p1.pointLineTest(point, p2) == 2)) {
      return p2;
    }
    if ((p2.pointLineTest(point, p1) == 1) && (p3.pointLineTest(point, p1) == 2)) {
      return p1;
    }
    return null;
  }
  
  public Point_dt[] calcVoronoiCell(Triangle_dt triangle, Point_dt p)
  {
    if (!triangle.isHalfplane())
    {
      Vector<Triangle_dt> neighbors = findTriangleNeighborhood(triangle, p);
      
      Iterator<Triangle_dt> itn = neighbors.iterator();
      Point_dt[] vertices = new Point_dt[neighbors.size()];
      
      int index = 0;
      while (itn.hasNext())
      {
        Triangle_dt tmp = (Triangle_dt)itn.next();
        vertices[(index++)] = tmp.circumcircle().Center();
      }
      return vertices;
    }
    Triangle_dt halfplane = triangle;
    
    Point_dt third = null;
    
    Triangle_dt neighbor = null;
    if (!halfplane.next_12().isHalfplane()) {
      neighbor = halfplane.next_12();
    } else if (!halfplane.next_23().isHalfplane()) {
      neighbor = halfplane.next_23();
    } else if (!halfplane.next_23().isHalfplane()) {
      neighbor = halfplane.next_31();
    }
    if ((!neighbor.p1().equals(halfplane.p1())) && (!neighbor.p1().equals(halfplane.p2()))) {
      third = neighbor.p1();
    }
    if ((!neighbor.p2().equals(halfplane.p1())) && (!neighbor.p2().equals(halfplane.p2()))) {
      third = neighbor.p2();
    }
    if ((!neighbor.p3().equals(halfplane.p1())) && (!neighbor.p3().equals(halfplane.p2()))) {
      third = neighbor.p3();
    }
    double halfplane_delta = (halfplane.p1().y() - halfplane.p2().y()) / (halfplane.p1().x() - halfplane.p2().x());
    
    double perp_delta = 1.0D / halfplane_delta * -1.0D;
    
    double y_orient = halfplane_delta * (third.x() - halfplane.p1().x()) + halfplane.p1().y();
    
    boolean above = true;
    if (y_orient > third.y()) {
      above = false;
    }
    double sign = 1.0D;
    if (((perp_delta < 0.0D) && (!above)) || ((perp_delta > 0.0D) && (above))) {
      sign = -1.0D;
    }
    Point_dt circumcircle = neighbor.circumcircle().Center();
    double x_cell_line = circumcircle.x() + 500.0D * sign;
    double y_cell_line = perp_delta * (x_cell_line - circumcircle.x()) + circumcircle.y();
    
    Point_dt[] result = new Point_dt[2];
    result[0] = circumcircle;
    result[1] = new Point_dt(x_cell_line, y_cell_line);
    
    return result;
  }
  
  public Iterator<Triangle_dt> getLastUpdatedTriangles()
  {
    Vector<Triangle_dt> tmp = new Vector();
    if (trianglesSize() > 1)
    {
      Triangle_dt t = this.currT;
      allTriangles(t, tmp, this._modCount);
    }
    return tmp.iterator();
  }
  
  private void allTriangles(Triangle_dt curr, Vector<Triangle_dt> front, int mc)
  {
    if ((curr != null) && (curr._mc == mc) && (!front.contains(curr)))
    {
      front.add(curr);
      allTriangles(curr.abnext, front, mc);
      allTriangles(curr.bcnext, front, mc);
      allTriangles(curr.canext, front, mc);
    }
  }
  
  private Triangle_dt insertPointSimple(Point_dt p)
  {
    this.nPoints += 1;
    if (!this.allCollinear)
    {
      Triangle_dt t = find(this.startTriangle, p);
      if (t.halfplane) {
        this.startTriangle = extendOutside(t, p);
      } else {
        this.startTriangle = extendInside(t, p);
      }
      return this.startTriangle;
    }
    if (this.nPoints == 1)
    {
      this.firstP = p;
      return null;
    }
    if (this.nPoints == 2)
    {
      startTriangulation(this.firstP, p);
      return null;
    }
    switch (p.pointLineTest(this.firstP, this.lastP))
    {
    case 1: 
      this.startTriangle = extendOutside(this.firstT.abnext, p);
      this.allCollinear = false;
      break;
    case 2: 
      this.startTriangle = extendOutside(this.firstT, p);
      this.allCollinear = false;
      break;
    case 0: 
      insertCollinear(p, 0);
      break;
    case 3: 
      insertCollinear(p, 3);
      break;
    case 4: 
      insertCollinear(p, 4);
    }
    return null;
  }
  
  private void insertCollinear(Point_dt p, int res)
  {
      if(++flipCount >= breakCount) 
          throw new IllegalStateException();
    Triangle_dt t;
    Triangle_dt tp;
    switch (res)
    {
    case 3: 
      t = new Triangle_dt(this.firstP, p);
      tp = new Triangle_dt(p, this.firstP);
      t.abnext = tp;
      tp.abnext = t;
      t.bcnext = tp;
      tp.canext = t;
      t.canext = this.firstT;
      this.firstT.bcnext = t;
      tp.bcnext = this.firstT.abnext;
      this.firstT.abnext.canext = tp;
      this.firstT = t;
      this.firstP = p;
      break;
    case 4: 
      t = new Triangle_dt(p, this.lastP);
      tp = new Triangle_dt(this.lastP, p);
      t.abnext = tp;
      tp.abnext = t;
      t.bcnext = this.lastT;
      this.lastT.canext = t;
      t.canext = tp;
      tp.bcnext = t;
      tp.canext = this.lastT.abnext;
      this.lastT.abnext.bcnext = tp;
      this.lastT = t;
      this.lastP = p;
      break;
    case 0: 
      Triangle_dt u = this.firstT;
      int loops = 0;
      while (p.isGreater(u.a)) {
          if(++loops == 100) throw new IllegalStateException();
        u = u.canext;
      }
      t = new Triangle_dt(p, u.b);
      tp = new Triangle_dt(u.b, p);
      u.b = p;
      u.abnext.a = p;
      t.abnext = tp;
      tp.abnext = t;
      t.bcnext = u.bcnext;
      u.bcnext.canext = t;
      t.canext = u;
      u.bcnext = t;
      tp.canext = u.abnext.canext;
      u.abnext.canext.bcnext = tp;
      tp.bcnext = u.abnext;
      u.abnext.canext = tp;
      if (this.firstT == u) {
        this.firstT = t;
      }
      break;
    }
  }
  
  private void startTriangulation(Point_dt p1, Point_dt p2)
  {
    Point_dt pb;
    Point_dt ps;
    if (p1.isLess(p2))
    {
      ps = p1;
      pb = p2;
    }
    else
    {
      ps = p2;
      pb = p1;
    }
    this.firstT = new Triangle_dt(pb, ps);
    this.lastT = this.firstT;
    Triangle_dt t = new Triangle_dt(ps, pb);
    this.firstT.abnext = t;
    t.abnext = this.firstT;
    this.firstT.bcnext = t;
    t.canext = this.firstT;
    this.firstT.canext = t;
    t.bcnext = this.firstT;
    this.firstP = this.firstT.b;
    this.lastP = this.lastT.a;
    this.startTriangleHull = this.firstT;
  }
  
  private Triangle_dt extendInside(Triangle_dt t, Point_dt p)
  {
    Triangle_dt h1 = treatDegeneracyInside(t, p);
    if (h1 != null) {
      return h1;
    }
    h1 = new Triangle_dt(t.c, t.a, p);
    Triangle_dt h2 = new Triangle_dt(t.b, t.c, p);
    t.c = p;
    t.circumcircle();
    h1.abnext = t.canext;
    h1.bcnext = t;
    h1.canext = h2;
    h2.abnext = t.bcnext;
    h2.bcnext = h1;
    h2.canext = t;
    h1.abnext.switchneighbors(t, h1);
    h2.abnext.switchneighbors(t, h2);
    t.bcnext = h2;
    t.canext = h1;
    return t;
  }
  
  private Triangle_dt treatDegeneracyInside(Triangle_dt t, Point_dt p)
  {
    if ((t.abnext.halfplane) && (p.pointLineTest(t.b, t.a) == 0)) {
      return extendOutside(t.abnext, p);
    }
    if ((t.bcnext.halfplane) && (p.pointLineTest(t.c, t.b) == 0)) {
      return extendOutside(t.bcnext, p);
    }
    if ((t.canext.halfplane) && (p.pointLineTest(t.a, t.c) == 0)) {
      return extendOutside(t.canext, p);
    }
    return null;
  }
  
  private Triangle_dt extendOutside(Triangle_dt t, Point_dt p)
  {
    if (p.pointLineTest(t.a, t.b) == 0)
    {
      Triangle_dt dg = new Triangle_dt(t.a, t.b, p);
      Triangle_dt hp = new Triangle_dt(p, t.b);
      t.b = p;
      dg.abnext = t.abnext;
      dg.abnext.switchneighbors(t, dg);
      dg.bcnext = hp;
      hp.abnext = dg;
      dg.canext = t;
      t.abnext = dg;
      hp.bcnext = t.bcnext;
      hp.bcnext.canext = hp;
      hp.canext = t;
      t.bcnext = hp;
      return dg;
    }
    Triangle_dt ccT = extendcounterclock(t, p);
    Triangle_dt cT = extendclock(t, p);
    ccT.bcnext = cT;
    cT.canext = ccT;
    this.startTriangleHull = cT;
    return cT.abnext;
  }
  
  private Triangle_dt extendcounterclock(Triangle_dt t, Point_dt p)
  {
      if(++flipCount >= breakCount)
      {
          throw new IllegalStateException("Insertion of Point resulting in too many counterclocks. Insertion aborted");
      }
    t.halfplane = false;
    t.c = p;
    t.circumcircle();
    
    
    Triangle_dt tca = t.canext;
    if (p.pointLineTest(tca.a, tca.b) >= 2)
    {
      Triangle_dt nT = new Triangle_dt(t.a, p);
      nT.abnext = t;
      t.canext = nT;
      nT.canext = tca;
      tca.bcnext = nT;
      return nT;
    }
    return extendcounterclock(tca, p);
  }
  
  private Triangle_dt extendclock(Triangle_dt t, Point_dt p)
  {
      if(++flipCount >= breakCount)
      {
          throw new IllegalStateException("Insertion of Point resulting in too many clocks. Insertion aborted");
      }
    t.halfplane = false;
    t.c = p;
    t.circumcircle();
    
    Triangle_dt tbc = t.bcnext;
    if (p.pointLineTest(tbc.a, tbc.b) >= 2)
    {
      Triangle_dt nT = new Triangle_dt(p, t.b);
      nT.abnext = t;
      t.bcnext = nT;
      nT.bcnext = tbc;
      tbc.canext = nT;
      return nT;
    }
    return extendclock(tbc, p);
  }
  
  private void flip(Triangle_dt t, int mc)
  {
      if(++flipCount >= breakCount)
      {
          throw new IllegalStateException("Insertion of Point resulting in too many flips. Insertion aborted");
      }
      
    //System.out.println("flip count:"+flipCount);
    Triangle_dt u = t.abnext;    
    Triangle_dt v;
    t._mc = mc;
    if ((u.halfplane) || (!u.circumcircle_contains(t.c))) {
      return;
    }
    if (t.a == u.a)
    {
      v = new Triangle_dt(u.b, t.b, t.c);
      v.abnext = u.bcnext;
      t.abnext = u.abnext;
    }
    else if (t.a == u.b)
    {
      v = new Triangle_dt(u.c, t.b, t.c);
      v.abnext = u.canext;
      t.abnext = u.bcnext;
    }
    else if (t.a == u.c)
    {
      v = new Triangle_dt(u.a, t.b, t.c);
      v.abnext = u.abnext;
      t.abnext = u.canext;
    }
    else
    {
      throw new RuntimeException("Error in flip.");
    }
    v._mc = mc;
    v.bcnext = t.bcnext;
    v.abnext.switchneighbors(u, v);
    v.bcnext.switchneighbors(t, v);
    t.bcnext = v;
    v.canext = t;
    t.b = v.a;
    t.abnext.switchneighbors(u, t);
    t.circumcircle();
    
    this.currT = v;
    flip(t, mc);
    flip(v, mc);
  }
  
  public void write_tsin(String tsinFile)
    throws Exception
  {
    FileWriter fw = new FileWriter(tsinFile);
    PrintWriter os = new PrintWriter(fw);
    
    int len = this._vertices.size();
    os.println(len);
    Iterator<Point_dt> it = this._vertices.iterator();
    while (it.hasNext()) {
      os.println(((Point_dt)it.next()).toFile());
    }
    os.close();
    fw.close();
  }
  
  public void write_smf(String smfFile)
    throws Exception
  {
    int len = this._vertices.size();
    Point_dt[] ans = new Point_dt[len];
    Iterator<Point_dt> it = this._vertices.iterator();
    Comparator<Point_dt> comp = Point_dt.getComparator();
    for (int i = 0; i < len; i++) {
      ans[i] = ((Point_dt)it.next());
    }
    Arrays.sort(ans, comp);
    
    FileWriter fw = new FileWriter(smfFile);
    PrintWriter os = new PrintWriter(fw);
    
    os.println("begin");
    for (int i = 0; i < len; i++) {
      os.println("v " + ans[i].toFile());
    }
    int t = 0;int i1 = -1;int i2 = -1;int i3 = -1;
    for (Iterator<Triangle_dt> dt = trianglesIterator(); dt.hasNext();)
    {
      Triangle_dt curr = (Triangle_dt)dt.next();
      t++;
      if (!curr.halfplane)
      {
        i1 = Arrays.binarySearch(ans, curr.a, comp);
        i2 = Arrays.binarySearch(ans, curr.b, comp);
        i3 = Arrays.binarySearch(ans, curr.c, comp);
        if (((i1 < 0 ? 1 : 0) | (i2 < 0 ? 1 : 0) | (i3 < 0 ? 1 : 0)) != 0) {
          throw new RuntimeException("wrong triangulation inner bug - cant write as an SMF file!");
        }
        os.println("f " + (i1 + 1) + " " + (i2 + 1) + " " + (i3 + 1));
      }
    }
    os.println("end");
    os.close();
    fw.close();
  }
  
  public int CH_size()
  {
    int ans = 0;
    Iterator<Point_dt> it = CH_vertices_Iterator();
    while (it.hasNext())
    {
      ans++;
      it.next();
    }
    return ans;
  }
  
  public void write_CH(String tsinFile)
    throws Exception
  {
    FileWriter fw = new FileWriter(tsinFile);
    PrintWriter os = new PrintWriter(fw);
    
    os.println(CH_size());
    Iterator<Point_dt> it = CH_vertices_Iterator();
    while (it.hasNext()) {
      os.println(((Point_dt)it.next()).toFileXY());
    }
    os.close();
    fw.close();
  }
  
  private static Point_dt[] read_file(String file)
    throws Exception
  {
    if ((file.substring(file.length() - 4).equals(".smf") | file.substring(file.length() - 4).equals(".SMF"))) {
      return read_smf(file);
    }
    return read_tsin(file);
  }
  
  private static Point_dt[] read_tsin(String tsinFile)
    throws Exception
  {
    FileReader fr = new FileReader(tsinFile);
    BufferedReader is = new BufferedReader(fr);
    String s = is.readLine();
    while (s.charAt(0) == '/') {
      s = is.readLine();
    }
    StringTokenizer st = new StringTokenizer(s);
    int numOfVer = new Integer(s).intValue();
    
    Point_dt[] ans = new Point_dt[numOfVer];
    for (int i = 0; i < numOfVer; i++)
    {
      st = new StringTokenizer(is.readLine());
      double d1 = new Double(st.nextToken()).doubleValue();
      double d2 = new Double(st.nextToken()).doubleValue();
      double d3 = new Double(st.nextToken()).doubleValue();
      ans[i] = new Point_dt((int)d1, (int)d2, d3);
    }
    return ans;
  }
  
  private static Point_dt[] read_smf(String smfFile)
    throws Exception
  {
    return read_smf(smfFile, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D);
  }
  
  private static Point_dt[] read_smf(String smfFile, double dx, double dy, double dz, double minX, double minY, double minZ)
    throws Exception
  {
    FileReader fr = new FileReader(smfFile);
    BufferedReader is = new BufferedReader(fr);
    String s = is.readLine();
    while (s.charAt(0) != 'v') {
      s = is.readLine();
    }
    Vector<Point_dt> vec = new Vector();
    Point_dt[] ans = null;
    while ((s != null) && (s.charAt(0) == 'v'))
    {
      StringTokenizer st = new StringTokenizer(s);
      st.nextToken();
      double d1 = new Double(st.nextToken()).doubleValue() * dx + minX;
      double d2 = new Double(st.nextToken()).doubleValue() * dy + minY;
      double d3 = new Double(st.nextToken()).doubleValue() * dz + minZ;
      vec.add(new Point_dt((int)d1, (int)d2, d3));
      s = is.readLine();
    }
    ans = new Point_dt[vec.size()];
    for (int i = 0; i < vec.size(); i++) {
      ans[i] = ((Point_dt)vec.elementAt(i));
    }
    return ans;
  }
  
  public Triangle_dt find(Point_dt p)
  {
    if(++flipCount >= breakCount) 
        throw new IllegalStateException("Too many search errors, aborting search");
    Triangle_dt searchTriangle = this.startTriangle;
    if (this.gridIndex != null)
    {
      Triangle_dt indexTriangle = this.gridIndex.findCellTriangleOf(p);
      if (indexTriangle != null) {
        searchTriangle = indexTriangle;
      }
    }
    return find(searchTriangle, p);
  }
  
  public Triangle_dt find(Point_dt p, Triangle_dt start)
  {
    if(++flipCount >= breakCount) 
        throw new IllegalStateException("Too many search errors, aborting search");
    if (start == null) {
      start = this.startTriangle;
    }
    Triangle_dt T = find(start, p);
    return T;
  }
  
  private static Triangle_dt find(Triangle_dt curr, Point_dt p)
  {
    if(++flipCount >= breakCount)  
        throw new IllegalStateException("Too many search errors, aborting search");
    if (p == null) {
      return null;
    }
    if (curr.halfplane)
    {
      Triangle_dt next_t = findnext2(p, curr);
      if ((next_t == null) || (next_t.halfplane)) {
        return curr;
      }
      curr = next_t;
    }
    for (;;)
    {
      Triangle_dt next_t = findnext1(p, curr);
      if (next_t == null) {
        return curr;
      }
      if (next_t.halfplane) {
        return next_t;
      }
      curr = next_t;
    }
  }
  
  private static Triangle_dt findnext1(Point_dt p, Triangle_dt v)
  {

      if(++flipCount >= breakCount)  
        throw new IllegalStateException("Too many search errors, aborting search");
    if ((p.pointLineTest(v.a, v.b) == 2) && (!v.abnext.halfplane)) {
      return v.abnext;
    }
    if ((p.pointLineTest(v.b, v.c) == 2) && (!v.bcnext.halfplane)) {
      return v.bcnext;
    }
    if ((p.pointLineTest(v.c, v.a) == 2) && (!v.canext.halfplane)) {
      return v.canext;
    }
    if (p.pointLineTest(v.a, v.b) == 2) {
      return v.abnext;
    }
    if (p.pointLineTest(v.b, v.c) == 2) {
      return v.bcnext;
    }
    if (p.pointLineTest(v.c, v.a) == 2) {
      return v.canext;
    }
    return null;
  }
  
  private static Triangle_dt findnext2(Point_dt p, Triangle_dt v)
  {
      if(++flipCount >= breakCount)  
        throw new IllegalStateException("Too many search errors, aborting search");
    if ((v.abnext != null) && (!v.abnext.halfplane)) {
      return v.abnext;
    }
    if ((v.bcnext != null) && (!v.bcnext.halfplane)) {
      return v.bcnext;
    }
    if ((v.canext != null) && (!v.canext.halfplane)) {
      return v.canext;
    }
    return null;
  }
  
  private Vector<Point_dt> findConnectedVertices(Point_dt point)
  {
    return findConnectedVertices(point, false);
  }
  
  private Vector<Point_dt> findConnectedVertices(Point_dt point, boolean saveTriangles)
  {
      if(++flipCount >= breakCount)  
        throw new IllegalStateException("Too many search errors, aborting search");
    Set<Point_dt> pointsSet = new HashSet();
    Vector<Point_dt> pointsVec = new Vector();
    Vector<Triangle_dt> triangles = null;
    
    Triangle_dt triangle = find(point);
    if (!triangle.isCorner(point))
    {
      System.err.println("findConnectedVertices: Could not find connected vertices since the first found triangle doesn't share the given point.");
      
      return null;
    }
    triangles = findTriangleNeighborhood(triangle, point);
    if (triangles == null)
    {
      System.err.println("Error: can't delete a point on the perimeter");
      return null;
    }
    if (saveTriangles) {
      this.deletedTriangles = triangles;
    }
    for (Triangle_dt tmpTriangle : triangles)
    {
      Point_dt point1 = tmpTriangle.p1();
      Point_dt point2 = tmpTriangle.p2();
      Point_dt point3 = tmpTriangle.p3();
      if ((point1.equals(point)) && (!pointsSet.contains(point2)))
      {
        pointsSet.add(point2);
        pointsVec.add(point2);
      }
      if ((point2.equals(point)) && (!pointsSet.contains(point3)))
      {
        pointsSet.add(point3);
        pointsVec.add(point3);
      }
      if ((point3.equals(point)) && (!pointsSet.contains(point1)))
      {
        pointsSet.add(point1);
        pointsVec.add(point1);
      }
    }
    return pointsVec;
  }
  
  private boolean onPerimeter(Vector<Triangle_dt> triangles)
  {
    for (Triangle_dt t : triangles) {
      if (t.isHalfplane()) {
        return true;
      }
    }
    return false;
  }
  
  public Vector<Triangle_dt> findTriangleNeighborhood(Triangle_dt firstTriangle, Point_dt point)
  {
      if(++flipCount >= breakCount)  
        throw new IllegalStateException("Too many search errors, aborting search");
    Vector<Triangle_dt> triangles = new Vector(30);
    triangles.add(firstTriangle);
    
    Triangle_dt prevTriangle = null;
    Triangle_dt currentTriangle = firstTriangle;
    Triangle_dt nextTriangle = currentTriangle.nextNeighbor(point, prevTriangle);
    while (nextTriangle != firstTriangle)
    {
      if (nextTriangle.isHalfplane()) {
        return null;
      }
      triangles.add(nextTriangle);
      prevTriangle = currentTriangle;
      currentTriangle = nextTriangle;
      nextTriangle = currentTriangle.nextNeighbor(point, prevTriangle);
    }
    return triangles;
  }
  
  private Triangle_dt findTriangle(Vector<Point_dt> pointsVec, Point_dt p)
  {
      if(++flipCount >= breakCount)  
        throw new IllegalStateException("Too many search errors, aborting search");
    Point_dt[] arrayPoints = new Point_dt[pointsVec.size()];
    pointsVec.toArray(arrayPoints);
    
    int size = arrayPoints.length;
    if (size < 3) {
      return null;
    }
    if (size == 3) {
      return new Triangle_dt(arrayPoints[0], arrayPoints[1], arrayPoints[2]);
    }
    for (int i = 0; i <= size - 1; i++)
    {
      Point_dt p1 = arrayPoints[i];
      int j = i + 1;
      int k = i + 2;
      if (j >= size)
      {
        j = 0;
        k = 1;
      }
      else if (k >= size)
      {
        k = 0;
      }
      Point_dt p2 = arrayPoints[j];
      Point_dt p3 = arrayPoints[k];
      
      Triangle_dt t = new Triangle_dt(p1, p2, p3);
      if ((calcDet(p1, p2, p3) >= 0.0D) && (!t.contains(p)) && 
        (!t.fallInsideCircumcircle(arrayPoints))) {
        return t;
      }
      if ((size == 4) && (calcDet(p1, p2, p3) >= 0.0D) && (!t.contains_BoundaryIsOutside(p))) {
        if (!t.fallInsideCircumcircle(arrayPoints)) {
          return t;
        }
      }
    }
    return null;
  }
  
  private double calcDet(Point_dt A, Point_dt B, Point_dt P)
  {
    return A.x() * (B.y() - P.y()) - A.y() * (B.x() - P.x()) + (B.x() * P.y() - B.y() * P.x());
  }
  
  public boolean contains(Point_dt p)
  {
    Triangle_dt tt = find(p);
    return !tt.halfplane;
  }
  
  public boolean contains(double x, double y)
  {
    return contains(new Point_dt(x, y));
  }
  
  public Point_dt z(Point_dt q)
  {
    Triangle_dt t = find(q);
    return t.z(q);
  }
  
  public double z(double x, double y)
  {
    Point_dt q = new Point_dt(x, y);
    Triangle_dt t = find(q);
    return t.z_value(q);
  }
  
  private void updateBoundingBox(Point_dt p)
  {
    double x = p.x();double y = p.y();double z = p.z();
    if (this._bb_min == null)
    {
      this._bb_min = new Point_dt(p);
      this._bb_max = new Point_dt(p);
    }
    else
    {
      if (x < this._bb_min.x()) {
        this._bb_min.x = x;
      } else if (x > this._bb_max.x()) {
        this._bb_max.x = x;
      }
      if (y < this._bb_min.y) {
        this._bb_min.y = y;
      } else if (y > this._bb_max.y()) {
        this._bb_max.y = y;
      }
      if (z < this._bb_min.z) {
        this._bb_min.z = z;
      } else if (z > this._bb_max.z()) {
        this._bb_max.z = z;
      }
    }
  }
  
  public BoundingBox getBoundingBox()
  {
    return new BoundingBox(this._bb_min, this._bb_max);
  }
  
  public Point_dt minBoundingBox()
  {
    return this._bb_min;
  }
  
  public Point_dt maxBoundingBox()
  {
    return this._bb_max;
  }
  
  public Iterator<Triangle_dt> trianglesIterator()
  {
    if (size() <= 2) {
      this._triangles = new Vector();
    }
    initTriangles();
    return this._triangles.iterator();
  }
  
  public Iterator<Point_dt> CH_vertices_Iterator()
  {
    Vector<Point_dt> ans = new Vector();
    Triangle_dt curr = this.startTriangleHull;
    boolean cont = true;
    double x0 = this._bb_min.x();double x1 = this._bb_max.x();
    double y0 = this._bb_min.y();double y1 = this._bb_max.y();
    while (cont)
    {
      boolean sx = (curr.p1().x() == x0) || (curr.p1().x() == x1);
      boolean sy = (curr.p1().y() == y0) || (curr.p1().y() == y1);
      if ((sx & sy | (!sx ? true : false) & (!sy ? true : false))) {
        ans.add(curr.p1());
      }
      if ((curr.bcnext != null) && (curr.bcnext.halfplane)) {
        curr = curr.bcnext;
      }
      if (curr == this.startTriangleHull) {
        cont = false;
      }
    }
    return ans.iterator();
  }
  
  public Iterator<Point_dt> verticesIterator()
  {
    return this._vertices.iterator();
  }
  
  private void initTriangles()
  {
    if (this._modCount == this._modCount2) {
      return;
    }
    if (size() > 2)
    {
      this._modCount2 = this._modCount;
      Vector<Triangle_dt> front = new Vector();
      this._triangles = new Vector();
      front.add(this.startTriangle);
      while (front.size() > 0)
      {
        Triangle_dt t = (Triangle_dt)front.remove(0);
        if (!t._mark)
        {
          t._mark = true;
          this._triangles.add(t);
          if ((t.abnext != null) && (!t.abnext._mark)) {
            front.add(t.abnext);
          }
          if ((t.bcnext != null) && (!t.bcnext._mark)) {
            front.add(t.bcnext);
          }
          if ((t.canext != null) && (!t.canext._mark)) {
            front.add(t.canext);
          }
        }
      }
      for (int i = 0; i < this._triangles.size(); i++) {
        ((Triangle_dt)this._triangles.elementAt(i))._mark = false;
      }
    }
  }
  
  public void IndexData(int xCellCount, int yCellCount)
  {
    this.gridIndex = new GridIndex(this, xCellCount, yCellCount);
  }
  
  public void RemoveIndex()
  {
    this.gridIndex = null;
  }
}
