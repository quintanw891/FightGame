package FightGame;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * The Fight Game Player Object
 * This class represents the character that the user controls in the FightGame.
 * A player object contains values relating to its state and can shoot
 * projectiles, teleport to the fired projectiles, and damage enemies.
 * @author William Quintano
 *
 */
public class Player extends Being {

	public boolean invulnerable, shooting, inAttackCooldown, inTeleportCooldown;
	public int invulnerableTimer = 0;
	public final int INVULNERABLE_DURATION = 60;
	public int attackCooldownTimer = 0;
	public final int ATTACK_COOLDOWN_DURATION = 180;
	public int teleportCooldownTimer = 0;
	public final int TELEPORT_COOLDOWN_DURATION = 10;
	private int lives;
	
	public Player(){
		super();
		maxHp = 25;
		hp = maxHp;
		invulnerable = false;
		shooting = false;
		inAttackCooldown = false;
		facing = Direction.UP;
		speed = new Speed(3);
		lives = 3;
	}
	
	/**
	 * Inflicts a specified amount of damage on the player.
	 * @param damage the amount of damage the player takes.
	 */
	public void suffer(int damage){
		hp -= damage;
		invulnerable = true;
	}
	
	/**
	 * Shoots a projectile in the direction that the player is facing.
	 * @param p the projectile that the player shoots.
	 */
	public void shoot(Projectile p){
		p.facing = facing;
		p.x = x+(width/2)-(p.width/2);
		p.y = y+(height/2)-(p.height/2);
		p.fired = true;
		inAttackCooldown = true;
	}

	/**
	 * decrements the players lives and resets their hp
	 */
	public void die() {
		lives--;
		hp = maxHp;
	}

	/**
	 * @return the current amount of lives the player has left
	 */
	public int getLives(){
		return lives;
	}

	/**
	 * Sets the players amount of lives to a specified quantity
	 * @param l the number of lives the player will have
	 */
	public void setLives(int l) {
		lives = l;
	}

	/**
	 * Teleports the player to the position of a fired projectile if possible. If 
	 * there is enough room for the player to spawn at the projectile's position,
	 * then the player will instantly move to that location and the projectile will
	 * be destroyed. Otherwise the player will not move and the projectile will
	 * continue on its path.
	 * @param p the projectile to teleport to.
	 * @param l the level that the player and projectile are in.
	 * @return true if the teleport was successful, false there was not enough room.
	 */
	public boolean teleportTo(Projectile p, Level l) {
		Rectangle landingZone = new Rectangle(p.x+(p.width/2)-(width/2),p.y+(p.height/2)-(height/2),width,height);
		Rectangle[] walls = l.getWalls();
		Rectangle[] enemies = l.getAliveEnemies();
		boolean enoughRoom = true;
		for(int i=0; i<walls.length;i++){
			if(landingZone.intersects(walls[i])){
				enoughRoom = false;
			}
		}
		for(int i=0; i<enemies.length;i++){
			if(landingZone.intersects(enemies[i])){
				enoughRoom = false;
			}
		}
		if(landingZone.x < 0 || landingZone.x > 600-landingZone.width || landingZone.y < l.getHudLength() 
				|| landingZone.y > 400 + l.getHudLength() - landingZone.height){
			enoughRoom = false;
		}
		if(enoughRoom){
			setLocation(landingZone.x, landingZone.y);
			inTeleportCooldown = true;
			return true;
		}
		return false;
	}
}
