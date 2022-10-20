package rasterize;

import model.Point;
import model.Polygon;
import org.jetbrains.annotations.NotNull;

public class PolygonRasterizer {

    FilledLineRasterizer filledLineRasterizer;
    public PolygonRasterizer(FilledLineRasterizer filledLineRasterizer) {
        this.filledLineRasterizer = filledLineRasterizer;
    }

    public void drawPolygon(@NotNull Polygon polygon){

        if(polygon.getCount() < 3)
            return;

        for (int i = 0; i < polygon.getCount(); i++) {
            Point point1 = polygon.getPoint(i);
            Point point2;
            if((i+1)!=polygon.getCount()){point2 = polygon.getPoint(i+1);} else{point2 = polygon.getPoint(0);}
            //filledLineRasterizer.drawInterLine(point1,point2);
            filledLineRasterizer.drawLine(point1,point2);
        }
    }
}
