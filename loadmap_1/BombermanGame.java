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
    
    public static final int WIDTH = 20; // rộng
    public static final int HEIGHT = 14; // dài
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
       // int x=18, y=13;
        Bomber bomberman = new Bomber(1, 2, Sprite.player_right.getFxImage());
        entities.add(bomberman); // thêm bomberman vào danh sách khi đó  sẽ được vẽ và cập nhật trong trò chơi.

        scene.setOnKeyPressed(e -> { // di chuyển

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
    public void createMap()  { // CTN: tạo một map cho trò chơi với lưới thảm cỏ và tường đá xung quanh

      /*  for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }

//                }

                stillObjects.add(object); // `Wall` và `Grass được lưu trong stillObject
            }
        }
       */
        // Mở file map.txt
        File file = new File("src/uet/oop/bomberman/levels/level 1.txt").getAbsoluteFile();
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
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);

            // Tạo đối tượng Wall hoặc Brick tại mỗi vị trí
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                Entity object;
                if (ch == '#') {
                    // Tạo đối tượng Wall tại vị trí (x, y)
                    object = new Wall(x, y, Sprite.wall.getFxImage());

                    //board.addEntity(x + y * width, wall);
                } else if (ch == '*') {
                    // Tạo đối tượng Brick tại vị trí (x, y)
                    object = new Brick(x, y, Sprite.brick.getFxImage());
                } else {
                    // Tạo đối tượng Grass tại vị trí (x, y)
                    object = new Grass(x, y, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }

// Đóng file
        scanner.close();


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

