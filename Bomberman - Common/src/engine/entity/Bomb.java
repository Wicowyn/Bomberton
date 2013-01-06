package engine.entity;

import org.newdawn.slick.geom.Rectangle;

import engine.CTSCollision;
import engine.Engine;
import engine.Entity;
import engine.abillity.Bang;

public class Bomb extends Entity{
	private static Rectangle shape=new Rectangle(0, 0, 1000, 1000);
	

	public Bomb(Engine engine) {
		super(engine, Bomb.shape);
		Bang bang=new Bang(this);
		addAbillity(bang);
		this.collisionType=CTSCollision.Bomb;
	}
	
	@Override
	public Rectangle getNormalCollisionShape(){
		return (Rectangle) super.getNormalCollisionShape();
	}
	
}
