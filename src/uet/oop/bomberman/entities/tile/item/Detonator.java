package uet.oop.bomberman.entities.tile.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public class Detonator extends Item {

    private int timeActive = 600;

    public void setActive(boolean active) {
        this.active = active;
    }

    public Detonator(double x, double y, Image img) {
        super(x, y, img);
        remove = false;
    }

    @Override
    public void update() {

        if (active) {
            remove = true;
            int left = Board.getPlayer().getHealth();
            Board.getPlayer().setHealth(left + 1);
            Board.getPlayer().updateStatus();
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) super.render(gc);
    }
}
