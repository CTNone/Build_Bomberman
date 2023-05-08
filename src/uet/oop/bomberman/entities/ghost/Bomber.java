package uet.oop.bomberman.entities;


import javafx.scene.image.Image;

import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.BombermanGame.*;


public class Bomber extends Animal {
    public static int swap_kill = 1;
    private static int count_kill = 0;

//    public Bomber(int is_move, int swap, String direction, int count, int count_to_run) {
//        super(8, 1, "down", 0, 0);
//    }
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }
//    public Bomber() { }

    private void killBomber(Animal animal) { // sau khi phải chết thì load 3 ảnh và set là đã cết
        if (count_kill % 16 == 0) {
            if (swap_kill == 1) {
                animal.setImg(Sprite.player_dead1.getFxImage());
                swap_kill = 2;
            }
            else if (swap_kill == 2) {
                animal.setImg(Sprite.player_dead2.getFxImage());
                swap_kill = 3;
            }
            else if (swap_kill == 3) {
                animal.setImg(Sprite.player_dead3.getFxImage());
                swap_kill = 4;
            }
            else {
                animal.setImg(Sprite.transparent.getFxImage());
                running = false;
                Image gameOver = new Image("images/gameOver.png");
                author_view.setImage(gameOver);
            }
        }
    }

    private void checkBombs() {
        if (list_kill[player.getX() / 32][player.getY() / 32] == 4)
            player.setLife(false);
    }

    private void checkEnemy() {
        int ax = player.getX() / 32;
        int ay = player.getY() / 32;
        for (Animal animal : enemy) {
            int bx = animal.getX() / 32;
            int by = animal.getY() / 32;
            if ( ax == bx && ay == by )
            {
                player.life = false;
                break;
            }
        }
    }

    @Override
    public void update() {
        checkBombs();
        checkEnemy();
        count_kill++;
        if (!player.life)
            killBomber(player);
    }

}
