package FightGame;

import java.awt.Rectangle;

public class Projectile extends Movable {
	
	public boolean fired;
	private int power;
	
	public Projectile(){
		this.setSize(10,10);
		power = 10;
		fired = false;
	}
	
	public boolean fly(Level l){
		switch(facing){
		case UP:
			return moveUp(l);
		case UP_RIGHT:
			return moveUp(l) && moveRight(l);
		case RIGHT:
			return moveRight(l);
		case DOWN_RIGHT:
			return moveDown(l) && moveRight(l);
		case DOWN:
			return moveDown(l);
		case DOWN_LEFT:
			return moveDown(l) && moveLeft(l);
		case LEFT:
			return moveLeft(l);
		case UP_LEFT:
			return moveUp(l) && moveLeft(l);
		}
		return false;
	}

	public void hit(Enemy enemy) {
		enemy.suffer(power);
	}
}
