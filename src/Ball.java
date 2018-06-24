import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int vX, vY;

	private int maxVelocity = 17;

	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);

		vX = 8;
		vY = 0;
	}

	public void draw(Graphics2D g) {
		g.setColor(GameScreen.THEME);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.RED);
		g.fill(getLowerBound());
		g.fill(getUpperBound());
		g.fill(getLeftBound());
		g.fill(getRightBound());

	}

	public void update() {
		x += vX;
		y += vY;

		if (vY > maxVelocity) {
			vY = maxVelocity;
		}
		if (vY < -maxVelocity) {
			vY = -maxVelocity;
		}
		checkBounds();
	}

	public void checkBounds() {
		if (y >= GameScreen.HEIGHT - height - GameScreen.borderLength) {
			y = GameScreen.HEIGHT - height - GameScreen.borderLength;
			vY = -vY;
		}
		if (y <= GameScreen.borderLength) {
			y = GameScreen.borderLength;
			vY = -vY;
		}

		if (x >= GameScreen.WIDTH - width) {
			x = GameScreen.WIDTH - width;
			vX = -vX;
		}
		if (x <= 0) {
			x = 0;
			vX = -vX;
		}
	}

	public Rectangle getLowerBound() {
		return new Rectangle(x, y + height - 2, width, 2);
	}

	public Rectangle getUpperBound() {
		return new Rectangle(x, y, width, 2);
	}

	public Rectangle getRightBound() {
		return new Rectangle(x + width - 3, y+2, 3, height-4);
	}

	public Rectangle getLeftBound() {
		return new Rectangle(x, y+2, 3, height-4);
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
