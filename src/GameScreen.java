import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 650;
	public static final int HEIGHT = 480;
	
	public static final int borderLength = 5;
	
	public static final Color THEME = Color.white;
	
	
	BufferedImage image;

	private Player p1,p2;
	private Ball ball;

	private boolean pause;

	Thread thread;

	int fps = 60;
	long tt = 1000 / fps;

	private boolean running;

	Graphics2D g;

	public GameScreen() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setFocusable(true);
		requestFocus();

		g = (Graphics2D) getGraphics();

		p1 = new Player(35, HEIGHT/2-40, 15, 80);
		p2 = new Player(WIDTH-35-15, HEIGHT/2-40, 15, 80);
		ball = new Ball(WIDTH/2-10, HEIGHT/2-10  , 20, 20);
		
		pause = false;

	}

	public void detectCollision(Player p1) {
		if (p1.getLeftBound().intersects(ball.getRightBound())) {
			ball.setvX(-ball.getvX());
			ball.setvY(ball.getvY() + p1.getvY());
		}
		if (p1.getRightBound().intersects(ball.getLeftBound())) {
			ball.setvX(-ball.getvX());
			ball.setvY(ball.getvY() + p1.getvY());
		}
		if (p1.getUpperBound().intersects(ball.getLowerBound())) {
			ball.setvY(-ball.getvY() + p1.getvY());
		}
		if (p1.getLowerBound().intersects(ball.getUpperBound())) {
			ball.setvY(-ball.getvY() + p1.getvY());
		}
	}

	public void update() {
		p1.update();
		p2.update();
		ball.update();
		detectCollision(p1);
		detectCollision(p2);
	}

	public void draw() {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(THEME);
		g.fillRect(borderLength, 0, WIDTH-2*borderLength, borderLength);
		g.fillRect(borderLength, HEIGHT-borderLength, WIDTH-2*borderLength, borderLength);
		
	//	for()

		p1.draw(g);
		p2.draw(g);
		ball.draw(g);

	}

	public void drawAll() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	public void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		while (running) {
			System.out.print("");
			if (!pause) {
				start = System.nanoTime();
				update();
				draw();
				drawAll();
				elapsed = System.nanoTime() - start;
				wait = tt - elapsed / 1000000;
				if (wait < 0)
					wait = 5;
				try {
					Thread.sleep(wait);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		p1.keyTyped(e);
		p2.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		p1.keyPressed(e);
		p2.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			pause = !pause;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		p1.keyReleased(e);
		p2.keyReleased(e);
	}
}
