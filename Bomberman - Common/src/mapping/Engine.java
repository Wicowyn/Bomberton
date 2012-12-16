package mapping;

import java.awt.Point;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.Direction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Engine implements Runnable{
	private Logger log=LogManager.getLogger(Engine.class.getName());
	List<Point> listPlace=new ArrayList<Point>();
	Point point=new Point();
	private List<Entity> entities=new ArrayList<Entity>();
	private Chart chart;
	private int rate;
	
	public Engine(){
		
	}
	
	public List<Entity> getEntities(){
		return this.entities;
	}
	
	public boolean loadGame(String mod) throws JDOMException, IOException{
		unLoad();
		SAXBuilder sax=new SAXBuilder();
		Document doc;
		
		doc=sax.build(new File("ressources/map/"+mod+".xml"));
		Element root=doc.getRootElement();
		List<Element> listElem=root.getChildren();
		
		for(Element elem : listElem){
			if(elem.getName().equals("Chart")){
				this.chart=new Chart(elem.getAttribute("sizeX").getIntValue(), elem.getAttribute("sizeY").getIntValue(), elem.getAttribute("resolution").getIntValue());
				listElem.remove(elem);
				break;
			}
		}
		
		if(this.chart==null){
			log.error("loadGame: the game "+mod+"don't define Chart, loading aborted");
			return false;
		}
		
		ListenEntity listen=new ListenEntity();
		for(Element elem : listElem){
			Entity entity=null;
			switch(elem.getName()){
			case "BreakableBlock":
				BreakableBlock block=new BreakableBlock(this.chart);
				entity=block;
				break;
			case "SolidBlock":
				entity=new SolidBlock(this.chart);
				break;
			case "Bomb":
				Bomb bomb=new Bomb(this.chart);
				entity=bomb;
				break;
			case "Fire":
				Fire fire=new Fire(this.chart);
				entity=fire;
				break;
			case "Bomberman":
				this.listPlace.add(new Point(elem.getAttribute("x").getIntValue(), elem.getAttribute("y").getIntValue()));
				continue;
			default:
				this.log.warn("loadGame: unknown type object -> "+elem.getName());
				continue;
			}

			if(!entity.setPos(new Point(elem.getAttribute("x").getIntValue()*this.chart.getResolution(), elem.getAttribute("y").getIntValue()*this.chart.getResolution()))){
				this.log.warn("loadGame: the position of "+elem.getName()+" is invalid -> "+elem.getAttribute("x").getIntValue()+"/"+elem.getAttribute("y").getIntValue());
				System.err.println("plop"+this.log.isWarnEnabled());
				continue;
			}
			
			switch(elem.getAttributeValue("dir")){
			case "UP":
				entity.setDirection(Direction.UP);
				break;
			case "DOWN":
				entity.setDirection(Direction.DOWN);
				break;
			case "LEFT":
				entity.setDirection(Direction.LEFT);
				break;
			case "RIGHT":
				entity.setDirection(Direction.RIGHT);
				break;
			default:
				entity.setDirection(Direction.DOWN);
			}
			
			entity.addListener(listen);
			this.entities.add(entity);
			this.log.debug("new Entity: "+entity.getClass().getSimpleName()+" -> [x="+entity.getPos().x+", y="+entity.getPos().y+"]");
		}
		
		return true;
	}
	
	public List<String> getListGame(){
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
		if(this.chart!=null){
			this.chart.clear();
			this.chart=null;
		}
		this.listPlace.clear();
		this.entities.clear();
	}

	@Override
	public void run(){
		long time, temp;
		while(true){
			time=System.currentTimeMillis();
			for(Entity entity : this.entities){
				entity.update();
			}
			
			temp=this.rate+System.currentTimeMillis()-time;
			if(temp<0){
				this.log.error("Overload of Engine");
			}
			else{
				try {
					Thread.sleep(temp);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	void setTimeRate(int time){
		this.rate=time;
	}
	
	private class ListenEntity implements BasicEvent{
		@Override
		public void kill(Entity entity) {
			Engine.this.entities.remove(entity);			
		}

		@Override
		public void born(Entity entity) {
			Engine.this.entities.remove(entity);
			entity.addListener(this);			
		}
		
	}
	
	private class GameFileFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".xml");
		}
		
	}
}
