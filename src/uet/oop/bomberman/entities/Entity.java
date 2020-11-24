package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public abstract class Entity<mask> {
    protected double x;
    protected double y;
    protected Image img;
    protected boolean remove = false;
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
        return this.img;
    }


    public void setImg(Image img) {
        this.img = img;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
    }

    public abstract void update();


}
