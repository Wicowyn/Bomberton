package engine.bonus;

import engine.Engine;
import engine.entity.Bonus;

public class PowerBonus extends Bonus {

	public PowerBonus(Engine engine) {
		super(engine);
		setPower(1);
	}

}
