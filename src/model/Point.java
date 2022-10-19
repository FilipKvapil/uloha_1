package model;

public class Point {

    public int x, y;
    public int color;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = 0xFFFF0000 ;
    }

    public Point(int x, int y,int color) {
        this.x = x;
        this.y = y;
        this.color = color ;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }
}
