package uet.oop.bomberman.levels;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Animal;
import uet.oop.bomberman.entities.ghost.Kondoria;
import uet.oop.bomberman.entities.ghost.Ballom;
import uet.oop.bomberman.entities.ghost.Oneal;
import uet.oop.bomberman.graphics.createMap;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

import static uet.oop.bomberman.control.menu.bomb_number;
import static uet.oop.bomberman.control.menu.time_number;
import static uet.oop.bomberman.entities.Bomber.swap_kill;
import static uet.oop.bomberman.entities.Bomb.is_bomb;
import static uet.oop.bomberman.entities.Bomb.power_bomb;
import static uet.oop.bomberman.entities.items.SpeedItem.speed;
import static uet.oop.bomberman.SoundManager.is_sound_died;
import static uet.oop.bomberman.SoundManager.is_sound_title;

public class Level2 {
    public Level2() {
        enemy.clear();
        block.clear();
        swap_kill = 1;
        new createMap("res/levels/Level2.txt");
        player.setLife(true);
        player.setX(32);
        player.setY(32);
        speed = 1;
        is_sound_died = false;
        is_sound_title = false;
        time_number = 120;
        bomb_number = 30;
        is_bomb = 0;

        player.setImg(Sprite.player_right_2.getFxImage());
        Image transparent = new Image("images/transparent.png");
        author_view.setImage(transparent);

        Animal enemy1 = new Ballom(5, 5, Sprite.balloom_left1.getFxImage());
        Animal enemy2 = new Ballom(11, 9, Sprite.balloom_left1.getFxImage());
        enemy.add(enemy1);
        enemy.add(enemy2);

        Animal enemy3 = new Kondoria(1, 3, Sprite.kondoria_right1.getFxImage());
        Animal enemy4 = new Kondoria(1, 7, Sprite.kondoria_right1.getFxImage());
        Animal enemy5 = new Kondoria(1, 11, Sprite.kondoria_right1.getFxImage());
        enemy.add(enemy3);
        enemy.add(enemy4);
        enemy.add(enemy5);

        Animal enemy6 = new Oneal(7, 5, Sprite.oneal_right1.getFxImage());
        Animal enemy7 = new Oneal(19, 7, Sprite.oneal_right1.getFxImage());
        enemy.add(enemy6);
        enemy.add(enemy7);

        for (Animal animal : enemy) {
            animal.setLife(true);
        }
    }
}
