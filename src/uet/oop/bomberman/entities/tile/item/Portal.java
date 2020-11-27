package uet.oop.bomberman.entities.tile.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;

public class Portal extends Item {

    private boolean active = false;
    private boolean check = false;

    public boolean getActive() {
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public Portal(double x, double y, Image img) {
        super(x, y, img);
        remove = false;
    }

    @Override
    public void update() {

        if (BombermanGame.board.getEnemies().size() == 0) {
            active = true;
        }
        if (active) {
            Board.map[(int) this.y][(int) this.x] = ' ';
        }
    }
}
