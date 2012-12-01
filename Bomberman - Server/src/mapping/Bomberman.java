package mapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Bomberman extends Entity {
	private Map<Long, Item> tempEffect=new HashMap<Long, Item>();
	private Set<Bonus> currentBonus=new HashSet<Bonus>();
	private int bombe;
	private int power;
	private int life;
	private int currentBombe;
	
	public Bomberman(Chart chart) {
		super(chart);
		boolean[][] shape=new boolean[chart.getResolution()][chart.getResolution()];
		for(boolean[] tab : shape){
			for (int i=0; i<tab.length; i++) tab[i]=true;
		}
		setShape(shape);
		this.level=Level.OVERGROUND;
	}
	
	public boolean canMoveTo(int x, int y){
		if(!super.canMoveTo(x, y)) return false;
		
		Set<Entity> set=new HashSet<Entity>();
		
		for(int i=0; i<this.chart.getResolution(); i++){
			switch (getDirection()) {
			case UP:
				set.addAll(this.chart.getListEntityAt(x+i, y));
				break;
			case DOWN:
				set.addAll(this.chart.getListEntityAt(x+i, y+this.chart.getResolution()-1));
				break;
			case LEFT:
				set.addAll(this.chart.getListEntityAt(x, y+i));
				break;
			case RIGHT:
				set.addAll(this.chart.getListEntityAt(x+this.chart.getResolution()-1, y+i));
				break;
			default:
				System.err.println("Error: Bomberman: canMoveTo(int, int): direction non gérée");
				System.exit(1);
				break;
			}
		}
		
		for(Entity entity : set){
			if(entity instanceof Block) return false; //TODO rajouter l'item Bombe dans la condition
		}
		
		return true;
	}

	@Override
	protected void checkState() {
		Set<Entity> set=new HashSet<Entity>();
		
		for(int i=0; i<this.chart.getResolution(); i++){
			set.addAll(this.chart.getListEntityAt(getX()+i, getY()));
			set.addAll(this.chart.getListEntityAt(getX()+i, getY()+this.chart.getResolution()-1));
			set.addAll(this.chart.getListEntityAt(getX(), getY()+i));
			set.addAll(this.chart.getListEntityAt(getX()+this.chart.getResolution()-1, getY()+i));
		}
		
		for(Entity entity : set){
			if(entity instanceof Item){
				Item item=(Item) entity;
				addBombe(item.bombe);
				addLife(item.life);
				addPower(item.power);
				addSpeed(item.speed);
				
				if(item.duration!=0){
					this.tempEffect.put(System.currentTimeMillis()+item.duration, item);
				}
				
				if(item instanceof Bonus){
					Bonus bonus=(Bonus) item;
					this.currentBonus.add(bonus);
					bonus.kill();
				}
			}
		}
		
		for(Iterator<Entry<Long, Item>> it=this.tempEffect.entrySet().iterator(); it.hasNext();){
			//TODO parcours de la map façon ordonnée pour gain de perf? à verif/fair
			Entry<Long, Item> entry=it.next();
			if(System.currentTimeMillis()>entry.getKey()){
				it.remove();
				Item item=entry.getValue();
				addBombe(item.bombe*-1);
				addLife(item.life*-1);
				addPower(item.power*-1);
				addSpeed(item.speed*-1);
			}
		}
	}
	
	public void addBombe(int bombe){
		this.bombe+=bombe;
		if(this.bombe<1) this.bombe=1;
	}
	
	public void addLife(int life){
		this.life+=life;
		if(this.life<1) notifyKill();
	}
	
	public void addPower(int power){
		this.power+=power;
		if(this.power<1) this.power=1;
	}
	
	public void addSpeed(int speed){
		this.speed+=speed;
	}
}
