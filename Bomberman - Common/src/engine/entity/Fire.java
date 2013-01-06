package engine.entity;

import org.newdawn.slick.geom.Rectangle;

import engine.CTSCollision;
import engine.Engine;
import engine.collisionHandler.Item;

public class Fire extends Item {
	private static Rectangle shape=new Rectangle(0, 200, 1000, 600);

	
	public Fire(Engine engine) {
		super(engine, Fire.shape);
		this.life=-1;
		this.collisionType=CTSCollision.Fire;
	}
	
	@Override
	public Rectangle getNormalCollisionShape(){
		return (Rectangle) super.getNormalCollisionShape();
	}
	
}