package listeners;

import collidablesdata.Sprite;
import gamelogic.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;
import levelsdata.BackgroundDisplayer;
import levelsdata.ImageParser;

import java.awt.*;

public class BackgroundListener implements HitListener {

    private GameLevel level;

    public BackgroundListener(GameLevel level) {
        this.level = level;
    }

    public void hitEvent(Block beingHit, Ball hitter) {
        Image img = new ImageParser().imageFromString("(bear.jpg)");
        Sprite back = new BackgroundDisplayer(3, img);
        level.addSprite(back);
        // back.drawOn(level.getDrawSurface());
    }
}
