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

    public int chooseDirectionRandom(Enemy enemy, int currentDirection) {
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        if (tempX == (int) tempX && tempY == (int) tempY) {
            Random rd = new Random(System.currentTimeMillis()); //something like srand in C++
            int i = Math.abs(rd.nextInt()) % 4;
            return i;
        } else {
            return currentDirection;
        }
    }

    public int chooseDirectionMedium(Enemy enemy, int currentDirection) {
        double bomberX = BombermanGame.board.getPlayer().getX();
        double bomberY = BombermanGame.board.getPlayer().getY();
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        double diffX = bomberX - enemy.getX();
        double diffY = bomberY - enemy.getY();

        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distance > 10) {
            enemy.setSpeed(Board.speedOfEnemy);
        }

        if (distance < 3) {
            enemy.setSpeed(Board.speedOfEnemy * 1.25 * 1.2);
        }
        if (distance < 6) {
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
        } else return chooseDirectionRandom(enemy, currentDirection);
    }

    public int chooseDirectionMedium2(Bomber bomber, Enemy enemy, int currentDirection) {
        double bomberX = BombermanGame.board.getPlayer().getX();
        double bomberY = BombermanGame.board.getPlayer().getY();
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        double diffX = bomberX - enemy.getX();
        double diffY = bomberY - enemy.getY();

        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distance < Math.sqrt(50)) {
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
                    case 0:
                        int direction = -1;
                        for (int i = 0; i < 4; i++) {
                            if (checkDirectionToAvoidBomb(bomber, enemy, i)) {
                                direction = i;
                            }
                        }
                        if (direction != -1) return direction;
                        else {
                            return chooseDirectionRandom(enemy, currentDirection);
                        }
                    case 1:
                        return possibleDirections.get(0);
                    case 2:
                        return possibleDirections.get(Math.abs(random.nextInt() % 2));
                }
            } return currentDirection;
        } else return chooseDirectionRandom(enemy, currentDirection);
    }

    public int chooseDirectionGoThroughBrick(Enemy enemy, int currentDirection) {
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        if (tempX == (int) tempX && tempY == (int) tempY) {
            ArrayList<Integer> possibleDirections = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                if (checkCanMoveThroughBrick(enemy, i)) possibleDirections.add(i);
            }

            int size = possibleDirections.size();
            if (size == 0) return -1;
            else {
                int i = Math.abs(random.nextInt() % size);
                return possibleDirections.get(i);
            }
        } else {
            return currentDirection;
        }
    }

    public boolean checkDirectionToAvoidBomb(Bomber bomber, Enemy enemy, int direction) {
        List<Bomb> bombs = bomber.getBombs();
        for (int i = 0; i < bombs.size(); i++) {
            if (!avoidBomb(enemy, bombs.get(i).getX(), bombs.get(i).getY(), direction))
                return false;
        }
        return true;
    }

    public boolean avoidBomb(Enemy enemy, double xBomb, double yBomb, int direction) {
        double X = (double) Math.round(enemy.getX() * 1000) / 1000;
        double Y = (double) Math.round(enemy.getY() * 1000) / 1000;

        int xEnemy = (int) X;
        int yEnemy = (int) Y;

        xEnemy += deltaX[direction];
        yEnemy += deltaY[direction];

        int diffX = xEnemy - (int) xBomb;
        int diffY = yEnemy - (int) yBomb;

        int distanceSquare = diffX * diffX + diffY * diffY;

        if (distanceSquare < 3) {
            return false;
        } {
            return true;
        }
    }

    public boolean checkCanMoveThroughBrick(Enemy enemy, int direction) {
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;

        tempX += deltaX[direction];
        tempY += deltaY[direction];
        return Board.map[(int) tempY][(int) tempX] != '#';
    }

}
