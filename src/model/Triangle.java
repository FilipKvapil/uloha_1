package model;

public class Triangle {

    //Body základny
    public Point z1;
    public Point z2;
    //Bod myši
    public Point m;

    public Triangle(Point z1, Point z2, Point m) {
        this.z1 = z1;
        this.z2 = z2;
        this.m = m;
    }

    public Point getPoint (){
        //Základna
        float k1 = (z2.getY()- z1.getY())/(float)(z2.getX()- z1.getX());
        //Středový bod základny
        Point s = new Point((z1.getX()+ z2.getX())/2,(z1.getY()+ z2.getY())/2);
        //Pravouhla
        float k2 = (float)(-1*(1.0/k1));
        float q2 = s.getY() - k2 * s.getY();

        //Rovnobezna
        float q3 = m.getY()-k1* m.getX();

        float xPoint = (q3-q2)/(k2-k1);
        float yPoint = k1*xPoint+q3;
        return new Point((int)xPoint,(int)yPoint);
    }
}
