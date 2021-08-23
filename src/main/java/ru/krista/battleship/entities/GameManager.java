package ru.krista.battleship.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Класс Менеджер игры для централизации всех компонентов игры.
 */
@SessionScoped
public class GameManager implements Serializable {

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


    /**
     * Победитель игры.
     */
    @Inject
    private Winner winner;

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
     * Получает победителя игры.
     *
     * @return Возвращает победителя игры.
     */
    public Winner getWinner() {
        return winner;
    }

    /**
     * Начинает игру для игрока и для оппонента(бота).
     *
     * @see Player#startGame()
     * @see Opponent#startGame()
     */
    public void startGame() {
        player.startGame();
        opponent.startGame();
    }

}
