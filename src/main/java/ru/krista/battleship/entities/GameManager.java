package ru.krista.battleship.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;

/**
 * Класс Менеджер игры для централизации всех компонентов игры.
 */
@SessionScoped
public class GameManager implements Serializable {

    private Date startDate;
    private Date finishDate;
    /**
     * Игрок.
     */
    @Inject
    private Player player;

    /**
     * Оппонент(бот).
     */
    @Inject
    private Opponent opponent;


    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    /**
     * Получает игрока.
     *
     * @return Возвращает игрока
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Получает оппонента(бота)
     *
     * @return Возвращает оппонента(бота)
     */
    public Opponent getOpponent() {
        return opponent;
    }

    /**
     * Начинает игру для игрока и для оппонента(бота).
     *
     * @see Player#startGame()
     * @see Opponent#startGame()
     */
    public void startGame() {
        startDate = new Date();
        player.startGame();
        opponent.startGame();
    }

    public void confirmWin(String name, Date startDate, Date finishDate) {
        //
    }

}
