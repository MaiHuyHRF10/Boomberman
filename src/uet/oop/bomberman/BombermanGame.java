package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class BombermanGame extends Application {
    public static GraphicsContext gc;
    public static Canvas canvas;
    public static GraphicsContext gcForPlayer;
    public static Scene gameScene;
    private java.util.List<Text> textList = new ArrayList<>();

    public static Board board;
    public static KeyBoard keyBoard;
    public static Text textScore;
    public static Text textTime;

    private static final int MENU_WIDTH = 992;
    private static final int MENU_HEIGHT = 480;
    private AnchorPane menuPane;
    private Scene menuScene;
    private Stage menuStage;
    private Button startButton;




    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }



    public void start(Stage stage) {
        createMenu();
        stage = menuStage;
        Stage finalStage = stage;
        stage.show();

        startButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    initializeStage();
                    finalStage.setScene(gameScene);
                    finalStage.show();

                    AnimationTimer timer = new AnimationTimer() {
                        @Override
                        public void handle(long l) {
                            update();
                            board.render();
                            board.update();
                        }
                    };

                    timer.start();
                    try {
                        initNewGame();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    keyBoard.status(gameScene); // bat su kien
                    Sound.play("ghost");
                    board.countDown();
                }

            }
        });

    }


    public void initNewGame () throws FileNotFoundException {
            board = new Board();
            keyBoard = new KeyBoard();
            board.getGameLevel().createMapLevel(1);
    }

    public void createTextScene () {
        Text textS = new Text(30, 35, "Score: ");
        Text textT = new Text(300, 35, "Time: ");

        textS.setFill(Color.WHITE);
        textS.setFont(new Font(20));

        textT.setFill(Color.WHITE);
        textT.setFont(new Font(20));

        textList.add(textS);
        textList.add(textT);

        textScore = new Text(130, 35, String.valueOf(board.score));
        textScore.setFill(Color.WHITE);
        textScore.setFont(new Font(20));

        textList.add(textScore);

        textTime = new Text(400, 35, String.valueOf(board.countDownTime / 60));
        textTime.setFill(Color.WHITE);
        textTime.setFont(new Font(20));

        textList.add(textTime);
    }

    public void initializeStage () {
        canvas = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();


        Canvas canvasForPlayer = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gcForPlayer = canvasForPlayer.getGraphicsContext2D();

        createTextScene();
        Group gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().add(canvasForPlayer);

        gameRoot.getChildren().addAll(textList);

        gameScene = new Scene(gameRoot, Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2), Color.BLACK);

    }

    public void update () {
        textScore.setText(String.valueOf(board.score));
        textTime.setText(String.valueOf(board.countDown() / 60));
    }



    private void createMenu() {
        menuPane = new AnchorPane();
        menuScene = new Scene(menuPane, MENU_WIDTH, MENU_HEIGHT, Color.BLACK);
        menuStage = new Stage();
        menuStage.setScene(menuScene);
        createBackGround();
        createStartButton();
    }

    private void createStartButton () {
        InputStream input = getClass().getResourceAsStream("/button/start.png");

        javafx.scene.image.Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        startButton = new Button("", imageView);
        startButton.setStyle("-fx-background-color: #000000; ");

        menuPane.getChildren().add(startButton);
        startButton.setLayoutX(450);
        startButton.setLayoutY(350);

    }

    private void createBackGround () {
        Image back = new Image("/background/bomba.png", MENU_WIDTH, MENU_HEIGHT, false, true);
        BackgroundImage backMenu = new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        menuPane.setBackground(new Background(backMenu));
    }

}
