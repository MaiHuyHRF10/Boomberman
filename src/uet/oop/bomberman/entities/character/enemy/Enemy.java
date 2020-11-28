package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.movingObj;

import java.util.HashSet;

public abstract class Enemy extends movingObj {
    protected Image[] imgFrameRight;
    protected Image[] imgFrameLeft;
    protected Image[] imgFrameDie;
    protected int currentDirection = 1;
    boolean alive = true;
    protected int time = 0;
    protected AI enemyAI = new AI();

    public Enemy(double x, double y, Image img, double speed) {
        super(x, y, img, speed);
    }

    public abstract int chooseDirection();

    public void checkToMapMoveRight() {
        double widthFrameNow = 32;

        double distance = 1;
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
        double widthFrameNow = 32;

        double distance = 1;
        int xPos = (int) (x);
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
        double widthFrameNow = 32;
        double distance = 1;
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

    public void collideWithExplosion(Entity obj) {
        if (alive) {
            HashSet<String> maskPlayer1 = getMask(this);
            HashSet<String> maskPlayer2 = getMask(obj);
            maskPlayer1.retainAll(maskPlayer2);
            if (maskPlayer1.size() > 0) {
                alive = false;
            }
        }
    }

    public void update() {
        if (alive) {
            movingPlayer();
        } else {
            enemyDie();
        }
    }

    public abstract void enemyDie();
}
