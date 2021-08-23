package ru.krista.battleship.entities;

import javax.inject.Inject;
import java.io.Serializable;

public class Player implements Serializable {
    private String name;

    @Inject
    private Field field;

    @Inject
    private GameManager manager;

    private Opponent op = manager.getOpponent();

    private Winner winner = manager.getWinner();

    private boolean turn = true;

    public Opponent getOp() {
        return op;
    }

    public String getName() {
        return name;
    }

    public boolean getTurn() {
        return turn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void startGame() {
        field.fillInactiveCells();
    }

    public void fire(int x, int y) {
        if ((x > 0 && x < 11) && (y > 0 && y < 11)) {
            if (op.getField().hitted(x, y)) {
                field.markWhenHitted(x, y);
                if (checkWin()) return;
            } else {
                field.markWhenEmpty(x, y);
                op.fire();
            }
        }
    }

    public Field getField() {
        return field;
    }

    public boolean checkWin() {
        if (op.getField().getHP() == 0) {
            winner.setName(this.name);
            winner.setDate();
            winner.win();
            return true;
        }
        return false;
    }
}
