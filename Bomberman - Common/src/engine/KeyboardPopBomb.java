package engine;

import org.newdawn.slick.Input;

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
			Bomb bomb=new Bomb(this.owner.getEngine());
			bomb.setPosition(this.owner.getPosition());
			bomb.setOwner(this.owner);
			
			for(Abillity abillity : bomb.getAbillities()){
				if(abillity instanceof Bang){
					Bang bang=(Bang) abillity;
					bang.setPower(this.power);
					bang.startDetonation();
					bang.setInitializer(this);
				}
			}
			
			this.owner.getEngine().addEntityToBuff(bomb);
			this.currentBomb++;
		}

	}
	
	public void setKeyPop(int key){
		this.keyPop=key;
	}
}
