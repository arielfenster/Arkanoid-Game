package gameobjects;

import java.util.List;

/**
 * The program is used to identify if a line object will intersect another object.
 * It calculates the intersection point in different situations by calculating the line's linear equation and checking
 * if that line crosses the other object.
 *
 * @author Ariel Fenster
 */
public class Line {
    //members
    private Point start;
    private Point end;
    private double slope;

    /**
     * Constructor #1 for the Line class - using two declared points.
     *
     * @param start - the starting point of the line
     * @param end   - the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Function name: intersectionWith.
     * Checks if there is an intersection between two lines.
     *
     * @param other - the line which we check if there is an intersection which
     * @return interPoint - the intersection point (if they intersect), null otherwise
     */
    public Point intersectionWith(Line other) {
        // setting the slopes for each of the lines for future use
        this.setSlope(this.start, this.end);
        other.setSlope(other.start, other.end);

        // check if the lines are parallel. if true we treat them as having no intersections
        if (this.getSlope() == other.getSlope()) {
            return null;
        }
        Point interPoint;

        /*
         * special case where a line (or both) parallel to the Y axis (the line is in the form of 'x=a'):
         * 1. both are parallel - no intersection, return null.
         * 2. one of them is parallel - use a specific function to get the point
         * 3. none of them are parallel - get the point from a different function
         */
        if (this.isParallelToY()) {
            if (other.isParallelToY()) {
                // if both are parallel
                return null;
                // if only 'this' line is parallel
            } else {
                interPoint = createInterPointWhenParallel(this, other);
            }
            // if we didn't enter the 'if' above, we know that 'this' line isn't parallel so we check only 'other' line
        } else if (other.isParallelToY()) {
            interPoint = createInterPointWhenParallel(other, this);
            // if both of the lines aren't parallel to Y axis
        } else {
            interPoint = createIntersectionPoint(this, other);
        } // end of special case

        // if the intersection point isn't on one of the lines
        if (!this.isPointOnSegment(interPoint) || !other.isPointOnSegment(interPoint)) {
            return null;
        }
        // return a valid intersection point
        return interPoint;
    }

    /**
     * Function name: createInterPointWhenParallel.
     * The function calculates the intersection point when one of the lines is parallel to the Y axis
     *
     * @param paraLine - the line that is parallel to the Y axis
     * @param l2       - the other one (that isn't parallel)
     * @return intersection point between the lines
     */
    public Point createInterPointWhenParallel(Line paraLine, Line l2) {
        double xInter, yInter;
        double m2, n2;
        // calculating l2's "equation"
        m2 = l2.getSlope();
        n2 = crossWithYAxis(m2, l2.start);

        // assigning the x coordinate of the intersection to the equation of l2 to get the y value of the intersection
        xInter = paraLine.start.getX();
        yInter = (m2 * xInter) + n2;

        return (new Point((xInter), (yInter)));
    }

    /**
     * Function name: createIntersectionPoint
     * The function calculates an intersection point between two lines who aren't parallel to the Y axis. This is
     * considered a "normal" case.
     *
     * @param l1 - a line
     * @param l2 - another line
     * @return intersection point
     */
    public Point createIntersectionPoint(Line l1, Line l2) {
        // special case: if the one of the lines is actually a point, we return that point as the intersection
        if (l1.isLineAPoint()) {
            return l1.start;
        } else if (l2.isLineAPoint()) {
            return l2.start;
        }

        // variables used to define the lines' equations: y=mx+n
        double m1, n1, m2, n2;

        // x and y coordinates of the intersection point
        double xInter, yInter;

        // calculating the slopes
        m1 = l1.getSlope();
        m2 = l2.getSlope();
        // calculating intersections with Y axis
        n1 = (crossWithYAxis(m1, l1.start));
        n2 = (crossWithYAxis(m2, l2.start));

        /*
         * assigning the intersection point's values - derived from two linear equations and extracting the x and y
         * values of the intersection: y1=m1*x+n1, y2=m2*x+n2.
         */
        xInter = (n2 - n1) / (m1 - m2);
        yInter = (m1 * ((n2 - n1) / (m1 - m2)) + n1);

        // create a new point and return it
        return (new Point((xInter), (yInter)));
    }

