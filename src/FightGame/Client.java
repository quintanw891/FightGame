package FightGame;
import java.awt.*;
import javax.swing.*;

/**
 * The Client Object
 * This class serves to create the interface that will run the fight game.
 * @author William Quintano
 */
@SuppressWarnings("serial")
public class Client extends JFrame{

	public Client(){
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setTitle("Fight Game");
	}
	
	public static void main(String[] args) {
		Client frame = new Client();
		GamePanel p = new GamePanel(frame);
		frame.add(p);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
