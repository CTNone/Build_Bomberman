package uet.oop.bomberman.entities;

import uet.oop.bomberman.SoundManager;

import uet.oop.bomberman.control.blocked;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.*;//.RunBomberman.*;
import static uet.oop.bomberman.BombermanGame.player;//.RunBomberman.player;
import static uet.oop.bomberman.control.menu.bomb_number;

public class Bomb extends Entity {
    private static long time_bomb;      //  hẹn giờ phát nổ
    private static long time_tmp;       // Thời gian giữa 2 vụ đánh bom
    private static Entity bomb;
    private static int swap_active = 1;     // Thay đổi trạng thái hoạt động của bom
    private static int swap_explosion = 1;  //  Thay đổi trạng thái nổ của bom
    private static final List<Entity> list_bomb_middle_width = new ArrayList();
    private static final List<Entity> list_bomb_middle_height = new ArrayList();
    public static int power_bomb = 0;   // Sức công phá của bom
    private static int power_bomb_down = 0;     //  Sức công phá của bom từ trên xuống dưới
    private static int power_bomb_up = 0;       // TSức công phá của quả bom là từ dưới lên
    private static int power_bomb_left = 0;     // Sức công phá của quả bom là từ trai sang
    private static int power_bomb_right = 0;    // Sức công phá của quả bom là từ phai sang
    private static Entity edge_down = null;     // Cạnh dưới của khối chặn nhân vật đi qua
    private static Entity edge_up = null;       // Cạnh trên của khối chặn nhân vật đi qua
    private static Entity edge_left = null;     // The left edge of the block blocks the character from going through
    private static Entity edge_right = null;    // The right edge of the block blocks the character from going through
    private static boolean is_edge = false;     // Kiểm tra xem cạnh đó có tồn tại không
    private static boolean is_middle = false;  //   Kiểm tra xem quả bom có phát nổ ở trung tâm không  (plus sign, not T )
    public static int is_bomb = 0;      //  Kiểm tra xem có bom ở đó không: /0 không có bom /1 có bom /2 vụ nổ
    public static int numBoms = 1; // số lượng bom tối đa có thể đặt liên tiếp
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public static void putBomb() {      // sử dụng cho Bomber để đặt bom
        if (is_bomb == 0 && bomb_number > 0) {
            new SoundManager("sound/put_bombs.wav", "putBomb");
            bomb_number--;
            is_bomb = 1;
            time_bomb = System.currentTimeMillis();
            time_tmp = time_bomb;
            int x = player.getX() / 32;
            int y = player.getY() / 32;
            x = Math.round((float)x);
            y = Math.round((float)y);
            bomb = new Bomb(x, y, Sprite.bomb.getFxImage());
            block.add(bomb);
            id_objects[player.getX() / 32][player.getY() / 32] = 4;
        }

    }

    public static void activeBomb() {   // Hiển thị hình ảnh động từ khi quả bom được đặt đến khi nó phát nổ
        if (swap_active == 1) {
            bomb.setImg(Sprite.bomb.getFxImage());
            swap_active = 2;
        }
        else if (swap_active == 2) {
            bomb.setImg(Sprite.bomb_1.getFxImage());
            swap_active = 3;
        }
        else if (swap_active == 3) {
            bomb.setImg(Sprite.bomb_2.getFxImage());
            swap_active = 4;
        }
        else {
            bomb.setImg(Sprite.bomb_1.getFxImage());
            swap_active = 1;
        }

    }

