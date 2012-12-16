package engine;

public abstract class CollisionAbillity extends Abillity {

	public CollisionAbillity(Entity owner) {
		super(owner);
	}
	public abstract int getColliderType();
	public abstract void performCollision(Collidable collidable);
}
