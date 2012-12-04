package mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.Direction;

/**
 * Abstract class which represent every entity on the map
 * @author yapiti
 *
 */
public abstract class Entity {
	private int posX;
	private int posY;
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
			this.chart.deleteShape(this, posX, posY, this.shapes.get(lastDir));
			int moveOf=(int) ((currentTime-this.futureMove)/speed);
			boolean success=false;
			
			switch(this.direction){
			case UP:
				while(!success && moveOf>0){
					success=setPos(this.posX, this.posY-moveOf);
					moveOf--;
				}
				break;
			case DOWN:
				while(!success && moveOf>0){
					success=setPos(this.posX, this.posY+moveOf);
					moveOf--;
				}
				break;
			case LEFT:
				while(!success && moveOf>0){
					success=setPos(this.posX-moveOf, this.posY);
					moveOf--;
				}
				break;
			case RIGHT:
				while(!success && moveOf>0){
					success=setPos(this.posX+moveOf, this.posY);
					moveOf--;
				}
				break;
			default:
				System.err.println("Error: Entity: update: direction non gérée");
				System.exit(1);
				break;
			}
			
			this.chart.addShape(this, posX, posY, this.shapes.get(this.direction));
			this.lastDir=this.direction;
			this.futureMove+=this.speed;
		} else if(this.direction!=this.lastDir){
			this.chart.deleteShape(this, posX, posY, this.shapes.get(lastDir));
			this.chart.addShape(this, posX, posY, this.shapes.get(this.direction));
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
		this.chart.deleteShape(this, posX, posY, this.shapes.get(lastDir));		
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
	public final boolean setPos(int x, int y){
		if(canMoveTo(x, y)){
			this.posX=x;
			this.posY=y;
			
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
	public boolean canMoveTo(int x, int y){
		int nbMaxX=this.chart.getSizeX()-this.chart.getResolution();
		int nbMaxY=this.chart.getSizeY()-this.chart.getResolution();
		
		if(x<0 || x>nbMaxX) return false;
		if(y<0 || y>nbMaxY) return false;
		
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
		return this.posX;
	}
	
	/**
	 * Get the position on the y axis
	 * @return Y
	 */
	public int getY(){
		return this.posY;
	}
	
	/**
	 * Get the case position on the x axis
	 * @return X
	 */
	public int getCaseX(){
		int mod=this.posX%this.chart.getResolution();
		
		if(mod<=this.chart.getResolution()/2){
			return this.posX-mod;
		}
		else{
			return this.posX+this.chart.getResolution()-mod;
		}
	}
	
	/**
	 * Get the case position on the y axis
	 * @return Y
	 */
	public int getCaseY(){
		int mod=this.posY%this.chart.getResolution();
		
		if(mod<=this.chart.getResolution()/2){
			return this.posY-mod;
		}
		else{
			return this.posY+this.chart.getResolution()-mod;
		}
	}
}
