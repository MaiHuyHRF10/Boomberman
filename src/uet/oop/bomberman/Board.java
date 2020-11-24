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
                Entity object, object1;
                if (map[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                } else if (map[i][j] == ' ') {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                } else {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    object1 = new Grass(j, i, Sprite.grass.getFxImage());
                    entities.add(object);
                    stillObjects.add(object1);
                }
            }
        }
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
