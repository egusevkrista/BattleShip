package ru.krista.battleship.entities;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;

/**
 * Класс Победитель для оформления информации о победе.
 */
public class Winner implements Serializable {

    /**
     * Имя победившего игрока.
     */
    private String name;
    /**
     * Время победы.
     */
    private Date date;

    /**
     * Текущая сессия.
     */
    @Inject
    private HttpSession session;

    /**
     * Переменная для обозначения - закончилась игра или нет.
     */
    private boolean gameIsEnded = false;

    /**
     * Метод, вызываемые в случае победы.
     * Завершает сессию.
     */
    public void win() {
        gameIsEnded = true;
        session.invalidate();
    }

    /**
     * Получает информацию, завершена игра или нет.
     *
     * @return Возвращает переменную завершения игры.
     */
    public boolean getGameIsEnded() {
        return gameIsEnded;
    }

    /**
     * Задает имя победителя
     *
     * @param name имя победителя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Задает дату победы.
     */
    public void setDate() {
        this.date = new Date();
    }
}
