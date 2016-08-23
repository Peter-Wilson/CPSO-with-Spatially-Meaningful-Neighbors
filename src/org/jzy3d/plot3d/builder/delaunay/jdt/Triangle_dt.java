/*
 * Decompiled with CFR 0_115.
 */
package org.jzy3d.plot3d.builder.delaunay.jdt;

import java.io.PrintStream;
import org.jzy3d.plot3d.builder.delaunay.jdt.BoundingBox;
import org.jzy3d.plot3d.builder.delaunay.jdt.Circle_dt;
import org.jzy3d.plot3d.builder.delaunay.jdt.Point_dt;

public class Triangle_dt {
    Point_dt a;
    Point_dt b;
    Point_dt c;
    Triangle_dt abnext;
    Triangle_dt bcnext;
    Triangle_dt canext;
    Circle_dt circum;
    int _mc = 0;
    boolean halfplane = false;
    boolean _mark = false;
    public static int _counter = 0;
    public static int _c2 = 0;

    public Triangle_dt(Point_dt A, Point_dt B, Point_dt C) {
        this.a = A;
        int res = C.pointLineTest(A, B);
        if (res <= 1 || res == 3 || res == 4) {
            this.b = B;
            this.c = C;
        } else {
            //System.out.println("Warning, ajTriangle(A,B,C) expects points in counterclockwise order.");
            //System.out.println("" + A + B + C);
            this.b = C;
            this.c = B;
        }
        this.circumcircle();
    }

    public Triangle_dt(Point_dt A, Point_dt B) {
        this.a = A;
        this.b = B;
        this.halfplane = true;
    }

    public boolean isHalfplane() {
        return this.halfplane;
    }

    public Point_dt p1() {
        return this.a;
    }

    public Point_dt p2() {
        return this.b;
    }

    public Point_dt p3() {
        return this.c;
    }

    public Triangle_dt next_12() {
        return this.abnext;
    }

    public Triangle_dt next_23() {
        return this.bcnext;
    }

    public Triangle_dt next_31() {
        return this.canext;
    }

    public BoundingBox getBoundingBox() {
        Point_dt lowerLeft = new Point_dt(Math.min(this.a.x(), Math.min(this.b.x(), this.c.x())), Math.min(this.a.y(), Math.min(this.b.y(), this.c.y())));
        Point_dt upperRight = new Point_dt(Math.max(this.a.x(), Math.max(this.b.x(), this.c.x())), Math.max(this.a.y(), Math.max(this.b.y(), this.c.y())));
        return new BoundingBox(lowerLeft, upperRight);
    }

    void switchneighbors(Triangle_dt Old, Triangle_dt New) {
        if (this.abnext == Old) {
            this.abnext = New;
        } else if (this.bcnext == Old) {
            this.bcnext = New;
        } else if (this.canext == Old) {
            this.canext = New;
        } else {
            //System.out.println("Error, switchneighbors can't find Old.");
        }
    }

    Triangle_dt neighbor(Point_dt p) {
        if (this.a == p) {
            return this.canext;
        }
        if (this.b == p) {
            return this.abnext;
        }
        if (this.c == p) {
            return this.bcnext;
        }
        //System.out.println("Error, neighbors can't find p: " + p);
        return null;
    }

    Triangle_dt nextNeighbor(Point_dt p, Triangle_dt prevTriangle) {
        Triangle_dt neighbor = null;
        if (this.a.equals(p)) {
            neighbor = this.canext;
        }
        if (this.b.equals(p)) {
            neighbor = this.abnext;
        }
        if (this.c.equals(p)) {
            neighbor = this.bcnext;
        }
        if (neighbor.equals(prevTriangle) || neighbor.isHalfplane()) {
            if (this.a.equals(p)) {
                neighbor = this.abnext;
            }
            if (this.b.equals(p)) {
                neighbor = this.bcnext;
            }
            if (this.c.equals(p)) {
                neighbor = this.canext;
            }
        }
        return neighbor;
    }

    Circle_dt circumcircle() {
        double u = ((this.a.x - this.b.x) * (this.a.x + this.b.x) + (this.a.y - this.b.y) * (this.a.y + this.b.y)) / 2.0;
        double v = ((this.b.x - this.c.x) * (this.b.x + this.c.x) + (this.b.y - this.c.y) * (this.b.y + this.c.y)) / 2.0;
        double den = (this.a.x - this.b.x) * (this.b.y - this.c.y) - (this.b.x - this.c.x) * (this.a.y - this.b.y);
        if (den == 0.0) {
            this.circum = new Circle_dt(this.a, Double.POSITIVE_INFINITY);
        } else {
            Point_dt cen = new Point_dt((u * (this.b.y - this.c.y) - v * (this.a.y - this.b.y)) / den, (v * (this.a.x - this.b.x) - u * (this.b.x - this.c.x)) / den);
            this.circum = new Circle_dt(cen, cen.distance2(this.a));
        }
        return this.circum;
    }

