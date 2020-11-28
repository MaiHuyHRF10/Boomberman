package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.Portal;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static char[][] map = new char[HEIGHT][WIDTH];
    public static int bombCount = 1;
    public static int bombRadius = 1;
    public static int score = 0;
    public static int countDownTime = 181 * 60;

    public static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Enemy> enemies = new ArrayList<>();
    private double speedOfEnemy = 0.025;
    private static Bomber player;


    public Board() {
        double speedOfPlayer = 0.05;
        player = new Bomber(1, 1, Sprite.player_right.getFxImage(), speedOfPlayer);

        entities.add(player);

    }

    public int countDown() {
        countDownTime--;
        return countDownTime;
    }
    public void createMapLevel1() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("res/levels/Level1.txt"));
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                map[row][i] = line.charAt(i);
            }
            row++;
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[i][j] == '#') {
                    Wall object = new Wall(j, i, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                } else {
                    Grass object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                }

                switch (map[i][j]) {
                    case '*':
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage());
                        entities.add(brick);
                        break;
                    case 's':
                        Brick bricks = new Brick(j, i, Sprite.brick.getFxImage());
                        SpeedItem objectBelow1 = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        entities.add(bricks);
                        bricks.addEntityBelow(objectBelow1);
                        break;
                    case 'b':
                        Brick brickb = new Brick(j, i, Sprite.brick.getFxImage());
                        BombItem objectBelow2 = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        entities.add(brickb);
                        brickb.addEntityBelow(objectBelow2);
                        break;
                    case 'f':
                        Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                        FlameItem objectBelow3 = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        entities.add(object);
                        object.addEntityBelow(objectBelow3);
                        break;
                    case '1':
                        map[i][j] = ' ';
                        Balloom balloom = new Balloom(j, i, Sprite.balloom_right1.getFxImage(), speedOfEnemy);
                        enemies.add(balloom);
                        break;
                    case '2':
                        map[i][j] = ' ';
                        Oneal newOneal = new Oneal(j, i, Sprite.oneal_right1.getFxImage(), speedOfEnemy * 1.25);
                        enemies.add(newOneal);
                        break;
                    case '3':
                        map[i][j] = ' ';
                        Doll doll = new Doll(j, i, Sprite.doll_right1.getFxImage(), speedOfEnemy * 1.25);
                        enemies.add(doll);
                        break;
                    case '4':
                        map[i][j] = ' ';
                        Kondoria kondoria = new Kondoria(j, i, Sprite.kondoria_right1.getFxImage(), speedOfEnemy);
                        enemies.add(kondoria);
                        break;
                    case 'x':
                        Brick objectx = new Brick(j, i, Sprite.brick.getFxImage());
                        Portal objectBelow4 = new Portal(j, i, Sprite.portal.getFxImage());
                        entities.add(objectx);
                        objectx.addEntityBelow(objectBelow4);
                        break;
                }
            }
        }
    }

    public void removeEntityAt(double x, double y) {
        for (int i = 0; i < entities.size(); i++) {
            Entity temp = entities.get(i);
            if (temp.getX() == x && temp.getY() == y ) {
                entities.remove(i);
                break;
            }
        }
    }

    public void addEntity(Entity object) {
        entities.add(object);
    }

    public void addStillObject(Entity object) {
        stillObjects.add(object);
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public List<Entity> getStillObjects() {
        return this.stillObjects;
    }

    public static Bomber getPlayer() {
        return player;
    }

    public void setPlayer(Bomber player) {
        this.player = player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemyAt(double x, double y) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy temp = enemies.get(i);
            if (temp.getX() == x && temp.getY() == y) {
                enemies.remove(temp);
            }
        }
    }
}
