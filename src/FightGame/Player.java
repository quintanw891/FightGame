package FightGame;

import java.awt.Point;
import java.awt.Rectangle;

public class Player extends Being {

	public boolean invulnerable, shooting;
	public int invulnerableTimer = 0;
	public final int INVULNERABLE_DURATION = 60;
	private int lives;
	
	public Player(){
		super();
		maxHp = 25;
		hp = maxHp;
		invulnerable = false;
		shooting = false;
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
		p.x = x+3;
		p.y = y+3;
		p.fired = true;
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
}
