package rasterize;

import model.Point;

import java.awt.image.BufferedImage;

public class FilledLineRasterizer extends LineRasterizer{

    public FilledLineRasterizer(Raster raster, BufferedImage img) {
        super(raster);
        this.img = img;
        this.color = 16711680;
    }

    public FilledLineRasterizer(Raster raster, BufferedImage img, int color) {
        super(raster);
        this.img = img;
        this.color = color;
    }
    private final BufferedImage img;
    private int color;

    public void drawLine(Point p1, Point p2) {
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (x2 < x1) {
                int x3 = x1;
                x1 = x2;
                x2 = x3;
                x3 = y1;
                y1 = y2;
                y2 = x3;
            }
            final float k = (y2 - y1) / (float)(x2 - x1);
            float y3 = y1 + 0.5f;
            for (int x3 = x1; x3 <= x2; ++x3) {
                this.drawPixel(x3, (int)y3);
                y3 += k;
            }
        }
        else {
            if (y2 < y1) {
                int x3 = x1;
                x1 = x2;
                x2 = x3;
                x3 = y1;
                y1 = y2;
                y2 = x3;
            }
            final float k = (x2 - x1) / (float)(y2 - y1);
            float y3 = x1 + 0.5f;
            for (int x3 = y1; x3 <= y2; ++x3) {
                this.drawPixel((int)y3, x3);
                y3 += k;
            }
        }
    }
    public void drawPixel(final int x, final int y) {
        if (x >= 0 && x < this.img.getWidth() && y >= 0 && y < this.img.getHeight()) {
            this.img.setRGB(x, y, this.color);
        }
    }
    public void drawPixel(final int x, final int y,int color) {
        if (x >= 0 && x < this.img.getWidth() && y >= 0 && y < this.img.getHeight()) {
            this.img.setRGB(x, y, color);
        }
    }
    public void setColor(final int color) {
        this.color = color;
    }
}
