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

import engine.Abillity;
import engine.EntityFactory;
import engine.EntityName;

public class BeamFire extends Abillity {
	private int power;

	public BeamFire(Entity owner) {
		super(owner);
	}
	
	@Override
	public void update(int delta) {
		Entity fire=EntityFactory.createEntity(EntityName.Fire, this.owner.getEngine());
		fire.setOwner(this.owner);
		fire.setDirection(this.owner.getDirection());
		
		if(this.power>1){
			BeamFire beamFire=new BeamFire(fire);
			beamFire.setOwner(fire);
			beamFire.setPower(this.power-1);
			fire.addAbillity(beamFire);
		}
		
		Kamikaze kamikaze=new Kamikaze(fire, 1500);
		fire.addAbillity(kamikaze);
				
		Vector2f position=this.owner.getPosition();
		position.x+=1000*Math.cos(Math.toRadians(this.owner.getDirection()));
		position.y+=1000*Math.sin(Math.toRadians(this.owner.getDirection()));
		fire.setPosition(position);
		
		this.owner.getEngine().addEntityToBuff(fire);
		this.owner.removeAbillity(this);
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
}
