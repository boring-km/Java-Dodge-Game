package game.pattern;

public class VerticalBall extends Ball {
	
	private final static int speed = 1;

	public void move() {
		y += speed;
	}
}
