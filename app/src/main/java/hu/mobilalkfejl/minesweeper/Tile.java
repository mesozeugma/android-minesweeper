package hu.mobilalkfejl.minesweeper;

public class Tile {
    private boolean known;
    private boolean mine;
    private int x;
    private int y;

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

    public Tile(int x, int y, boolean mine) {
        this.x = x;
        this.y = y;
        this.mine = mine;
        this.known = false;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown() {
        this.known = true;
    }

    public boolean isMine() {
        return mine;
    }

}
