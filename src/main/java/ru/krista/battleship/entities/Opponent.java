package ru.krista.battleship.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;
import java.io.Serializable;
import java.util.Random;

@SessionScoped
public class Opponent implements Serializable {

    @Inject
    private Field field;

    public Opponent() {}

    @Inject
    private Player player;

    @Inject
    private Winner winner;

    public Player getPlayer()
    {
        return player;
    }

    private boolean turn = false;

    public boolean getTurn()
    {
        return turn;
    }

    public Field getField(){
        return field;
    }

    public void fire()
    {
        Random r = new Random();
        int x = r.nextInt(10)+1;
        int y = r.nextInt(10)+1;

        while (!field.normalTarget(x,y)){
            x = r.nextInt(10) + 1;
            y = r.nextInt(10) + 1;
        }
        if (player.getField().hitted(x,y))
        {
            if (checkWin())
                return;
            field.markWhenHitted(x,y);
            fire();
        }
        else {
            field.markWhenEmpty(x,y);
            turn = false;
        }
    }

    public boolean checkWin()
    {
        if (player.getField().getHP() == 0)
        {
            winner.setName("AI");
            winner.setDate();
            winner.win();
            return true;
        }
        return false;
    }
}
