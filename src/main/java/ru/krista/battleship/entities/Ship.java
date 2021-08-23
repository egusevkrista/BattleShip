package ru.krista.battleship.entities;

/**
 * Класс Корабль для хранения информации об кораблях.
 */
public class Ship {

    /**
     * Координата х
     */
    private int x;
    /**
     * Координата y
     */
    private int y;
    /**
     * Размер корабля
     */
    private int size;
    /**
     * Направление корабля
     */
    private ShipDirection direction;

    /**
     * Конструкток для создания нового корабля
     *
     * @param x         координата х
     * @param y         координата y
     * @param size      размер корабля
     * @param direction направления корабля
     */
    public Ship(int x, int y, int size, ShipDirection direction) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.direction = direction;
    }

    /**
     * Получает координату х
     *
     * @return Возвращает координату х
     */
    public int getX() {
        return x;
    }

    /**
     * Получить координату y
     *
     * @return Возвращает координату y
     */
    public int getY() {
        return y;
    }

    /**
     * Получить размер корабля
     *
     * @return Возвращает размер корабля
     */
    public int getSize() {
        return size;
    }

    /**
     * Получить направление корабля
     *
     * @return Возвращает направление корабля
     */
    public ShipDirection getDirection() {
        return direction;
    }
}
