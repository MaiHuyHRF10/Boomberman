package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

// Kondoria = 4
public class Kondoria extends Enemy{
    public Kondoria(double x, double y, Image img, double speed) {
        super(x, y, img, speed);
        setFrameRight();
        setFrameLeft();
        setFrameDie();
    }

    public void setFrameRight() {
        Image right0 = Sprite.kondoria_right1.getFxImage();
        Image right1 = Sprite.kondoria_right2.getFxImage();
        Image right2 = Sprite.kondoria_right3.getFxImage();
        this.imgFrameRight = new Image[3];
        imgFrameRight[0] = right0;
        imgFrameRight[1] = right1;
        imgFrameRight[2] = right2;
    }

    public void setFrameLeft() {
        Image left0 = Sprite.kondoria_left1.getFxImage();
        Image left1 = Sprite.kondoria_left2.getFxImage();
        Image left2 = Sprite.kondoria_left3.getFxImage();
        this.imgFrameLeft = new Image[3];
        imgFrameLeft[0] = left0;
        imgFrameLeft[1] = left1;
        imgFrameLeft[2] = left2;
    }

    public void setFrameDie() {
        Image die0 = Sprite.kondoria_dead.getFxImage();
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
        return enemyAI.chooseDirectionGoThroughBrick(this, currentDirection);
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

    @Override
    public void movingPlayer() {
        currentDirection = chooseDirection();
        switch (currentDirection) {
            case 0:
                moveUp();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveLeft();
                break;
        }
    }
}
