package gametemplate;

import gametemplate.gameobject.BouncingBall;
import gametemplate.gameobject.GameObject;
import gametemplate.gameobject.MaxObjectsException;
import gametemplate.gameobject.StaticBrick;
import gametemplate.graphics.Rect;
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
        primaryStage.setTitle("Hello World");

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
        Scene scene = new Scene(root, 800, 600);
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
        try {
            new BouncingBall();
            new StaticBrick(new Rect(20,400,300,10));
            new StaticBrick(new Rect(400,200,200,30));
        } catch (MaxObjectsException e) {
            throw new MaxObjectsException("Increase MAX_OBJECTS");
        }
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                screenDelta.setX(-2);
                break;
            case RIGHT:
                screenDelta.setX(2);
                break;
            case UP:
                screenDelta.setY(-2);
                break;
            case DOWN:
                screenDelta.setY(2);
                break;
            case SPACE:
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
                screenDelta.setY(0);
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
        Main.canvas = new Canvas(800, 600);
        Main.screenDelta = new Vector2(0,0);
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