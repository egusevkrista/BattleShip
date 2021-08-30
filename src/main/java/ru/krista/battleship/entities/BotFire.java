package ru.krista.battleship.entities;

public class BotFire {
    private int x;
    private int y;
    private boolean hit;

    public BotFire(int x, int y, boolean hit) {
        this.x = x;
        this.y = y;
        this.hit = hit;
    }

    public BotFire() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

}
