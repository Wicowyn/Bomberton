package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

public class RenderAbillity extends Abillity {

	
	public RenderAbillity(Entity owner){
		super(owner);
		
	}
	@Override
	public void update(int delta) {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr){
		gr.setColor(Color.blue);
		gr.draw(this.owner.getCollisionShape().transform(Transform.createScaleTransform(0.1f, 0.1f)));
		//if(this.owner instanceof Bomberman) System.out.println(this.owner.getCollisionShape().getCenterX()+" - "+this.owner.getCollisionShape().getCenterY());
	}

}
