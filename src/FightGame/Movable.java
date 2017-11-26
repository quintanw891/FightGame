package FightGame;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The Movable Object
 * This class represents an element of the fight game that can move. Movable
 * elements include the player, enemies, projectiles, etc.
 * A movable object can move in the four cardinal directions and check if it
 * is in contact with any other objects in the level
 * @author William Quintano
 */
public class Movable extends Rectangle {
	protected Direction facing;
	public Speed speed;
	
	/**
	 * Moves the movable up if possible
	 * @param level the level in which the movable exists
	 * @return true if the move was successful false otherwise
	 */
	public boolean moveUp(Level level) {
		boolean upBound = false;
		if(y==level.getHudLength())
			upBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getAliveEnemies();
		Player[] player = level.getPlayer();
		if(isBelow(walls) || isBelow(enemies) || isBelow(player))
			upBound = true;
		if(!upBound)
			y--;
		return !upBound;
	}

	/**
	 * Determines if the movable is below a specified set of obstacles
	 * @param obstacles the obstacles that are potentially above the movable
	 * @return true if the movable is below any of the specified obstacles
	 * false otherwise
	 */
	private boolean isBelow(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(y==obstacles[i].y+obstacles[i].height && x>obstacles[i].x-width && x<obstacles[i].x+obstacles[i].width)
				return true;
		}
		return false;
	}

	/**
	 * Moves the movable down if possible
	 * @param level the level in which the movable exists
	 * @return true if the move was successful false otherwise
	 */
	public boolean moveDown(Level level) {
		boolean downBound = false;
		if(y==400+level.getHudLength()-height)
			downBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getAliveEnemies();
		Player[] player = level.getPlayer();
		if(isAbove(walls) || isAbove(enemies) || isAbove(player))
			downBound = true;
		if(!downBound)
			y++;
		return !downBound;
	}

	/**
	 * Determines if the movable is above a specified set of obstacles
	 * @param obstacles the obstacles that are potentially below the movable
	 * @return true if the movable is above any of the specified obstacles
	 * false otherwise
	 */
	private boolean isAbove(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(y+height==obstacles[i].y && x>obstacles[i].x-width && x<obstacles[i].x+obstacles[i].width)
				return true;
		}
		return false;
	}

	/**
	 * Moves the movable left if possible
	 * @param level the level in which the movable exists
	 * @return true if the move was successful false otherwise
	 */
	public boolean moveLeft(Level level) {
		boolean leftBound = false;
		if(x==0)
			leftBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getAliveEnemies();
		Player[] player = level.getPlayer();
		if(isRightOf(walls) || isRightOf(enemies) || isRightOf(player))
			leftBound = true;
		if(!leftBound)
			x--;
		return !leftBound;
	}
	
	/**
	 * Determines if the movable is directly to the right of a specified set
	 * of obstacles
	 * @param obstacles the obstacles that are potentially directly to the
	 * left of the movable
	 * @return true if the movable is directly to the right of the specified 
	 * obstacles false otherwise
	 */
	private boolean isRightOf(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(x==obstacles[i].x+obstacles[i].width && y+height>obstacles[i].y && y<obstacles[i].y+obstacles[i].height)
				return true;
		}
		return false;
	}

	/**
	 * Moves the movable right if possible
	 * @param level the level in which the movable exists
	 * @return true if the move was successful false otherwise
	 */
	public boolean moveRight(Level level) {
		boolean rightBound = false;
		if(x==600-width)
			rightBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getAliveEnemies();
		Player[] player = level.getPlayer();
		if(isLeftOf(walls) || isLeftOf(enemies) || isLeftOf(player))
			rightBound = true;
		if(!rightBound)
			x++;
		return !rightBound;
	}

	/**
	 * Determines if the movable is directly to the left of a specified set
	 * of obstacles
	 * @param obstacles the obstacles that are potentially directly to the
	 * right of the movable
	 * @return true if the movable is directly to the left of the specified 
	 * obstacles false otherwise
	 */
	private boolean isLeftOf(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(x+width==obstacles[i].x && y+height>obstacles[i].y && y<obstacles[i].y+obstacles[i].height)
				return true;
		}
		return false;
	}
	
	/**
	 * Determines if the movable is touching a specified object in the level
	 * @param thing the object that may be in contact with the movable
	 * @return true if the movable is touching the object, false otherwise
	 */
	public boolean isTouching(Rectangle thing){
		Rectangle[] b = new Rectangle[1];
		b[0] = thing;
		if(isAbove(b) || isBelow(b) || isRightOf(b) || isLeftOf(b))
			return true;
		return false;
	}
	
	/**
	 * Determines if the movable is touching the border of the screen
	 * @param level the level the movable is in
	 * @return true if the movable is touching the border false otherwise
	 */
	public boolean onBorder(Level level){
		if(x == 0 || y == level.getHudLength() || x+width == 600 
				|| y+height == 400+level.getHudLength())
			return true;
		return false;
	}
}
