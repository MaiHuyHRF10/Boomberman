package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Balloom extends Enemy {

    public Balloom(double x, double y, Image img, double speed) {
        super(x, y, img, speed);
        setFrameRight();
        setFrameLeft();
        setFrameDie();
    }

    public void setFrameRight() {
        Image right0 = Sprite.balloom_right1.getFxImage();
        Image right1 = Sprite.balloom_right2.getFxImage();
        Image right2 = Sprite.balloom_right3.getFxImage();
        imgFrameRight = new Image[3];
        imgFrameRight[0] = right0;
        imgFrameRight[1] = right1;
        imgFrameRight[2] = right2;
    }

    public void setFrameLeft() {
        Image left0 = Sprite.balloom_left1.getFxImage();
        Image left1 = Sprite.balloom_left2.getFxImage();
        Image left2 = Sprite.balloom_left3.getFxImage();
        this.imgFrameLeft = new Image[3];
        imgFrameLeft[0] = left0;
        imgFrameLeft[1] = left1;
        imgFrameLeft[2] = left2;
    }

    public void setFrameDie() {
        Image die0 = Sprite.balloom_dead.getFxImage();
        Image die1 = Sprite.mob_dead1.getFxImage();
        Image die2 = Sprite.mob_dead2.getFxImage();
        Image die3 = Sprite.mob_dead3.getFxImage();
        this.imgFrameDie = new Image[4];
        imgFrameDie[0] = die0;
        imgFrameDie[1] = die1;
        imgFrameDie[2] = die2;
        imgFrameDie[3] = die3;
    }

    public int chooseDirection() {
        double tempX = (double) Math.round(x * 1000) / 1000;
        double tempY = (double) Math.round(y * 1000) / 1000;
        if (tempX == (int) tempX && tempY == (int) tempY) {
            int _x = (int) x;
            int _y = (int) y;
            int[] deltaX = {0, 1, 0, -1};
            int[] deltaY = {-1, 0, 1, 0};

            Random rd = new Random(System.currentTimeMillis()); //something like srand in C++
            int i = Math.abs(rd.nextInt()) % 4;
            while (Board.map[_y + deltaY[i]][_x + deltaX[i]] != ' ') {
                i = Math.abs(rd.nextInt()) % 4;
            }
            return i;
        } else {
            return currentDirection;
        }
    }

    public void movingPlayer() {
        currentDirection = chooseDirection();
        switch (currentDirection) {
            case 0:
                moveUp();
                checkToMapMoveUp();
                break;
            case 1:
                moveRight();
                checkToMapMoveRight();
                break;
            case 2:
                moveDown();
                checkToMapMoveDown();
                break;
            case 3:
                moveLeft();
                checkToMapMoveLeft();
                break;
        }
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
            BombermanGame.board.removeEnemyAt(this.x, this.y);
        }
    }
}
