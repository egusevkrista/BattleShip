package ru.krista.battleship.entities;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
public class Player implements Serializable {
    private String name;

    @Inject
    private Field field;

    public Player() {}

    public Opponent getOp()
    {
        return op;
    }

    @Inject private Opponent op;

    @Inject
    private Winner winner;

    private boolean turn = true;

    public String getName()
    {
        return name;
    }

    public boolean getTurn()
    {
        return turn;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void fire(int x, int y)
    {
        if ((x>0 && x<11) && (y>0 && y<11))
        {
            if (op.getField().hitted(x,y))
            {
                field.markWhenHitted(x, y);
                if (checkWin())
                    return;
            }
            else {
                field.markWhenEmpty(x, y);
                op.fire();
            }
        }
    }

    public Field getField()
    {
        return field;
    }

    public boolean checkWin()
    {
        if (op.getField().getHP() == 0)
        {
            winner.setName(this.name);
            winner.setDate();
            winner.win();
            return true;
        }
        return false;
    }
}
