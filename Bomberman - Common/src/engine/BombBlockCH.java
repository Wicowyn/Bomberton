package engine;

import org.newdawn.slick.geom.Vector2f;

public class BombBlockCH implements CollisionHandler {

	public BombBlockCH() {
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomb;
	}

	@Override
	public int getCollider2Type() {
		return CTSCollision.Block;
	}

	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		if(!collidable1.isCollidingWith(collidable2)) return;
		for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
		for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
		if(!collidable1.isCollidingWith(collidable2)) return;
		
		Bomb Bomb;
		Block block;
		
		if(collidable1 instanceof Bomb){
			Bomb=(Bomb) collidable1;
			block=(Block) collidable2;
		}
		else{
			Bomb=(Bomb) collidable2;
			block=(Block) collidable1;
		}
		
		float direction=Bomb.getDirection();
		direction+=180; //reverse it
		
		
		do{
			Vector2f position=Bomb.getPosition();
			position.x+=Math.cos(Math.toRadians(direction));
			position.y+=Math.sin(Math.toRadians(direction));
			Bomb.setPosition(position);
		}while(Bomb.isCollidingWith(block));
		
	}

	@Override
	public void update() {
		
	}

}
