import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Rectangle implements KeyListener {

	private int vY;
	private boolean isUp, isDown;

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);

	}

	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fill(this);
	}

	public void update() {
		y += vY;
		vY = 0;
		if (isUp) {
			vY = -5;
		}
		if (isDown) {
			vY = 5;
		}
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
		return new Rectangle(2 + x, y + height - 2, width - 4, 2);
	}

	public Rectangle getUpperBound() {
		return new Rectangle(2 + x, y, width - 4, 2);
	}

	public Rectangle getRightBound() {
		return new Rectangle(x + width - 2, y, 2, height);
	}

	public Rectangle getLeftBound() {
		return new Rectangle(x, y, 2, height);
	}

}
