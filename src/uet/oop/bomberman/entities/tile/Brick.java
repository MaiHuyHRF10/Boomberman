package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.entities.tile.item.Portal;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private int timeAfterRemove = 30;
    private Item entityBelow = null;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public void addEntityBelow(Item entityBelow) {
        this.entityBelow = entityBelow;
    }

    @Override
    public void update() {
        if (remove && timeAfterRemove == 0) {
            BombermanGame.board.removeEntityAt(this.x, this.y);
            if (entityBelow != null) {
                if (!(entityBelow instanceof Portal)) {
                    BombermanGame.board.addEntity(entityBelow);
                    Board.map[(int) this.y][(int) this.x] = ' ';
                } else {
                    BombermanGame.board.addStillObject(entityBelow);
                }
            } else {
                Board.map[(int) this.y][(int) this.x] = ' ';
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) {
            super.render(gc);
            //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
        } else if (timeAfterRemove > 0) {
            int time = timeAfterRemove % 30;
            if (time >= 20) {
                setImg(Sprite.brick_exploded.getFxImage());
                super.render(gc);
                //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeAfterRemove--;
            } else if (time >= 10) {
                setImg(Sprite.brick_exploded1.getFxImage());
                super.render(gc);
                //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeAfterRemove--;
            } else {
                setImg(Sprite.brick_exploded2.getFxImage());
                super.render(gc);
                //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeAfterRemove--;
            }
        }
    }
}
