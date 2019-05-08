package gameobjects;

import java.util.ArrayList;

/**
 * The program defines a Rectangle blueprint that is the base for all of the game objects (except for the ball).
 * A rectangle has a position on the screen, height and width.
 * The program checks if a given line intersects with the rectangle and sets each of its corners every time it changes
 * position.
 * The program grants access to each line and each corner of the rectangle
 *
 * @author Ariel Fenster
 */
public class Rectangle {
    //members
    private Point upperLeft;
    private double width;
    private double height;

    private Point upperRight;
    private Point downLeft;
    private Point downRight;

    /**
     * Function name: Rectangle.
     * Constructor - create a new rectangle at a specific location with width and height.
     *
     * @param upperLeft - the upper left corner of the rectangle
     * @param width     - the width of the rectangle
     * @param height    - the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        // used to "create" all the other edge points of the rectangle
        this.updateCorners();
    }

    /**
     * Function name: intersectionPoints.
     * The function checks if a given line intersects the rectangle.
     *
     * @param line - the line we check if it intersects the rectangle
     * @return a (possibly empty) list of intersection points with the specific line
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        ArrayList<Point> list = new ArrayList<>();
        Point interPoint;

        // check intersections with all the sides of the rectangle and if exists such a point - add to the list
        interPoint = line.intersectionWith(this.getTopLine());
        if (interPoint != null) {
            list.add(interPoint);
        }
        interPoint = line.intersectionWith(this.getLeftLine());
        if (interPoint != null) {
            list.add(interPoint);
        }
        interPoint = line.intersectionWith(this.getBottomLine());
        if (interPoint != null) {
            list.add(interPoint);
        }
        interPoint = line.intersectionWith(this.getRightLine());
        if (interPoint != null) {
            list.add(interPoint);
        }
        return list;
    }

    // set functions for all the corner points of the rectangle:

    /**
     * Function name: updateCorners.
     * A collection of setting functions
     */
    public void updateCorners() {
        this.setUpperRight();
        this.setDownRight();
        this.setDownLeft();
    }

    /**
     * Function name: setUpperLeftXPosTo.
     * Setting the upper left corner's X coordinate to be a new value - used in Paddle class.
     *
     * @param newXPosition - the new value for the upper left corner's X coordinate
     */
    public void setUpperLeftXPosTo(double newXPosition) {
        this.upperLeft = new Point(newXPosition, this.upperLeft.getY());
    }

    /**
     * Function name: setUpperRight.
     * Setting the values of the upper right corner point of the rectangle
     */
    private void setUpperRight() {
        double x = this.upperLeft.getX() + this.getWidth();
        double y = this.upperLeft.getY();
        this.upperRight = new Point(x, y);
    }

    /**
     * Function name: setDownLeft.
     * Setting the values for the down left corner point of the rectangle
     */
    private void setDownLeft() {
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY() + this.getHeight();
        this.downLeft = new Point(x, y);
    }

    /**
     * Function name: setDownRight.
     * Setting the values for the down right corner point of the rectangle
     */
    private void setDownRight() {
        double x = this.upperLeft.getX() + this.getWidth();
        double y = this.upperLeft.getY() + this.getHeight();
        this.downRight = new Point(x, y);
    }

    // get functions for all the corner points of the rectangle:

    /**
     * Function name: getUpperLeft.
     * Return the upper left corner point of the rectangle
     *
     * @return upper left corner point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Function name: getUpperRight.
     * Return the upper right corner point of the rectangle
     *
     * @return upper right corner point
     */
    public Point getUpperRight() {
        return this.upperRight;
    }

    /**
     * Function name: getDownLeft.
     * Return the down left corner point of the rectangle
     *
     * @return down left corner point
     */
    private Point getDownLeft() {
        return this.downLeft;
    }

    /**
     * Function name: getDownRight.
     * Return the down right corner point of the rectangle
     *
     * @return down right corner point
     */
    public Point getDownRight() {
        return this.downRight;
    }

    // get functions for all the constructing lines of the rectangle:

    /**
     * Function name: getTopLine.
     * Return the top line of the rectangle
     *
     * @return top line of rectangle
     */
    public Line getTopLine() {
        return new Line(this.getUpperLeft(), this.getUpperRight());
    }

    /**
     * Function name: getLeftLine.
     * Return the left line of the rectangle
     *
     * @return left line of rectangle
     */
    private Line getLeftLine() {
        return new Line(this.getUpperLeft(), this.getDownLeft());
    }

    /**
     * Function name: getBottomLine.
     * Return the bottom line of the rectangle
     *
     * @return bottom line of rectangle
     */
    public Line getBottomLine() {
        return new Line(this.getDownLeft(), this.getDownRight());
    }

    /**
     * Function name: getRightLine.
     * Return the right line of the rectangle
     *
     * @return right line of rectangle
     */
    private Line getRightLine() {
        return new Line(this.getUpperRight(), this.getDownRight());
    }

    /**
     * Function name: getWidth.
     * Return the width of the rectangle
     *
     * @return rectangle width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Function name: getHeight.
     * Return the height of the rectangle
     *
     * @return rectangle height
     */
    public double getHeight() {
        return this.height;
    }
}