    public static void createEdge() {   // Tạo ra một egde để ngăn chặn chuyển động của nhân vật cũng như phạm vi nổ của quả bom
        int i;
        if (blocked.block_down_bomb(bomb, 0)) {
            edge_down = new Bomb(bomb.getX() / 32, bomb.getY() / 32 + 1, Sprite.bomb_exploded.getFxImage());
            if (power_bomb > 0) {
                for(i = 1; i <= power_bomb && blocked.block_down_bomb(bomb, i); ++i) {
                    edge_down.setY(bomb.getY() + 32 + i * 32);
                    ++power_bomb_down;
                }
            }

            block.add(edge_down);
        }

        if (blocked.block_up_bomb(bomb, 0)) {
            edge_up = new Bomb(bomb.getX() / 32, bomb.getY() / 32 - 1, Sprite.bomb_exploded.getFxImage());
            if (power_bomb > 0) {
                for(i = 1; i <= power_bomb && blocked.block_up_bomb(bomb, i); ++i) {
                    edge_up.setY(bomb.getY() - 32 - i * 32);
                    ++power_bomb_up;
                }
            }

            block.add(edge_up);
        }

        if (blocked.block_left_bomb(bomb, 0)) {
            edge_left = new Bomb(bomb.getX() / 32 - 1, bomb.getY() / 32, Sprite.bomb_exploded.getFxImage());
            if (power_bomb > 0) {
                for(i = 1; i <= power_bomb && blocked.block_left_bomb(bomb, i); ++i) {
                    edge_left.setX(bomb.getX() - 32 - i * 32);
                    ++power_bomb_left;
                }
            }

            block.add(edge_left);
        }

        if (blocked.block_right_bomb(bomb, 0)) {
            edge_right = new Bomb(bomb.getX() / 32 + 1, bomb.getY() / 32, Sprite.bomb_exploded.getFxImage());
            if (power_bomb > 0) {
                for(i = 1; i <= power_bomb && blocked.block_right_bomb(bomb, i); ++i) {
                    edge_right.setX(bomb.getX() + 32 + i * 32);
                    ++power_bomb_right;
                }
            }

            block.add(edge_right);
        }

    }

    public static void createMiddle() {     // Điều chỉnh quả bom phát nổ tại vị trí trung tâm
        Entity middle;
        int i;
        for(i = 1; i <= power_bomb_down; i++) {
            middle = new Bomb(bomb.getX() / 32, bomb.getY() / 32 + i, Sprite.bomb_exploded.getFxImage());
            list_bomb_middle_height.add(middle);
        }

        for(i = 1; i <= power_bomb_up; i++) {
            middle = new Bomb(bomb.getX() / 32, bomb.getY() / 32 - i, Sprite.bomb_exploded.getFxImage());
            list_bomb_middle_height.add(middle);
        }

        for(i = 1; i <= power_bomb_left; i++) {
            middle = new Bomb(bomb.getX() / 32 - i, bomb.getY() / 32, Sprite.bomb_exploded.getFxImage());
            list_bomb_middle_width.add(middle);
        }

        for(i = 1; i <= power_bomb_right; i++) {
            middle = new Bomb(bomb.getX() / 32 + i, bomb.getY() / 32, Sprite.bomb_exploded.getFxImage());
            list_bomb_middle_width.add(middle);
        }

        block.addAll(list_bomb_middle_width);
        block.addAll(list_bomb_middle_height);
    }

