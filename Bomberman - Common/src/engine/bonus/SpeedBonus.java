package engine.bonus;

import engine.Bonus;
import engine.Engine;

public class SpeedBonus extends Bonus {

	public SpeedBonus(Engine engine) {
		super(engine);
		
		setSpeed(1);
	}

}
