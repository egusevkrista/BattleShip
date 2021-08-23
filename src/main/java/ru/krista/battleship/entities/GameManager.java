package ru.krista.battleship.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class GameManager implements Serializable {

    @Inject
    private Player player;

    @Inject
    private Opponent opponent;

    @Inject
    private Winner winner;

    public Player getPlayer() {
        return player;
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public Winner getWinner() {
        return winner;
    }

    public void startGame() {
        player.startGame();
        opponent.startGame();
    }

}
