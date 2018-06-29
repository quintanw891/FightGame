package FightGame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

/**
 * The GamePanel Object
 * This class represents the interface on which the fight game is displayed
 * A GamePanel object runs the fight game and responds to user input.
 * @author William Quintano
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	//declare game values
	//flags recording the state of the game
	public boolean start, inLevel, loading, gameOver, gameWon = false;
	//menu items
	public Rectangle titleBox, titleBorder, messageBox, messageBorder;
	//player input
	public boolean up, down, left, right, enter, space, attackButtonHeld, teleportButtonHeld = false;
	//hud items
	public final int HUD_LENGTH = 30;
	public Rectangle lifeMeter, lifeBar;
	//level related values
	public String levelFilename;
	public final int NUMBER_OF_LEVELS = 3;
	public int levelNum;
	public Level level;
	public Player player = new Player();
	public Projectile projectile = new Projectile();
	public Color lightRed = new Color(225, 48, 48);
	public Color brown = new Color(94, 48, 0);
	private int timer = 0;
	
	/**
	 * constructor
	 * sets hud element values
	 */
	public GamePanel(Client c){
		setPreferredSize(new Dimension(600,400+HUD_LENGTH));
		titleBox = new Rectangle(260, 133, 71, 25);
		titleBorder = new Rectangle(262, 135, 66, 20);
		messageBox = new Rectangle(206, 168, 185, 25);
		messageBorder = new Rectangle(208, 170, 180, 20);
		lifeMeter = new Rectangle(100,10,53,10);
		lifeBar = new Rectangle(102,12,50,7);
		KeyAdapter l = new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_W)
					up = true;
				if(e.getKeyCode() == KeyEvent.VK_S)
					down = true;
				if(e.getKeyCode() == KeyEvent.VK_A)
					left = true;
				if(e.getKeyCode() == KeyEvent.VK_D)
					right = true;
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					enter = true;
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
					space = true;
			}
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_W)
					up = false;
				if(e.getKeyCode() == KeyEvent.VK_S)
					down = false;
				if(e.getKeyCode() == KeyEvent.VK_D)
					right = false;
				if(e.getKeyCode() == KeyEvent.VK_A)
					left = false;
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					enter = false;
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
					space = false;
			}
		};
		c.addKeyListener(l);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//display start screen
		if(!start){
			g.setColor(Color.BLACK);
			g.fillRect(titleBox.x,titleBox.y,titleBox.width,titleBox.height);
			g.fillRect(messageBox.x,messageBox.y,messageBox.width,messageBox.height);
			g.fillRect(messageBox.x,messageBox.y + 35,messageBox.width,messageBox.height);
			g.fillRect(messageBox.x,messageBox.y + 70,messageBox.width,messageBox.height);
			g.fillRect(messageBox.x,messageBox.y + 105,messageBox.width,messageBox.height);
			g.setColor(Color.WHITE);
			g.drawString("Fight Game", titleBorder.x + 2, titleBorder.y + 15);
			g.drawString("  Defeat all enemies to proceed.", messageBorder.x + 2, messageBorder.y + 15);
			g.drawString(" W,A,S,D to move. SPACE to fire.", messageBorder.x + 2, messageBorder.y + 50);
			g.drawString("      SPACE again to teleport.", messageBorder.x + 2, messageBorder.y + 85);
			g.drawString("         Press ENTER to start!", messageBorder.x + 2, messageBorder.y + 120);
			g.drawRect(titleBorder.x,titleBorder.y,titleBorder.width,titleBorder.height);
			g.drawRect(messageBorder.x,messageBorder.y,messageBorder.width,messageBorder.height);
			g.drawRect(messageBorder.x,messageBorder.y + 35,messageBorder.width,messageBorder.height);
			g.drawRect(messageBorder.x,messageBorder.y + 70,messageBorder.width,messageBorder.height);
			g.drawRect(messageBorder.x,messageBorder.y + 105,messageBorder.width,messageBorder.height);
			//begin the game when enter is pressed
			if(enter){
				System.out.println("home -> load");
				start = true;
				loading = true;
				levelNum = 1;
			}
		}else{//run the game
			if(inLevel){//display a level
				//run the game at a fixed rate
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
				
				//update the level's player data
				level.setPlayer(player);
				//draw all in-game graphics
				super.paintComponent(g);
				setBackground(lightRed);
				g.setColor(Color.BLACK);
				g.fillRect(0,0,600,30);
				g.setColor(Color.WHITE);
				g.drawString("HEALTH",50,20);
				g.drawString("LIVES: "+player.getLives(),430,20);
				g.drawRect(lifeMeter.x,lifeMeter.y,lifeMeter.width,lifeMeter.height);
				lifeBar.width =  player.getHp()*2;
				if(lifeBar.width>=25)
					g.setColor(Color.GREEN);
				else if(lifeBar.width>15)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.RED);
				g.fillRect(lifeBar.x,lifeBar.y,lifeBar.width,lifeBar.height);
				g.setColor(Color.YELLOW);
				g.fillRect(player.x, player.y, player.width, player.height);
				if(projectile.fired)
					g.fillRect(projectile.x,projectile.y,projectile.width,projectile.height);
				g.setColor(Color.CYAN);
				Enemy[] enemies = level.getEnemies();
				for(int i=0; i<enemies.length; i++){
					if(enemies[i].alive)
						g.fillRect(enemies[i].x, enemies[i].y, enemies[i].width, enemies[i].height);
				}
				g.setColor(brown);
				Rectangle[] walls = level.getWalls();
				for(int i=0; i<walls.length; i++){
					g.fillRect(walls[i].x, walls[i].y, walls[i].width, walls[i].height);
				}
				
				//move the player & set player's direction to the four cardinal directions
				if(timer % (6 - player.speed.value) == 0){//only execute movement sometimes depending on player speed
					if(up){
						player.facing = (Direction.UP);
						player.moveUp(level);
					}
					if(down){
						player.facing = (Direction.DOWN);
						player.moveDown(level);
					}
					if(left){
						player.facing = (Direction.LEFT);
						player.moveLeft(level);
					}
					if(right){
						player.facing = (Direction.RIGHT);
						player.moveRight(level);
					}
					//set player's direction to diagonals
					if(up && right)
						player.facing = Direction.UP_RIGHT;
					if(right && down)
						player.facing = Direction.DOWN_RIGHT;
					if(down && left)
						player.facing = Direction.DOWN_LEFT;
					if(left && up)
						player.facing = Direction.UP_LEFT;
				}
				//manage player's invulnerability
				if(player.invulnerable){
					player.invulnerableTimer++;
					if(player.invulnerableTimer == player.INVULNERABLE_DURATION){
						player.invulnerable = false;
						player.invulnerableTimer = 0;
					}
				}
				
				//fire projectiles
				if(space && !projectile.fired && !player.inAttackCooldown && !teleportButtonHeld){
					player.shoot(projectile);
					attackButtonHeld = true;
				}
				if(projectile.fired){
					if(timer % (6 - projectile.speed.value) == 0){//only execute movement sometimes based on projectile speed
						projectile.fly(level);
					}
					for(int i=0; i<enemies.length; i++){
						if(enemies[i].alive){
							if(projectile.isTouching(enemies[i])){
								projectile.hit(enemies[i]);
								projectile.fired = false;
							}
						}
					}
					for(int i=0; i<walls.length; i++){
						if(projectile.isTouching(walls[i])){
							projectile.fired = false;
						}
					}
					if(projectile.onBorder(level))
						projectile.fired = false;
					//teleport functionality
					if(space && !player.inTeleportCooldown && !attackButtonHeld){
						if(player.teleportTo(projectile, level)){
							projectile.fired = false;
							teleportButtonHeld = true;
						}
					}
				}
				if(attackButtonHeld){
					if(!space){
						attackButtonHeld = false;
					}
				}
				if(teleportButtonHeld){
					if(!space){
						teleportButtonHeld = false;
					}
				}
				//manage cooldowns
				if(player.inAttackCooldown){
					player.attackCooldownTimer++;
					if(player.attackCooldownTimer == player.ATTACK_COOLDOWN_DURATION){
						player.inAttackCooldown = false;
						player.attackCooldownTimer = 0;
					}
				}
				if(player.inTeleportCooldown){
					player.teleportCooldownTimer++;
					if(player.teleportCooldownTimer == player.TELEPORT_COOLDOWN_DURATION){
						player.inTeleportCooldown = false;
						player.teleportCooldownTimer = 0;
					}
				}
				//move the enemies
				for(int i=0; i<enemies.length; i++){
					if(enemies[i].alive){
						if(enemies[i].resting){
							enemies[i].restTimer++;
							if(enemies[i].restTimer == enemies[i].REST_DURATION){
								enemies[i].restTimer = 0;
								enemies[i].resting = false;
							}
						}else{
							if(timer % (6 - enemies[i].speed.value) == 0)
								enemies[i].animate(player, level);
							if(enemies[i].isTouching(player))
								enemies[i].attack(player);
						}
					}
				}
				//check for player/enemy death
				for(int i=0; i<enemies.length; i++){
					if(enemies[i].alive && enemies[i].hp <= 0)
						enemies[i].alive = false;
				}
				if(player.hp <= 0){
					player.die();
					projectile.fired = false;
					if(player.getLives() > 0){
						player.goToStart();
						for(int i=0; i<enemies.length; i++){
							enemies[i].alive = true;
							enemies[i].hp = enemies[i].maxHp;
							enemies[i].goToStart();
						}
					}
					else{
						inLevel = false;
						gameOver = true;
					}
				}
				boolean enemiesRemaining = false;
				for(int i=0;i<enemies.length;i++){
					if(enemies[i].alive){
						enemiesRemaining = true;
					}
				}
				if(enemiesRemaining == false){
					inLevel = false;
					if(levelNum == NUMBER_OF_LEVELS)
						gameWon = true;
				}
				
				timer++;
				if(timer == Integer.MAX_VALUE)
					timer = 0;
			}else{//run inter-level processes
				if(loading){//load the next level
					level = new Level(HUD_LENGTH);
					//open the level file
					levelFilename = "level" + levelNum;
					File levelContent = new File("src\\FightGame\\"+levelFilename);
					BufferedReader reader = null;
					try{
						reader = new BufferedReader(new FileReader(levelContent));
					}catch(FileNotFoundException e){
						System.out.println("cannot find file: "+levelContent.getAbsolutePath());
						System.exit(0);
					}
					//Retrieve player start position data from file
					String line = "";
					try{
						line = reader.readLine();
						if(!line.equals("Player Start Position")){
							System.out.println("error: \"Player Start Position\" "+
							"is not present on line 1 of "+levelFilename+".txt");
							System.exit(0);
						}
						line = reader.readLine();
						StringTokenizer tokens = new StringTokenizer(line);
						int playerX = Integer.parseInt(tokens.nextToken());
						int playerY = Integer.parseInt(tokens.nextToken());
						Point playerStart = new Point(playerX, playerY + HUD_LENGTH);
						player.setStart(playerStart);
						//Retrieve wall data from file
						do{
							line = reader.readLine();
						}
						while(!line.equals("Walls") && line != null);
						if(line == null){
							System.out.println("ERROR: Walls category not found in "+
							levelFilename+".txt");
							System.exit(0);
						}
						int numWalls = 0;
						numWalls = countObjects(reader);
						Rectangle[] walls = new Rectangle[numWalls];
						for(int i=0; i<numWalls; i++){
							line = reader.readLine();
							tokens = new StringTokenizer(line);
							int x = Integer.parseInt(tokens.nextToken());
							int y = Integer.parseInt(tokens.nextToken());
							int w = Integer.parseInt(tokens.nextToken());
							int h = Integer.parseInt(tokens.nextToken());
							Rectangle r = new Rectangle(x,y+HUD_LENGTH,w,h);
							walls[i] = r;
							level.setWalls(walls);
						}
						//Retrieve enemy data from file
						do{
							line = reader.readLine();
						}
						while(!line.equals("Enemies") && line != null);
						if(line == null){
							System.out.println("ERROR: Enemies category not found in "+
							levelFilename+".txt");
							System.exit(0);
						}
						int numEnemies = 0;
						numEnemies = countObjects(reader);
						Enemy[] enemies = new Enemy[numEnemies];
						for(int i=0; i<numEnemies; i++){
							line = reader.readLine();
							tokens = new StringTokenizer(line);
							int x = Integer.parseInt(tokens.nextToken());
							int y = Integer.parseInt(tokens.nextToken());
							Enemy e = new Enemy(x,y+HUD_LENGTH);
							Point enemyStart = new Point(x,y+HUD_LENGTH);
							e.setStart(enemyStart);
							enemies[i] = e;
							level.setEnemies(enemies);
						}
					}catch(IOException e){
						System.out.println(e.getMessage());
					}
					System.out.println("load -> level");
					player.goToStart();
					loading = false;
					inLevel = true;
				}else{//functionality between or after levels
					if(gameOver){//when the player has lost display game over message and offer restart
						g.setColor(Color.BLACK);
						g.fillRect(titleBox.x,titleBox.y,titleBox.width,titleBox.height);
						g.fillRect(messageBox.x,messageBox.y,messageBox.width,messageBox.height);
						g.setColor(Color.WHITE);
						g.drawString("Game Over", titleBorder.x + 2, titleBorder.y + 15);
						g.drawString("         Press Enter to Restart.", messageBorder.x + 2, messageBorder.y + 15);
						g.drawRect(titleBorder.x,titleBorder.y,titleBorder.width,titleBorder.height);
						g.drawRect(messageBorder.x,messageBorder.y,messageBorder.width,messageBorder.height);
						if(enter){
							levelNum = 1;
							player.setLives(3);
							player.hp = player.maxHp;
							gameOver = false;
							loading = true;
						}
					}
					else if(gameWon){//when the player has won display winning message and offer restart
						g.setColor(Color.BLACK);
						g.fillRect(titleBox.x,titleBox.y,titleBox.width,titleBox.height);
						g.fillRect(messageBox.x,messageBox.y,messageBox.width,messageBox.height);
						g.fillRect(messageBox.x,messageBox.y + 35,messageBox.width,messageBox.height);
						g.setColor(Color.WHITE);
						g.drawString("   You Win", titleBorder.x + 2, titleBorder.y + 15);
						g.drawString("           Thanks for playing.", messageBorder.x + 2, messageBorder.y + 15);
						g.drawString("         Press Enter to Restart.", messageBorder.x + 2, messageBorder.y + 50);
						g.drawRect(titleBorder.x,titleBorder.y,titleBorder.width,titleBorder.height);
						g.drawRect(messageBorder.x,messageBorder.y,messageBorder.width,messageBorder.height);
						g.drawRect(messageBorder.x,messageBorder.y + 35,messageBorder.width,messageBorder.height);
						if(enter){
							levelNum = 1;
							player.setLives(3);
							player.hp = player.maxHp;
							gameWon = false;
							loading = true;
						}
					}
					else{//when the player has finished a level that is not the last level
						g.setColor(Color.BLACK);
						g.fillRect(titleBox.x,titleBox.y,titleBox.width,titleBox.height);
						g.fillRect(messageBox.x,messageBox.y,messageBox.width,messageBox.height);
						g.setColor(Color.WHITE);
						g.drawString("   All Clear", titleBorder.x + 2, titleBorder.y + 15);
						g.drawString("        Press Enter to proceed.", messageBorder.x + 2, messageBorder.y + 15);
						g.drawRect(titleBorder.x,titleBorder.y,titleBorder.width,titleBorder.height);
						g.drawRect(messageBorder.x,messageBorder.y,messageBorder.width,messageBorder.height);
						if(enter){
							levelNum++;
							player.hp = player.maxHp;
							loading = true;
						}
					}
				}
			}
		}
		repaint();
	}

	/**
	 * determines the number of objects of a certain type that should be loaded
	 * into the game
	 * @param reader the reader that reads the data defining the objects that
	 * should be loaded into the level
	 * @return the number of objects of a certain type that should be loaded
	 * into the level
	 */
	private int countObjects(BufferedReader reader) {
		int count = 0;
		try{
			reader.mark(1000);
			String line = reader.readLine();
			while(line != null && !line.isEmpty()){
				count++;
				line = reader.readLine();
			}
			reader.reset();
		}catch(IOException e){
			System.out.println("Error: problem counting lines in content file");
		}
		return count;
	}
}
