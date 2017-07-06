package FightGame;

import java.awt.Rectangle;

public class Player extends Being {

	public boolean invulnerable, shooting;
	public int invulnerableTimer = 0;
	public final int INVULNERABLE_DURATION = 60;
	
	public Player(){
		super();
		maxHp = 50;
		hp = maxHp;
		invulnerable = false;
		shooting = false;
		facing = Direction.UP;
	}
	
	public void goToLevel(Level level){
		setLocation(level.getStart());
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
}
