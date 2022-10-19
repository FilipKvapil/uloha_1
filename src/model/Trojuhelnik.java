package model;

import model.Point;

public class Trojuhelnik {
    //Body základny
    Point A =new Point(1,2);
    Point B =new Point(2,3);
    //Bod myši
    Point M =new Point(4,5);
    //Středový bod základny
    Point S = new Point((A.getX()+A.getY())/2,(B.getX()+B.getY())/2);

    //Základna
    public int k1 = 1;
    public int q1 = 2;

    //Pravouhla
    public int k2 = -(1/k1);
    public int q2 = S.getY() - k2;

    //Rovnobezna
    public int k3 = k1;
    public int q3 = M.getY()-k3*M.getX();

    public Point getbod (){
        int xBod = (q3-q2)/(k2-k3);
        int yBod = k3*xBod+q3;
        return new Point(xBod,yBod);
    }
}
