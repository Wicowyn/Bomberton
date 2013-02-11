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

import org.newdawn.slick.geom.Shape;

import collision.Entity;

import engine.Abillity;
import engine.Engine;

public abstract class Item extends Entity {
	protected int time;
	protected int life;
	protected int bomb;
	protected int power;
	protected int speed;
	protected List<Abillity> listAbbillity=new ArrayList<Abillity>();
	
	public Item(Engine engine, Shape collisionShape) {
		super(engine, collisionShape);
	}

}
