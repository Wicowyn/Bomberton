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

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

import collision.Entity;


public class BasicRender extends Render {

	
	public BasicRender(Entity owner){
		super(owner);
		
	}
	@Override
	public void update(int delta) {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr){
		gr.setColor(Color.blue);
		gr.draw(this.owner.getCollisionShape().transform(Transform.createScaleTransform(0.04f, 0.04f)));
		//if(this.owner instanceof Bomberman) System.out.println(this.owner.getCollisionShape().getCenterX()+" - "+this.owner.getCollisionShape().getCenterY());
	}

}
