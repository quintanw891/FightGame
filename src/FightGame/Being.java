package FightGame;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * The Being Object
 * This class represents a living being in the fight game. 
 * A being object is movable and has a starting position and
 * an amount of health.
 * @author William Quintano
 */
public class Being extends Movable {
	protected int hp, maxHp;
	protected Point start;
	
	public Being(){
		this.setSize(20,20);
	}
	
	/**
	 * @param p the position that the being spawns at
	 */
	public void setStart(Point p){
		start = p;
	}
	
	/**
	 * @return the starting position of the being
	 */
	public Point getStart(){
		return start;
	}
	
	/**
	 * positions the being at their starting position in the level
	 */
	public void goToStart(){
		setLocation(start);
	}
	
	/**
	 * @return the hit points of the being
	 */
	public int getHp(){
		return hp;
	}
	
	/**
	 * @return the maximum hit points of the being
	 */
	public int getMaxHp(){
		return maxHp;
	}
}
