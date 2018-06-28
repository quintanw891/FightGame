package FightGame;

/**
 * The Speed Object
 * This class represents the speed value that beings in the game possess.
 * A speed object can only be valued from 1 to 5.
 * @author William
 *
 */
public class Speed{
	public int value;
	
	public Speed(int s){
		if(s<1 || s>5)
			throw new IllegalArgumentException();
		else
			value = s;
	}
}
