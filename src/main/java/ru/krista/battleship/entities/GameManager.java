package ru.krista.battleship.entities;

import ru.krista.battleship.DataAccessObject;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Класс Менеджер игры для централизации всех компонентов игры.
 */
@SessionScoped
public class GameManager implements Serializable {

    private Date startDate;
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

    @Inject
    private DataAccessObject dao;

    @Inject
    private HttpSession httpSession;

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

    public void confirmWin(String name, Date finishDate, String result) {
        dao.saveGame(new Winner(name, result, this.startDate, finishDate));
        httpSession.invalidate();
    }

    public List<Winner> getWinners() {
        return dao.getWinners();
    }

}
