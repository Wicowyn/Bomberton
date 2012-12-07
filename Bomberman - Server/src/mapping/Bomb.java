package mapping;

import java.awt.Point;
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
		Point pos=getPosCase();
		boolean canAdd=true;
		
		for(int i=0; i<this.power && canAdd; i++){			
			set=this.chart.getListEntityAt(pos);
			
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
				fire.setPos(pos);
				fire.setDirection(Direction.UP);
				notifyBorn(fire);
			}
			else return;
			
			switch(dir){
			case DOWN:
				pos.x+=this.chart.getResolution();
				break;
			case LEFT:
				pos.y-=this.chart.getResolution();
				break;
			case RIGHT:
				pos.y+=this.chart.getResolution();
				break;
			case UP:
				pos.x-=this.chart.getResolution();
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
	
	@Override
	public boolean canMoveTo(Point pos){
		if(!super.canMoveTo(pos)) return false;
		
		Set<Entity> set=new HashSet<Entity>();
		
		for(int i=0; i<this.chart.getResolution(); i++){
			switch (getDirection()) {
			case UP:
				set.addAll(this.chart.getListEntityAt(pos.x+i, pos.y));
				break;
			case DOWN:
				set.addAll(this.chart.getListEntityAt(pos.x+i, pos.y+this.chart.getResolution()-1));
				break;
			case LEFT:
				set.addAll(this.chart.getListEntityAt(pos.x, pos.y+i));
				break;
			case RIGHT:
				set.addAll(this.chart.getListEntityAt(pos.x+this.chart.getResolution()-1, pos.y+i));
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
