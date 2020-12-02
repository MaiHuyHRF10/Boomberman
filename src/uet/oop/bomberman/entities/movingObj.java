package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class movingObj extends Entity {
    protected double speed;

    public movingObj(double x, double y, Image img, double speed) {
        super(x, y, img);
        this.speed = speed;
    }


    public abstract void moveRight();
    public abstract void moveLeft();
    public abstract void moveUp();
    public abstract void moveDown();

}
