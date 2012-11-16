package mapping;

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
	private boolean dirChanged=true;
	
	protected Direction direction;
	protected Level level;
	protected Chart chart;
	protected int speed=0; // ms pour une case
	
	/**
	 * Basic constructor with the given map
	 * @param chart The map
	 */
	public Entity(Chart chart){
		this.chart=chart;
		this.dirChanged=true;
		this.direction=Direction.DOWN;
		stop();		
	}
	
	/**
	 * Removes the entity from the map, same operation as {@link #draw()} but in removal.
	 */
	abstract protected void clean();
	/**
	 * Draw the entity on the map
	 */
	abstract protected void draw();
	
	/**
	 * Updates the position of the entity on the map.
	 * Typically should be synchronized with the frame rate
	 */
	public void update(){ 
		double currentTime=System.currentTimeMillis();
		if(isRun() && currentTime>=this.futureMove){
			clean();
			int moveOf=(int) ((currentTime-this.futureMove)%speed);
			boolean verif=true;
			
			switch(this.direction){
			case UP:
				verif=setPos(this.posX, this.posY-moveOf);
				break;
			case DOWN:
				verif=setPos(this.posX, this.posY+moveOf);
				break;
			case LEFT:
				verif=setPos(this.posX-moveOf, this.posY);
				break;
			case RIGHT:
				verif=setPos(this.posX+moveOf, this.posY);
				break;
			default:
				System.err.println("Error: Entity: update: direction non gérée");
				System.exit(1);
				break;
			}
			
			if(verif) draw();
			this.dirChanged=false;
			this.futureMove+=this.speed;
		} else if(this.dirChanged){
			draw();
			this.dirChanged=false;
		}
		
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
		this.direction=dir;
		this.dirChanged=true;
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
}
