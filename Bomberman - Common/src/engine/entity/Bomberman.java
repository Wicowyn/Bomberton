package engine.entity;

import org.newdawn.slick.geom.Rectangle;

import engine.CTSCollision;
import engine.Engine;
import engine.Entity;

public class Bomberman extends Entity {
	static private Rectangle shape=new Rectangle(0, 0, 1000, 1000);

	
	public Bomberman(Engine engine) {
		super(engine, Bomberman.shape);
		this.collisionType=CTSCollision.Bomberman;	
	}
	
	@Override
	public Rectangle getNormalCollisionShape(){
		return (Rectangle) this.collisionShape;
	}

}
