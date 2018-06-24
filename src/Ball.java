import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball extends Rectangle {

	private int vX, vY;

	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);

		vX = 3;
		vY = 5;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);

	}

	public void update() {
		x += vX;
		y += vY;

		checkBounds();
	}

	public void checkBounds() {
		if (y >= 500 - height) {
			y = 500 - height;
			vY = -vY;
		}
		if (y <= 0) {
			y = 0;
			vY = -vY;
		}

		if (x >= 500 - height) {
			x = 500 - height;
			vX = -vX;
		}
		if (x <= 0) {
			x = 0;
			vX = -vX;
		}
	}

	public Rectangle getLowerBound() {
		return new Rectangle(1 + x, y + height - 1, width - 2, 1);
	}

	public Rectangle getUpperBound() {
		return new Rectangle(1 + x, y, width - 2, 1);
	}

	public Rectangle getRightBound() {
		return new Rectangle(x + width - 1, y, 1, height);
	}

	public Rectangle getLeftBound() {
		return new Rectangle(x, y, 1, height);
	}

	public int getvX() {
		return vX;
	}

	public void setvX(int vX) {
		this.vX = vX;
	}

	public int getvY() {
		return vY;
	}

	public void setvY(int vY) {
		this.vY = vY;
	}
}
