package engine.bonus;

import engine.Engine;
import engine.entity.Bonus;

public class SpeedBonus extends Bonus {

	public SpeedBonus(Engine engine) {
		super(engine);
		
		setSpeed(1);
	}

}
