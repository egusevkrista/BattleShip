package ru.krista.battleship.entities;

import javax.inject.Inject;
import java.io.Serializable;
/**
 * Класс Игрок в морской бой для хранения и обработки информации об игроке.
 * @author egusev
 * @version 1.0
 */

public class Player implements Serializable {
    /**
     * Имя игрока.
     */
    private String name;


    /**
     * Поле боя игрока.
     */
    @Inject
    private Field field;

    /**
     * Менеджер игры.
     */
    @Inject
    private GameManager manager;

    /**
     * Текущий ход.
     */
    private boolean turn = true;

    /**
     * Метод получения имени игрока
     * @return Возвращает имя игрока.
     */
    public String getName() {
        return name;
    }

    /**
     * Получение хода игрока
     * @return Возвращает ход игрока.
     */
    public boolean getTurn() {
        return turn;
    }

    /**
     * Задание имени игрока
     * @param name имя игрока
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод начинает игру для игрока, заполняя неактивные ячейки поля.
     * @see Field#fillInactiveCells()
     */
    public void startGame() {
        field.fillInactiveCells();
    }

    /**
     * Метод стрельбы по противнику - боту.
     * @param x координата x.
     * @param y координата y.
     */
    public void fire(int x, int y) {
        if ((x > 0 && x < 11) && (y > 0 && y < 11)) {
            if (manager.getOpponent().getField().hitted(x, y)) {
                field.markWhenHitted(x, y);
                if (checkWin()) return;
            } else {
                field.markWhenEmpty(x, y);
                manager.getOpponent().fire();
            }
        }
    }


    /**
     * Получает поля боя игрока.
     * @return Возвращает поле боя.
     */
    public Field getField() {
        return field;
    }

    /**
     * Метод проверки, победил ли игрок после выстрела.
     * При победе передает в класс "Winner" имя игрока и время победы.
     * @return Возвращает true, если игрок победил. False - в обратном случае.
     * @see Winner#win()
     */
    public boolean checkWin() {
        if (manager.getOpponent().getField().getHP() == 0) {
            manager.getWinner().setName(this.name);
            manager.getWinner().setDate();
            manager.getWinner().win();
            return true;
        }
        return false;
    }
}
