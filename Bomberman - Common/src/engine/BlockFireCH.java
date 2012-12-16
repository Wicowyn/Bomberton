package engine;

public class BlockFireCH implements CollisionHandler {

	public BlockFireCH() {
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Block;
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
		
		Block block;
		Fire fire;
		
		if(collidable1 instanceof Block){
			block=(Block) collidable1;
			fire=(Fire) collidable2;
		}
		else{
			block=(Block) collidable2;
			fire=(Fire) collidable1;
		}
		
		fire.getEngine().removeEntityToBuff(fire);
		
		if(block instanceof BreakableBlock) block.getEngine().removeEntityToBuff(block);
		
		//end kill

	}

}
