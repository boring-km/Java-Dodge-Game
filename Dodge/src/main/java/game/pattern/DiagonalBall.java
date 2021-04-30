package game.pattern;

public class DiagonalBall extends Ball {

	private final static int speed = 2;
	private final static double angle = Math.random() * 360;

	@Override
	public void move() {
		
		x += (int)(Math.cos(Math.toRadians(angle))*speed);
		y += (int)(Math.sin(Math.toRadians(angle))*speed);
	}
}
