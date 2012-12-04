package mapping;

import java.util.HashSet;
import java.util.Set;

import network.Direction;

public class Bomb extends Entity {
	private int power=2;
	private long futureBlast;
	private boolean started=false;

	public Bomb(Chart chart) {
		super(chart);
		
	}
	
	public void kill(){
		super.kill();
		
		popFire(Direction.UP);
		popFire(Direction.RIGHT);
		popFire(Direction.DOWN);
		popFire(Direction.LEFT);
	}
	
	private void popFire(Direction dir){
		Set<Entity> set;
		int posX=getCaseX(), posY=getCaseY();
		boolean canAdd=true;
		
		for(int i=0; i<this.power && canAdd; i++){			
			set=this.chart.getListEntityAt(posX, posY);
			
			for(Entity entity : set){
				if(entity instanceof Block){
					if(entity instanceof BreakableBlock){
						entity.kill();
					}
					canAdd=false;
				}
			}
			
			if(canAdd){
				Fire fire=new Fire(this.chart);
				fire.setPos(posX, posY);
				fire.setDirection(Direction.UP);
				notifyBorn(fire);
			}
			else return;
			
			switch(dir){
			case DOWN:
				posX+=this.chart.getResolution();
				break;
			case LEFT:
				posY-=this.chart.getResolution();
				break;
			case RIGHT:
				posY+=this.chart.getResolution();
				break;
			case UP:
				posX-=this.chart.getResolution();
				break;
			default:
				System.err.println("Error: Bomb: popFire(Direction dir): direction non gérée");
				System.exit(1);
				break;
			}
		}
	}
	
	public void start(){
		this.started=true;
		this.futureBlast=System.currentTimeMillis()+1500;
	}
	
	public boolean isStart(){
		return this.started;
	}

	@Override
	protected void checkState() {
		if(isStart() && System.currentTimeMillis()>this.futureBlast){
			kill();
			return;
		}
		
		Set<Entity> set=new HashSet<Entity>();
		
		for(int i=0; i<this.chart.getResolution(); i++){
			set.addAll(this.chart.getListEntityAt(getX()+i, getY()));
			set.addAll(this.chart.getListEntityAt(getX()+i, getY()+this.chart.getResolution()-1));
			set.addAll(this.chart.getListEntityAt(getX(), getY()+i));
			set.addAll(this.chart.getListEntityAt(getX()+this.chart.getResolution()-1, getY()+i));
		}
		
		for(Entity entity : set){
			if(entity instanceof Fire){
				kill();
				return;
			}
		}
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
			if(entity instanceof Block) return false;
		}
		
		return true;
	}

}
