package engine;

public class BombFireCH implements CollisionHandler {

	public BombFireCH() {
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomb;
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
		/*
		Bomb bomb;
		Fire fire;
		
		if(collidable1 instanceof Bomb){
			bomb=(Bomb) collidable1;
			fire=(Fire) collidable2;
		}
		else{
			bomb=(Bomb) collidable2;
			fire=(Fire) collidable1;
		}
		
		bomb.setOwner(fire.getOwner());
		
		for(Abillity abillity : bomb.getAbillities()){
			if(abillity instanceof Bang){
				Bang bang=(Bang) abillity;
				bang.bang();
			}
		}*/
	}

}
