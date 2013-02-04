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
		Rectangle shape=get(0, 0, 1000, 1000);
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
