package engine;

public class BonusFireCH implements CollisionHandler {

	public BonusFireCH() {
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bonus;
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
		
		Bonus bonus;
		Fire fire;
		
		if(collidable1 instanceof Bonus){
			bonus=(Bonus) collidable1;
			fire=(Fire) collidable2;
		}
		else{
			bonus=(Bonus) collidable2;
			fire=(Fire) collidable1;
		}
		
		fire.getEngine().removeEntityToBuff(bonus);
		//TODO end kill

	}

}
