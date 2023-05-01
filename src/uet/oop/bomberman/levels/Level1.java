package uet.oop.bomberman.levels;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Animal;
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

public class Level1 {
    public Level1() {
        // clear all enemy if u play again from level 1
        enemy.clear();
        block.clear();
        swap_kill = 1;
        power_bomb = 0;
        new createMap("res/levels/Level1.txt");
        player.setLife(true);
        player.setX(32);
        player.setY(32);
        is_sound_died = false;
        is_sound_title = false;
        time_number = 180;
        bomb_number = 30;
        is_bomb = 0;
        speed = 1;

        // load authorView Scr
        player.setImg(Sprite.player_down_2.getFxImage());
        Image transparent = new Image("images/transparent.png");
        author_view.setImage(transparent);

        Animal enemy1 = new Ballom(13, 1, Sprite.balloom_left1.getFxImage());
        Animal enemy2 = new Ballom(18, 3, Sprite.balloom_left1.getFxImage());
        Animal enemy3 = new Ballom(5, 13, Sprite.balloom_left1.getFxImage());
        enemy.add(enemy1);
        enemy.add(enemy2);
        enemy.add(enemy3);

        Animal enemy4 = new Oneal(15, 7, Sprite.oneal_right1.getFxImage());
        Animal enemy5 = new Oneal(24, 3, Sprite.oneal_right1.getFxImage());
        enemy.add(enemy4);
        enemy.add(enemy5);

        // set default for enemy
        for (Animal animal : enemy) {
            animal.setLife(true);
        }
    }
}