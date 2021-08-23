package ru.krista.battleship.entities;

import javax.inject.Inject;
import java.io.Serializable;

public class Player implements Serializable {
    private String name;

    @Inject
    private Field field;

    @Inject
    private GameManager manager;

    private boolean turn = true;

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
            if (manager.getOpponent().getField().hitted(x, y)) {
                field.markWhenHitted(x, y);
                if (checkWin()) return;
            } else {
                field.markWhenEmpty(x, y);
                manager.getOpponent().fire();
            }
        }
    }

    public Field getField() {
        return field;
    }

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
