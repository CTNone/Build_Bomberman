package uet.oop.bomberman.graphics;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;

import javafx.scene.text.*;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 14;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();


        //Them time va score len dau man choi
        Text txt1 = new Text("Time: ");
        Text txt2 = new Text("Score: 0");
        txt1.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        txt1.setFill(Color.WHITE);
        //vi tri cua time trong stage
        txt1.setX(150);
        txt1.setY(20);
        txt2.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        txt2.setFill(Color.WHITE);
        //vi tri cua score trong stage
        txt2.setX(600);
        txt2.setY(20);


        //text de tinh thoi gian choi
        Text time = new Text("0");
        time.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        time.setFill(Color.WHITE);
        time.setX(190);
        time.setY(20);

        // Tao root container
        Group root = new Group();
        //them time va score vao root
        root.getChildren().add(txt1);
        root.getChildren().add(txt2);
        root.getChildren().add(time);
        //
        root.getChildren().add(canvas);


        // Tao scene
        Scene scene = new Scene(root);
        //fill black cho scence
        scene.setFill(Color.BLACK);

        // Văn bản của số sẽ được tăng lên mỗi giây
        /**new java.util.Timer().schedule(new java.util.TimerTask() {
            int i = 0;

            @Override
            public void run() {
                i++;

                // Cập nhật văn bản số trong luồng UI
                javafx.application.Platform.runLater(() -> {
                    time.setText(Integer.toString(i));
                });

                // Nếu số đạt 10 thì ngừng
                if (i == 10) {
                    // Loại bỏ số khỏi Scene
                    javafx.application.Platform.runLater(() -> {
                        scene.getRoot().getChildrenUnmodifiable().remove(time);
                    });
                    this.cancel();
                }
            }
        }, 0, 1000);*/




        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("BombermenGame");
        stage.show();

        createMap();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();



        Entity bomberman = new Bomber(1, 2, Sprite.player_right.getFxImage());
        entities.add(bomberman);

    }

    public void createMap() {
        // Mở file map.txt
        File file = new File("C:\\Users\\nguyen van khanh\\IdeaProjects\\bomberman-starter\\res\\levels\\levell1.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

// Đọc nội dung file vào một danh sách các chuỗi
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

// Duyệt từng chuỗi trong danh sách
        for (int y = 0; y <= lines.size(); y++) {
            String line = lines.get(y);

            // Tạo đối tượng Wall hoặc Brick tại mỗi vị trí
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                Entity object;
                if (ch == '#') {
                    // Tạo đối tượng Wall tại vị trí (x, y)
                    object = new Wall( x, y + 1, Sprite.wall.getFxImage());
                } else if (ch == '*') {
                    // Tạo đối tượng Brick tại vị trí (x, y)
                    object = new Brick(x , y + 1, Sprite.brick.getFxImage());
                } else if (ch == 'X') {
                    // Tạo đối tượng Grass tại vị trí (x, y)
                    object = new Portal(x, y + 1, Sprite.portal.getFxImage());
                } else if (ch == '1') {
                    object = new Bomber(x, y + 1, Sprite.balloom_left1.getFxImage());
                } else if (ch == '2') {
                    object = new Oneal(x, y + 1, Sprite.oneal_right1.getFxImage());
                } else if (ch == 'b') {
                    object = new BombItem(x, y + 1, Sprite.powerup_bombs.getFxImage());
                } else if (ch == 'f') {
                    object = new FlameItem(x, y + 1, Sprite.powerup_flames.getFxImage());
                } else if (ch == 's') {
                    object = new SpeedItem(x, y + 1, Sprite.powerup_speed.getFxImage());
                } else {
                    object = new Grass(x, y + 1, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }

        scanner.close();

    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
