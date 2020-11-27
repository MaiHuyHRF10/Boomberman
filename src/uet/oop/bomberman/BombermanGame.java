package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.awt.*;
import java.io.FileNotFoundException;

public class BombermanGame extends Application {
    private GraphicsContext gc;
    private Canvas canvas;
    private GraphicsContext gcForPlayer;
    private Scene gameScene;
    public static Board board = new Board();
    public static KeyBoard keyBoard;
    public static Text textScore;
    public static Text textTime;

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
                //collide();
                update();
                render();
            }
        };
        timer.start();

        initNewGame();
        keyBoard.status(gameScene); // bat su kien
        Sound.play("ghost");
        board.countDown();
    }

    public void initNewGame() throws FileNotFoundException {
        keyBoard = new KeyBoard();
        board.createMapLevel1();
    }

    public void initializeStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();

        Canvas canvasForPlayer = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gcForPlayer = canvasForPlayer.getGraphicsContext2D();
        Text text = new Text(30, 35, "Score: ");
        Text text1 = new Text(300, 35, "Time: ");
        text.setFill(Color.WHITE);
        text.setFont(new Font(20));
        text1.setFill(Color.WHITE);
        text1.setFont(new Font(20));
        textScore = new Text(130, 35, String.valueOf(board.score));
        textScore.setFill(Color.WHITE);
        textScore.setFont(new Font(20));
        textTime = new Text(400, 35, String.valueOf(board.countDownTime / 60));
        textTime.setFill(Color.WHITE);
        textTime.setFont(new Font(20));
        Group gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().add(canvasForPlayer);
        gameRoot.getChildren().addAll(text, text1, textScore, textTime);

        // Tao scene
        gameScene = new Scene(gameRoot, Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2), Color.BLACK);

    }

    public void update() {
        for (int i = 0; i < board.getEntities().size(); i++) {
            board.getEntities().get(i).update();
        }
        for (int i = 0; i < board.getEnemies().size(); i++) {
            board.getEnemies().get(i).update();
        }
        textScore.setText(String.valueOf(board.score));
        textTime.setText(String.valueOf(board.countDown() / 60));
    }

    public void render() {
        gcForPlayer.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        board.getStillObjects().forEach(g -> g.render(gc));
        board.getEntities().forEach(g -> g.render(gcForPlayer));
        board.getEnemies().forEach(g -> g.render(gcForPlayer));

    }

    public Entity getEntity(double x, double y) {
        for (Entity temp : board.getEntities()) {
            if (temp.getX() == x && temp.getY() == y) return temp;
        }
        return null;
    }

}
