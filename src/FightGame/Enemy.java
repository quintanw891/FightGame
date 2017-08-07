package FightGame;

import java.util.Random;

public class Enemy extends Being{
	
	private int movementTimer = 0;
	private int movementInterval = 50;
	public int restTimer;
	public final int REST_DURATION = 60;
	private int strength;
	public boolean resting = false;
	public boolean alive = true;
	
	public Enemy(int x, int y){
		super();
		this.x = x;
		this.y = y;
		maxHp = 10;
		hp = maxHp;
		restTimer = 0;
		strength = 5;
		facing = randomDirection();
		speed = new Speed(2);
	}

	private Direction randomDirection(){
		Direction direction = null;
		Random r = new Random();
		int randomNumber = r.nextInt(8)+1;
		switch(randomNumber){
		case 1: direction = Direction.UP;
				break;
		case 2: direction = Direction.UP_RIGHT;
				break;
		case 3: direction = Direction.RIGHT;
				break;
		case 4: direction = Direction.DOWN_RIGHT;
				break;
		case 5: direction = Direction.DOWN;
				break;
		case 6: direction = Direction.DOWN_LEFT;
				break;
		case 7: direction = Direction.LEFT;
				break;
		case 8: direction = Direction.UP_LEFT;
				break;
		}
		return direction;
	}

	public void animate(Player p, Level l){
		if(movementTimer != movementInterval){
			//when in range of player, chase the player
			if(Math.abs(p.x - x) < 150 && Math.abs(p.y - y) < 150)
				chase(p,l);
			//otherwise move in direction facing
			else if(facing == Direction.UP){
				moveUp(l);
			}
			else if(facing == Direction.UP_RIGHT){
				moveUp(l);
				moveRight(l);
			}
			else if(facing == Direction.RIGHT){
				moveRight(l);
			}
			else if(facing == Direction.DOWN_RIGHT){
				moveDown(l);
				moveRight(l);
			}
			else if(facing == Direction.DOWN){
				moveDown(l);
			}
			else if(facing == Direction.DOWN_LEFT){
				moveDown(l);
				moveLeft(l);
			}
			else if(facing == Direction.LEFT){
				moveLeft(l);
			}
			else if(facing == Direction.UP_LEFT){
				moveUp(l);
				moveLeft(l);
			}
			movementTimer++;
		}else{
			facing = randomDirection();
			movementTimer = 0;
		}
	}
	
	private void chase(Player p, Level l){
		int xDistance = p.x - x;
		int yDistance = y - p.y;
		double direction = Math.toDegrees(Math.atan2(yDistance, xDistance));
		if(direction >= 60 && direction < 120){
			moveUp(l);
		}else if(direction >= 30 && direction < 60){
			moveUp(l);
			moveRight(l);
		}else if(direction >= -30 && direction < 30){
			moveRight(l);
		}else if(direction >= -60 && direction < -30){
			moveDown(l);
			moveRight(l);
		}else if(direction >= -120 && direction < -60){
			moveDown(l);
		}else if(direction >= -150 && direction < -120){
			moveDown(l);
			moveLeft(l);
		}else if(direction >= 150 || direction < -150){
			moveLeft(l);
		}else if(direction >= 120 && direction < 150){
			moveUp(l);
			moveLeft(l);
		}
		
	}
	
	public void attack(Player player) {
		if(!player.invulnerable){
			player.suffer(strength);
			resting = true;
		}
	}

	public void suffer(int damage) {
		hp -= damage;
	}

}