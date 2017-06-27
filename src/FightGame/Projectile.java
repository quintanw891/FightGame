package FightGame;

import java.awt.Rectangle;

public class Projectile extends Rectangle {

	private Direction direction;
	
	public Projectile(Player p, Direction direction){
		this.direction = direction;
	}
}
