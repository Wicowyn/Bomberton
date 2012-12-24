package engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Render extends Abillity {

	public Render(Entity owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void render(GameContainer gc, StateBasedGame sb, Graphics gr);
}
