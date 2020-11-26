package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public class Explosion extends Entity {

    protected boolean last;

    public Explosion(double x, double y, int direction, boolean last) {
        this.x = x;
        this.y = y;
        this.last = last;
        switch (direction) {
            case 0:
                if (!last) {
                    img = Sprite.explosion_vertical2.getFxImage();
                } else {
                    img = Sprite.explosion_vertical_top_last2.getFxImage();
                }
                break;
            case 1:
                if (!last) {
                    img = Sprite.explosion_horizontal2.getFxImage();
                } else {
                    img = Sprite.explosion_horizontal_right_last2.getFxImage();
                }
                break;
            case 2:
                img = last ? Sprite.explosion_vertical_down_last2.getFxImage() : Sprite.explosion_vertical2.getFxImage();
                break;
            case 3:
                img = last ? Sprite.explosion_horizontal_left_last2.getFxImage() : Sprite.explosion_horizontal2.getFxImage();
                break;
        }
    }


    public void update(int direction, int time) {
        BombermanGame.board.getPlayer().collideToDie(this);
        int sizeBomb = BombermanGame.board.getPlayer().getBombs().size();
        for (int i = 0; i < sizeBomb; i++) {
            BombermanGame.board.getPlayer().getBombs().get(i).collideWithBombOther(this);
        }
        int sizeEnemy = BombermanGame.board.getEnemies().size();
        for (int i = 0; i < sizeEnemy; i++) {
            BombermanGame.board.getEnemies().get(i).collideWithExplosion(this);
        }
        time %= 30;
        if (time > 10 && time <= 20) {
            switch (direction) {
                case 0:
                    img = last ? Sprite.explosion_vertical_top_last1.getFxImage() : Sprite.explosion_vertical1.getFxImage();
                    break;
                case 1:
                    img = last ? Sprite.explosion_horizontal_right_last1.getFxImage() : Sprite.explosion_horizontal1.getFxImage();
                    break;
                case 2:
                    img = last ? Sprite.explosion_vertical_down_last1.getFxImage() : Sprite.explosion_vertical1.getFxImage();
                    break;
                case 3:
                    img = last ? Sprite.explosion_horizontal_left_last1.getFxImage() : Sprite.explosion_horizontal1.getFxImage();
                    break;
            }
        }
        if (time <= 10) {
            switch (direction) {
                case 0:
                    img = last ? Sprite.explosion_vertical_top_last.getFxImage() : Sprite.explosion_vertical.getFxImage();
                    break;
                case 1:
                    img = last ? Sprite.explosion_horizontal_right_last.getFxImage() : Sprite.explosion_horizontal.getFxImage();
                    break;
                case 2:
                    img = last ? Sprite.explosion_vertical_down_last.getFxImage() : Sprite.explosion_vertical.getFxImage();
                    break;
                case 3:
                    img = last ? Sprite.explosion_horizontal_left_last.getFxImage() : Sprite.explosion_horizontal.getFxImage();
                    break;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (Board.map[(int) this.y][(int) this.x] != 'B') super.render(gc);
    }

    @Override
    public void update() {

    }
}
