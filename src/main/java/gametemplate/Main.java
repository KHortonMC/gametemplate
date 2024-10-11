package gametemplate;

import gametemplate.gameobject.GameObject;
import gametemplate.gameobject.MaxObjectsException; 
import gametemplate.graphics.Vector2;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    static Canvas canvas = null;
    static Vector2 screenDelta = null;
    public static double getWindowWidth() { return Main.canvas.getWidth(); }
    public static double getWindowHeight() { return Main.canvas.getHeight(); }
    public static Vector2 getScreenDelta() { return Main.screenDelta; }

    // this start method came from online documents about JavaFX
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("gametemplate.fxml"));
        primaryStage.setTitle("Tetris");

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a Group to hold the Canvas
        Group root = new Group();
        root.getChildren().add(canvas);

        // chatGPT used for a text area
        TextArea textArea = new TextArea();
        textArea.setOpacity(0);
        textArea.setOnKeyPressed(this::handleKeyPress);
        textArea.setOnKeyReleased(this::handleKeyRelease);
        root.getChildren().add(textArea);

        // Set up the Scene
        Scene scene = new Scene(root, Tetris.SCREEN_WIDTH, Tetris.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        buildObjects();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                update(); // Update game logic
                draw(gc); // Draw the frame
            }
        };
        gameLoop.start();
    }

    private void buildObjects() throws MaxObjectsException {
        Tetris.buildObjects();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                screenDelta.setX(-1);
                break;
            case RIGHT:
                screenDelta.setX(1);
                break;
            case UP:
                // do nothing for up
                break;
            case DOWN:
                screenDelta.setY(1);
                break;
            case SPACE:
                if (Tetris.activePiece != null) {
                    Tetris.activePiece.rotate();
                }
            default:
                break;
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                screenDelta.setX(0);
                break;
            case RIGHT:
                screenDelta.setX(0);
                break;
            case UP:
                // do nothing on up
                break;
            case DOWN:
                screenDelta.setY(0);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        // Create a Canvas for drawing
        Main.canvas = new Canvas(Tetris.SCREEN_WIDTH, Tetris.SCREEN_HEIGHT);
        Main.screenDelta = new Vector2(0,0);
        GameObject.initialize();
        launch(args);
    }

    private void update() {
        GameObject.updateAll();
    }

    private void draw(GraphicsContext gc) {
        // clear the draw pane
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, Main.getWindowWidth(), Main.getWindowHeight());

        GameObject.drawAll(gc);
    }
}