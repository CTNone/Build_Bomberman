package uet.oop.bomberman.entities.ghost;

import java.util.List;

import uet.oop.bomberman.control.Move;
import uet.oop.bomberman.entities.intelligent.AStar;
import uet.oop.bomberman.entities.intelligent.Node;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Animal;

import static uet.oop.bomberman.BombermanGame.*;

public class Doll extends Animal {
    private static int swap_kill = 1; // đổi hình ảnh của quái vật khi bị tiêu diệt.
    private static int count_kill = 0;  // đếm số lần quái vật `Doll` bị tiêu diệt.

    public Doll(int x_unit, int y_unit, Image img) {
        super(x_unit, y_unit, img);
    }

    public Doll(int is_move, int swap, String direction, int count, int count_to_run) {
        super(4, 1, "up", 0, 0);
    }

    public Doll(boolean life) {
        super(life);
    }

    public Doll() {

    }

    private void killDoll(Animal animal) { // cập nhật hình ảnh chết và xóa khỏi danh sach
        if (count_kill % 16 == 0) {  // hiện hình ảnh chết nhanh hay chậm
            if (swap_kill == 1) {
                animal.setImg(Sprite.doll_dead.getFxImage());
                swap_kill = 2;
            } else if (swap_kill == 2) {
                animal.setImg(Sprite.player_dead3.getFxImage());
                swap_kill = 3;
            } else {
                animal.setLife(false); // cập nhật chết
                enemy.remove(animal); // xóa khỏi danh sách
                swap_kill = 1; // reset biến
            }
        }
    }

    private void moveDoll() {// di chuyển quái vật Doll theo đường đi tối ưu đến vị trí của người chơi.
        if (this.x % 32 == 0 && this.y %32 == 0) {
            Node initial_node = new Node(this.y / 32, this.x / 32); // vị trí quái vật
            Node final_node = new Node(player.getY() / 32, player.getX() / 32); // vị trí Bomber

            int rows = height; // hàng - ngang
            int cols = width; // cột - dọc

            AStar a_star = new AStar(rows, cols, initial_node, final_node); // tìm đường đi tối ưu giữa 2 Node

            int[][] blocks_in_array = new int[width * height][2]; // chứa các dối tượng cản tro từ mảng id_object
            int count_block = 0; // số lượng vật cản

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (id_objects[j][i] != 0) {
                        blocks_in_array[count_block][0] = i;
                        blocks_in_array[count_block][1] = j; //COPY
                        count_block++;
                    }
                }
            }
            a_star.setBlocks(blocks_in_array, count_block);
            List<Node> path = a_star.findPath(); //  tính toán đường đi tối ưu và di chuyển quái vật
            if (path.size() != 0) {
                int nextX = path.get(1).getCol();
                int nextY = path.get(1).getRow();

                if (this.y / 32 > nextY)
                    Move.up(this);
                if (this.y / 32 < nextY)
                    Move.down(this);
                if (this.x / 32 > nextX)
                    Move.left(this);
                if (this.x / 32 < nextX)
                    Move.right(this);
            }
        }
    }

    @Override
    public void update() {
        count_kill++;
        for (Animal animal:enemy) {
            if (animal instanceof Doll && !((Doll) animal).life)
                killDoll(animal); //gói tới để xóa quái vật ra khỏi DS
        }
        moveDoll();
    }
}
