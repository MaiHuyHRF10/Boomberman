package uet.oop.bomberman.entities.tile.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public class WallPass extends Item {
    //private int timeActive = 600;

    public void setActive(boolean active) {
        this.active = active;
    }

    public WallPass(double x, double y, Image img) {
        super(x, y, img);
        remove = false;
    }

    @Override
    public void update() {

        if (active) {
            remove = true;
            Board.wallPass = true;
            for (int i = 0; i< Board.HEIGHT; i++) {
                for (int j = 0; j <Board.WIDTH; j ++) {
                    if (Board.map[i][j] == '*') {
                        Board.map[i][j] = 't';
                    }
                }
            }
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) super.render(gc);
    }
}
