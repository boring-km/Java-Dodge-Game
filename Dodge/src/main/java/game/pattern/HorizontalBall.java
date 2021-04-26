package game.pattern;

public class HorizontalBall extends Ball {

	private final static int speed = (int)(Math.random()*3) + 1;

	public void move() {
		x += speed;
	}
}
