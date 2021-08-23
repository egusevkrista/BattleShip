package ru.krista.battleship.entities;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Random;

public class Opponent implements Serializable {

    @Inject
    private Field field;

    @Inject
    private GameManager manager;

    private Player player = manager.getPlayer();

    private Winner winner = manager.getWinner();

    private boolean turn = false;

    public Player getPlayer() {
        return player;
    }

    public boolean getTurn() {
        return turn;
    }

    public Field getField() {
        return field;
    }

    public void startGame() {
        field.fillInactiveCells();
        field.fullShipsRandom();
    }

    public void fire() {
        Random r = new Random();
        int x = r.nextInt(10) + 1;
        int y = r.nextInt(10) + 1;

        while (!field.normalTarget(x, y)) {
            x = r.nextInt(10) + 1;
            y = r.nextInt(10) + 1;
        }
        if (player.getField().hitted(x, y)) {
            if (checkWin()) return;
            field.markWhenHitted(x, y);
            fire();
        } else {
            field.markWhenEmpty(x, y);
            turn = false;
        }
    }

    public boolean checkWin() {
        if (player.getField().getHP() == 0) {
            winner.setName("AI");
            winner.setDate();
            winner.win();
            return true;
        }
        return false;
    }
}
