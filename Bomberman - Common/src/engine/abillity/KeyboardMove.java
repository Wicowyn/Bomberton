package engine.abillity;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import engine.Entity;

public class KeyboardMove extends Move{
	private Input input=null;
	private int keyUp=Input.KEY_UP;
	private int keyRight=Input.KEY_RIGHT;
	private int keyDown=Input.KEY_DOWN;
	private int keyLeft=Input.KEY_LEFT;
	
	
	public KeyboardMove(Entity owner, Input input) {
		super(owner);
		this.input=input;
	}
	
	@Override
	public void update(int delta) {
		if((this.input.isKeyDown(this.keyUp) == this.input.isKeyDown(this.keyDown)) && (this.input.isKeyDown(this.keyRight) == this.input.isKeyDown(this.keyLeft))) return;
		
		if(this.input.isKeyPressed(this.keyDown)) this.owner.setDirection(90);
		else if(this.input.isKeyPressed(this.keyLeft)) this.owner.setDirection(180);
		else if(this.input.isKeyPressed(this.keyUp)) this.owner.setDirection(-90);
		else if(this.input.isKeyPressed(this.keyRight)) this.owner.setDirection(0);
		
		Vector2f position=this.owner.getPosition();
		float hip=this.speed*delta;
		
		position.x+=hip*Math.cos(Math.toRadians(this.owner.getDirection()));
		position.y+=hip*Math.sin(Math.toRadians(this.owner.getDirection()));
		
		this.owner.setPosition(position);
	}
	
	public void setKeyUp(int key){
		this.keyUp=key;
	}
	
	public void setKeyRight(int key){
		this.keyRight=key;
	}
	
	public void setKeyDown(int key){
		this.keyDown=key;
	}
	
	public void setKeyLeft(int key){
		this.keyLeft=key;
	}
}
