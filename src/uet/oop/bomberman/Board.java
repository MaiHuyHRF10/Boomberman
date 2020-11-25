package uet.oop.bomberman;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.*;
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
    public static List<Entity> entities = new ArrayList<>();
    private final List<Entity> stillObjects = new ArrayList<>();
    private Bomber player;
    private Balloom balloom;
    private Oneal oneal;

    public Board() {
        double speedOfPlayer = 0.05;
        player = new Bomber(1, 1, Sprite.player_right.getFxImage(), speedOfPlayer);
        balloom = new Balloom(5, 5, Sprite.balloom_right1.getFxImage(), 0.025);
        oneal = new Oneal(25, 11, Sprite.oneal_right1.getFxImage(), 0.025);
        entities.add(player);
        entities.add(balloom);
        entities.add(oneal);
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
                if (map[i][j] == '*') {
                    Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                    entities.add(object);
                } else if (map[i][j] == 's') {
                    Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                    SpeedItem objectBelow1 = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                    entities.add(object);
                    object.addEntityBelow(objectBelow1);
                } else if (map[i][j] == 'b') {
                    Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                    BombItem objectBelow1 = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                    entities.add(object);
                    object.addEntityBelow(objectBelow1);
                } else if (map[i][j] == 'f') {
                    Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                    FlameItem objectBelow1 = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                    entities.add(object);
                    object.addEntityBelow(objectBelow1);
                }
            }
        }
    }

    public void removeEntityAt( double x, double y) {
        for (int i = 0; i< entities.size(); i++) {
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
    public List<Entity> getEntities() {
        return this.entities;
    }

    public List<Entity> getStillObjects() {
        return this.stillObjects;
    }

    public Bomber getPlayer() {
        return this.player;
    }

    public void setPlayer(Bomber player) {
        this.player = player;
    }

    public Balloom getBalloom() {
        return this.balloom;
    }

    public void setBalloom(Balloom balloom) {
        this.balloom = balloom;
    }

    public Oneal getOneal() {
        return oneal;
    }

    public void setOneal(Oneal oneal) {
        this.oneal = oneal;
    }


}
