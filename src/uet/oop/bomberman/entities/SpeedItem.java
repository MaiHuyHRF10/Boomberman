package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class SpeedItem extends Item{

    private boolean active = false;
    private boolean check = false;
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
            if (!check) {
                BombermanGame.board.getPlayer().speed += 0.025;
                check = true;
            }
//            else {
//                BombermanGame.board.getPlayer().speed = 0.05;
//                active = false;
//            }
        } else if (remove){
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
         if (!remove) super.render(gc);
    }
}
