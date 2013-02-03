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

import org.newdawn.slick.geom.Vector2f;

import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.CollisionHandler;
import engine.entity.Block;
import engine.entity.Bomb;

public class BombBlockCH implements CollisionHandler {

	public BombBlockCH() {
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomb;
	}

	@Override
	public int getCollider2Type() {
		return CTSCollision.Block;
	}

	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		if(!collidable1.isCollidingWith(collidable2)) return;
		for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
		for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
		if(!collidable1.isCollidingWith(collidable2)) return;
		
		Bomb Bomb;
		Block block;
		
		if(collidable1 instanceof Bomb){
			Bomb=(Bomb) collidable1;
			block=(Block) collidable2;
		}
		else{
			Bomb=(Bomb) collidable2;
			block=(Block) collidable1;
		}
		
		float direction=Bomb.getDirection();
		direction+=180; //reverse it
		
		
		do{
			Vector2f position=Bomb.getPosition();
			position.x+=Math.cos(Math.toRadians(direction));
			position.y+=Math.sin(Math.toRadians(direction));
			Bomb.setPosition(position);
		}while(Bomb.isCollidingWith(block));
		
	}

	@Override
	public void update() {
		
	}

}
