/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2013  Nicolas Barranger <wicowyn@gmail.com>

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

package engine.marker;

import collision.Entity;

public class DamageMarker implements TouchDamageMarker {
	private Entity owner;
	private int damage;
	
	public DamageMarker(Entity owner) {
		this.owner=owner;
	}
	
	public void setDamage(int damage){
		this.damage=damage;
	}
		
	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Entity getOwner() {
		return this.owner;
	}

	@Override
	public int getDamage() {
		return this.damage;
	}

}