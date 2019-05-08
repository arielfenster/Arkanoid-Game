package collidablesdata;

import gameobjects.Point;

/**
 * The Velocity class is a pair of numbers representing the speed in the X and Y axes, respectively.
 * It can be set using two different ways:
 * by using a direct pair of numbers and by using an angle and a speed in a diagonal line.
 * Finally, the program moves a point and changes its position based on its velocity
 *
 * @author Ariel Fenster
 */
public class Velocity {
    // members
    private double dx;
    private double dy;

    /**
     * Constructor #1 for the Velocity class - using a pair of numbers, each represents a speed in an axis.
     *
     * @param dx - speed in the X axis
     * @param dy - speed in the Y axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Constructor #2 for the Velocity class - using an angle and a speed.
     *
     * @param angle - the angle (in degrees) in which the ball is heading in
     * @param speed - the speed in that angle
     * @return velocity based on the angle and speed using trigonometric functions
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // 0 degrees is set to be straight up, so we calculate the speeds in each axis based on that
        double dx = speed * Math.sin(Math.toRadians(180 - angle));
        double dy = speed * Math.cos(Math.toRadians(180 - angle));
        return (new Velocity(dx, dy));
    }

    /**
     * Function name: applyToPoint.
     * The function moves a ball in the X and Y axis
     *
     * @param p - the center of a ball
     * @return a new position for the center of a ball, caused by applying the ball's velocity to its center
     */
    public Point applyToPoint(Point p) {
        // the new center is the x/y coordinate plus the speed in that axis
        return (new Point(p.getX() + this.dx, p.getY() + this.dy));
    }

    /**
     * Function name: getVelocityDx.
     * Return the ball's velocity in the X axis
     *
     * @return velocity in X axis
     */
    public double getVelocityDx() {
        return this.dx;
    }

    /**
     * Function name: getVelocityDy.
     * Return the ball's velocity in the Y axis
     *
     * @return velocity in Y axis
     */
    public double getVelocityDy() {
        return this.dy;
    }
}
