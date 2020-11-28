package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class Oneal extends Enemy {
    public Oneal(double x, double y, Image img, double speed) {
        super(x, y, img, speed);
        setFrameRight();
        setFrameLeft();
        setFrameDie();
    }

    public int chooseDirection() {
        return enemyAI.chooseDirectionMedium(x, y, currentDirection);
    }

    public void setFrameDie() {
        Image die0 = Sprite.oneal_dead.getFxImage();
        Image die1 = Sprite.mob_dead1.getFxImage();
        Image die2 = Sprite.mob_dead2.getFxImage();
        Image die3 = Sprite.mob_dead3.getFxImage();
        this.imgFrameDie = new Image[4];
        imgFrameDie[0] = die0;
        imgFrameDie[1] = die1;
        imgFrameDie[2] = die2;
        imgFrameDie[3] = die3;
    }

    public void setFrameRight() {
        Image right0 = Sprite.oneal_right1.getFxImage();
        Image right1 = Sprite.oneal_right2.getFxImage();
        Image right2 = Sprite.oneal_right3.getFxImage();
        this.imgFrameRight = new Image[3];
        imgFrameRight[0] = right0;
        imgFrameRight[1] = right1;
        imgFrameRight[2] = right2;
    }

    public void setFrameLeft() {
        Image left0 = Sprite.oneal_left1.getFxImage();
        Image left1 = Sprite.oneal_left2.getFxImage();
        Image left2 = Sprite.oneal_left3.getFxImage();
        this.imgFrameLeft = new Image[3];
        imgFrameLeft[0] = left0;
        imgFrameLeft[1] = left1;
        imgFrameLeft[2] = left2;
    }

    public void enemyDie() {
        if (time < 10) {
            this.setImg(imgFrameDie[0]);
            time++;
        } else if (time < 20) {
            this.setImg(imgFrameDie[1]);
            time++;
        } else if (time < 30) {
            this.setImg(imgFrameDie[2]);
            time++;
        } else if (time < 40) {
            this.setImg(imgFrameDie[3]);
            Board.score += 200;
            BombermanGame.board.removeEnemyAt(this.x, this.y);
        }
    }

}
