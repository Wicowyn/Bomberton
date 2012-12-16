package engine;

public class BombermanFireCH implements CollisionHandler {

	public BombermanFireCH() {
		
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomberman;
	}

	@Override
	public int getCollider2Type() {
		return CTSCollision.Fire;
	}

	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		if(!collidable1.isCollidingWith(collidable2)) return;
		for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
		for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
		if(!collidable1.isCollidingWith(collidable2)) return;
		
		Bomberman bomberman;
		Fire fire;
		
		if(collidable1 instanceof Bomberman){
			bomberman=(Bomberman) collidable1;
			fire=(Fire) collidable2;
		}
		else{
			bomberman=(Bomberman) collidable2;
			fire=(Fire) collidable1;
		}
		
		fire.getEngine().removeEntityToBuff(bomberman);
		//end kill
		
	}

}
