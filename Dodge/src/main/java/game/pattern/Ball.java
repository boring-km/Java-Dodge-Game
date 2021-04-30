package game.pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Ball {

	protected int x;
	protected int y;
	protected final static int RAD = 20;
	
	public int getRad() {
		return RAD;
	}

	public abstract void move();
}
