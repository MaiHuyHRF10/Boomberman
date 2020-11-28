package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {
    private Random random = new Random();
    int[] deltaX = {0, 1, 0, -1};
    int[] deltaY = {-1, 0, 1, 0};

    public int chooseDirectionRandom(double x, double y, int currentDirection) {
        double tempX = (double) Math.round(x * 1000) / 1000;
        double tempY = (double) Math.round(y * 1000) / 1000;
        if (tempX == (int) tempX && tempY == (int) tempY) {
            int _x = (int) x;
            int _y = (int) y;

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

    public int chooseDirectionMedium2(Bomber bomber, Enemy enemy, int currentDirection) {
        double bomberX = BombermanGame.board.getPlayer().getX();
        double bomberY = BombermanGame.board.getPlayer().getY();
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        double diffX = bomberX - enemy.getX();
        double diffY = bomberY - enemy.getY();

        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distance < 5) {
            if (tempX == (int) tempX && tempY == (int) tempY) {
                ArrayList<Integer> possibleDirections = new ArrayList<>();
                if (diffX > 0) {
                    if (checkDirectionToAvoidBomb(bomber, enemy, 1))
                        possibleDirections.add(1);
                } else {
                    if (checkDirectionToAvoidBomb(bomber, enemy, 3))
                        possibleDirections.add(3);
                }

                if (diffY > 0) {
                    if (checkDirectionToAvoidBomb(bomber, enemy, 2))
                        possibleDirections.add(2);
                } else {
                    if (checkDirectionToAvoidBomb(bomber, enemy, 0))
                        possibleDirections.add(0);
                }

                switch (possibleDirections.size()) {
                    case 0: {
                        for (int i = 0; i < 4; i++) {
                            if (checkDirectionToAvoidBomb(bomber, enemy, i)) return i;
                        }
                    }
                    case 1:
                        return possibleDirections.get(0);
                    case 2:
                        return possibleDirections.get(Math.abs(random.nextInt(2)));
                }
                return possibleDirections.get(random.nextInt(2));
            } return currentDirection;
        } else return chooseDirectionRandom(enemy.getX(), enemy.getY(), currentDirection);
    }

    public boolean checkDirectionToAvoidBomb(Bomber bomber, Enemy enemy, int currentDirection) {
        List<Bomb> bombs = bomber.getBombs();
        for (int i = 0; i < bombs.size(); i++) {
            if (!avoidBomb(enemy, bombs.get(i).getX(), bombs.get(i).getY(), currentDirection))
                return false;
        }
        return true;
    }

    public boolean avoidBomb(Enemy enemy, double xBomb, double yBomb, int direction) {
        double tempX = enemy.getX();
        double tempY = enemy.getY();
        switch (direction) {
            case 0:
                tempY -= enemy.getSpeed();
                break;
            case 1:
                tempX += enemy.getSpeed();
                break;
            case 2:
                tempY += enemy.getSpeed();
                break;
            case 3:
                tempX -= enemy.getSpeed();
                break;
        }

        double diffX = tempX - xBomb;
        double diffY = tempY - yBomb;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        return distance < Math.sqrt(8);
    }
}
