package levelsdata;

import biuoop.DrawSurface;
import collidablesdata.Sprite;
import listeners.BackgroundListener;

import java.awt.*;

public class BackgroundDisplayer implements Sprite {

    private int seconds;
    private Image img;
    private long startTime;
    private long totalTimePassed;
    private boolean isTimePassed;

    public BackgroundDisplayer(int howLong, Image img) {
        this.seconds = howLong;
        this.img = img;
        this.startTime = System.currentTimeMillis();
        this.isTimePassed = false;
    }

    @Override
    public void timePassed(double dt) {
        long endTime = System.currentTimeMillis();
        totalTimePassed += endTime - this.startTime;

        if (this.totalTimePassed > seconds) {
            this.isTimePassed = true;
        }
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (!this.isTimePassed) {
            d.drawImage(0, 0, this.img);
        }
    }
}
