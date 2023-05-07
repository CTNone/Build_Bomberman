package uet.oop.bomberman.entities.items;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public class FlameItem extends Items {

    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }

    public FlameItem(boolean received) {
        super(received);
    }

    public FlameItem() {
    }

    // M? r?ng ph?m vi bom n?u ng??i ch?i nh?n ???c v?t ph?m
    @Override
    public void update() {
        for (Entity entity : block)
            if (entity instanceof FlameItem && !this.received)
                if (list_kill[entity.getX() / 32][entity.getY() / 32] == 4)
                    entity.setImg(Sprite.powerup_flames.getFxImage());

        if (!this.received)
            if (player.getX() == this.x && player.getY() == this.y) {
                this.setImg(Sprite.grass.getFxImage());
                this.received = true;
                Bomb.power_bomb += 1;
            }
    }
}
