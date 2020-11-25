package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class SpeedItem extends Item{

    private int timeActive = 600;
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
            if (timeActive > 0) {
                BombermanGame.board.getPlayer().speed = 0.1;
                timeActive --;
            } else {
                BombermanGame.board.getPlayer().speed = 0.05;
                active = false;
            }
        } else if (remove){
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
         if (!remove) super.render(gc);
    }
}
