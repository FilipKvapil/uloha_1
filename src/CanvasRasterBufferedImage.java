import model.Point;
import model.Polygon;
import model.Triangle;
import rasterize.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;


public class CanvasRasterBufferedImage {

	private final JPanel panel;
	private final RasterBufferedImage raster;
	private final FilledLineRasterizer lineRasterizer;
	private final PolygonRasterizer polygonRasterizer;
	private final Polygon polygon;
	private final Polygon trianglePoints;
	private boolean TPress =false	;

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
		trianglePoints = new Polygon();
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

		panel.requestFocus();
		panel.requestFocusInWindow();

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && polygon.getCount() == 0)
					polygon.addPoint(new Point(e.getX(), e.getY()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {

				if(TPress && e.getButton() == MouseEvent.BUTTON1 && (trianglePoints.getCount() == 0 || trianglePoints.getCount() == 1)){
					trianglePoints.addPoint(new Point(e.getX(), e.getY()));
					if(trianglePoints.getCount() == 2){
						lineRasterizer.drawLine(trianglePoints.getPoint(0),new Point(e.getX(),e.getY()));
						panel.repaint();}
				}
					if (e.getButton() == MouseEvent.BUTTON1 && !TPress) {
						polygon.addPoint(new Point(e.getX(),e.getY()));
						raster.clear();
						polygonRasterizer.drawPolygon(polygon);
						if (polygon.getCount() == 2){
							raster.clear();
							lineRasterizer.drawLine(polygon.getPoint(polygon.getCount()-2),new Point(e.getX(),e.getY()));}
						panel.repaint();
					}
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

					if(TPress && trianglePoints.getCount() <= 3){
							raster.clear();
							lineRasterizer.drawLine(trianglePoints.getPoint(0),trianglePoints.getPoint(1));
							Point point = new Triangle(trianglePoints.getPoint(0),trianglePoints.getPoint(1),new Point(e.getX(),e.getY())).getPoint();
							lineRasterizer.drawLine(trianglePoints.getPoint(1),point);
							lineRasterizer.drawLine(point,trianglePoints.getPoint(0));
						panel.repaint();
					}else{
						if (polygon.getCount()==1){
								raster.clear();
								lineRasterizer.drawInterLine(polygon.getPoint(0),new Point(e.getX(),e.getY()));
							} else {
								raster.clear();
								Polygon polygon1 = new Polygon();
								polygon1.setList(polygon.getList());
								polygon1.addPoint(new Point(e.getX(),e.getY()));
								polygonRasterizer.drawPolygon(polygon1);
								}
						panel.repaint();
					}
			}
		});

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_T -> {
						TPress = !TPress;
						clear();
					}
					case KeyEvent.VK_C -> clear();
					default -> {
					}
				}

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
	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}
	public void clear (){
		raster.clear();
		polygon.clear();
		trianglePoints.clear();
		panel.repaint();
	}

}
