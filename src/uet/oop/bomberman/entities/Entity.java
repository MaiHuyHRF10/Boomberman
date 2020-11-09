package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected double x;
    protected double y;
    protected Image img;

    public Entity() {
    }

    public Entity(double x, double y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
    }

    public abstract void update();
}
