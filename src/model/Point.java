package model;

public class Point {

    public int x, y;
    public int color;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = 0xFFFF0000 ;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
