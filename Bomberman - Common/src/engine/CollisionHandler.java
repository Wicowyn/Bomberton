package engine;

public interface CollisionHandler {
	
	public int getCollider1Type();
	public int getCollider2Type();
	public void performCollision(Collidable collidable1, Collidable collidable2);
	public void update();
}
