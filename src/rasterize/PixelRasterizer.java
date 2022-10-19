package rasterize;

import java.awt.image.BufferedImage;

public class PixelRasterizer {
    BufferedImage img;

    public PixelRasterizer(BufferedImage img) {
        this.img = img;
    }

    protected void drawPixel(final int x, final int y, final int barva) {
        if (x >= 0 && x < this.img.getWidth() && y >= 0 && y < this.img.getHeight()) {
            this.img.setRGB(x, y, barva);
        }
    }
}
