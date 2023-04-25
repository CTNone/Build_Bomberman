package uet.oop.bomberman.levels;

import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.level;
import static uet.oop.bomberman.BombermanGame.author_view   ;
import static uet.oop.bomberman.entities.Portal.is_portal;

public class NextLevel {
    public static boolean wait;
    public static long waiting_time;

    public static void waitToLevelUp() {
        if ( wait ) {
            Image wait_to_next = new Image("images/levelUp.png");
            // add image level up to authorView
            author_view.setImage(wait_to_next);
            // load next Level
            long now = System.currentTimeMillis();
            if (now - waiting_time > 3000) {
                switch(level) {
                    case 1:
                        is_portal = false;
                        new Level2();
                        break;
                    case 2:
                        new Level3();
                        break;
                    case 3:
                        new Level1();
                }
                wait = false;
            }
        }
    }
}
