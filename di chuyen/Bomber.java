package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    public void moveUp(){
        y-=10;
    }
    public void moveDown(){
        y+=10;
    }
    public void moveLeft(){
        x-=10;
    }
    public void moveRight(){
        x+=10;
    }


    @Override
    public void update() {

    }
}
