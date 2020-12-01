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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BombermanGame extends Application {
    public static GraphicsContext gc;
    public static Canvas canvas;
    public static GraphicsContext gcForPlayer;
    public static Scene gameScene;
    private java.util.List<Text> textList = new ArrayList<>();

    public static Board board;
    public static KeyBoard keyBoard;
    private Text textScore;
    private Text textTime;
    private Text textLevel;
    private Text playerHealth;

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
                    try {
                        initNewGame();
                        createTextScene();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    initializeStage();
                    finalStage.setScene(gameScene);
                    finalStage.show();

                    AnimationTimer timer = new AnimationTimer() {
                        @Override
                        public void handle(long l) {
                            update();
                            board.render();
                            board.update();
                            if (Board.getPlayer().isDie()  || Board.countDownTime < 0) {
                                String res = "Game Over !!!";
                                endGame(res);
                                finalStage.setScene(gameScene);
                            }
                            if (Board.getPlayer().isWin()) {
                                Board.scorePrevious = 0;
                                BombermanGame.board.setLevel(1);
                                Board.getPlayer().setHealth(3);
                                Board.getPlayer().updateStatus();
                                String res = "YOU WIN !!!";
                                endGame(res);
                                finalStage.setScene(gameScene);
                            }
                        }
                    };

                    timer.start();

                    keyBoard.status(gameScene); // bat su kien
                    Sound.play("ghost");
                    board.countDown();
                }

            }
        });

    }


    public void initNewGame() throws FileNotFoundException {
        board = new Board();
        keyBoard = new KeyBoard();
        board.getGameLevel().createMapLevel(board.getLevel());
    }

    public void createTextScene() {
        Text textS = new Text(30, 35, "Score: ");
        Text textT = new Text(230, 35, "Time: ");

        textS.setFill(Color.WHITE);
        textS.setFont(new Font(20));

        textT.setFill(Color.WHITE);
        textT.setFont(new Font(20));

        textList.add(textS);
        textList.add(textT);

        textScore = new Text(130, 35, String.valueOf(Board.score + Board.scorePrevious));
        textScore.setFill(Color.WHITE);
        textScore.setFont(new Font(20));

        textList.add(textScore);

        textTime = new Text(290, 35, String.valueOf(Board.countDownTime / 60));
        textTime.setFill(Color.WHITE);
        textTime.setFont(new Font(20));

        textList.add(textTime);

        Text textL = new Text(600, 35, "Level: ");
        textL.setFill(Color.WHITE);
        textL.setFont(new Font(20));
        textList.add(textL);

        textLevel = new Text(670, 35, String.valueOf(board.getLevel()));
        textLevel.setFill(Color.WHITE);
        textLevel.setFont(new Font(20));
        textList.add(textLevel);

        Text textLeft = new Text(400, 35, "Left: ");
        textLeft.setFill(Color.WHITE);
        textLeft.setFont(new Font(20));
        textList.add(textLeft);

        playerHealth = new Text(450, 35, String.valueOf(Board.getPlayer().getHealth()));
        playerHealth.setFill(Color.WHITE);
        playerHealth.setFont(new Font(20));
        textList.add(playerHealth);

    }

    public void initializeStage() {
        canvas = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();


        Canvas canvasForPlayer = new Canvas(Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2));
        gcForPlayer = canvasForPlayer.getGraphicsContext2D();

        Group gameRoot = new Group();
        gameRoot.getChildren().add(canvas);
        gameRoot.getChildren().add(canvasForPlayer);

        gameRoot.getChildren().addAll(textList);

        gameScene = new Scene(gameRoot, Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2), Color.BLACK);

    }

    public void update() {
        playerHealth.setText(String.valueOf(Board.getPlayer().getHealth()));
        textLevel.setText(String.valueOf(board.getLevel()));
        textScore.setText(String.valueOf(Board.score + Board.scorePrevious));
        textTime.setText(String.valueOf(board.countDown() / 60));
    }


    private void createMenu() {
        menuPane = new AnchorPane();
        menuScene = new Scene(menuPane, MENU_WIDTH, MENU_HEIGHT);
        menuStage = new Stage();
        menuStage.setScene(menuScene);
        createBackGround();
        createStartButton();
    }

    private void createStartButton() {
        InputStream input = getClass().getResourceAsStream("/button/start.png");

        javafx.scene.image.Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        startButton = new Button("", imageView);
        startButton.setStyle("-fx-background-color: #000000; ");

        menuPane.getChildren().add(startButton);
        startButton.setLayoutX(450);
        startButton.setLayoutY(350);

    }

    private void createBackGround() {
        Image back = new Image("/background/bomba.png", MENU_WIDTH, MENU_HEIGHT, false, true);
        BackgroundImage backMenu = new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        menuPane.setBackground(new Background(backMenu));
    }

    private void endGame(String string) {
        Group gameRoot = new Group();
        Text textOver = new Text(250, 240, string);

        textOver.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        textOver.setFill(Color.WHITE);

        gameRoot.getChildren().add(textOver);
        gameScene = new Scene(gameRoot, Sprite.SCALED_SIZE * Board.WIDTH, Sprite.SCALED_SIZE * (Board.HEIGHT + 2), Color.BLACK);
    }
}
