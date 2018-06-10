package com.tuyasmart.five.bean;

/**
 * Created by letian on 2017/9/10.
 */

public class FiveNode {

    public FiveNode(int x, int y, boolean white) {
        this.x = x;
        this.y = y;
        this.white = white;
    }

    private int x;
    private int y;

    private boolean white;

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

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }
}
