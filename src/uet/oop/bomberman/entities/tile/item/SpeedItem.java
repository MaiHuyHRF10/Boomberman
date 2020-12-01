package uet.oop.bomberman.entities.tile.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

import uet.oop.bomberman.Board;

public class SpeedItem extends Item {

    private boolean active = false;
    public void setActive(boolean active) {
        this.active = active;
    }

    public SpeedItem(double x, double y, Image img) {
        super(x, y, img);
        remove = false;
    }

    @Override
    public void update() {
        if (active) {
            remove = true;
            double currentSpeed = Board.getPlayer().getSpeed();
            Board.getPlayer().setSpeed(currentSpeed + 0.025 / 2);
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
         if (!remove) super.render(gc);
    }
}
