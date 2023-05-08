package uet.oop.bomberman.entities.ghost;

import javafx.scene.image.Image;
import uet.oop.bomberman.control.Move;
import uet.oop.bomberman.entities.Animal;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.enemy;
import static uet.oop.bomberman.BombermanGame.width;

public class Kondoria extends Animal {

    private static int swap_kill = 1; // đổi hình ảnh của quái vật khi bị tiêu diệt.
    private static int count_kill = 0; //biến đếm số lần quái vật bị tiêu diệt để load ảnh chết
    private static boolean direction; // lưu hướng di chuyển hiện tại

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
    }

//    public Kondoria(int is_move, int swap, String directionection, int count, int count_to_run) {
//        super(4, 1, "up", 0, 0);
//    }
//
//    public Kondoria(boolean life) {
//        super(life);
//    }
//
//    public Kondoria() {
//    }

    private void killKondoria(Animal animal) { // cập nhật hình ảnh chết và xóa khỏi danh sach
        if (count_kill % 16 == 0) { // hiện hình ảnh chết nhanh hay chậm
            if (swap_kill == 1) {
                animal.setImg(Sprite.kondoria_dead.getFxImage());
                swap_kill = 2;
            }
            else if (swap_kill == 2) {
                animal.setImg(Sprite.player_dead3.getFxImage()); // sử dụng tạm
                swap_kill = 3;
            }
            else {
                animal.setLife(false);// cập nhật chết
                enemy.remove(animal); // xóa khỏi danh sách
                swap_kill = 1; // reset biến
            }
        }
    }

    @Override
    public void update() {
        count_kill++;
        for (Animal animal : enemy) {
            if (animal instanceof Kondoria && !((Kondoria) animal).life)
                killKondoria(animal); //gói tới để xóa quái vật ra khỏi DS
        }

        if (this.y % 16 == 0 && this.x % 16 == 0) {
            if (this.x / 32 <= 1 || this.x / 32 >= width - 2) // đổi hướng di chuyển
                direction = !direction;

            if (direction) // == 1
                Move.left(this);
            else
                Move.right(this);
        }
    }
}
