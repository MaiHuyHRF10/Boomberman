package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.enemy.Balloom;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {
    private Board boardGame;

    public Level(Board boardGame) {
        this.boardGame = boardGame;
    }

    public void createMapLevel(int level) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("res/levels/Level" + level + ".txt"));
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                Board.map[row][i] = line.charAt(i);
            }
            row++;
        }

        for (int i = 0; i < Board.HEIGHT; i++) {
            for (int j = 0; j < Board.WIDTH; j++) {
                if (Board.map[i][j] == '#') {
                    Wall object = new Wall(j, i, Sprite.wall.getFxImage());

                    boardGame.addStillObject(object);
                } else {
                    Grass object = new Grass(j, i, Sprite.grass.getFxImage());
                    boardGame.addStillObject(object);
                }

                switch (Board.map[i][j]) {
                    case '*':
                        Brick brick = new Brick(j, i, Sprite.brick.getFxImage());
                        boardGame.addEntity(brick);
                        break;
                    case 's':
                        Brick bricks = new Brick(j, i, Sprite.brick.getFxImage());
                        SpeedItem objectBelow1 = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        boardGame.addEntity(bricks);
                        bricks.addEntityBelow(objectBelow1);
                        break;
                    case 'b':
                        Brick brickb = new Brick(j, i, Sprite.brick.getFxImage());
                        BombItem objectBelow2 = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        boardGame.addEntity(brickb);
                        brickb.addEntityBelow(objectBelow2);
                        break;
                    case 'f':
                        Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                        FlameItem objectBelow3 = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        boardGame.addEntity(object);
                        object.addEntityBelow(objectBelow3);
                        break;
                    case '1':
                        Board.map[i][j] = ' ';
                        Balloom balloom = new Balloom(j, i, Sprite.balloom_right1.getFxImage(), Board.speedOfEnemy);
                        boardGame.addEnemy(balloom);
                        break;
                    case '2':
                        Board.map[i][j] = ' ';
                        Oneal newOneal = new Oneal(j, i, Sprite.oneal_right1.getFxImage(), Board.speedOfEnemy * 1.25);
                        boardGame.addEnemy(newOneal);
                        break;
                    case '3':
                        Board.map[i][j] = ' ';
                        Doll doll = new Doll(j, i, Sprite.doll_right1.getFxImage(), Board.speedOfEnemy * 1.25);
                        boardGame.addEnemy(doll);
                        break;
                    case '4':
                        Board.map[i][j] = ' ';
                        Kondoria kondoria = new Kondoria(j, i, Sprite.kondoria_right1.getFxImage(), Board.speedOfEnemy);
                        boardGame.addEnemy(kondoria);
                        break;
                    case 'x':
                        Brick objectx = new Brick(j, i, Sprite.brick.getFxImage());
                        Portal objectBelow4 = new Portal(j, i, Sprite.portal.getFxImage());
                        boardGame.addEntity(objectx);
                        objectx.addEntityBelow(objectBelow4);
                        break;
                    case 'l':
                        Brick objectl = new Brick(j, i, Sprite.brick.getFxImage());
                        FlamePass objectBelow5 = new FlamePass(j, i, Sprite.powerup_flamepass.getFxImage());
                        boardGame.addEntity(objectl);
                        objectl.addEntityBelow(objectBelow5);
                        break;
                    case 'o':
                        Brick objecto = new Brick(j, i, Sprite.brick.getFxImage());
                        BombPass objectBelow6 = new BombPass(j, i, Sprite.powerup_bombpass.getFxImage());
                        boardGame.addEntity(objecto);
                        objecto.addEntityBelow(objectBelow6);
                        break;
                    case 'a':
                        Brick objecta = new Brick(j, i, Sprite.brick.getFxImage());
                        WallPass objectBelow7 = new WallPass(j, i, Sprite.powerup_wallpass.getFxImage());
                        boardGame.addEntity(objecta);
                        objecta.addEntityBelow(objectBelow7);
                        break;
                    case 'd':
                        Brick objectd = new Brick(j, i, Sprite.brick.getFxImage());
                        Detonator objectBelow8 = new Detonator(j, i, Sprite.powerup_detonator.getFxImage());
                        boardGame.addEntity(objectd);
                        objectd.addEntityBelow(objectBelow8);
                        break;
                }
            }
        }
        Board.getPlayer().setX(1);
        Board.getPlayer().setY(1);
        Board.score = 0;
        BombermanGame.keyBoard.right = false;
        BombermanGame.keyBoard.left = false;
        BombermanGame.keyBoard.up = false;
        BombermanGame.keyBoard.down = false;
        Board.getPlayer().setImg(Sprite.player_right.getFxImage());
        boardGame.addEntity(Board.getPlayer());
    }
}
