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

import engine.Abillity;
import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.Entity;
import engine.entity.Bomb;


public class KickBomb extends Abillity implements CollisionAbillity {

	public KickBomb(Entity owner) {
		super(owner);
		
	}

	@Override
	public int getColliderType() {
		return CTSCollision.Bomb;
	}

	@Override
	public void performCollision(Collidable collidable) {
		Bomb bomb=(Bomb) collidable;
		
		for(Abillity abillity : bomb.getAbillities()){
			if(abillity instanceof BeamMove) bomb.removeAbillityToBuff(abillity);
		}
		
		BeamMove move=new BeamMove(bomb);
		move.setSpeed(5);
		bomb.setDirection(this.owner.getDirection());
		bomb.addAbillityToBuff(move);
	}

	@Override
	public void update(int delta) {
		
	}

}
