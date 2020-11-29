package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {
    protected int timeActive = 600;
    protected boolean active = false;

    public Item(double x, double y, Image img) {
        super(x, y, img);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
