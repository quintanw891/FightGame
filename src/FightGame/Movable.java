package FightGame;

import java.awt.Rectangle;

public class Movable extends Rectangle {
	protected Direction facing;

	public boolean moveUp(Level level) {
		boolean upBound = false;
		if(y==0)
			upBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getEnemies();
		Player[] player = level.getPlayer();
		if(isBelow(walls) || isBelow(enemies) || isBelow(player))
			upBound = true;
		if(!upBound)
			y--;
		return !upBound;
	}

	private boolean isBelow(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(y==obstacles[i].y+obstacles[i].height && x>obstacles[i].x-width && x<obstacles[i].x+obstacles[i].width)
				return true;
		}
		return false;
	}

	public boolean moveDown(Level level) {
		boolean downBound = false;
		if(y==400-height)
			downBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getEnemies();
		Player[] player = level.getPlayer();
		if(isAbove(walls) || isAbove(enemies) || isAbove(player))
			downBound = true;
		if(!downBound)
			y++;
		return !downBound;
	}

	private boolean isAbove(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(y+height==obstacles[i].y && x>obstacles[i].x-width && x<obstacles[i].x+obstacles[i].width)
				return true;
		}
		return false;
	}

	public boolean moveLeft(Level level) {
		boolean leftBound = false;
		if(x==0)
			leftBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getEnemies();
		Player[] player = level.getPlayer();
		if(isRightOf(walls) || isRightOf(enemies) || isRightOf(player))
			leftBound = true;
		if(!leftBound)
			x--;
		return !leftBound;
	}
	
	private boolean isRightOf(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(x==obstacles[i].x+obstacles[i].width && y+height>obstacles[i].y && y<obstacles[i].y+obstacles[i].height)
				return true;
		}
		return false;
	}

	public boolean moveRight(Level level) {
		boolean rightBound = false;
		if(x==600-width)
			rightBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getEnemies();
		Player[] player = level.getPlayer();
		if(isLeftOf(walls) || isLeftOf(enemies) || isLeftOf(player))
			rightBound = true;
		if(!rightBound)
			x++;
		return !rightBound;
	}

	private boolean isLeftOf(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(x+width==obstacles[i].x && y+height>obstacles[i].y && y<obstacles[i].y+obstacles[i].height)
				return true;
		}
		return false;
	}
	
	public boolean isTouching(Being theBeing){
		Rectangle[] b = new Rectangle[1];
		b[0] = theBeing;
		if(isAbove(b) || isBelow(b) || isRightOf(b) || isLeftOf(b))
			return true;
		return false;
	}
}
