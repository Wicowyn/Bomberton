package mapping;

import network.BonusType;

public abstract class Item extends Entity {
	public int speed;
	public int bombe;
	public int power;
	public int life;
	/**
	 * 0 to infinite
	 */
	public int duration; 
	
	public BonusType type;

	public Item(Chart chart) {
		super(chart);
	}

}
