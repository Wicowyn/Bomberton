package engine;

import java.util.ArrayList;
import java.util.List;

public class BlockFireCH implements CollisionHandler {
	private List<ActionKill> listeners=new ArrayList<ActionKill>();
	
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
		
		if(block instanceof BreakableBlock){
			block.getEngine().removeEntityToBuff(block);
			notifyKill(fire, block);
		}
	}
	
	public void addListener(ActionKill listener){
		this.listeners.add(listener);
	}
	
	public void remove(ActionKill listener){
		this.listeners.remove(listener);
	}
	
	protected void notifyKill(Entity killer, Entity killed){
		for(ActionKill listener : this.listeners) listener.kill(killer, killed);
	}

	@Override
	public void update() {
		
	}

}
