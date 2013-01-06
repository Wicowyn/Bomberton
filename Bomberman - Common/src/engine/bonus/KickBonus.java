package engine.bonus;

import engine.Engine;
import engine.abillity.KickBomb;
import engine.entity.Bonus;


public class KickBonus extends Bonus {

	public KickBonus(Engine engine) {
		super(engine);
		
		KickBomb kick=new KickBomb(null);
		this.listAbbillity.add(kick);
	}

}
