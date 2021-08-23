package ru.krista.battleship.entities;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;

public class Winner implements Serializable {

    private String name;
    private Date date;

    @Inject
    private HttpSession session;

    private boolean gameIsEnded = false;

    public void win() {
        gameIsEnded = true;
        session.invalidate();
    }

    public boolean getGameIsEnded() {
        return gameIsEnded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate() {
        this.date = new Date();
    }
}
