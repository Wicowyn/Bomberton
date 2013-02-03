/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2012-2013  Nicolas Barranger <wicowyn@gmail.com>

    Bomberton is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Bomberton is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bomberton.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package engine.abillity;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import engine.Entity;

public class RealRender extends Render {
	private Map<IntervalAngl, Renderable> mapMoveRender=new HashMap<IntervalAngl, Renderable>();
	private Map<IntervalAngl, Renderable> mapStaticRender=new HashMap<IntervalAngl, Renderable>();
	private Renderable currentRender;
	private Vector2f lastPos;
	private int diffX, diffY;
	
	public RealRender(Entity owner) {
		super(owner);
		this.lastPos=this.owner.getPosition().scale(0.1f);
	}
	
	public void setMoveRender(float anglFirst, float anglSecond, Renderable renderable){
		this.mapMoveRender.put(new IntervalAngl(anglFirst, anglSecond), renderable);
	}
	
	public void setStaticRender(float anglFirst, float anglSecond, Renderable renderable){
		this.mapStaticRender.put(new IntervalAngl(anglFirst, anglSecond), renderable);
	}
	
	public void addDiffX(int diffX){
		this.diffX+=diffX;
	}
	public void addDiffY(int diffY){
		this.diffY+=diffY;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr) {
		if(this.currentRender!=null){
			Vector2f pos=this.owner.getPosition().scale(0.04f);
			this.currentRender.draw(pos.x+this.diffX, pos.y+this.diffY);
		}
	}

	@Override
	public void update(int delta) {
		Vector2f position=this.owner.getPosition();
		
		if(this.lastPos.equals(position)){
			for(IntervalAngl intAngl : this.mapStaticRender.keySet()){
				if(intAngl.isInclued(this.owner.getDirection())){
					this.currentRender=this.mapStaticRender.get(intAngl);
					break;
				}
			}
		}
		else{
			for(IntervalAngl intAngl : this.mapMoveRender.keySet()){
				if(intAngl.isInclued(this.owner.getDirection())){
					this.currentRender=this.mapMoveRender.get(intAngl);
					break;
				}
			}
		}
		
		this.lastPos=position;
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
