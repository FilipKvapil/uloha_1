import model.Point;
import model.Polygon;
import rasterize.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.Timer;
import java.util.TimerTask;


public class CanvasRasterBufferedImage {

	private final JPanel panel;
	private final RasterBufferedImage raster;
	private final FilledLineRasterizer lineRasterizer;
	private final Polygon polygon;
	private final PolygonRasterizer polygonRasterizer;

	public CanvasRasterBufferedImage(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());

		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		raster = new RasterBufferedImage(width, height);
		lineRasterizer = new FilledLineRasterizer(raster,raster.getImg());
		polygonRasterizer = new PolygonRasterizer(lineRasterizer);
		polygon = new Polygon();

		panel = new JPanel() {
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && polygon.getCount() == 0)
					polygon.addPoint(new Point(e.getX(), e.getY()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					polygon.addPoint(new Point(e.getX(),e.getY()));
					raster.clear();
					polygonRasterizer.drawPolygon(polygon);
				}
				if (e.getButton() == MouseEvent.BUTTON1 && polygon.getCount() == 2){
					raster.clear();
					lineRasterizer.drawLine(polygon.getPoint(polygon.getCount()-2),new Point(e.getX(),e.getY()));
					System.out.println("a");}
				panel.repaint();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				raster.clear();
				if (polygon.getCount()==1){
					lineRasterizer.drawLine(polygon.getPoint(polygon.getCount()-1),new Point(e.getX(),e.getY()));
				} else {
						Polygon polygon1 = new Polygon();
						polygon1.setList(polygon.getList());
						polygon1.addPoint(new Point(e.getX(),e.getY()));
						polygonRasterizer.drawPolygon(polygon1);
					}
				panel.repaint();
			}
		});

		/*panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (panel.getWidth()<1 || panel.getHeight()<1)
					return;
				if (panel.getWidth()<=raster.getWidth() && panel.getHeight()<=raster.getHeight()) //no resize if new one is smaller
					return;
				RasterBufferedImage newRaster = new RasterBufferedImage(panel.getWidth(), panel.getHeight());

				newRaster.draw(raster);
				raster = newRaster;
				linerasterizer = new LineRasterizerGraphics(raster);

			}
		});*/

	}

	public void clear(int color) {
		raster.setClearColor(color);
		raster.clear();
	}

	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}

	public void start() {
		clear(0xaaaaaa);
		raster.getGraphics().drawString("Use mouse buttons and try resize the window", 5, 15);
		panel.repaint();
	}

}