    public static void explosionCenter() {      // Xác định tâm nổ của quả bom và chuyển đổi hình ảnh bom nổ
        if (swap_explosion == 1) {
            bomb.setImg(Sprite.bomb_exploded.getFxImage());
            list_kill[bomb.getX() / 32][bomb.getY() / 32] = 4;
            if (blocked.block_down_bomb(bomb, power_bomb_down)) {
                edge_down.setImg(Sprite.explosion_vertical_down_last.getFxImage());
                list_kill[edge_down.getX() / 32][edge_down.getY() / 32] = 4;
            }

            if (blocked.block_up_bomb(bomb, power_bomb_up)) {
                edge_up.setImg(Sprite.explosion_vertical_top_last.getFxImage());
                list_kill[edge_up.getX() / 32][edge_up.getY() / 32] = 4;
            }

            if (blocked.block_left_bomb(bomb, power_bomb_left)) {
                edge_left.setImg(Sprite.explosion_horizontal_left_last.getFxImage());
                list_kill[edge_left.getX() / 32][edge_left.getY() / 32] = 4;
            }

            if (blocked.block_right_bomb(bomb, power_bomb_right)) {
                edge_right.setImg(Sprite.explosion_horizontal_right_last.getFxImage());
                list_kill[edge_right.getX() / 32][edge_right.getY() / 32] = 4;
            }

            if (list_bomb_middle_height.size() > 0) {
                for (Entity e : list_bomb_middle_height) {
                    e.setImg(Sprite.explosion_vertical.getFxImage());
                    list_kill[e.getX() / 32][e.getY() / 32] = 4;
                }
            }

            if (list_bomb_middle_width.size() > 0) {
                for (Entity e : list_bomb_middle_width) {
                    e.setImg(Sprite.explosion_horizontal.getFxImage());
                    list_kill[e.getX() / 32][e.getY() / 32] = 4;
                }
            }

            swap_explosion = 2;
        }
        else if (swap_explosion == 2) {
            bomb.setImg(Sprite.bomb_exploded1.getFxImage());
            if (blocked.block_down_bomb(bomb, power_bomb_down)) {
                edge_down.setImg(Sprite.explosion_vertical_down_last1.getFxImage());
            }

            if (blocked.block_up_bomb(bomb, power_bomb_up)) {
                edge_up.setImg(Sprite.explosion_vertical_top_last1.getFxImage());
            }

            if (blocked.block_left_bomb(bomb, power_bomb_left)) {
                edge_left.setImg(Sprite.explosion_horizontal_left_last1.getFxImage());
            }

            if (blocked.block_right_bomb(bomb, power_bomb_right)) {
                edge_right.setImg(Sprite.explosion_horizontal_right_last1.getFxImage());
            }

            if (is_middle) {
                for (Entity e : list_bomb_middle_height) {
                    e.setImg(Sprite.explosion_vertical1.getFxImage());
                }
                for (Entity e : list_bomb_middle_width) {
                    e.setImg(Sprite.explosion_horizontal1.getFxImage());
                }
            }

            swap_explosion = 3;
        }
        else if (swap_explosion == 3) {
            bomb.setImg(Sprite.bomb_exploded2.getFxImage());
            if (blocked.block_down_bomb(bomb, power_bomb_down)) {
                edge_down.setImg(Sprite.explosion_vertical_down_last2.getFxImage());
            }

            if (blocked.block_up_bomb(bomb, power_bomb_up)) {
                edge_up.setImg(Sprite.explosion_vertical_top_last2.getFxImage());
            }

            if (blocked.block_left_bomb(bomb, power_bomb_left)) {
                edge_left.setImg(Sprite.explosion_horizontal_left_last2.getFxImage());
            }

            if (blocked.block_right_bomb(bomb, power_bomb_right)) {
                edge_right.setImg(Sprite.explosion_horizontal_right_last2.getFxImage());
            }

            if (is_middle) {
                for (Entity e : list_bomb_middle_height) {
                    e.setImg(Sprite.explosion_vertical2.getFxImage());
                }
                for (Entity e : list_bomb_middle_width) {
                    e.setImg(Sprite.explosion_horizontal2.getFxImage());
                }
            }

            swap_explosion = 1;
        }

    }

    private static void checkActive() {     // Kiểm tra xem quả bom đã trải qua những giai đoạn nào :(đang chờ phát nổ hoặc đang phát nổ)
        //Nếu quả bom đang trong giai đoạn chờ phát nổ,
        // phương thức này sẽ kiểm tra thời gian từ khi quả bom được đặt đến hiện tại và kích hoạt (phát nổ) nếu đã đủ 2 giây.
        // Nếu quả bom đang phát nổ hoặc đã phát nổ xong, phương thức này không làm gì cả.

        if (is_bomb == 1) {
            if (System.currentTimeMillis() - time_bomb < 2000L) {
                if (System.currentTimeMillis() - time_tmp > 100L) {
                    activeBomb();
                    time_tmp += 100L;
                }
            }
            else {
                is_bomb = 2;
                time_bomb = System.currentTimeMillis();
                time_tmp = time_bomb;
            }
        }

    }

