package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import uet.oop.bomberman.graphics.Sprite;
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
        gc.drawImage(this.img, x * Sprite.SCALED_SIZE, (y + 2) * Sprite.SCALED_SIZE);
    }

    public abstract void update();

    public HashSet<String> getMask(Entity go) {
        Image imgObj = go.getImg();
        HashSet<String> mask = null;
        if (imgObj != null) {
            mask = new HashSet<String>();
            int W = (int) imgObj.getWidth();
            int H = (int) imgObj.getHeight();

            PixelReader reader = imgObj.getPixelReader();

            int a;
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    final int argb = reader.getArgb(x, y);
                    a = (argb >> 24) & 0xff;
                    if (a != 0) {
                        mask.add((int) (go.getX() * 32) + x + "," + ((int) (go.getY() * 32) - y)); // add the absolute x and absolute y coordinates to our set
                    }
                }
            }
        }
        return mask;
    }
}
