import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable, KeyListener {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	BufferedImage image;

	private Player p1;
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

		p1 = new Player(50, 50, 20, 70);
		ball = new Ball(230, 230, 12, 12);

		pause = false;

	}

	public void detectCollision() {
		if (p1.getLeftBound().intersects(ball.getRightBound())) {
			ball.setvX(-ball.getvX());
		}
		if (p1.getRightBound().intersects(ball.getLeftBound())) {
			System.out.println("hit");
			ball.setvX(-ball.getvX());
		}
		if (p1.getUpperBound().intersects(ball.getLowerBound())) {
			ball.setvY(-ball.getvY());
		}
		if (p1.getLowerBound().intersects(ball.getUpperBound())) {
			ball.setvY(-ball.getvY());
		}
	}

	public void update() {
		p1.update();
		ball.update();
		detectCollision();
	}

	public void draw() {
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		p1.draw(g);
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
	}

	@Override
	public void keyPressed(KeyEvent e) {
		p1.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			pause = !pause;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		p1.keyReleased(e);
	}
}
