package engine.bonus;

import engine.Bonus;
import engine.Engine;

public class BombBonus extends Bonus {

	public BombBonus(Engine engine) {
		super(engine);
		setBomb(1);
	}

}
