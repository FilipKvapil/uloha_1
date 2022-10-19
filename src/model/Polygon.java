package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    public List<Point> points;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point p){
        points.add(p);
    }

    public Point getPoint (int index){
        return points.get(index);
    }

    public int getCount (){
        return points.size();
    }

    public List<Point> getList() {
        return new ArrayList<>(points);
    }

    public void setList(List<Point> points){
        this.points = points;
    }
    public void setPoint (int index,Point p){
        points.set(index,p);
    }
    public void clear(){
        points.clear();
    }
}
