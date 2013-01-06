package engine.abillity;

import engine.Abillity;
import engine.Entity;

public abstract class Move extends Abillity {
	protected float speed=2f;
	
	public Move(Entity owner) {
		super(owner);
	}
	
	public void setSpeed(float speed){
		this.speed=speed;
	}
	
	public float getSpeed(){
		return this.speed;
	}

}
