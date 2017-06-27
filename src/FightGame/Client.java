package FightGame;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Client extends JFrame{

	public Client(){
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.black);
		setTitle("Fight Game");
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Client frame = new Client();
		GamePanel p = new GamePanel(frame);
		frame.add(p);
		frame.pack();
	}

}
