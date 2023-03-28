package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20; // rộng
    public static final int HEIGHT = 15; // dài
    
    private GraphicsContext gc;
    private Canvas canvas; //CTN: sử duụng GraphicsContext và  Canvas để vẽ các đối tượng lên màn hình
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
//CTN : entities và stillObjects lưu trữ Bomber, Wall, Grass.
    //public static Animal player;


    @Override
    public void start(Stage stage) { // khởi tạo trò chơi
        // Tao Canvas, < CTN: tạo cửa sổ >
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT); // khởi tạo canvas
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group(); // khởi tạo Group trống
        root.getChildren().add(canvas); // thêm đối tượng Canvas vào Group

        Scene scene = new Scene(root); // Tạo ra một `Scene` mới bằng cách truyền `Group` đã tạo vào `Scene`

        // Thêm scene vao stage
        stage.setScene(scene);
        stage.show(); // hiển thị stage lên màn hình

        AnimationTimer timer = new AnimationTimer() { //CTN: cập nhật và hiển thị khung hình thường xuyên
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        int x=18, y=13;
       //Entity bomberman = new Bomber( x, y, Sprite.player_right.getFxImage());
     //  entities.add(bomberman); // thêm bomberman vào danh sách khi đó  sẽ được vẽ và cập nhật trong trò chơi.
        Bomber bomberman = new Bomber(18, 13, Sprite.player_right.getFxImage());
        entities.add(bomberman);
       // canvas.setOnKeyPressed(e -> canvas.requestFocus());

        scene.setOnKeyPressed(e -> {

            switch (e.getCode()) {
                case UP:
                    bomberman.moveUp();
                    break;
                case DOWN:
                    bomberman.moveDown();
                    break;
                case LEFT:
                    bomberman.moveLeft();
                    break;
                case RIGHT:
                    bomberman.moveRight();
                    break;
                default:
                    break;
            }
        });

    }
    public void createMap() { // CTN: tạo một map cho trò chơi với lưới thảm cỏ và tường đá xung quanh
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
//xử lý tường đá xung quanh
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object); // `Wall` và `Grass được lưu trong stillObject
            }
        }
    }
    public void update() { //CTN: cập nhật thông tin mới nhất về các đối tượng
        entities.forEach(Entity::update); //CTN: duyệt qua từng đối tượng trong entities
    }
    public void render() { //CTN: vẽ các đối tượng đã được cập nhật lên khung hình.
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //CTN: xóa bỏ khung hình trước đó
        stillObjects.forEach(g -> g.render(gc)); //CTN:  vẽ đối tượng trong stillObjects
        entities.forEach(g -> g.render(gc)); // CTN:vẽ đối tượng trong entities
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
        //new MyFrame();
        //MyFrame() F = new My;

    }
}

