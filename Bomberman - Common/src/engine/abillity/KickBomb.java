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

import collision.Entity;
import collision.TouchHandle;
import collision.TouchMarker;
import engine.Abillity;
import engine.CTSCollision;


public class KickBomb extends Abillity implements TouchHandle {
	private int priority;

	public KickBomb(Entity owner) {
		super(owner);
		
	}

	@Override
	public void update(int delta) {
		
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
		return CTSCollision.Bomb;
	}

	@Override
	public void perform(TouchMarker marker) {
		Entity bomb=marker.getOwner();
				
		BeamMove move=new BeamMove(bomb);
		move.setSpeed(5);
		bomb.setDirection(this.owner.getDirection());
		bomb.addAbillity(move);
		
	}

	@Override
	public Entity getOwner() {
		return this.owner;
	}
}
