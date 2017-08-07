package FightGame;

import java.awt.Point;
import java.awt.Rectangle;

public class Being extends Movable {
	protected int hp, maxHp;
	protected Point start;
	
	public Being(){
		this.setSize(20,20);
	}
	
	public void setStart(Point p){
		start = p;
	}
	public Point getStart(){
		return start;
	}
	
	public void goToStart(){
		setLocation(start);
	}
	
	public int getHp(){
		return hp;
	}
	
	public int getMaxHp(){
		return maxHp;
	}
}
