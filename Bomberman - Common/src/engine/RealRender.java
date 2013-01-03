package engine;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class RealRender extends Render {
	private Map<IntervalAngl, Animation> mapAnimation=new HashMap<IntervalAngl, Animation>();
	private Animation currentAnim;
	private Vector2f lastPos;
	
	public RealRender(Entity owner) {
		super(owner);
		this.lastPos=this.owner.getPosition().scale(0.1f);
	}
	
	public void setAnimation(float anglFirst, float anglSecond, Animation animation){
		this.mapAnimation.put(new IntervalAngl(anglFirst, anglSecond), animation);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		if(this.currentAnim!=null){
			Vector2f position=this.owner.getPosition().scale(0.04f);
			if(!position.equals(this.lastPos)){
				this.currentAnim.draw(position.x+40-this.currentAnim.getWidth(), position.y+40-this.currentAnim.getHeight());
			}
			else{
				this.currentAnim.getImage(0).draw(position.x+40-this.currentAnim.getWidth(), position.y+40-this.currentAnim.getHeight());
			}
			
			this.lastPos=position;
		}
	}

	@Override
	public void update(int delta) {
		for(IntervalAngl intAngl : this.mapAnimation.keySet()){
			if(intAngl.isInclued(this.owner.getDirection())){
				this.currentAnim=this.mapAnimation.get(intAngl);
				break;
			}
		}
	}
	
	public class IntervalAngl{
		private float first;
		private float second;
		
		public IntervalAngl(float first, float second){
			this.first=first;
			this.second=second;
		}
		
		public boolean isInclued(float number){
			number=number%360;
			return number>=this.first && number<this.second;
		}
	}

}
