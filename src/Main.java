import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame gameScreen = new JFrame("Ping Pong");
		gameScreen.add(new GameScreen());
		gameScreen.pack();
		gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameScreen.setLocationRelativeTo(null);
		gameScreen.setVisible(true);
		gameScreen.setResizable(false);
	}
}
