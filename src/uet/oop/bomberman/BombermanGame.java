package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;

public class BombermanGame extends Application {
    private GraphicsContext gc;
    private Canvas canvas;
    private GraphicsContext gcForPlayer;
    private Scene gameScene;
    public static Board board = new Board();
    public static KeyBoard keyBoard;

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
    }

    public void initNewGame() throws FileNotFoundException {
        keyBoard = new KeyBoard();
        board.createMapLevel1();
    }

    public void initializeStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * Board.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Canvas canvasForPlayer = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * Board.HEIGHT);
        gcForPlayer = canvasForPlayer.getGraphicsContext2D();

        Group gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().add(canvasForPlayer);

        // Tao scene
        gameScene = new Scene(gameRoot);

    }

    public void update() {
        for (int i = 0; i < board.getEntities().size(); i++) {
            board.getEntities().get(i).update();
        }
    }

    public void render() {

        gcForPlayer.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        board.getStillObjects().forEach(g -> g.render(gc));
        board.getEntities().forEach(g -> g.render(gcForPlayer));
    }

    public Entity getEntity(double x, double y) {
        for (Entity temp : board.getEntities()) {
            if (temp.getX() == x && temp.getY() == y) return temp;
        }
        return null;
    }

}
