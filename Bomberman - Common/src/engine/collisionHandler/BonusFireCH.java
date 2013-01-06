package engine.collisionHandler;

import java.util.ArrayList;
import java.util.List;

import engine.ActionKill;
import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.CollisionHandler;
import engine.Entity;
import engine.entity.Bonus;
import engine.entity.Fire;

public class BonusFireCH implements CollisionHandler {
	private List<ActionKill> listeners=new ArrayList<ActionKill>();
	
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
		notifyKill(fire, bonus);
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