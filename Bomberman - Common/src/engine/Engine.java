package engine;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.geom.Vector2f;

import engine.bonus.BombBonus;
import engine.bonus.KickBonus;
import engine.bonus.PowerBonus;
import engine.bonus.SpeedBonus;
import engine.collisionHandler.BlockFireCH;
import engine.collisionHandler.BombBlockCH;
import engine.collisionHandler.BombFireCH;
import engine.collisionHandler.BombermanBlockCH;
import engine.collisionHandler.BombermanBombCH;
import engine.collisionHandler.BombermanBonusCH;
import engine.collisionHandler.BombermanFireCH;
import engine.collisionHandler.BonusFireCH;
import engine.entity.Bomb;
import engine.entity.Bomberman;
import engine.entity.Bonus;
import engine.entity.BreakableBlock;
import engine.entity.Fire;
import engine.entity.SolidBlock;

public class Engine {
	private List<EngineListener> listeners=new ArrayList<EngineListener>();
	private CollisionManager collisionManager=new CollisionManager();
	private List<Entity> entities=new ArrayList<Entity>();
	private List<Entity> entitiesAdd=new ArrayList<Entity>();
	private List<Entity> entitiesRemove=new ArrayList<Entity>();
	private Logger log=LogManager.getLogger(getClass());
	private boolean loaded=false;
	private List<Bonus> futureBonus=new ArrayList<Bonus>();
	private int nbBBlock=0;
	
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
	
	public void addEntityToBuff(Entity entity){
		this.entitiesAdd.add(entity);
	}
	
	public void removeEntityToBuff(Entity entity){
		this.entitiesRemove.add(entity);
	}
	
	public void addEntity(Entity entity){
		this.entities.add(entity);
		
		this.collisionManager.addCollidable(entity);
		if(entity instanceof BreakableBlock) this.nbBBlock++;
		notifyEntityAdded(entity);
		this.log.debug("add Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
	}
	
	public void removeEntity(Entity entity){
		this.entities.remove(entity);
		
		this.collisionManager.removeCollidable(entity);
		if(entity instanceof BreakableBlock){
			if(this.nbBBlock>0 && this.futureBonus.size()>0){
				int pourcent=this.futureBonus.size()*100/this.nbBBlock;
				
				if(Math.random()*100 <= pourcent){
					Bonus bonus=this.futureBonus.get((int) (Math.random()*this.futureBonus.size()));
					this.futureBonus.remove(bonus);
					System.out.println(this.futureBonus.size());
					bonus.setPosition(entity.getPosition());
					addEntity(bonus);
				}
			}			
			
			this.nbBBlock--;
		}
		
		notifyEntityRemoved(entity);
		this.log.debug("remove Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
	}
	
	public List<? extends Entity> getListOf(Class<?> classType){
		List<Entity> list=new ArrayList<Entity>();
		
		for(Entity entity : this.entities) if(entity.getClass().equals(classType)) list.add(entity);
		
		return list;
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
			case "SpeedBonus":
				entity=new SpeedBonus(this);
				break;
			case "PowerBonus":
				entity=new PowerBonus(this);
				break;
			case "BombBonus":
				entity=new BombBonus(this);
				break;
			case "KickBonus":
				entity=new KickBonus(this);
				break;
			case "FutureBonus":
				futureBonus(elem);
				continue;
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
		this.collisionManager.addHandler(new BombBlockCH());
		BombermanBombCH han=new BombermanBombCH(this.collisionManager);
		addListener(han);
		this.collisionManager.addHandler(han);
		
		this.loaded=true;
	}
	
	private void futureBonus(Element elem){
		try {
			int nbSpeed=elem.getAttribute("nbSpeed").getIntValue();
			int nbBomb=elem.getAttribute("nbBomb").getIntValue();
			int nbPower=elem.getAttribute("nbPower").getIntValue();
			int nbKick=elem.getAttribute("nbKick").getIntValue();
			
			for(int i=0; i<nbSpeed; i++) this.futureBonus.add(new SpeedBonus(this));
			for(int i=0; i<nbBomb; i++) this.futureBonus.add(new BombBonus(this));
			for(int i=0; i<nbPower; i++) this.futureBonus.add(new PowerBonus(this));
			for(int i=0; i<nbKick; i++) this.futureBonus.add(new KickBonus(this));
			
		} catch (DataConversionException e) {
			this.log.error("wrong format of attribute nbSpeed, nbBomb, nbPower ou nbKick");
		}
		
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
	
	private static class GameFileFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".xml");
		}
		
	}
}
