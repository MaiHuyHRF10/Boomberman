package uet.oop.bomberman.entities.tile.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.tile.item.Item;

public class BombItem extends Item {
    private boolean active = false;
    private boolean check = false;

    public void setActive(boolean active) {
        this.active = active;
    }

    public BombItem(double x, double y, Image img) {
        super(x, y, img);
        remove = false;
    }

    @Override
    public void update() {

        if (active) {
            remove = true;
//            if (!check) {
            Board.bombCount ++;
                //check = true;
            //}
//            if (timeActive > 0) {
//                timeActive --;
//            } else {
//                Board.bombCount --;
//                active = false;
//            }
        //} else if (remove){
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) super.render(gc);
    }
}
