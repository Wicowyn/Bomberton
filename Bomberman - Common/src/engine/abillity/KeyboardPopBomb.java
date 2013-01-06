package engine.abillity;

import org.newdawn.slick.Input;

import engine.Entity;

public class KeyboardPopBomb extends PopBomb {
	private Input input=null;
	private int keyPop=Input.KEY_A;
	
	
	public KeyboardPopBomb(Entity owner, Input input){
		super(owner);
		this.input=input;
	}
	
	@Override
	public void update(int delta) {
		if(this.input.isKeyPressed(this.keyPop) && this.currentBomb<this.maxBomb){
			popBomb();
		}

	}
	
	public void setKeyPop(int key){
		this.keyPop=key;
	}
}
