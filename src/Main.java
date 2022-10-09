import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasRasterBufferedImage(800, 600).start());
    }
}
