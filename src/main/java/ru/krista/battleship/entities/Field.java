package ru.krista.battleship.entities;

import java.io.Serializable;
import java.util.Random;


/**
 * Класс Поле боя для игрока или оппонента.
 */
public class Field implements Serializable {
    /**
     * Основное поле боя. Двумерный массив 12х12.
     */
    private int[][] mainField = new int[12][12];
    /**
     * Дополнительное поле боя для обозначения информации о промахе/попадании по вражескому кораблю.
     * Двумерный массив 12х12.
     */
    private int[][] secondField = new int[12][12];

    /**
     * Счетчик однопалубных кораблей.
     */
    private int countSimpleDeck = 0;
    /**
     * Счетчик двухпалубных кораблей.
     */
    private int countTwoDeck = 0;
    /**
     * Счетчик трехпалубных кораблей.
     */
    private int countThreeDeck = 0;
    /**
     * Счетчик четырехпалубных кораблей.
     */
    private int countFourDeck = 0;

    /**
     * Счетчик здоровья на поле боя.
     */
    private int hp = 20;

    /**
     * Проверяет, достигнут ли лимит кораблей на поле боя.
     * Если лимит не достигнут - увеличивает соотвествующий счетчик для корабля.
     *
     * @param size размер корабля.
     * @return Возвращает true, если лимит кораблей размером size не достигнут, false - в ином случае.
     */
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

    /**
     * Проверяет возможность установки корабля по заданным параметрам и лимит кораблей на поле боя.
     * Устанавливает корабль, если это возможно.
     *
     * @param x         координата x.
     * @param y         координата y.
     * @param size      размер корабля.
     * @param direction направление установки корабля.
     * @return Возвращает 0, если корабль успешно установлен, -1 - в ином случае.
     * @see Field#checkPlace(int, int, int, ShipDirection)
     * @see Field#checkCountShips(int)
     */
    public int placeShip(int x, int y, int size, ShipDirection direction) {
        if (checkPlace(x, y, size, direction) && checkCountShips(size)) {
            captureCells(x, y, size, direction);
        } else return -1;

        return 0;
    }

    /**
     * Заполняет неактивные ячейки поля значением -1.
     * Неактивные ячейки - выходящие за пределы поля боя.
     */
    public void fillInactiveCells() {
        for (int i = 0; i < 12; i++) {
            mainField[i][0] = mainField[i][11] = -1;
        }

        for (int j = 0; j < 12; j++) {
            mainField[0][j] = mainField[11][j] = -1;
        }
    }

    /**
     * Проверяет, можно ли установить корабль с заданными параметрами.
     *
     * @param x         координата x.
     * @param y         координата y.
     * @param size      размер корабля.
     * @param direction направление корабля.
     * @return Возвращает true, если на это место возможно установить корабль, false - в ином случае.
     */
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

    /**
     * Проверяет, занята ли текущая ячейка.
     *
     * @param x координата x.
     * @param y координата y.
     * @return Возвращает true, если ячейка свободна, false - в ином случае.
     * @throws ArrayIndexOutOfBoundsException Бросает исключение, если проверка ячейки вышла за пределы поля боя 12х12.
     */
    private boolean checkCell(int x, int y) throws ArrayIndexOutOfBoundsException {
        try {
            return mainField[x][y] == 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Захватывает ячейки для корабля по заданным параметрам.
     * Ячейка со значением "1" - часть живого корабля.
     * Ячейка со значением "2" - место рядом с кораблем, в котором нельзя устанавливать другие корабли.
     *
     * @param x         координата x.
     * @param y         координата y.
     * @param size      размер корабля.
     * @param direction направление корабля.
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

    /**
     * Метод, проверяющий, произошло ли попадание по кораблю от противника по заданным параметрам.
     * Если противник попал - уменьшает счетчик здоровья и помечает текущую ячейку значением "3"
     *
     * @param x координата x.
     * @param y координата y.
     * @return Возвращает true, если противник попал, false - в ином случае.
     */
    public boolean hitted(int x, int y) {
        if (mainField[x][y] == 1) {
            mainField[x][y] = 3;
            hp--;
            return true;
        }
        return false;
    }

    /**
     * Помечает меткой "3" ячейку с заданными координатами на дополнительном поле.
     * Метод используется для задании информации о том, что по заданным координатам у противника был корабль.
     *
     * @param x координата x.
     * @param y координата y.
     */
    public void markWhenHitted(int x, int y) {
        secondField[x][y] = 3;
    }

    /**
     * Помечает меткой "4" ячейку с заданными координатами на дополнительном поле.
     * Метод используется для задании информации о том, что по заданным координатам у противника было пусто.
     *
     * @param x координата x.
     * @param y координата y.
     */
    public void markWhenEmpty(int x, int y) {
        secondField[x][y] = 4;
    }

    /**
     * Метод, проверяющий, подходящая ли ячейка для стрельбы по заданным параметрам.
     * Ячейка может быть неподходящей, если в эту ячейку уже была произведена стрельба.
     * Используется для логики стрельбы бота.
     *
     * @param x координата x.
     * @param y координата y.
     * @return Возвращает true, если ячейка подходящая, false - в ином случае.
     */
    public boolean normalTarget(int x, int y) {
        return secondField[x][y] == 0;
    }

    /**
     * Получает текущее здоровье
     *
     * @return Возвращает здоровье.
     */
    public int getHP() {
        return this.hp;
    }

    /**
     * Заполняет поле боя случайным образом.
     * Используется для заполнения поле боя бота.
     */
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
