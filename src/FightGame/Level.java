package FightGame;

import java.awt.*;

public class Level {
	private Point start;//upper-left of player starting point
	private Rectangle[] walls;//the set of all walls in a level
	private Enemy[] enemies;//the set of all enemies in a level
	private Player[] player;//the player
	private Projectile projectile;//the projectile fired by the player
	
	public Point getStart(){
		return start;
	}
	
	public void setStart(Point p){
		start = p;
	}
	
	public Rectangle[] getWalls(){
		return walls;
	}
	
	public void setWalls(Rectangle[] w){
		walls = w;
	}
	
	public Enemy[] getEnemies(){
		return enemies;
	}
	
	public void setEnemies(Enemy[] e){
		enemies = e;
	}
	
	public Player[] getPlayer(){
		return player;
	}
	
	public void setPlayer(Player p){
		player = new Player[1];
		player[0] = p;
	}

}
