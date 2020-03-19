
public class Diagonal extends Ball{
	
	private int x;
	private int y;
	private int speed = 2;
	private double angle = Math.random() * 360;
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}

	public void move() {
		
		x += (int)(Math.cos(Math.toRadians(angle))*speed);
		y += (int)(Math.sin(Math.toRadians(angle))*speed);
	}
}
