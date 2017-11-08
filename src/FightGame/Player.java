package FightGame;

import java.awt.Point;
import java.awt.Rectangle;

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
	
	public void suffer(int damage){
		hp -= damage;
		invulnerable = true;
	}
	
	public void shoot(Projectile p){
		p.facing = facing;
		p.x = x+(width/2)-(p.width/2);
		p.y = y+(height/2)-(p.height/2);
		p.fired = true;
		inAttackCooldown = true;
	}

	public void die() {
		lives--;
		hp = maxHp;
	}

	public int getLives(){
		return lives;
	}

	public void setLives(int l) {
		lives = l;
	}

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
