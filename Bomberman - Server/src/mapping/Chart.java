package mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chart {
	private int sizeX;
	private int sizeY;
	private int resolution;
	private List<List<Set<Entity>>> map;
	
	public Chart(int nbCaseX, int nbCaseY, int resolution){
		this.resolution=resolution;
		this.sizeX=nbCaseX*this.resolution;
		this.sizeY=nbCaseY*this.resolution;
		
		this.map=new ArrayList<List<Set<Entity>>>();
		for(int i=0; i<this.sizeX; i++){
			this.map.add(new ArrayList<Set<Entity>>());
			for(int j=0; j<this.sizeY; j++){
				this.map.get(i).add(new HashSet<Entity>());
			}
		}
	}
	
	public Set<Entity> getListEntityAt(int x, int y){
		return this.map.get(x).get(y);
	}
	
	public boolean add(Entity entity, int posX, int posY){
		Set<Entity> list=this.map.get(posY).get(posY);
		
		return list.add(entity);
	}
	
	public boolean delete(Entity entity, int posX, int posY){
		return this.map.get(posX).get(posY).remove(entity);
	}
	
	public void addShape(Entity entity, int posX, int posY, boolean[][] shape){
		List<Set<Entity>> mainList;
		Set<Entity> list;
		
		for(int x=posX; x<getResolution(); x++){
			mainList=this.map.get(x);
			for(int y=posY; y<getResolution(); y++){
				list=mainList.get(y);
				if(shape[x][y]) list.add(entity);
			}
		}
	}
	
	public void deleteShape(Entity entity, int posX, int posY, boolean[][] shape){
		List<Set<Entity>> mainList;
		Set<Entity> list;
		
		for(int x=posX; x<getResolution(); x++){
			mainList=this.map.get(x);
			for(int y=posY; y<getResolution(); y++){
				list=mainList.get(y);
				if(shape[x][y]) list.remove(entity);
			}
		}
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
	
	public void clear(){
		for(List<Set<Entity>> list : this.map){
			for(Set<Entity> set : list){
				set.clear();
			}
		}
	}
}