    boolean circumcircle_contains(Point_dt p) {
        return this.circum.Radius() > this.circum.Center().distance2(p);
    }

    public String toString() {
        String res = "";
        res = res + "A: " + this.a.toString() + " B: " + this.b.toString();
        if (!this.halfplane) {
            res = res + " C: " + this.c.toString();
        }
        return res;
    }

    public boolean contains(Point_dt p) {
        boolean ans = false;
        if (this.halfplane | p == null) {
            return false;
        }
        if (this.isCorner(p)) {
            return true;
        }
        int a12 = p.pointLineTest(this.a, this.b);
        int a23 = p.pointLineTest(this.b, this.c);
        int a31 = p.pointLineTest(this.c, this.a);
        if (a12 == 1 && a23 == 1 && a31 == 1 || a12 == 2 && a23 == 2 && a31 == 2 || a12 == 0 || a23 == 0 || a31 == 0) {
            ans = true;
        }
        return ans;
    }

    public boolean contains_BoundaryIsOutside(Point_dt p) {
        boolean ans = false;
        if (this.halfplane | p == null) {
            return false;
        }
        if (this.isCorner(p)) {
            return true;
        }
        int a12 = p.pointLineTest(this.a, this.b);
        int a23 = p.pointLineTest(this.b, this.c);
        int a31 = p.pointLineTest(this.c, this.a);
        if (a12 == 1 && a23 == 1 && a31 == 1 || a12 == 2 && a23 == 2 && a31 == 2) {
            ans = true;
        }
        return ans;
    }

    public boolean isCorner(Point_dt p) {
        return p.x == this.a.x & p.y == this.a.y | p.x == this.b.x & p.y == this.b.y | p.x == this.c.x & p.y == this.c.y;
    }

    public boolean fallInsideCircumcircle(Point_dt[] arrayPoints) {
        boolean isInside = false;
        Point_dt p1 = this.p1();
        Point_dt p2 = this.p2();
        Point_dt p3 = this.p3();
        for (int i = 0; !isInside && i < arrayPoints.length; ++i) {
            Point_dt p = arrayPoints[i];
            if (p.equals(p1) || p.equals(p2) || p.equals(p3)) continue;
            isInside = this.circumcircle_contains(p);
        }
        return isInside;
    }

    public double z_value(Point_dt q) {
        if (q == null || this.halfplane) {
            throw new RuntimeException("*** ERR wrong parameters, can't approximate the z value ..***: " + q);
        }
        if (q.x == this.a.x & q.y == this.a.y) {
            return this.a.z;
        }
        if (q.x == this.b.x & q.y == this.b.y) {
            return this.b.z;
        }
        if (q.x == this.c.x & q.y == this.c.y) {
            return this.c.z;
        }
        double X = 0.0;
        double x0 = q.x;
        double x1 = this.a.x;
        double x2 = this.b.x;
        double x3 = this.c.x;
        double Y = 0.0;
        double y0 = q.y;
        double y1 = this.a.y;
        double y2 = this.b.y;
        double y3 = this.c.y;
        double Z = 0.0;
        double m01 = 0.0;
        double k01 = 0.0;
        double m23 = 0.0;
        double k23 = 0.0;
        int flag01 = 0;
        if (x0 != x1) {
            m01 = (y0 - y1) / (x0 - x1);
            k01 = y0 - m01 * x0;
            if (m01 == 0.0) {
                flag01 = 1;
            }
        } else {
            flag01 = 2;
        }
        int flag23 = 0;
        if (x2 != x3) {
            m23 = (y2 - y3) / (x2 - x3);
            k23 = y2 - m23 * x2;
            if (m23 == 0.0) {
                flag23 = 1;
            }
        } else {
            flag23 = 2;
        }
        if (flag01 == 2) {
            X = x0;
            Y = m23 * X + k23;
        } else if (flag23 == 2) {
            X = x2;
            Y = m01 * X + k01;
        } else {
            X = (k23 - k01) / (m01 - m23);
            Y = m01 * X + k01;
        }
        double r = 0.0;
        r = flag23 == 2 ? (y2 - Y) / (y2 - y3) : (x2 - X) / (x2 - x3);
        Z = this.b.z + (this.c.z - this.b.z) * r;
        r = flag01 == 2 ? (y1 - y0) / (y1 - Y) : (x1 - x0) / (x1 - X);
        double qZ = this.a.z + (Z - this.a.z) * r;
        return qZ;
    }

    public double z(double x, double y) {
        return this.z_value(new Point_dt(x, y));
    }

    public Point_dt z(Point_dt q) {
        double z = this.z_value(q);
        return new Point_dt(q.x, q.y, z);
    }
}

