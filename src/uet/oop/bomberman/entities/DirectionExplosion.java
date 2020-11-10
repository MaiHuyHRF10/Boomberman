package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;

import java.util.stream.IntStream;

public class DirectionExplosion extends Entity {

    protected int direction;
    protected Explosion[] explosions;
    private boolean remove = false;

    public DirectionExplosion(double x, double y, int direction) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        explosions = new Explosion[radius()];
        createExplosions();
    }

    private int radius() {
        int radius1 = 0;
        int x1 = (int) x;
        int y1 = (int) y;
        if (direction == 0) y1--;
        if (direction == 1) x1++;
        if (direction == 2) y1++;
        if (direction == 3) x1--;
        if (BombermanGame.map[y1][x1] != '#') radius1++;
        return radius1;

    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    private void createExplosions() {
        boolean last;

        int x1 = (int) x;
        int y1 = (int) y;
        for (int i = 0; i < explosions.length; i++) {
            last = i == explosions.length - 1;
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

        IntStream.range(0, explosions.length).filter(i -> !remove).forEach(i -> explosions[i].render(gc));
    }
}
