package engine;

import org.newdawn.slick.geom.Rectangle;

public abstract class Block extends Entity {
	private static Rectangle shape=new Rectangle(0, 0, 1000, 1000);

	
	public Block(Engine engine) {
		super(engine, Block.shape);
		this.collisionType=CTSCollision.Block;
	}

	@Override
	public Rectangle getNormalCollisionShape(){
		return (Rectangle) super.getNormalCollisionShape();
	}

}
