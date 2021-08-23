package ru.krista.battleship.entities;

/**
 * enum для перечисления направлений корабля.
 */
public enum ShipDirection {
    UP,
    RIGHT;

    /**
     * Получение направления корабля по номеру
     * @param number номер корабля
     * @return Возвращает направление корабля
     */
    public static ShipDirection getDirectionByNumber(int number)
    {
        ShipDirection directions[] = ShipDirection.values();
        return directions[number];
    }
}
