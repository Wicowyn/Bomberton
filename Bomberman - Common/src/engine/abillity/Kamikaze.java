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
import engine.Abillity;

public class Kamikaze extends Abillity {
	private int time=0;
	private int currentTime=0;
	
	public Kamikaze(Entity owner, int time) {
		super(owner);
		this.time=time;
	}

	@Override
	public void update(int delta) {
		this.currentTime+=delta;
		if(this.currentTime>this.time){
			this.owner.getEngine().removeEntityToBuff(this.owner);
		}
	}

}
