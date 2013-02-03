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

package engine.collisionHandler;

import engine.Abillity;
import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.CollisionHandler;
import engine.abillity.Move;
import engine.abillity.PopBomb;
import engine.entity.Bomberman;
import engine.entity.Bonus;

public class BombermanBonusCH implements CollisionHandler {

	public BombermanBonusCH() {
		
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomberman;
	}

	@Override
	public int getCollider2Type() {
		return CTSCollision.Bonus;
	}

	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		if(!collidable1.isCollidingWith(collidable2)) return;
		for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
		for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
		if(!collidable1.isCollidingWith(collidable2)) return;
		
		Bomberman bomberman;
		Bonus bonus;
		
		if(collidable1 instanceof Bomberman){
			bomberman=(Bomberman) collidable1;
			bonus=(Bonus) collidable2;
		}
		else{
			bomberman=(Bomberman) collidable2;
			bonus=(Bonus) collidable1;
		}
		
		for(Abillity abillity : bomberman.getAbillities()){
			if(abillity instanceof Move){
				Move move=(Move) abillity;
				move.setSpeed(move.getSpeed()+bonus.getSpeed());
			}
			else if(abillity instanceof PopBomb){
				PopBomb popBomb=(PopBomb) abillity;
				popBomb.setMaxBomb(popBomb.getMaxBomb()+bonus.getBomb());
				popBomb.setPower(popBomb.getPower()+bonus.getPower());
			}
		}
		
		for(Abillity abillity : bonus.getListAbillity()){
			abillity.setOwner(bomberman);
			bomberman.addAbillity(abillity);
		}
		
		bonus.getEngine().removeEntityToBuff(bonus);
	}

	@Override
	public void update() {
		
	}

}
