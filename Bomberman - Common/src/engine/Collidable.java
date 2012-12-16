package engine;

import java.util.List;

import org.newdawn.slick.geom.Shape;

public interface Collidable {
	
    public Shape getNormalCollisionShape();
	public Shape getCollisionShape();
	public int getCollisionType();
	public boolean isCollidingWith(Collidable collidable);
	public List<CollisionAbillity> getCollisionAbillity(int colliderType);
}
