package engine;

public class BombermanBonusCH implements CollisionHandler {

	public BombermanBonusCH() {
		
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomberman;
	}

	@Override
	public int getCollider2Type() {
		return CTSCollision.Bonus;
	}

	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		if(!collidable1.isCollidingWith(collidable2)) return;
		for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
		for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
		if(!collidable1.isCollidingWith(collidable2)) return;
		
		Bomberman bomberman;
		Bonus bonus;
		
		if(collidable1 instanceof Bomberman){
			bomberman=(Bomberman) collidable1;
			bonus=(Bonus) collidable2;
		}
		else{
			bomberman=(Bomberman) collidable2;
			bonus=(Bonus) collidable1;
		}
		
		for(Abillity abillity : bomberman.getAbillities()){
			if(abillity instanceof Move){
				Move move=(Move) abillity;
				move.setSpeed(bonus.getSpeed());
			}
			else if(abillity instanceof PopBomb){
				PopBomb popBomb=(PopBomb) abillity;
				popBomb.setMaxBomb(bonus.getBomb());
				popBomb.setPower(bonus.getPower());
			}
		}
		
		for(Abillity abillity : bonus.getListAbillity()) bomberman.addAbillity(abillity);
		
		bonus.getEngine().removeEntityToBuff(bonus);
	}

}
