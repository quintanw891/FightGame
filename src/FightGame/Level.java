package FightGame;

import java.awt.*;
import java.util.ArrayList;

/**
 * The Game Level Object
 * This class represents a level of the fight game. A level object 
 * holds information about the objects that appear in a level such
 * as walls enemies, etc.
 * @author William Quintano
 */
public class Level {
	//private Point start;//upper-left of player starting point
	private Rectangle[] walls;//the set of all walls in a level
	private Enemy[] enemies;//the set of all enemies in a level
	private Player[] player;//the player
	private Projectile projectile;//the projectile fired by the player
	private final int HUD_LENGTH;
	
	/**
	 * @param hudLength the height of the hud
	 */
	public Level(int hudLength){
		HUD_LENGTH = hudLength;
	}
	
	/**
	 * @return an array of the walls in the level
	 */
	public Rectangle[] getWalls(){
		return walls;
	}
	
	/**
	 * @param w the set of walls in the level
	 */
	public void setWalls(Rectangle[] w){
		walls = w;
	}
	
	/**
	 * @return an array of the enemies in this level
	 */
	public Enemy[] getEnemies(){
		return enemies;
	}
	
	/**
	 * @return an array of enemies that are still alive in the level
	 */
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
	
	/**
	 * @param e the set of enemies in the level
	 */
	public void setEnemies(Enemy[] e){
		enemies = e;
	}
	
	/**
	 * @return the player in the level
	 */
	public Player[] getPlayer(){
		return player;
	}
	
	/**
	 * @param p the player of the level
	 */
	public void setPlayer(Player p){
		player = new Player[1];
		player[0] = p;
	}

	/**
	 * @return the height of the hud
	 */
	public int getHudLength() {
		return HUD_LENGTH;
	}

}
