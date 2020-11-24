package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends movingObj{

    Image[] imgFrameRight;
    Image[] imgFrameLeft;
    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    private final int animate = 5;

    public Oneal(double x, double y, Image img, double speed) {
        super(x, y, img, speed);
        setFrameRight();
        setFrameLeft();
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
        this.imgFrameRight = new Image[3];
        imgFrameRight[0] = left0;
        imgFrameRight[1] = left1;
        imgFrameRight[2] = left2;
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
            up++;
        } else if (up < 2 * animate) {
            up++;
        } else {
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
            down++;
        } else if (down < 2 * animate) {
            down++;
        } else {
            down++;
            if (down == 3 * animate) {
                down = 0;
            }
        }
        this.y += speed;
    }

    public void movingPlayer() {
        int randomDirection = 0;
        switch (randomDirection) {
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

    public void checkToMapMoveRight() {
        double widthFrameNow;
        if (right < animate) {
            widthFrameNow = 20.0;
        } else if (right < 2 * animate) {
            widthFrameNow = 22.0;
        } else {
            widthFrameNow = 24.0;
        }

        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) (x + speed);
        int xPos2 = (int) (x + speed + distance);

        int yPos = (int) y;
        int yPos2 = (int) (y + 1);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos][xPos2] != ' ' || Board.map[yPos2][xPos2] != ' ') {
                if (Board.map[(int) y][xPos2] != ' ') {
                    if (y == (int) y) {
                        this.x = xPos2 - distance;
                    } else {
                        if (this.y - (int) y >= 0.7) {
                            this.y = (int) y + 1;
                        } else {
                            this.x = xPos2 - distance;
                        }
                    }
                } else if (Board.map[(int) (y + 1)][xPos2] != 0) {
                    if (this.y - (int) y <= 0.3) {
                        this.y = (int) y;
                    } else {
                        this.x = xPos2 - distance;
                    }
                }
            }
        }
    }

    public void checkToMapMoveLeft() {
        int xPos = (int) (x - speed);

        int yPos = (int) y;
        int yPos2 = (int) (y + 1);

        if (xPos >= 0 && xPos < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos][xPos] != ' ' || Board.map[yPos2][xPos] != ' ') {
                if (Board.map[(int) y][xPos] != ' ') {
                    if (this.y == (int) y) {
                        this.x = xPos + 1;
                    } else {
                        if (this.y - (int) y >= 0.7) {
                            this.y = (int) y + 1;
                        } else {
                            this.x = xPos + 1;
                        }
                    }
                } else if (Board.map[(int) (y + 1)][xPos] != ' ') {
                    if (this.y - (int) y <= 0.3) {
                        this.y = (int) y;
                    } else {
                        this.x = xPos + 1;
                    }
                }
            }
        }
    }

    public void checkToMapMoveUp() {
        double widthFrameNow = 24;

        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) x;
        int xPos2 = (int) (x + distance);

        int yPos = (int) (y);
        int yPos2 = (int) (y - speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos2][xPos] != ' ' || Board.map[yPos2][xPos2] != ' ') {
                if (Board.map[yPos2][xPos] != ' ') {
                    if (this.x - (int) x >= 0.7) {
                        this.x = (int) x + 1;
                    } else {
                        this.y = yPos2 + 1;
                    }
                } else if (Board.map[yPos2][xPos2] != ' ') {
                    if (this.x - (int) x <= 0.45) {
                        this.x = (int) x + 1 - distance;
                    } else {
                        this.y = yPos2 + 1;
                    }
                }

            }
        }
    }

    public void checkToMapMoveDown() {
        double widthFrameNow = 24.0;
        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) x;
        int xPos2 = (int) (x + distance);

        int yPos = (int) (y + speed);
        int yPos2 = (int) (y + 1 + speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos2][xPos] != ' ' || Board.map[yPos2][xPos2] != ' ') {
                if (Board.map[(int) (y + 1)][xPos] != ' ') {
                    if (this.x - (int) x >= 0.7) {
                        this.x = (int) x + 1;
                    } else {
                        this.y = yPos;
                    }
                } else if (Board.map[(int) (y + 1)][xPos2] != ' ') {
                    if (this.x - (int) x <= 0.45) {
                        this.x = (int) x + 1 - distance;
                    } else {
                        this.y = yPos;
                    }
                }
            }
        }
    }
}