    /**
     * Function name: crossWithYAxis.
     * The function calculates the y value where a line would cross the Y axis (the 'n' in the equation y=mx+n)
     *
     * @param incline - the slope of a line
     * @param p       - a point on the line
     * @return the y value of intersecting the Y axis
     */
    public double crossWithYAxis(double incline, Point p) {
        return (p.getY() - (incline * p.getX()));
    }

    /**
     * Function name: isPointOnSegment.
     * Determines if a given point is on a segment of a line by checking if the distance from that point to each of the
     * edge points of the segment is longer than the length of the segment itself.
     *
     * @param p - the point we check if it's on the segment
     * @return true if the point is on the line, false otherwise
     */
    public boolean isPointOnSegment(Point p) {
        // calculating the distance from 'p' to each of the edge points, and choosing the longer distance of the two
        double maxDist = Math.max(p.distance(this.start), p.distance(this.end));

        if (maxDist > this.length()) {
            return false;
        }
        return true;
    }

    /**
     * Function name: isLineAPoint.
     * Determines if a line is actually a point - its start and end points are the same
     *
     * @return true if the line is actually a point, false otherwise
     */
    public boolean isLineAPoint() {
        return (this.start.equals(this.end));
    }

    /**
     * Function name: isParallelToY.
     * Determines if a line is parallel to the Y axis
     *
     * @return true if the line is parallel, false otherwise
     */
    public boolean isParallelToY() {
        // if it is a vertical line - both its edges' x values are the same
        return (this.start.getX() == this.end.getX());
    }

    /**
     * Function name: closestIntersectionToStartOfLine.
     * The function receives a (maybe empty) list of intersection points between this line and a given rectangle
     * object and returns the point that is nearest to the start of the line
     *
     * @param rect - the object we check if the line is intersecting with
     * @return if the list is empty - return null, otherwise return the point that is nearest to the start of the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // getting the list of points
        List<Point> listOfInterPoints = rect.intersectionPoints(this);

        // if there are no intersection points
        if (listOfInterPoints.size() == 0) {
            return null;

            // if the size is 1 - return the only point
        } else if (listOfInterPoints.size() == 1) {
            return listOfInterPoints.get(0);

            // if the size is 2 - there are two intersection points
        } else {
            Point p1 = listOfInterPoints.get(0);
            Point p2 = listOfInterPoints.get(1);
            // return the point that is closer to the start of the line
            if (p1.distance(this.start()) <= p2.distance(this.start())) {
                return p1;
            }
            return p2;
        }
    }

    /**
     * Function name: start.
     * Returns the start point of the line.
     *
     * @return start of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Function name: middle.
     * Calculates the middle point between the start and end points on the line and return it.
     *
     * @return the center point of the line
     */
    public Point middle() {
        double midX;
        double midY;

        // calculating the middle values of the x and y coordinates
        midX = (this.start.getX() + this.end.getX()) / 2;
        midY = (this.start.getY() + this.end.getY()) / 2;

        // create a new point with the values above and return it
        return (new Point(midX, midY));
    }

    /**
     * Function name: end.
     * Returns the end point of the line.
     *
     * @return end of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Function name: getSlope.
     * Return the slope of the line
     *
     * @return slope of the line
     */
    public double getSlope() {
        return this.slope;
    }

    /**
     * Function name: setSlope.
     * Calculates the slope of a line between two points and assigns the class's member as the calculated value
     *
     * @param p1 - a point on the line
     * @param p2 - another point on the line
     */
    public void setSlope(Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        // slope is change in y over change in x
        this.slope = (dy / dx);
    }

    /**
     * Function name: length.
     * Calculates the length of a line.
     *
     * @return the length of the line - distance between the start and end points
     */
    public double length() {
        return (this.start.distance(this.end));
    }
}