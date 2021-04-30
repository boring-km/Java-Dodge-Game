package game.pattern;

public class ReverseHorizontalBall extends Ball {
	
	private final static int speed = (int)(Math.random()*2);

	@Override
	public void move() {
		x -= speed;
	}
}
