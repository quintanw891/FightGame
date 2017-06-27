package FightGame;

import java.awt.Rectangle;

public class Being extends Rectangle {
	protected int hp, maxHp;
	protected Direction facing;
	
	public Being(){
		this.setSize(20,20);
	}

	public void moveUp(Level level) {
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
	}

	private boolean isBelow(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(y==obstacles[i].y+obstacles[i].height && x>obstacles[i].x-20 && x<obstacles[i].x+obstacles[i].width)
				return true;
		}
		return false;
	}

	public void moveDown(Level level) {
		boolean downBound = false;
		if(y==380)
			downBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getEnemies();
		Player[] player = level.getPlayer();
		if(isAbove(walls) || isAbove(enemies) || isAbove(player))
			downBound = true;
		if(!downBound)
			y++;
	}

	private boolean isAbove(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(y+20==obstacles[i].y && x>obstacles[i].x-20 && x<obstacles[i].x+obstacles[i].width)
				return true;
		}
		return false;
	}

	public void moveLeft(Level level) {
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
	}
	
	private boolean isRightOf(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(x==obstacles[i].x+obstacles[i].width && y+20>obstacles[i].y && y<obstacles[i].y+obstacles[i].height)
				return true;
		}
		return false;
	}

	public void moveRight(Level level) {
		boolean rightBound = false;
		if(x==580)
			rightBound = true;
		Rectangle[] walls = level.getWalls();
		Enemy[] enemies = level.getEnemies();
		Player[] player = level.getPlayer();
		if(isLeftOf(walls) || isLeftOf(enemies) || isLeftOf(player))
			rightBound = true;
		if(!rightBound)
			x++;
	}

	private boolean isLeftOf(Rectangle[] obstacles) {
		for(int i=0; i<obstacles.length; i++){
			if(x+20==obstacles[i].x && y+20>obstacles[i].y && y<obstacles[i].y+obstacles[i].height)
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
