package game.pattern;

public class VerticalBall extends Ball {
	
	private final static int speed = 1;

	@Override
	public void move() {
		y += speed;
	}
}
