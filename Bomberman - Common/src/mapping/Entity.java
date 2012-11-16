package mapping;

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
	
	
	public Entity(Chart chart){
		this.chart=chart;
		this.dirChanged=true;
		this.direction=Direction.DOWN;
		stop();		
	}
	
	abstract protected void clean();
	abstract protected void draw();
	
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
	
	public boolean setPos(int x, int y){
		int nbMaxX=this.chart.getSizeX()-this.chart.getResolution();
		int nbMaxY=this.chart.getSizeY()-this.chart.getResolution();
		
		if(x<0 || x>nbMaxX) return false;
		if(y<0 || y>nbMaxY) return false;
		
		this.posX=x;
		this.posY=y;
		
		return true;
	}
	
	public void setDirection(Direction dir){
		this.direction=dir;
		this.dirChanged=true;
	}
	
	public Direction getDirection(){
		return this.direction;
	}
	
	public Level getLevel(){
		return this.level;
	}
	
	public void run(){
		this.futureMove=System.currentTimeMillis()+this.speed;
		this.inRun=true;
	}
	
	public void stop(){
		this.futureMove=0;
		this.inRun=false;
	}
	
	public boolean isRun(){
		return this.inRun;
	}

	public int getX(){
		return this.posX;
	}
	
	public int getY(){
		return this.posY;
	}
}
