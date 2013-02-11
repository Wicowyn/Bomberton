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

package engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Rectangle;

public class EntityFactory {
	private static Logger log=LogManager.getLogger(EntityFactory.class);
	private static float heigt;
	private static float width;
	
	public static void setGlobalHeight(float height){
		EntityFactory.heigt=height;
	}
	
	public static void setGobalWidth(float width){
		EntityFactory.width=width;
	}
	
	private static Rectangle get(float x, float y, float width, float height){
		return new Rectangle(
				x*EntityFactory.width/1000,
				y*EntityFactory.heigt/1000,
				width*EntityFactory.width/1000,
				height*EntityFactory.heigt/1000);
	}
	
	public static Entity createEntity(EntityName name, Engine engine){
		Entity entity=null;
		
		switch(name){
		case Bomb:
			entity=getBomb(engine);
			break;
		case BombermanPlayer:
			entity=getBombermanPlayer(engine);
			break;
		case Bonus:
			entity=getBonus(engine);
			break;
		case BreakableBlock:
			entity=getBreakableBlock(engine);
			break;
		case Fire:
			entity=getFire(engine);
			break;
		case SolidBlock:
			entity=getSolidBlock(engine);
			break;
		default:
			EntityFactory.log.fatal("Value : "+name+" don't handle");
			System.exit(1);
			break;
		}
		
		return entity;
	}
	
	protected static Entity getBreakableBlock(Engine engine){
		Rectangle shape=get(0, 0, 1000, 1000);
		Entity entity=new Entity(engine, shape);
		
		return entity;
	}
	
	protected static Entity getSolidBlock(Engine engine){
		Rectangle shape=get(0, 0, 1000, 1000);
		Entity entity=new Entity(engine, shape);
		
		return entity;
	}
	
	protected static Entity getFire(Engine engine){
		Rectangle shape=get(200, 0, 600, 1000);
		Entity entity=new Entity(engine, shape);
		
		return entity;
	}
	
	protected static Entity getBombermanPlayer(Engine engine){
		Rectangle shape=get(0, 0, 1000, 1000);
		Entity entity=new Entity(engine, shape);
		
		return entity;
	}
	
	protected static Entity getBonus(Engine engine){
		Rectangle shape=get(0, 0, 1000, 1000);
		Entity entity=new Entity(engine, shape);
		
		return entity;
	}
	
	protected static Entity getBomb(Engine engine){
		Rectangle shape=get(0, 0, 1000, 1000);
		Entity entity=new Entity(engine, shape);
		
		return entity;
	}
}
