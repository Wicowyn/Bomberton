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

import org.newdawn.slick.geom.Vector2f;

import collision.Entity;
import collision.TouchHandle;
import collision.TouchMarker;
import engine.CTSCollision;

public class BeamMove extends Move implements TouchHandle{
	private int priority;

	public BeamMove(Entity owner) {
		super(owner);
		
	}

	@Override
	public void update(int delta) {
		Vector2f position=this.owner.getPosition();
		float hip=this.speed*delta;
		
		position.x+=hip*Math.cos(Math.toRadians(this.owner.getDirection()));
		position.y+=hip*Math.sin(Math.toRadians(this.owner.getDirection()));
		
		this.owner.setPosition(position);
	}

	@Override
	public int compareTo(TouchHandle arg0) {
		return getPriority()>arg0.getPriority() ? 1 : getPriority()<arg0.getPriority() ? -1 : 0;
	}

	@Override
	public void setPriority(int priority) {
		this.priority=priority;
		
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public int getType() {
		return CTSCollision.Block;
	}

	@Override
	public void perform(TouchMarker marker) {
		this.owner.removeAbillity(this);
		
	}

	@Override
	public Entity getOwner() {
		return this.owner;
	}

}
