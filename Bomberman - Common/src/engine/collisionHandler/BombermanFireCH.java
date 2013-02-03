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

import java.util.ArrayList;
import java.util.List;

import engine.ActionKill;
import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.CollisionHandler;
import engine.Entity;
import engine.entity.Bomberman;
import engine.entity.Fire;

public class BombermanFireCH implements CollisionHandler {
	private List<ActionKill> listeners=new ArrayList<ActionKill>();

	public BombermanFireCH() {
		
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomberman;
	}

	@Override
	public int getCollider2Type() {
		return CTSCollision.Fire;
	}

	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		if(!collidable1.isCollidingWith(collidable2)) return;
		for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
		for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
		if(!collidable1.isCollidingWith(collidable2)) return;
		
		Bomberman bomberman;
		Fire fire;
		
		if(collidable1 instanceof Bomberman){
			bomberman=(Bomberman) collidable1;
			fire=(Fire) collidable2;
		}
		else{
			bomberman=(Bomberman) collidable2;
			fire=(Fire) collidable1;
		}
		
		fire.getEngine().removeEntityToBuff(bomberman);
		notifyKill(fire, bomberman);
		
	}
	
	public void addListener(ActionKill listener){
		this.listeners.add(listener);
	}
	
	public void remove(ActionKill listener){
		this.listeners.remove(listener);
	}
	
	protected void notifyKill(Entity killer, Entity killed){
		for(ActionKill listener : this.listeners) listener.kill(killer, killed);
	}

	@Override
	public void update() {
		
	}

}
