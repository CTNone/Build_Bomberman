package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.block;
import static uet.oop.bomberman.BombermanGame.list_kill;

public class Brick extends Entity {

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    private void checkHidden() {    //Kiểm tra khả năng hiển thị của Brick
        for (Entity entity : block) {
            if (entity instanceof Brick)
                if (list_kill[entity.getX() / 32][entity.getY() / 32] == 4) {    // Tại phần tử của mảng listKill 2 chiều với giá trị 4, Brick and Grass sẽ xuất hiện
                    entity.setImg(Sprite.grass.getFxImage());
                }
        }
    }

    @Override
    public void update() {
        checkHidden();
    }
}
