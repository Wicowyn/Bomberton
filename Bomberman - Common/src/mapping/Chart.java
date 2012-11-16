package mapping;

import java.util.ArrayList;
import java.util.List;

public class Chart {
	private int sizeX;
	private int sizeY;
	private int resolution;
	private List<List<List<Entity>>> map;
	
	public Chart(int nbCaseX, int nbCaseY, int resolution){
		this.resolution=resolution;
		this.sizeX=nbCaseX*this.resolution;
		this.sizeY=nbCaseY*this.resolution;
		
		this.map=new ArrayList<List<List<Entity>>>();
		for(int i=0; i<this.sizeX; i++){
			this.map.add(new ArrayList<List<Entity>>());
			for(int j=0; j<this.sizeY; j++){
				this.map.get(i).add(new ArrayList<Entity>());
			}
		}
	}
	
	public List<Entity> getListEntityAt(int x, int y){
		return this.map.get(x).get(y);
	}
	
	public void add(Entity entity, int posX, int posY){
		this.map.get(posY).get(posY).add(entity);
	}
	
	public void delete(Entity entity, int posX, int posY){
		this.map.get(posX).get(posY).remove(entity);
	}
	
	public int getSizeX(){
		return this.sizeX;
	}
	
	public int getSizeY(){
		return this.sizeY;
	}
	
	public int getResolution(){
		return this.resolution;
	}
}
