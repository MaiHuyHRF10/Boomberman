package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.Random;

public class AI {
    private Random random = new Random();

    public int chooseDirectionRandom(double x, double y, int currentDirection) {
        double tempX = (double) Math.round(x * 1000) / 1000;
        double tempY = (double) Math.round(y * 1000) / 1000;
        if (tempX == (int) tempX && tempY == (int) tempY) {
            int _x = (int) x;
            int _y = (int) y;
            int[] deltaX = {0, 1, 0, -1};
            int[] deltaY = {-1, 0, 1, 0};

            Random rd = new Random(System.currentTimeMillis()); //something like srand in C++
            int i = Math.abs(rd.nextInt()) % 4;
            while (Board.map[_y + deltaY[i]][_x + deltaX[i]] != ' ') {
                i = Math.abs(rd.nextInt()) % 4;
            }
            return i;
        } else {
            return currentDirection;
        }
    }

    public int chooseDirectionMedium(double x, double y, int currentDirection) {
        double bomberX = BombermanGame.board.getPlayer().getX();
        double bomberY = BombermanGame.board.getPlayer().getY();
        double tempX = (double) Math.round(x * 1000) / 1000;
        double tempY = (double) Math.round(y * 1000) / 1000;
        double diffX = bomberX - x;
        double diffY = bomberY - y;

        int[] deltaX = {0, 1, 0, -1};
        int[] deltaY = {-1, 0, 1, 0};

        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distance < 5) {
            if (tempX == (int) tempX && tempY == (int) tempY) {
                ArrayList<Integer> possibleDirections = new ArrayList<>();
                if (diffX > 0) {
                    possibleDirections.add(1);
                } else possibleDirections.add(3);

                if (diffY > 0) {
                    possibleDirections.add(2);
                } else possibleDirections.add(0);

                return possibleDirections.get(Math.abs(random.nextInt() % 2));
            } return currentDirection;
        } else return chooseDirectionRandom(x, y, currentDirection);
    }
}
