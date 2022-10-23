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
	String mode = "Rectangle";

	public CanvasRasterBufferedImage(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());

		frame.setTitle("Filip Kvapil - Uloha 1" );
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
		clear();
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
						lineRasterizer.setColor(Color.BLUE.getRGB());
						lineRasterizer.drawInterLine(trianglePoints.getPoint(0),new Point(e.getX(),e.getY()));
						lineRasterizer.setColor(Color.RED.getRGB());
						repaint();}
				}
					if (e.getButton() == MouseEvent.BUTTON1 && !TPress) {
						polygon.addPoint(new Point(e.getX(),e.getY()));
						raster.clear();
						polygonRasterizer.drawPolygon(polygon);
						if (polygon.getCount() == 2){
							raster.clear();
							lineRasterizer.drawLine(polygon.getPoint(polygon.getCount()-2),new Point(e.getX(),e.getY()));}
						repaint();
					}
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

					if(TPress && trianglePoints.getCount() <= 3){
							raster.clear();
							lineRasterizer.setColor(Color.BLUE.getRGB());
							lineRasterizer.drawInterLine(trianglePoints.getPoint(0),trianglePoints.getPoint(1));
							Point point = new Triangle(trianglePoints.getPoint(0),trianglePoints.getPoint(1),new Point(e.getX(),e.getY())).getPoint();
							lineRasterizer.drawInterLine(trianglePoints.getPoint(1),point);
							lineRasterizer.drawInterLine(point,trianglePoints.getPoint(0));
							lineRasterizer.setColor(Color.RED.getRGB());
						repaint();
					}else{
						if (polygon.getCount()==1){
								raster.clear();
								lineRasterizer.drawLine(polygon.getPoint(0),new Point(e.getX(),e.getY()));
							repaint();
							} else {
								raster.clear();
								Polygon polygon1 = new Polygon();
								polygon1.setList(polygon.getList());
								polygon1.addPoint(new Point(e.getX(),e.getY()));
								polygonRasterizer.drawPolygon(polygon1);
							repaint();
								}

					}
			}
		});

		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_T -> {
						TPress = !TPress;
						mode = "Triangle";
						clear();
					}
					case KeyEvent.VK_R ->{
						TPress = !TPress;
						mode = "Rectangle";
						clear();
					}
					case KeyEvent.VK_C -> clear();
					default -> {
					}
				}

			}
		});

	}
	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}
	public void clear (){
		raster.clear();
		polygon.clear();
		trianglePoints.clear();
		repaint();
	}

	public void repaint() {
		panel.repaint();
		raster.getImg().getGraphics().setColor(Color.white);
			raster.getImg().getGraphics().drawString("Klávesa T - Triangle,Klávesa R - Rectangle, Klávesa C - Clear", 5, raster.getImg().getHeight() - 5);
			raster.getImg().getGraphics().drawString("Aktuální režim : " + mode, 5,  15);
	}
}























