package gameobjects;

/**
 * The program defines the Point class and its methods:
 * can measure distance to another point and determine if the point is equal to another point.
 *
 * @author Ariel Fenster
 */
public class Point {
    // members
    private double x;
    private double y;

    /**
     * The constructor for the Point class.
     *
     * @param x - x coordinate of the point
     * @param y - y coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Function name: distance.
     * Calculates the distance between two points.
     *
     * @param other - the point which we measure the distance to
     * @return the distance to the other point (no declared variable)
     */
    public double distance(Point other) {
        // the difference in the x and y coordinates, respectively
        double dx = this.x - other.x;
        double dy = this.y - other.y;

        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Function name: equals.
     * Checks if two points are equal (meaning they are the same point).
     *
     * @param other - the point which we compare to
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        // if the other point doesn't even exist
        if (other == null) {
            return false;
        }
        return ((this.x == other.getX()) && (this.y == other.getY()));
    }

    /**
     * Function name: getX.
     * Returns the x coordinate.
     *
     * @return the x coordinate of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Function name: getY.
     * Returns the y coordinate.
     *
     * @return the y coordinate of the point
     */
    public double getY() {
        return this.y;
    }
}
