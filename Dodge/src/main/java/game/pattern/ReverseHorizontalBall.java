package game.pattern;

public class ReverseHorizontalBall extends Ball {
	
	private final static int speed = (int)(Math.random()*2);

	public void move() {
		x -= speed;
	}
}
