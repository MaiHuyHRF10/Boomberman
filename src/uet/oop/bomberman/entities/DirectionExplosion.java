package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;

public class DirectionExplosion extends Entity {

    protected int direction;
    protected Explosion[] explosions;
    private boolean remove = false;

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public DirectionExplosion(double x, double y, int direction) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        explosions = new Explosion[1];
        createExplosions();
    }

    private void createExplosions() {
        boolean last = false;

        int x1 = (int) x;
        int y1 = (int) y;
        for (int i = 0; i < explosions.length; i++) {
            last = true;

            switch (direction) {
                case 0:
                    y1--;
                    break;
                case 1:
                    x1++;
                    break;
                case 2:
                    y1++;
                    break;
                case 3:
                    x1--;
                    break;
            }
            explosions[i] = new Explosion(x1, y1, direction, last);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

        for (int i = 0; i < explosions.length; i++) {
            if (!remove) explosions[i].render(gc);

        }
    }
}
