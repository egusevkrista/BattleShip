package ru.krista.battleship.entities;

import javax.enterprise.inject.Produces;
import java.io.Serializable;
import java.util.Random;

public class Field implements Serializable {
    private int[][] mainField = new int[12][12];
    private int[][] secondField = new int[12][12];

    private int countSimpleDeck = 0;
    private int countTwoDeck = 0;
    private int countThreeDeck = 0;
    private int countFourDeck = 0;

    private int hp = 20;

    public void startGame() {
        fillInactiveCells();
    }

    private boolean checkCountShips(int size) {
        switch (size) {
            case 1: {
                if (countSimpleDeck == 4) return false;
                countSimpleDeck++;
                return true;
            }
            case 2: {
                if (countTwoDeck == 3) return false;
                countTwoDeck++;
                return true;
            }
            case 3: {
                if (countThreeDeck == 2) return false;
                countThreeDeck++;
                return true;
            }
            case 4: {
                if (countFourDeck == 1) return false;
                countFourDeck++;
                return true;
            }
        }
        return false;
    }

    public int placeShip(int x, int y, int size, ShipDirection direction) {
        if (checkPlace(x, y, size, direction) && checkCountShips(size)) {
            captureCells(x, y, size, direction);
        } else return -1;

        return 0;
    }

    private void fillInactiveCells() {
        for (int i = 0; i < 12; i++) {
            mainField[i][0] = mainField[i][11] = -1;
        }

        for (int j = 0; j < 12; j++) {
            mainField[0][j] = mainField[11][j] = -1;
        }
    }

    private boolean checkPlace(int x, int y, int size, ShipDirection direction) {
        int countRight = 0;
        int countUp = 0;

        if (direction == ShipDirection.RIGHT) {
            for (int i = x; i < size + x; i++) {
                if (checkCell(i, y)) countRight++;
            }
            return countRight == size;
        }

        if (direction == ShipDirection.UP) {
            for (int i = y; i > y - size; i--) {
                if (checkCell(x, i)) countUp++;
            }
            return countUp == size;
        }
        return false;
    }

    private boolean checkCell(int x, int y) throws ArrayIndexOutOfBoundsException {
        try {
            return mainField[x][y] == 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /*
    0 - пустая клетка
    1 - в этой клетке часть корабля
    2 - соседняя клетка с кораблем
     */
    private void captureCells(int x, int y, int size, ShipDirection direction) {
        if (direction == ShipDirection.RIGHT) {
            for (int i = x; i < size + x; i++) {
                mainField[i][y] = 1;
            }
            for (int i = x - 1; i <= size + x; i++) {
                mainField[i][y - 1] = mainField[i][y + 1] = 2;
            }
            mainField[x - 1][y] = mainField[x + size][y] = 2;
        }

        if (direction == ShipDirection.UP) {
            for (int i = y; i > y - size; i--) {
                mainField[x][i] = 1;
            }
            for (int i = y + 1; i >= y - size; i--) {
                mainField[x - 1][i] = mainField[x + 1][i] = 2;
            }
            mainField[x][y + 1] = mainField[x][y - size] = 2;
        }
    }

    public boolean hitted(int x, int y) {
        if (mainField[x][y] == 1) {
            mainField[x][y] = 3;
            hp--;
            return true;
        }
        return false;
    }

    public void markWhenHitted(int x, int y) {
        secondField[x][y] = 3;
    }

    public void markWhenEmpty(int x, int y) {
        secondField[x][y] = 4;
    }

    public boolean normalTarget(int x, int y) {
        return secondField[x][y] == 0;
    }

    public int getHP() {
        return this.hp;
    }

    public void fullShipsRandom() {
        Random r = new Random();
        while (countSimpleDeck <= 3) {
            int x = r.nextInt(10) + 1;
            int y = r.nextInt(10) + 1;
            placeShip(x, y, 1, ShipDirection.RIGHT);
        }
        while (countTwoDeck <= 2) {
            int x = r.nextInt(10) + 1;
            int y = r.nextInt(10) + 1;
            int d = r.nextInt(2);

            if (d == 0) {
                placeShip(x, y, 2, ShipDirection.RIGHT);
            } else {
                placeShip(x, y, 2, ShipDirection.UP);
            }
        }
        while (countThreeDeck <= 1) {
            int x = r.nextInt(10) + 1;
            int y = r.nextInt(10) + 1;
            int d = r.nextInt(2);

            if (d == 0) {
                placeShip(x, y, 3, ShipDirection.RIGHT);
            } else {
                placeShip(x, y, 3, ShipDirection.UP);
            }
        }
        while (countFourDeck == 0) {
            int x = r.nextInt(10) + 1;
            int y = r.nextInt(10) + 1;
            int d = r.nextInt(2);

            if (d == 0) {
                placeShip(x, y, 4, ShipDirection.RIGHT);
            } else {
                placeShip(x, y, 4, ShipDirection.UP);
            }
        }
    }
}
