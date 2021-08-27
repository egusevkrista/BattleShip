package ru.krista.battleship.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    private ArrayList<Ship> ships = new ArrayList<Ship>();
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
     * Если лимит не достигнут - увеличивает соответствующий счетчик для корабля.
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

    public ArrayList getShips() {
        return ships;
    }

    /**
     * Проверяет возможность установки корабля по заданным параметрам и лимит кораблей на поле боя.
     * Устанавливает корабль, если это возможно.
     *
     * @param newShip переменная класса Корабль
     * @return Возвращает 0, если корабль успешно установлен, -1 - в ином случае.
     * @see Field#checkPlace(Ship)
     * @see Field#checkCountShips(int)
     */
    public int placeShip(Ship newShip) {
        if (checkPlace(newShip) && checkCountShips(newShip.getSize())) {
            captureCells(newShip);
            ships.add(newShip);
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
     * @param newShip переменная класса Корабль
     * @return Возвращает true, если на это место возможно установить корабль, false - в ином случае.
     */
    private boolean checkPlace(Ship newShip) {
        int countDown = 0;
        int countLeft = 0;

        if (newShip.getDirection() == ShipDirection.DOWN) {
            for (int i = newShip.getX(); i < newShip.getSize() + newShip.getX(); i++) {
                if (checkCell(i, newShip.getY())) countDown++;
            }
            return countDown == newShip.getSize();
        }

        if (newShip.getDirection() == ShipDirection.LEFT) {
            for (int i = newShip.getY(); i > newShip.getY() - newShip.getSize(); i--) {
                if (checkCell(newShip.getX(), i)) countLeft++;
            }
            return countLeft == newShip.getSize();
        }
        return false;
    }

    /**
     * Проверяет, занята ли текущая ячейка.
     *
     * @param x координата x.
     * @param y координата y.
     * @return Возвращает true, если ячейка свободна, false - в ином случае.
     */
    private boolean checkCell(int x, int y) {
        if ((x > 10 || x < 1) || (y > 10 || y < 1)) return false;
        return mainField[x][y] == 0;
    }

    /**
     * Захватывает ячейки для корабля по заданным параметрам.
     * Ячейка со значением "1" - часть живого корабля.
     * Ячейка со значением "2" - место рядом с кораблем, в котором нельзя устанавливать другие корабли.
     *
     * @param newShip переменная класса Корабль
     */
    private void captureCells(Ship newShip) {
        if (newShip.getDirection() == ShipDirection.DOWN) {
            for (int i = newShip.getX(); i < newShip.getSize() + newShip.getX(); i++) {
                mainField[i][newShip.getY()] = 1;
            }
            for (int i = newShip.getX() - 1; i <= newShip.getSize() + newShip.getX(); i++) {
                mainField[i][newShip.getY() - 1] = mainField[i][newShip.getY() + 1] = 2;
            }
            mainField[newShip.getX() - 1][newShip.getY()] =
                    mainField[newShip.getX() + newShip.getSize()][newShip.getY()] = 2;
        }

        if (newShip.getDirection() == ShipDirection.LEFT) {
            for (int i = newShip.getY(); i > newShip.getY() - newShip.getSize(); i--) {
                mainField[newShip.getX()][i] = 1;
            }
            for (int i = newShip.getY() + 1; i >= newShip.getY() - newShip.getSize(); i--) {
                mainField[newShip.getX() - 1][i] = mainField[newShip.getX() + 1][i] = 2;
            }
            mainField[newShip.getX()][newShip.getY() + 1] =
                    mainField[newShip.getX()][newShip.getY() - newShip.getSize()] = 2;
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
     * Метод используется для задания информации о том, что по заданным координатам у противника был корабль.
     *
     * @param x координата x.
     * @param y координата y.
     */
    public void markWhenHitted(int x, int y) {
        secondField[x][y] = 3;
    }

    /**
     * Помечает меткой "4" ячейку с заданными координатами на дополнительном поле.
     * Метод используется для задания информации о том, что по заданным координатам у противника было пусто.
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

        while (countSimpleDeck <= 3) {
            placeShip(createRandomShipBySize(1));
        }
        while (countTwoDeck <= 2) {
            placeShip(createRandomShipBySize(2));
        }
        while (countThreeDeck <= 1) {
            placeShip(createRandomShipBySize(3));
        }
        while (countFourDeck == 0) {
            placeShip(createRandomShipBySize(4));
        }
    }

    /**
     * Создает корабль со случайными параметрами по заданному размеру.
     *
     * @param size размер корабля
     * @return Возвращает созданный случайный корабль.
     */
    private Ship createRandomShipBySize(int size) {
        Random r = new Random();

        int x = r.nextInt(10) + 1;
        int y = r.nextInt(10) + 1;
        int d = r.nextInt(2);

        return new Ship(x, y, size, ShipDirection.getDirectionByNumber(d));
    }
}
