package ru.krista.battleship.entities;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Random;

/**
 * Класс Оппонента, играющего против игрока.
 */
public class Opponent implements Serializable {


    /**
     * Поле боя оппонента.
     */
    @Inject
    private Field field;

    /**
     * Менеджер игры.
     */
    @Inject
    private GameManager manager;

    /**
     * Текущий ход оппонента.
     */
    private boolean turn = false;

    /**
     * Получает ход оппонента.
     *
     * @return Возвращает ход оппонента.
     */
    public boolean getTurn() {
        return turn;
    }

    /**
     * Получает поле боя оппонента.
     *
     * @return Возвращает поле боя оппонента.
     */
    public Field getField() {
        return field;
    }

    /**
     * Начинает игру для оппонента.
     * Заполняет неактивные ячейки поля.
     * Расставляет корабли случайным образом.
     *
     * @see Field#fillInactiveCells()
     * @see Field#fullShipsRandom()
     */
    public void startGame() {
        field.fillInactiveCells();
        field.fullShipsRandom();
    }

    /**
     * Стреляет по игроку в случайную координату x, y.
     * Если попадает - стреляет ещё раз, и так далее.
     * Прекращает стрелять, пока не промахнется, либо не победит.
     */
    public void fire() {
        Random r = new Random();
        int x = r.nextInt(10) + 1;
        int y = r.nextInt(10) + 1;

        while (!field.normalTarget(x, y)) {
            x = r.nextInt(10) + 1;
            y = r.nextInt(10) + 1;
        }
        if (manager.getPlayer().getField().hitted(x, y)) {
            if (checkWin()) return;
            field.markWhenHitted(x, y);
            fire();
        } else {
            field.markWhenEmpty(x, y);
            turn = false;
        }
    }

    /**
     * Метод проверки, победил ли бот после выстрела.
     * При победе передает в класс "Winner" имя игрока, заданное как "AI", и время победы.
     *
     * @return Возвращает true, если бот победил. False - в обратном случае.
     * @see Winner#win()
     */
    public boolean checkWin() {
        if (manager.getPlayer().getField().getHP() == 0) {
            manager.getWinner().setName("AI");
            manager.getWinner().setDate();
            manager.getWinner().win();
            return true;
        }
        return false;
    }
}
