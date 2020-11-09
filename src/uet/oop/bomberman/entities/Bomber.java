package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Bomber extends movingObj {

    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    private int animate = 5;

    private boolean isLeftKeyPress = false;
    private boolean isRightKeyPress = false;
    private boolean isUpKeyPress = false;
    private boolean isDownKeyPress = false;

    Image []imgFrameRight;
    Image []imgFrameLeft;
    Image []imgFrameUp;
    Image []imgFrameDown;


    public Bomber(double x, double y, Image img, double speed) {
        super( x, y, img, speed);
        setFrameRight();
        setFrameLeft();
        setFrameUp();
        setFrameDown();
    }

    public void setLeftKeyPress(boolean status) {
        this.isLeftKeyPress = status;
    }

    public void setRightKeyPress(boolean status) {
        this.isRightKeyPress = status;
    }

    public void setDownKeyPress(boolean status) {
        this.isDownKeyPress = status;
    }

    public void setUpKeyPress(boolean status) {
        this.isUpKeyPress = status;
    }



    private void setFrameRight() {
        Image right0 = Sprite.player_right.getFxImage();
        Image right1 = Sprite.player_right_1.getFxImage();
        Image right2 = Sprite.player_right_2.getFxImage();
        this.imgFrameRight = new Image[3];
        imgFrameRight[0] = right0;
        imgFrameRight[1] = right1;
        imgFrameRight[2] = right2;
    }

    private void setFrameLeft() {
        Image left0 = Sprite.player_left.getFxImage();
        Image left1 = Sprite.player_left_1.getFxImage();
        Image left2 = Sprite.player_left_2.getFxImage();
        this.imgFrameLeft = new Image[3];
        imgFrameLeft[0] = left0;
        imgFrameLeft[1] = left1;
        imgFrameLeft[2] = left2;
    }

    private void setFrameUp() {
        Image up0 = Sprite.player_up.getFxImage();
        Image up1 = Sprite.player_up_1.getFxImage();
        Image up2 = Sprite.player_up_2.getFxImage();
        this.imgFrameUp = new Image[3];
        imgFrameUp[0] = up0;
        imgFrameUp[1] = up1;
        imgFrameUp[2] = up2;
    }

    private void setFrameDown() {
        Image down0 = Sprite.player_down.getFxImage();
        Image down1 = Sprite.player_down_1.getFxImage();
        Image down2 = Sprite.player_down_2.getFxImage();
        this.imgFrameDown = new Image[3];
        imgFrameDown[0] = down0;
        imgFrameDown[1] = down1;
        imgFrameDown[2] = down2;
    }


    @Override
    public void update() {
        movingPlayer();
    }

    @Override
    public void moveLeft() {
        if (left < animate) {
            this.setImg(imgFrameLeft[0]);
            left++;
        } else if (left < 2 * animate) {
            this.setImg(imgFrameLeft[1]);
            left++;
        } else {
            this.setImg(imgFrameLeft[2]);
            left++;
            if (left == 3 * animate) {
                left = 0;
            }
        }
        this.x -= speed;
    }

    @Override
    public void moveRight() {
        if (right < animate) {
            this.setImg(imgFrameRight[0]);
            right++;
        } else if (right < 2 * animate) {
            this.setImg(imgFrameRight[1]);
            right++;
        } else {
            this.setImg(imgFrameRight[2]);
            right++;
            if (right == 3 * animate) {
                right = 0;
            }
        }
        this.x += speed;
    }

    @Override
    public void moveUp() {
        if (up < animate) {
            this.setImg(imgFrameUp[0]);
            up++;
        } else if (up < 2 * animate) {
            this.setImg(imgFrameUp[1]);
            up++;
        } else {
            this.setImg(imgFrameUp[2]);
            up++;
            if (up == 3 * animate) {
                up = 0;
            }
        }
        this.y -= speed;
    }

    @Override
    public void moveDown() {
        if (down < animate) {
            this.setImg(imgFrameDown[0]);
            down++;
        } else if (down < 2 * animate) {
            this.setImg(imgFrameDown[1]);
            down++;
        } else {
            this.setImg(imgFrameDown[2]);
            down++;
            if (down == 3 * animate) {
                down = 0;
            }
        }
        this.y += speed;
    }

    public void movingPlayer() {
        if (this.isLeftKeyPress) {
            moveLeft();
            checkToMapMoveLeft();
        } else if (this.isRightKeyPress) {
            moveRight();
            checkToMapMoveRight();
        } else if (this.isDownKeyPress) {
            moveDown();
            checkToMapMoveDown();
        } else if (this.isUpKeyPress) {
            moveUp();
            checkToMapMoveUp();
        }

    }

    public void checkToMapMoveRight() {
        double widthFrameNow = 0;
        if (right  < animate) {
            widthFrameNow = 20.0;
        } else if (right < 2 * animate) {
            widthFrameNow = 22.0;
        } else {
            widthFrameNow = 24.0;
        }

        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) (x + speed);
        int xPos2 = (int) (x + speed + distance);

        int yPos = (int) (y + 0.12);
        int yPos2 = (int) (y + 1 - 0.12);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (BombermanGame.map[yPos][xPos2] != ' ' || BombermanGame.map[yPos2][xPos2] != ' ') {
                this.x = xPos2 - distance;
            }
        }
    }

    public void checkToMapMoveLeft() {
        int xPos = (int) (x - speed);

        int yPos = (int) (y + 0.12);
        int yPos2 = (int) (y + 1 - 0.12);

        if (xPos >= 0 && xPos < 31 && yPos >= 0 && yPos2 < 13) {
            if (BombermanGame.map[yPos][xPos] != ' ' || BombermanGame.map[yPos2][xPos] != ' ') {
                this.x = xPos + 1;
            }
        }
    }

    public void checkToMapMoveUp() {
        double widthFrameNow = 24;

        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) (x + 0.1);
        int xPos2 = (int) (x + distance - 0.1);

        int yPos = (int) (y);
        int yPos2 = (int) (y - speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (BombermanGame.map[yPos2][xPos] != ' ' || BombermanGame.map[yPos2][xPos2] != ' ') {
                this.y = yPos2 + 1;
            }
        }
    }

    public void checkToMapMoveDown() {
        double widthFrameNow = 24.0;
        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) (x + 0.1);
        int xPos2 = (int) (x + distance - 0.1);

        int yPos = (int) (y + speed);
        int yPos2 = (int) (y + 1 + speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (BombermanGame.map[yPos2][xPos] != ' ' || BombermanGame.map[yPos2][xPos2] != ' ') {
                this.y = yPos;
            }
        }
    }
}