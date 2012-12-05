package mapping;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.Direction;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Engine implements Runnable{
	private List<Entity> entities=new ArrayList<Entity>();
	private Chart chart;
	private int rate;
	private Logger log=Logger.getLogger(Engine.class);
	
	public void loadGame(String mod) throws JDOMException, IOException{
		unLoad();
		SAXBuilder sax=new SAXBuilder();
		Document doc;
		
		doc=sax.build(new File("xml.xml"));
		Element root=doc.getRootElement();
		List<Element> listElem=getAllElement(root);
		
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
			default:
				this.log.warn("Load Game: "+mod+" but unknown entry: "+elem.getName());
				continue;
			}
			
			if(entity.setPos(elem.getAttribute("x").getIntValue(), elem.getAttribute("y").getIntValue())){
				this.log.warn("Load Game: "+mod+" but entity have invalid postion: "+elem);
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
			
			this.entities.add(entity);
		}
	}
	
	private List<Element> getAllElement(Element elem){
		List<Element> list=new ArrayList<Element>();
		
		for(Element elemTemp : elem.getChildren()) list.addAll(getAllElement(elemTemp));
		
		return list;
	}
	
	public List<String> getListGame(){
		File directory=new File("engine/");
		GameFileFilter filter=new GameFileFilter();
		File[] arrayFile=directory.listFiles(filter);
		
		List<String> list=new ArrayList<String>();
		for(File file : arrayFile){
			list.add(file.getName());
		}
		
		return list;
	}
	
	public void unLoad(){
		if(this.chart!=null) this.chart.clear();
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
				log.error("Overload of Engine");
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
	
	private class GameFileFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			return name.matches("*.xml");
		}
		
	}
}
