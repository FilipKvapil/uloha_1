package rasterize;

import model.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilledLineRasterizer extends LineRasterizer{

    private final BufferedImage img;
    private int color;

    public FilledLineRasterizer(Raster raster, BufferedImage img) {
        super(raster);
        this.img = img;
        this.color = Color.RED.getRGB();
    }
    /*Byl použit triviální algoritmus.
    *Nevýhoda: násobení a sčítání v plovoucí řádové čárce je neefektivní
    *Výhoda: postup použitelný i pro složitější křivky
    * */

    public void drawLine(Point p1, Point p2) {

        float x1 = p1.getX();
        float y1 = p1.getY();
        float x2 = p2.getX();
        float y2 = p2.getY();
        //Určení řídící osy
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            //Výměna koncových bodů
            if (x2 < x1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (y2 - y1) / (x2 - x1);

            //Vyhreslení bodu podle Y
            for (float i = x1; i <= x2; ++i) {
                this.drawPixel((int)i,(int)y1);
                y1 += k;
            }

        }
        else {
            //Výměna koncových bodů
            if (y2 < y1) {
                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            float k = (x2 - x1) / (y2 - y1);
                //Vyhreslení bodu podle X
            for (float i = y1; i <= y2; ++i) {
                this.drawPixel((int)x1, (int)i);
                x1 += k;
            }
        }
    }
    //algoritmus pro vykreslení tečkované čáty
    public void drawInterLine(Point p1, Point p2) {

        float x1 = p1.getX();
        float y1 = p1.getY();
        float x2 = p2.getX();
        float y2 = p2.getY();

        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (x2 < x1) {

                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            final float k = (y2 - y1) / (x2 - x1);


            for (float i = x1; i <= x2; i++) {
                if(i%2 ==1)
                drawPixel((int)i, (int)y1);
                y1 += k;
            }

        }
        else {

            if (y2 < y1) {

                float pomocna = x1;
                x1 = x2;
                x2 = pomocna;

                pomocna = y1;
                y1 = y2;
                y2 = pomocna;
            }

            final float k = (x2 - x1) / (y2 - y1);


            for (float i = y1; i <= y2; i++) {
                if(i%2 ==1)
                drawPixel((int)x1, (int)i);
                x1 += k;
            }
        }
    }

    private void drawPixel(final int x, final int y) {
        //Blokace vykreslení mimo plochu
        if (x >= 0 && x < this.img.getWidth() && y >= 0 && y < this.img.getHeight()) {
            img.setRGB(x, y, this.color);
        }
    }
    public void setColor(final int color) {
        this.color = color;
    }

}
