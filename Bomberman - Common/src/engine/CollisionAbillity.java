package engine;

public interface CollisionAbillity{
	
	public abstract int getColliderType();
	public abstract void performCollision(Collidable collidable);
}
