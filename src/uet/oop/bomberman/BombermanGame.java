package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static char[][] map = new char[HEIGHT][WIDTH];
    private GraphicsContext gc;
    private Canvas canvas;
    private GraphicsContext gcForPlayer;
    private Scene gameScene;
    public static List<Entity> entities = new ArrayList<>();
    private final List<Entity> stillObjects = new ArrayList<>();
    private Bomber player;
    private Bomb bomb = new Bomb();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // Tao Canvas
        initializeStage();
        stage.setScene(gameScene);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render();
            }
        };
        timer.start();

        createMap();

        creatKeyListener();
        double speedOfPlayer = 0.05;
        player = new Bomber(1, 1, Sprite.player_right.getFxImage(), speedOfPlayer);
        entities.add(player);


    }

    public void initializeStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Canvas canvasForPlayer = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gcForPlayer = canvasForPlayer.getGraphicsContext2D();

        Group gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().add(canvasForPlayer);

        // Tao scene
        gameScene = new Scene(gameRoot);

    }

    public void createMap() throws FileNotFoundException {

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
        stillObjects.forEach(g -> g.render(gc));
    }

    public void update() {
        entities.forEach(Entity::update);
        bomb.update();

    }


    public void render() {
        gcForPlayer.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(g -> g.render(gcForPlayer));
        bomb.render(gcForPlayer);
    }


    public void creatKeyListener() {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player.setLeftKeyPress(true);
                    break;
                case RIGHT:
                    player.setRightKeyPress(true);
                    break;
                case UP:
                    player.setUpKeyPress(true);
                    break;
                case DOWN:
                    player.setDownKeyPress(true);
                    break;
                case SPACE:
                    bomb = new Bomb((int) player.getX(), (int) player.getY(), false, Sprite.bomb.getFxImage());
            }
        });

        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player.setLeftKeyPress(false);
                    player.setImg(Sprite.player_left.getFxImage());
                    break;
                case RIGHT:
                    player.setRightKeyPress(false);
                    player.setImg(Sprite.player_right.getFxImage());
                    break;
                case UP:
                    player.setUpKeyPress(false);
                    break;
                case DOWN:
                    player.setDownKeyPress(false);
                    break;
            }
        });

    }

    public Entity getEntity(double x, double y) {
        for (Entity temp : entities) {
            if (temp.getX() == x && temp.getY() == y) return temp;
        }
        return null;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
