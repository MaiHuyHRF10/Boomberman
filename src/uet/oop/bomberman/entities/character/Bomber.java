package uet.oop.bomberman.entities.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.item.Portal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Bomber extends movingObj {

    Image[] imgFrameRight;
    Image[] imgFrameLeft;
    Image[] imgFrameUp;
    Image[] imgFrameDown;
    Image[] imgFrameDie;
    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    private int time = 0; // time to die
    private final int animate = 5;
    private List<Bomb> bombs = new ArrayList<>();


    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Bomber(double x, double y, Image img, double speed) {
        super(x, y, img, speed);
        setFrameRight();
        setFrameLeft();
        setFrameUp();
        setFrameDown();
        setFrameDie();
    }

    private void setFrameRight() {
        Image right0 = Sprite.player_right.getFxImage();
        Image right1 = Sprite.player_right_1.getFxImage();
        Image right2 = Sprite.player_right_2.getFxImage();
        this.imgFrameRight = new Image[3];
        imgFrameRight[0] = right0;
        imgFrameRight[1] = right1;
        imgFrameRight[2] = right2

        ;
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

    private void setFrameDie() {
        Image die0 = Sprite.player_dead1.getFxImage();
        Image die1 = Sprite.player_dead2.getFxImage();
        Image die2 = Sprite.player_dead3.getFxImage();
        this.imgFrameDie = new Image[3];
        imgFrameDie[0] = die0;
        imgFrameDie[1] = die1;
        imgFrameDie[2] = die2;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public void addBomb(Bomb bomb) {
        boolean check = true;
        for (int i = 0; i < bombs.size(); i++) {
            Bomb temp = bombs.get(i);
            if (temp.getX() == bomb.getX() && temp.getY() == bomb.getY()) {
                check = false;
                break;
            }
        }
        if (check) {
            bombs.add(bomb);
            Sound.play("BOM_SET");
        }
    }

    public void removeBombAt(double x, double y) {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb temp = bombs.get(i);
            if (temp.getX() == x && temp.getY() == y) {
                bombs.remove(i);
                Board.map[(int) temp.getY()][(int) temp.getX()] = ' ';
                break;
            }
        }
    }

    @Override
    public void update() {
        collide();
        for (int i = 0; i < bombs.size(); i++) {
            updateWallFromBomb(bombs.get(i));
            bombs.get(i).update();
        }
        if (alive) {
            movingPlayer();
            placeBomb();
        } else {
            if (time < 10) {
                this.setImg(imgFrameDie[0]);
                time++;
            } else if (time < 20) {
                this.setImg(imgFrameDie[1]);
                time++;
            } else if (time < 30) {
                this.setImg(imgFrameDie[2]);
                time++;
            } else {
                this.setImg(null);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(gc);
        }
    }

    public void placeBomb() {
        if (BombermanGame.keyBoard.space && bombs.size() < Board.bombCount) {
            Bomb bomb = new Bomb(xBomb(), yBomb(), false, Sprite.bomb.getFxImage());
            addBomb(bomb);
        }
    }

    public void collide() {
        for (int i = 0; i < BombermanGame.board.getEntities().size(); i++) {
            if (BombermanGame.board.getEntities().get(i) instanceof Item) {
                collideWithItem((Item) BombermanGame.board.getEntities().get(i));
            }
        }
        for (int i = 0; i < BombermanGame.board.getEnemies().size(); i++) {
            if (BombermanGame.board.getEnemies().get(i) instanceof Enemy) {
                collideToDie((Enemy) BombermanGame.board.getEnemies().get(i));
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
        if (BombermanGame.keyBoard.left) {
            moveLeft();
            checkToMapMoveLeft();
        } else if (BombermanGame.keyBoard.right) {
            moveRight();
            checkToMapMoveRight();
        } else if (BombermanGame.keyBoard.down) {
            moveDown();
            checkToMapMoveDown();
        } else if (BombermanGame.keyBoard.up) {
            moveUp();
            checkToMapMoveUp();
        }
    }

    public int xBomb() {
        if (this.x == (int) this.x) return (int) this.x;
        double difference = this.x - (int) this.x;
        return (difference >= 0.64) ? (int) this.x + 1 : (int) this.x;

    }

    public int yBomb() {
        if (this.y == (int) this.y) return (int) this.y;
        double difference = this.y - (int) this.y;
        return (difference >= 0.64) ? (int) this.y + 1 : (int) this.y;
    }

    public void checkToMapMoveRight() {
        double widthFrameNow = 24;
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

    public void collideToDie(Entity obj) {
        if (alive) {
            HashSet<String> maskPlayer1 = getMask(this);
            HashSet<String> maskPlayer2 = getMask(obj);
            int size = maskPlayer2.size();
            maskPlayer1.retainAll(maskPlayer2);
            if (maskPlayer1.size() > 0) {
                setAlive(false);
                Sound.stop();

                Sound.play("AA126_11");

            }
        }
    }


    public void collideWithItem(Item obj) {
        if (alive) {
            HashSet<String> maskPlayer1 = getMask(this);
            HashSet<String> maskPlayer2 = getMask(obj);
            int size = maskPlayer2.size();
            maskPlayer1.retainAll(maskPlayer2);
//            if (obj instanceof Portal) {
//                Portal other = (Portal) obj;
//                if (other.getActive()) {
//                    if (maskPlayer1.size() > 0) {
//                        Sound.play("CRYST_UP");
//                    }
//                }
//            } else {
            if (!(obj instanceof Portal)) {
                if (maskPlayer1.size() > 0) {
                    obj.setActive(true);
                    Sound.play("Item");
                }
            }
        }
    }

    public void updateWallFromBomb(Bomb bomb) {
        if (alive) {
            HashSet<String> maskBomb = getMask(bomb);
            HashSet<String> maskPlayer = getMask(this);
            maskBomb.retainAll(maskPlayer);
            if (maskBomb.size() == 0) {
                Board.map[(int) bomb.getY()][(int) bomb.getX()] = 'B';
            }
        }
    }

}