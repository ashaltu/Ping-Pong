import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable{
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	Rectangle rect;
	
	Graphics g;
	public GameScreen() {
		setMinimumSize(new Dimension(WIDTH,HEIGHT));
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		g = getGraphics();
	}
	
	public void update(Graphics g) {
		
	}
	public void paint(Graphics g) {
		super.paint(g);
		rect = new Rectangle(0,40,25,70);
		g.setColor(Color.BLACK);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		
	}
	
	public void drawAll() {
		paint(g);
	}

	@Override
	public void run() {
		while(true) {
			System.out.println(rect.y);

			Thread thread = Thread.currentThread();
			thread.start();
			try {
				thread.sleep( 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(rect.y);

			drawAll();
		}
	}
}
