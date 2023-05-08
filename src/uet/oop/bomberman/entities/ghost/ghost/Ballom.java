package uet.oop.bomberman.entities.ghost;

import uet.oop.bomberman.control.Move;
import javafx.scene.image.Image;

import uet.oop.bomberman.entities.Animal;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.enemy;
import static uet.oop.bomberman.BombermanGame.list_kill;

public class Ballom extends Animal {
    private static int swap_kill = 1; // su dụng đẻ cập nhật hình ảnh chết
    private static int count_kill = 0;  //  tăng lên mỗi lần update, thước đo để hiện các hình ảnh chết

//    public Ballom(int is_move, int swap, String direction, int count, int count_to_run) {
//        super(4, 1, "up", 0, 0);
//    }
//
//    public Ballom(){
//
//    }

    private void killBallom(Animal animal) {    // cập nhật hình ảnh chết và xóa khỏi danh sach
        if (count_kill % 16 == 0) { // hiện hình ảnh chết nhanh hay chậm
            if (swap_kill == 1) {
                animal.setImg(Sprite.mob_dead1.getFxImage());
                swap_kill = 2;
            }
            else if (swap_kill == 2) {
                animal.setImg(Sprite.mob_dead2.getFxImage());
                swap_kill = 3;
            }
            else if (swap_kill == 3) {
                animal.setImg(Sprite.mob_dead3.getFxImage());
                swap_kill = 4;
            }
            else {
                animal.setLife(false); // cập nhật chết
                enemy.remove(animal); // xóa khỏi danh sách
                swap_kill = 1; // reset biến
            }
        }
    }

    private void kill() {
        for (Animal animal : enemy) {
            if (list_kill[animal.getX() / 32][animal.getY() / 32] == 4) { // nếu trong vung bom nổ
                animal.setLife(false); // đã chết
            }
        }
    }

    public Ballom(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        kill(); // KT tiêu diệt quái vật
        count_kill++;
        for (Animal animal : enemy) {
            if (animal instanceof Ballom && !((Ballom) animal).life) // == false (=) đã chết
                killBallom(animal); // gói tới để xóa quái vật ra khỏi DS
        }

        if (this.y % 16 == 0 && this.x % 16 == 0) { // quái vật đang đứng yên trên một ô vuông
            Random random = new Random();
            int direction = random.nextInt(4);
            switch (direction) {
                case 0:
                    Move.down(this);
                    break;
                case 1:
                    Move.up(this);
                    break;
                case 2:
                    Move.left(this);
                    break;
                case 3:
                    Move.right(this);
                    break;
            }
        }
    }
}