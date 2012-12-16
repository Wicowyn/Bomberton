package engine;

import org.newdawn.slick.geom.Rectangle;

public class Bomberman extends Entity {
	static private Rectangle shape=new Rectangle(0, 0, 1000, 1000);

	
	public Bomberman(Engine engine) {
		super(engine, Bomberman.shape);
		this.collisionType=CTSCollision.Bomberman;	
	}

}
