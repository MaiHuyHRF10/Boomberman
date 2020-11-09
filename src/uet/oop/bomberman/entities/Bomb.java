package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {

    protected double timeToExplode = 120;
    protected int timeAfter = 20;
    private int animate = 0;
    private Sprite sprite;
    private boolean remove = true;
    protected boolean exploded = false;
    protected DirectionExplosion[] explosions = null;

    public Bomb() {
    }

    public Bomb(double x, double y, boolean remove, Image img) {
        super(x, y, img);
        this.remove = remove;
    }


    @Override
    public void update() {
        if (!remove) {
            if (timeToExplode > 0)
                timeToExplode--;
            else {
                if (!exploded)
                    explosion();
                if (timeAfter > 0) {
                    timeAfter --;
                } else {
                    remove = true;
                }

                //else
                // updateExplosions();

            }


        }


    }

    protected void explosion() {

        exploded = true;
        explosions = new DirectionExplosion[4];

        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new DirectionExplosion((int) x, (int) y, i);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (exploded == false && remove == false) {
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);
            setImg(sprite.getFxImage());
            animate++;
            super.render(gc);
        }
        else if (!remove) {
            setImg(Sprite.bomb_exploded2.getFxImage());
            super.render(gc);
            for (int i = 0; i < explosions.length; i++) {
                explosions[i].render(gc);
            }
        }
    }
}
