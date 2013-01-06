package engine.abillity;

import org.newdawn.slick.geom.Vector2f;

import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.Entity;

public class BeamMove extends Move implements CollisionAbillity{

	public BeamMove(Entity owner) {
		super(owner);
		
	}

	@Override
	public void update(int delta) {
		Vector2f position=this.owner.getPosition();
		float hip=this.speed*delta;
		
		position.x+=hip*Math.cos(Math.toRadians(this.owner.getDirection()));
		position.y+=hip*Math.sin(Math.toRadians(this.owner.getDirection()));
		
		this.owner.setPosition(position);
	}

	@Override
	public int getColliderType() {
		return CTSCollision.Block;
	}

	@Override
	public void performCollision(Collidable collidable) {
		this.owner.removeAbillityToBuff(this);
		
	}

}
