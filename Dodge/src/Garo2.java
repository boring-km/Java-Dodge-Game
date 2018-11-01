
public class Garo2 extends Ball{
	
	private int x;
	private int y;
	
	private int garo2Speed = (int)(Math.random()*4);
	
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

	public void setGaro2Speed(int garo2Speed) {
		this.garo2Speed = garo2Speed;
	}

	public void move() {
		x -= garo2Speed;
	}
}