    private static void checkExplosion() {      // Kiểm tra thời gian kích nổ của bom sau khi bom được kích hoạt
        //nếu đang trong thời gian kích nổ, phương thức này sẽ kiểm tra thời gian từ khi quả bom được kích hoạt
        // đến hiện tại và tạo ra hiệu ứng nổ, cập nhật các trạng thái của các đối tượng có liên quan
       //Nếu đã quá thời gian kích nổ, phương thức này sẽ tiến hành xử lý các trạng thái của các đối tượng có liên quan
        // (thay đổi hình ảnh, xóa đối tượng, cập nhật danh sách các điểm chết, các thẻ `is_edge` và các thẻ `is_middle`) và đặt lại các giá trị tương ứng.
        if (is_bomb == 2) {
            if (System.currentTimeMillis() - time_bomb < 1000L) {
                if (System.currentTimeMillis() - time_tmp > 100L) {
                    if (!is_edge) {
                        createEdge();
                        is_edge = true;
                    }

                    if (power_bomb > 0 && !is_middle) {
                        createMiddle();
                        is_middle = true;
                    }

                    new SoundManager("sound/bomb_explosion.wav", "explosion");
                    explosionCenter();
                    time_tmp += 100L;
                }
            }
            else {
                is_bomb = 0;
                id_objects[bomb.getX() / 32][bomb.getY() / 32] = 0;
                list_kill[bomb.getX() / 32][bomb.getY() / 32] = 0;
                bomb.setImg(Sprite.transparent.getFxImage());
                if (blocked.block_down_bomb(bomb, power_bomb_down)) {
                    edge_down.setImg(Sprite.transparent.getFxImage());
                    id_objects[edge_down.getX() / 32][edge_down.getY() / 32] = 0;
                    list_kill[edge_down.getX() / 32][edge_down.getY() / 32] = 0;
                }

                if (blocked.block_up_bomb(bomb, power_bomb_up)) {
                    edge_up.setImg(Sprite.transparent.getFxImage());
                    id_objects[edge_up.getX() / 32][edge_up.getY() / 32] = 0;
                    list_kill[edge_up.getX() / 32][edge_up.getY() / 32] = 0;
                }

                if (blocked.block_left_bomb(bomb, power_bomb_left)) {
                    edge_left.setImg(Sprite.transparent.getFxImage());
                    id_objects[edge_left.getX() / 32][edge_left.getY() / 32] = 0;
                    list_kill[edge_left.getX() / 32][edge_left.getY() / 32] = 0;
                }

                if (blocked.block_right_bomb(bomb, power_bomb_right)) {
                    edge_right.setImg(Sprite.transparent.getFxImage());
                    id_objects[edge_right.getX() / 32][edge_right.getY() / 32] = 0;
                    list_kill[edge_right.getX() / 32][edge_right.getY() / 32] = 0;
                }

                if (is_middle) {
                    for (Entity e : list_bomb_middle_width) {
                        list_kill[e.getX() / 32][e.getY() / 32] = 0;
                        id_objects[e.getX() / 32][e.getY() / 32] = 0;
                    }
                    for (Entity e : list_bomb_middle_height) {
                        list_kill[e.getX() / 32][e.getY() / 32] = 0;
                        id_objects[e.getX() / 32][e.getY() / 32] = 0;
                    }
                }

                block.removeAll(list_bomb_middle_height);
                block.removeAll(list_bomb_middle_width);
                list_bomb_middle_height.clear();
                list_bomb_middle_width.clear();
                is_edge = false;
                is_middle = false;
                power_bomb_down = 0;
                power_bomb_up = 0;
                power_bomb_left = 0;
                power_bomb_right = 0;
            }
        }

    }

    @Override
    public void update() {
        checkActive();
        checkExplosion();
    }
}