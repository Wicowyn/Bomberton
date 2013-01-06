package engine.abillity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import engine.Abillity;
import engine.Entity;

public abstract class Render extends Abillity {

	public Render(Entity owner) {
		super(owner);
		
	}
	
	
	public abstract void render(GameContainer gc, StateBasedGame sb, Graphics gr);
}
