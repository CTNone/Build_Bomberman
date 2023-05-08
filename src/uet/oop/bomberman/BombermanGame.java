package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import uet.oop.bomberman.control.menu;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.control.Move;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


import static uet.oop.bomberman.SoundManager.updateSound;
import static uet.oop.bomberman.control.menu.*;
import static uet.oop.bomberman.levels.NextLevel.waitToLevelUp;
import static uet.oop.bomberman.levels.NextLevel.*;
import static uet.oop.bomberman.entities.Portal.*;

public class BombermanGame extends Application {

    public static final int WIDTH = 25;//31;//20; // rộng
    public static final int HEIGHT = 15;//14; // dài
    public static int width = 0; // chieu rộng của mảng id_object, mảng này lưu trứ các đôi tượng tường, cỏ...
    public static int height = 0;// chieu dài của mảng id_object
    public static int level = 1;// level ban đầu =1 , được gọi lần đầu trong creatmap()
    public static final List<Entity> block = new ArrayList<>();     // Chứa các thực thể cố định
    public static int[][] id_objects; // mảng 2 chiều chứa ID của các đối tượng trên màn hình game.
    public static List<Animal> enemy = new ArrayList<>();
    public static int[][] list_kill; // danh sách ô vuông đánh dấu là phá hủy trên duong bomb no

    public static Animal player;
    public static boolean running;
    public static ImageView author_view;
    private GraphicsContext gc;

    private Canvas canvas; //CTN: sử duụng GraphicsContext và  Canvas để vẽ các đối tượng lên màn hình

    private int frame = 1;
    private long last_time;

    public static Stage main_stage = null;
    public static boolean isPause = false;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        canvas.setTranslateY(32);
        gc = canvas.getGraphicsContext2D();
        Image author = new Image("images/author.png");
        author_view = new ImageView(author);
        author_view.setX(-400);
        author_view.setY(-208);
        author_view.setScaleX(0.5);
        author_view.setScaleY(0.5);
        Group root = new Group();

        menu.createMenu(root); // Khởi chạy tu level 1   ở đây
        root.getChildren().add(canvas);
        root.getChildren().add(author_view);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> { // giữ phím
            if(player.isLife()) {
                switch (event.getCode()) {
                    case DOWN:
                      //  System.out.println("down");
                        Move.down(player); // truyền đối tượng được di chuyển vào phương thức down
                        break;
                    case UP:
                       // System.out.println("up");
                        Move.up(player);
                        break;
                    case LEFT:
                     //   System.out.println("left");
                        Move.left(player);
                        break;
                    case RIGHT:
                     //   System.out.println("right");
                        Move.right(player);
                        break;
                    case SPACE:
                        Bomb.putBomb();
                        break;
                    case P:
                        isPause = !isPause;
                        break;
                    default:
                        break;
                }
            }
        });

        stage.setScene(scene);
        stage.setTitle("Bomberman ");
        Image icon = new Image("images/ttsalpha4.0@0.5x.png");
        stage.getIcons().add(icon);
        main_stage = stage;
        main_stage.show();

        last_time = System.currentTimeMillis();

        AnimationTimer timer = new AnimationTimer() { //CTN: cập nhật và hiển thị khung hình thường xuyên
            @Override
            public void handle(long l) { // vòng lặp của game
                if (running) {
                    render();
                    if(!isPause){ // nếu chưa dừng lại
                        update();
                        time();
                    }
                    updateMenu();
                }
            }
        };
        timer.start();

        player = new Bomber(1, 2, Sprite.player_right_2.getFxImage());
        player.setLife(false);

    }
    public void update() { //CTN: cập nhật thông tin
        block.forEach(Entity::update);
        enemy.forEach(Entity::update);
        player.update();

        player.setCountToRun(player.getCountToRun() + 1);
        if (player.getCountToRun() == 4) {
            Move.checkRun(player);
            player.setCountToRun(0);
        }

        for (Animal a : enemy) {
            a.setCountToRun(a.getCountToRun() + 1);
            if (a.getCountToRun() == 8) {
                Move.checkRun(a);
                a.setCountToRun(0);
            }
        }

        if (enemy.size() == 0 && !is_portal && ! wait) {
            Entity portal = new Portal(WIDTH -2, HEIGHT-2, Sprite.portal.getFxImage());
            block.add(portal);
            if (player.getX() / 32 == portal.getX() / 32 && player.getY() / 32 == portal.getY() / 32) {
                wait = true;
                waiting_time = System.currentTimeMillis();
            }
        }
        waitToLevelUp();
        updateSound();
    }
    public void render() { //CTN: vẽ các đối tượng
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //CTN: xóa bỏ khung hình trước đó
        block.forEach(g -> g.render(gc));
        enemy.forEach(g -> g.render(gc));
        player.render(gc);
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void time() {

        long now = System.currentTimeMillis();
        if (now - last_time > 1000) { // 1s cập nhật 1 lần
            last_time = System.currentTimeMillis(); // thời gian lần cuối câập nhật
           main_stage.setTitle("Bomberman " );// tiêu đề

            time.setText("Time: " + time_number);
            time_number--;
            if (time_number < 0) {
                player.setLife(false);
            }
        }
    }
}



