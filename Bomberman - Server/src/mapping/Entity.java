package mapping;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.Direction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class which represent every entity on the map
 * @author yapiti
 *
 */
public abstract class Entity {
	protected Logger log=LogManager.getLogger(this.getClass().getName());
	private Point pos=new Point();;
	private double futureMove;
	private boolean inRun;
	private Direction lastDir;
	private List<BasicEvent> listeners=new ArrayList<BasicEvent>();
	
	protected Map<Direction, boolean[][]> shapes=new HashMap<Direction, boolean[][]>();
	protected Direction direction;
	protected Level level;
	protected Chart chart;
	protected int speed=0; // ms pour une case
	//TODO sauvgarder le temps à l'entré de update pour une meilleure synchro/perf
	
	/**
	 * Basic constructor with the given map
	 * @param chart The map
	 */
	public Entity(Chart chart){
		this.chart=chart;
		this.direction=Direction.DOWN;
		this.lastDir=this.direction;
		for(Direction dir : Direction.values()) this.shapes.put(dir, new boolean[this.chart.getResolution()][this.chart.getResolution()]);
		stop();		
	}
	
	/**
	 * Set the shape of the entity. The shape is one case of chart.
	 * @param shape The DOWN shape, one case.
	 * @return true / false
	 */
	protected final boolean setShape(boolean[][] shape){
		if(shape.length!=this.chart.getResolution()) return false;
		if(shape[0].length!=this.chart.getResolution()) return false;
		
		this.shapes.clear();
		boolean[][] up, left, right;
		this.shapes.put(Direction.DOWN, shape);
		
		up=new boolean[this.chart.getResolution()][this.chart.getResolution()];
		for(int i=0; i<shape.length; i++){
			
			for(int j=0; j<shape.length; j++){
				up[j][i]=shape[shape.length-1-j][shape.length-1-i];		
			}
		}
		this.shapes.put(Direction.UP, up);
		
		right=new boolean[this.chart.getResolution()][this.chart.getResolution()];
		for(int i=0; i<shape.length/2; i++){
			
			for(int j=i; j<shape.length-i; j++){
/*P*/			right[j][i]=shape[shape.length-1-i][j];
/*O*/			right[shape.length-1-i][j]=shape[shape.length-1-j][shape.length-1-i];
/*W*/			right[j][shape.length-1-i]=shape[i][j];
/*A*/			right[i][j]=shape[shape.length-1-j][i];
/*!*/		}
		}
		this.shapes.put(Direction.RIGHT, right);
		
		left=new boolean[this.chart.getResolution()][this.chart.getResolution()];
		for(int i=0; i<shape.length/2; i++){
			
			for(int j=i; j<shape.length-i; j++){
				left[shape.length-1-i][j]=shape[j][i];
				left[shape.length-1-j][shape.length-1-i]=shape[shape.length-1-i][j];
				left[i][j]=shape[j][shape.length-1-i];
				left[j][i]=shape[i][shape.length-1-j];				
			}
		}
		this.shapes.put(Direction.LEFT, left);
		
		return true;
	}
	
	/**
	 * Updates the position of the entity on the map.
	 * Typically should be synchronized with the frame rate
	 */
	public final void update(){ 
		double currentTime=System.currentTimeMillis();
		if(isRun() && currentTime>=this.futureMove){
			this.chart.deleteShape(this, this.pos, this.shapes.get(lastDir));
			int moveOf=(int) ((currentTime-this.futureMove)/speed);
			boolean success=false;
			Point newPos=new Point();
			
			while(!success && moveOf>0){
				switch(this.direction){
				case UP:
					newPos.setLocation(this.pos.x, this.pos.y-moveOf);
					break;
				case DOWN:
					newPos.setLocation(this.pos.x, this.pos.y+moveOf);
					break;
				case LEFT:
					newPos.setLocation(this.pos.x-moveOf, this.pos.y);
					break;
				case RIGHT:
					newPos.setLocation(this.pos.x+moveOf, this.pos.y);
					break;
				default:
					System.err.println("Error: Entity: update: direction non gérée");
					System.exit(1);
					break;
				}
				setPos(newPos);
				moveOf--;
			}
			
			this.chart.addShape(this, this.pos, this.shapes.get(this.direction));
			this.lastDir=this.direction;
			this.futureMove+=this.speed;
		} else if(this.direction!=this.lastDir){
			this.chart.deleteShape(this, this.pos, this.shapes.get(lastDir));
			this.chart.addShape(this, this.pos, this.shapes.get(this.direction));
			this.lastDir=this.direction;
		}
		checkState();
	}
	
