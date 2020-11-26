package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public abstract class movingObj extends Entity {

    protected double speed;
    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;
    protected final int animate = 5;
    protected boolean alive = true;

    public movingObj(double x, double y, Image img, double speed) {
        super(x, y, img);
        this.speed = speed;
    }

    public abstract void movingPlayer();

    @Override
    public void update() {
        movingPlayer();
    }

    public abstract void moveRight();

    public abstract void moveLeft();

    public abstract void moveUp();

    public abstract void moveDown();

}
