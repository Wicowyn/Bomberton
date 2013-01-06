package engine.bonus;

import engine.Engine;
import engine.entity.Bonus;

public class BombBonus extends Bonus {

	public BombBonus(Engine engine) {
		super(engine);
		setBomb(1);
	}

}
