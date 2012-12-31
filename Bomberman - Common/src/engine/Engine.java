package engine;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.geom.Vector2f;

public class Engine {
	private List<EngineListener> listeners=new ArrayList<EngineListener>();
	private CollisionManager collisionManager=new CollisionManager();
	private List<Entity> entities=new ArrayList<Entity>();
	private List<Entity> entitiesAdd=new ArrayList<Entity>();
	private List<Entity> entitiesRemove=new ArrayList<Entity>();
	private List<Bomberman> bombermans=new ArrayList<Bomberman>();
	private Logger log=LogManager.getLogger(getClass());
	private boolean loaded=false;
	
	public Engine(){
		
	}
	
	public void update(int delta){
		for(Entity entity : this.entities) entity.update(delta);
		checkEntityBuff();
		this.collisionManager.performCollision();
		checkEntityBuff();
	}
	
	public void checkEntityBuff(){
		for(Entity entity : this.entitiesAdd) addEntity(entity);
		for(Entity entity : this.entitiesRemove) removeEntity(entity);
		
		this.entitiesAdd.clear();
		this.entitiesRemove.clear();
	}
	
	public List<Entity> getEntities(){
		return this.entities;
	}
	
	protected void addEntityToBuff(Entity entity){
		this.entitiesAdd.add(entity);
	}
	
	protected void removeEntityToBuff(Entity entity){
		this.entitiesRemove.add(entity);
	}
	
	public void addEntity(Entity entity){
		this.entities.add(entity);
		if(entity instanceof Bomberman) this.bombermans.add((Bomberman) entity);
		
		this.collisionManager.addCollidable(entity);
		notifyEntityAdded(entity);
		this.log.debug("add Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
	}
	
	public void removeEntity(Entity entity){
		this.entities.remove(entity);
		if(entity instanceof Bomberman) this.bombermans.remove((Bomberman) entity);
		
		this.collisionManager.removeCollidable(entity);
		notifyEntityRemoved(entity);
		this.log.debug("remove Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
	}
	
	public List<Bomberman> getBombermans(){
		return this.bombermans;
	}
	
	public void loadLevel(String filePath) throws JDOMException, IOException{
		SAXBuilder sax=new SAXBuilder();
		Document doc;
		
		doc=sax.build(new File(filePath));
		Element root=doc.getRootElement();
		List<Element> listElem=root.getChildren();
		
		for(Element elem : listElem){
			Entity entity=null;
			switch(elem.getName()){
			case "BreakableBlock":
				entity=new BreakableBlock(this);
				break;
			case "SolidBlock":
				entity=new SolidBlock(this);
				break;
			case "Bomb":
				entity=new Bomb(this);
				break;
			case "Fire":
				entity=new Fire(this);
				break;
			case "Bomberman":
				entity=new Bomberman(this);
				break;
			case "Bonus":
				Bonus bonus=new Bonus(this);
				bonus.setBomb(elem.getAttribute("bomb").getIntValue());
				bonus.setPower(elem.getAttribute("power").getIntValue());
				bonus.setSpeed(elem.getAttribute("speed").getIntValue());
				
				entity=bonus;
				break;
			default:
				this.log.warn("loadLevel: unknown type object -> "+elem.getName());
				continue;
			}

			Vector2f position=new Vector2f();
			position.x=elem.getAttribute("x").getIntValue()*1000;
			position.y=elem.getAttribute("y").getIntValue()*1000;
			entity.setPosition(position);
			
			entity.setDirection(elem.getAttribute("dir").getIntValue());
			
			addEntity(entity);			
		}
		
		this.collisionManager.addHandler(new BombermanBlockCH(this.collisionManager));
		this.collisionManager.addHandler(new BombermanBonusCH());
		this.collisionManager.addHandler(new BombermanFireCH());
		this.collisionManager.addHandler(new BombFireCH());
		this.collisionManager.addHandler(new BonusFireCH());
		this.collisionManager.addHandler(new BlockFireCH());
		
		this.loaded=true;
	}

	public List<String> getListGame(){ //TODO à améliorer
		File directory=new File("ressources/map");
		GameFileFilter filter=new GameFileFilter();
		File[] arrayFile=directory.listFiles(filter);
		List<String> list=new ArrayList<String>();
		for(File file : arrayFile){
			list.add(file.getName().substring(0, file.getName().indexOf(".")));
		}
		
		return list;
	}
	
	public void unLoad(){
		for(Entity entity : this.entities) entity.clear();
		
		this.entities.clear();
		this.entitiesAdd.clear();
		this.entitiesRemove.clear();
		this.bombermans.clear();
		this.collisionManager.clear();
		
		this.loaded=false;
	}
	
	public boolean isLoad(){
		return this.loaded;
	}
	
	public void addListener(EngineListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(EngineListener listener){
		this.listeners.remove(listener);
	}
	
	protected void notifyEntityAdded(Entity entity){
		for(EngineListener listener : this.listeners) listener.entityAdded(entity);
	}
	
	protected void notifyEntityRemoved(Entity entity){
		for(EngineListener listener : this.listeners) listener.entityRemoved(entity);
	}
	
	private class GameFileFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".xml");
		}
		
	}
}
