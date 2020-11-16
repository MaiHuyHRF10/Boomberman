package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private int timeAfterRemove = 30;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) {
            gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
        } else if (timeAfterRemove > 0) {
            int time = timeAfterRemove % 30 ;
            if (time >=20) {
                setImg(Sprite.brick_exploded.getFxImage());
                gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeAfterRemove --;
            } else if (time >= 10) {
                setImg(Sprite.brick_exploded1.getFxImage());
                gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeAfterRemove--;
            } else {
                setImg(Sprite.brick_exploded2.getFxImage());
                gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeAfterRemove--;
            }
        }
    }
}
