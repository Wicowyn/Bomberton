package engine.bonus;

import engine.Bonus;
import engine.Engine;
import engine.KickBomb;


public class KickBonus extends Bonus {

	public KickBonus(Engine engine) {
		super(engine);
		
		KickBomb kick=new KickBomb(null);
		this.listAbbillity.add(kick);
	}

}
