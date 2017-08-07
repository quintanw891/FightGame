package FightGame;

import java.awt.*;
import java.util.ArrayList;

public class Level {
	//private Point start;//upper-left of player starting point
	private Rectangle[] walls;//the set of all walls in a level
	private Enemy[] enemies;//the set of all enemies in a level
	private Player[] player;//the player
	private Projectile projectile;//the projectile fired by the player
	private final int HUD_LENGTH;
	
	public Level(int hudLength){
		HUD_LENGTH = hudLength;
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
	
	public Enemy[] getAliveEnemies() {
		ArrayList<Enemy> aliveEnemiesList = new ArrayList<Enemy>();
		for(int i=0; i<enemies.length; i++){
			if(enemies[i].alive){
				aliveEnemiesList.add(enemies[i]);
			}
		}
		Enemy[] aliveEnemiesArray = aliveEnemiesList.toArray(new Enemy[0]);
		return aliveEnemiesArray;
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

	public int getHudLength() {
		return HUD_LENGTH;
	}

}