	/**
	 * Check the state of the entity on the map
	 * It's a function of callback, she is call before the end of {@link #update()};
	 */
	protected abstract void checkState();
	
	/**
	 * Kill the entity
	 */
	public void kill(){
		notifyKill(this);
		this.chart.deleteShape(this, this.pos, this.shapes.get(lastDir));		
	}
	
	/**
	 * Add an listener
	 * @param listener The listener
	 */
	public void addListener(BasicEvent listener){
		this.listeners.add(listener);
	}
	
	/**
	 * Remove an listener
	 * @param listener The listener
	 * @return success or not
	 */
	public boolean removeListener(BasicEvent listener){
		return this.listeners.remove(listener);
	}
	
	protected void notifyKill(Entity entity){
		for(BasicEvent event : this.listeners) event.kill(entity);
	}
	
	protected void notifyBorn(Entity entity){
		for(BasicEvent event : this.listeners) event.born(entity);
	}
	
	/**
	 * Set the new position, return if the position was set to(call of {@link #canMoveTo(int, int)})
	 * @param x Position in X
	 * @param y Position in Y
	 * @return yes or no
	 */
	public final boolean setPos(Point pos){
		if(canMoveTo(pos)){
			this.pos=pos;
			
			return true;
		}
		return false;
	}
	
	/**
	 * Say if it's possible to move the entity at the given position
	 * @param x Position in X
	 * @param y Position in Y
	 * @return yes or no
	 */
	public boolean canMoveTo(Point pos){
		int nbMaxX=this.chart.getSizeX()-this.chart.getResolution();
		int nbMaxY=this.chart.getSizeY()-this.chart.getResolution();
		
		if(pos.x<0 || pos.x>nbMaxX) return false;
		if(pos.y<0 || pos.y>nbMaxY) return false;
		
		return true;
	}
	
	/**
	 * Set the direction
	 * @param dir The new direction
	 */
	public void setDirection(Direction dir){
		this.lastDir=this.direction;
		this.direction=dir;
	}
	
	/**
	 * Get the direction
	 * @return The direction
	 */
	public Direction getDirection(){
		return this.direction;
	}
	
	/**
	 * Get the level
	 * @return The level
	 */
	public Level getLevel(){
		return this.level;
	}
	
	/**
	 * Moving up the entity(see also {@link #stop()})
	 */
	public void run(){
		this.futureMove=System.currentTimeMillis()+this.speed;
		this.inRun=true;
	}
	
	/**
	 * Moving down the entity (see also {@link #run()}) 
	 */
	public void stop(){
		this.futureMove=0;
		this.inRun=false;
	}
	
	/**
	 * Said if the entity is moving
	 * @return yes or no
	 */
	public boolean isRun(){
		return this.inRun;
	}

	/**
	 * Get the position on the x axis
	 * @return X
	 */
	public int getX(){
		return this.pos.x;
	}
	
	/**
	 * Get the position on the y axis
	 * @return Y
	 */
	public int getY(){
		return this.pos.y;
	}
	
	public Point getPos(){
		return this.pos.getLocation();
	}
	
	/**
	 * Get the case position on the x axis
	 * @return X
	 */
	public int getCaseX(){
		int mod=this.pos.x%this.chart.getResolution();
		
		if(mod<=this.chart.getResolution()/2){
			return this.pos.x-mod;
		}
		else{
			return this.pos.x+this.chart.getResolution()-mod;
		}
	}
	
	/**
	 * Get the case position on the y axis
	 * @return Y
	 */
	public int getCaseY(){
		int mod=this.pos.y%this.chart.getResolution();
		
		if(mod<=this.chart.getResolution()/2){
			return this.pos.y-mod;
		}
		else{
			return this.pos.y+this.chart.getResolution()-mod;
		}
	}
	
	public Point getPosCase(){
		return new Point(getCaseX(), getCaseY());
	}
}
