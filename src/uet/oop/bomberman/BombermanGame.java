package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private GraphicsContext gc;
    private Canvas canvas;
    private Canvas canvasForPlayer;
    private GraphicsContext gcForPlayer;


    private Scene gameScene;
    private Group gameRoot;
    public static char[][] map = new char[HEIGHT][WIDTH];
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    Bomber player;


    private double speedOfPlayer = 0.05;

    public static void main(String[] args) throws FileNotFoundException {
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
        player = new Bomber(1, 1, Sprite.player_right.getFxImage(), speedOfPlayer);
        entities.add(player);


    }

    public void initializeStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        canvasForPlayer = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gcForPlayer = canvasForPlayer.getGraphicsContext2D();

        gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().add(canvasForPlayer);

        // Tao scene
        gameScene = new Scene(gameRoot);

    }

    public void createMap() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("C:\\Users\\DELL\\OneDrive\\Máy tính\\Boomberman\\res\\levels\\Level1.txt"));
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
                Entity object;
                if (map[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (map[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
        stillObjects.forEach(g -> g.render(gc));
    }

    public void update() {
            entities.forEach(Entity::update);
    }


    public void render() {
        gcForPlayer.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(g -> g.render(gcForPlayer));
    }



    public void creatKeyListener() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        player.setLeftKeyPress(false);
                        break;
                    case RIGHT:
                        player.setRightKeyPress(false);
                        break;
                    case UP:
                        player.setUpKeyPress(false);
                        break;
                    case DOWN:
                        player.setDownKeyPress(false);
                        break;
                }
            }
        });

    }


}
