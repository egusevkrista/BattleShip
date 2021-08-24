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
     * Конструктор для создания нового корабля
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
     * Конструктор по умолчанию
     */
    public Ship() {

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

    /**
     * Задает координату х
     * @param x координата х
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Задает координату y
     * @param y координата y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Задает размер корабля
     * @param size размер корабля
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Задает направление корабля
     * @param direction направление корабля
     */
    public void setDirection(ShipDirection direction) {
        this.direction = direction;
    }

}
