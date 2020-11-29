package uet.oop.bomberman.entities.tile.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public class FlamePass extends Item {
    private int timeActive = 600;

    public void setActive(boolean active) {
        this.active = active;
    }

    public FlamePass(double x, double y, Image img) {
        super(x, y, img);
        remove = false;
    }

    @Override
    public void update() {

//        if (active) {
//            remove = true;
//            Board.flamePass = true;
//            if (timeActive > 0) {
//                timeActive--;
//            } else {
//                Board.flamePass = false;
//                active = false;
//            }
//        } else if (remove) {
//            BombermanGame.board.removeEntityAt(this.x, this.y);
//        }
        if (active) {
            remove = true;
            Board.flamePass = true;
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) super.render(gc);
    }
}
