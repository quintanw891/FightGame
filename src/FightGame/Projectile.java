package FightGame;

import java.awt.Rectangle;

/**
 * The Projectile Object
 * This class represents the projectile that the player can fire to damage
 * enemies or be used for teleportation.
 * @author William Quintano
 */
public class Projectile extends Movable {
	
	public boolean fired;
	private int power;
	
	public Projectile(){
		this.setSize(10,10);
		power = 5;
		fired = false;
		speed = new Speed(5);
	}
	
	/**
	 * Moves the projectile in the direction that it is facing if possible.
	 * @param l the level that the projectile is in.
	 * @return true if the movement was successful, false if the projectile
	 * hit something.
	 */
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

	/**
	 * Inflicts damage on an enemy. This method is called when the projectile
	 * contacts an enemy.
	 * @param enemy the enemy that the projectile damages.
	 */
	public void hit(Enemy enemy) {
		enemy.suffer(power);
	}
}
