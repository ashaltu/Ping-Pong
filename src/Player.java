import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Rectangle implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int vY;
	private boolean isUp, isDown;

	private double accelaration = 1;
	private int maxVelocityY = 12;
	private double gravity = .981;

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);

	}

	public void draw(Graphics2D g) {
		g.setColor(GameScreen.THEME);
		g.fill(this);
		
	}

	public void update() {
		if (vY > maxVelocityY) {
			vY = maxVelocityY;
		}
		if (vY < -maxVelocityY) {
			vY = -maxVelocityY;
		}

		if (isUp) {
			vY -= accelaration;
		} else if (isDown) {
			vY += accelaration;
		} else {
			vY*=gravity;
		}
				
		y += vY;
		
		checkBounds();

	}

	public void checkBounds() {
		if (y > 500 - height) {
			y = 500 - height;
		}
		if (y < 0) {
			y = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int event = e.getKeyCode();
		if (event == KeyEvent.VK_UP || event == KeyEvent.VK_W) {
			isUp = true;
		}
		if (event == KeyEvent.VK_DOWN || event == KeyEvent.VK_D) {
			isDown = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int event = e.getKeyCode();
		if (event == KeyEvent.VK_UP || event == KeyEvent.VK_W) {
			isUp = false;
		}
		if (event == KeyEvent.VK_DOWN || event == KeyEvent.VK_D) {
			isDown = false;
		}
	}

	public Rectangle getLowerBound() {
		return new Rectangle(x, y + height - 2, width, 2);
	}

	public Rectangle getUpperBound() {
		return new Rectangle(x, y, width, 2);
	}

	public Rectangle getRightBound() {
		return new Rectangle(x + width - 7, y+2, 7, height-4);
	}

	public Rectangle getLeftBound() {
		return new Rectangle(x, y+2, 7, height-4);
	}

	public int getvY() {
		return vY;
	}

}
