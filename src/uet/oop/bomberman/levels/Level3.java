package uet.oop.bomberman.levels;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Animal;
import uet.oop.bomberman.entities.ghost.Doll;
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
public class Level3 {
    public Level3() {
        enemy.clear();
        block.clear();
        swap_kill = 1;
        new createMap("res/levels/Level3.txt");
        player.setLife(true);
        player.setX(32);
        player.setY(32);
        speed = 1;
        is_sound_died = false;
        is_sound_title = false;
        time_number = 120;
        bomb_number = 40;
        is_bomb = 0;

        player.setImg(Sprite.player_right_2.getFxImage());
        Image transparent = new Image("images/transparent.png");
        author_view.setImage(transparent);

        Animal enemy1 = new Ballom(5, 5, Sprite.balloom_left1.getFxImage());
        Animal enemy2 = new Ballom(11, 9, Sprite.balloom_left1.getFxImage());
        enemy.add(enemy1);
        enemy.add(enemy2);

        Animal enemy3 = new Doll(7, 5, Sprite.doll_left1.getFxImage());
        enemy.add(enemy3);

        for (Animal animal : enemy) {
            animal.setLife(true);
        }
    }
}
