package uet.oop.bomberman;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class KeyBoard {
    public boolean right, left, up, down, space;

    public KeyBoard() {
        right = false;
        left = false;
        up = false;
        down = false;
        space = false;
    };

    public void status(Scene gameScene) {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true;
                    break;
                case UP:
                    up = true;
                    break;
                case DOWN:
                    down = true;
                    break;
                case SPACE:
                    space = true;
                    break;
            }
        });

        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    left = false;
                    BombermanGame.board.getPlayer().setImg(Sprite.player_left.getFxImage());
                    break;
                case RIGHT:
                    right = false;
                    BombermanGame.board.getPlayer().setImg(Sprite.player_right.getFxImage());
                    break;
                case UP:
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break;
                case SPACE:
                    space = false;
                    break;
            }
        });
    }


}
