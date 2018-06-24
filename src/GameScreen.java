import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

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
	
	private int p1Score,p2Score;
	
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

		gameReset();

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
	
	public void gameReset() {
		g = (Graphics2D) getGraphics();

		p1 = new Player(35, HEIGHT/2-40, 15, 80);
		p2 = new Player(WIDTH-35-15, HEIGHT/2-40, 15, 80);
		ball = new Ball(WIDTH/2-10, HEIGHT/2-10  , 20, 20);
		
		pause = false;
		
		p1Score=0;
		p2Score=0;
	}
	
	public void checkBounds() {
		if (ball.y >= HEIGHT - ball.height - borderLength) {
			ball.y = HEIGHT - ball.height - borderLength;
			ball.setvY(-ball.getvY());
		}
		if (ball.y <= borderLength) {
			ball.y = borderLength;
			ball.setvY(-ball.getvY());
		}

		if (ball.x >= WIDTH + 10) {
			p1Score++;
			resetCoordinates();
		}
		if (ball.x <= -30) {
			p2Score++;
			resetCoordinates();
		}
	}	
	
	public void checkScore() {
		if(p1Score==5) {
			System.out.println("Player 1 Wins!");
			gameReset();
			pause = true;
		}
		if(p2Score == 5) {
			System.out.println("Player 2 Wins!");
			gameReset();
			pause=true;
		}
	}
	
	public void resetCoordinates() {
		ball.x = (WIDTH-15)/2;
		ball.y= (HEIGHT-40)/2;
		
		Random random = new Random();
		int randomVY = random.nextInt(10)-5;

		ball.setvY(randomVY);
		
		System.out.printf("\tScoreboard\nPlayer 1\t\tPlayer 2\n%d\t\t%d\n",p1Score,p2Score);
	}

	public void update() {
		p1.update();
		p2.update();
		ball.update();
		checkBounds();
		detectCollision(p1);
		detectCollision(p2);
		checkScore();
	}

	public void draw() {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (int i = 0; i < 25; i++) {
			g.setColor(THEME);
			g.fillRect((WIDTH-12)/2, 8+7*i+12*i, 6, 12);
		}
		
		g.setColor(THEME);
		g.fillRect(borderLength, 0, WIDTH-2*borderLength, borderLength);
		g.fillRect(borderLength, HEIGHT-borderLength, WIDTH-2*borderLength, borderLength);

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
