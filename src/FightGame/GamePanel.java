package FightGame;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	//declare game values
	//flags recording the state of the game
	public boolean start, inLevel, loading = false;
	//menu items
	public Rectangle titleBox, titleBorder, messageBox, messageBorder;
	//player input
	public boolean up, down, left, right, enter, space = false;
	//level related values
	public String levelFilename;
	public int levelNum;
	public Level level;
	public Player player = new Player();
	public Color lightRed = new Color(225, 48, 48);
	public Color brown = new Color(94, 48, 0);
	
	public GamePanel(Client c){
		setPreferredSize(new Dimension(600,400));
		titleBox = new Rectangle(260, 133, 71, 25);
		titleBorder = new Rectangle(262, 135, 66, 20);
		messageBox = new Rectangle(206, 168, 185, 25);
		messageBorder = new Rectangle(208, 170, 180, 20);
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
			g.setColor(Color.WHITE);
			g.drawString("Fight Game", titleBorder.x + 2, titleBorder.y + 15);
			g.drawString("  Defeat all enemies to proceed.", messageBorder.x + 2, messageBorder.y + 15);
			g.drawString("   W,A,S,D to move. UP to attack.", messageBorder.x + 2, messageBorder.y + 50);
			g.drawString("         Press ENTER to start!", messageBorder.x + 2, messageBorder.y + 85);
			g.drawRect(titleBorder.x,titleBorder.y,titleBorder.width,titleBorder.height);
			g.drawRect(messageBorder.x,messageBorder.y,messageBorder.width,messageBorder.height);
			g.drawRect(messageBorder.x,messageBorder.y + 35,messageBorder.width,messageBorder.height);
			g.drawRect(messageBorder.x,messageBorder.y + 70,messageBorder.width,messageBorder.height);
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
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
				
				//update the level's player data
				level.setPlayer(player);
				//draw all in-game graphics
				super.paintComponent(g);
				setBackground(lightRed);
				g.setColor(Color.YELLOW);
				g.fillRect(player.x, player.y, player.width, player.height);
				g.setColor(Color.CYAN);
				Enemy[] enemies = level.getEnemies();
				for(int i=0; i<enemies.length; i++){
					g.fillRect(enemies[i].x, enemies[i].y, enemies[i].width, enemies[i].height);
				}
				g.setColor(brown);
				Rectangle[] walls = level.getWalls();
				for(int i=0; i<walls.length; i++){
					g.fillRect(walls[i].x, walls[i].y, walls[i].width, walls[i].height);
				}
				
				//move the player & set player's direction to the four cardinal directions
				if(up)
					player.moveUp(level);
				if(down)
					player.moveDown(level);
				if(left)
					player.moveLeft(level);
				if(right)
					player.moveRight(level);
				
				//set player's direction to diagonals
				if(up && right)
					player.facing = Direction.UP_RIGHT;
				if(right && down)
					player.facing = Direction.DOWN_RIGHT;
				if(down && left)
					player.facing = Direction.DOWN_LEFT;
				if(left && up)
					player.facing = Direction.UP_LEFT;
				
				//manage player's invulnerability
				if(player.invulnerable){
					player.invulnerableTimer++;
					if(player.invulnerableTimer == player.INVULNERABLE_DURATION){
						player.invulnerable = false;
						player.invulnerableTimer = 0;
					}
				}
				
				//fire projectiles
				if(space)
					player.shoot(level);
				
				//move the enemies
				for(int i=0; i<enemies.length; i++){
					if(enemies[i].resting){
						enemies[i].restTimer++;
						if(enemies[i].restTimer == enemies[i].REST_DURATION){
							enemies[i].restTimer = 0;
							enemies[i].resting = false;
						}
					}else{
						enemies[i].animate(player, level);
						if(enemies[i].isTouching(player))
							enemies[i].attack(player);
					}
				}
				
			}else{//run inter-level processes
				if(loading){//load the next level
					level = new Level();
					//open the level file
					levelFilename = "level" + levelNum;
					File levelContent = new File("src\\FightGame\\"+levelFilename);
					BufferedReader reader = null;
					try{
						reader = new BufferedReader(new FileReader(levelContent));
					}catch(FileNotFoundException e){
						System.out.println("cannot find file: "+levelFilename);
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
						Point playerStart = new Point(playerX, playerY);
						level.setStart(playerStart);
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
							Rectangle r = new Rectangle(x,y,w,h);
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
							Enemy e = new Enemy(x,y);
							enemies[i] = e;
							level.setEnemies(enemies);
						}
					}catch(IOException e){
						System.out.println(e.getMessage());
					}
					System.out.println("load -> level");
					player.goToLevel(level);
					loading = false;
					inLevel = true;
				}else{//display inter-level message
					
				}
			}
		}
		repaint();
	}

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
