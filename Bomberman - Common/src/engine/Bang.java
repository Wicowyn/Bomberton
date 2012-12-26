package engine;

import org.newdawn.slick.geom.Vector2f;

public class Bang extends CollisionAbillity {
	private int time=1500;
	private int currentTime=0;
	private boolean detonnation=false;
	private PopBomb initializer=null;
	private int power=1;
	
	public Bang(Entity owner) {
		super(owner);
	}

	@Override
	public void update(int delta) {
		if(this.detonnation){
			this.currentTime+=delta;
			if(this.currentTime>this.time) bang();
		}
	}
	
	public void startDetonation(){
		this.detonnation=true;
		this.currentTime=0;
	}
	
	public void stopDetonnation(){
		this.detonnation=false;
	}
	
	public void bang(){
		this.detonnation=false;
		if(this.initializer!=null) this.initializer.setCurrentBomb(this.initializer.getCurrentBomb()-1);
		//TODO say initializer.owner kill owner;
		for(int diffDir=0; diffDir<360; diffDir+=90){
			Fire fire=new Fire(this.owner.getEngine());
			fire.setOwner(this.owner);
			fire.setDirection(this.owner.getDirection()+diffDir);
			
			BeamFire beamFire=new BeamFire(fire);
			beamFire.setPower(this.power-1);
			fire.addAbillity(beamFire);
			
			Kamikaze kamikaze=new Kamikaze(fire, 1500);
			fire.addAbillity(kamikaze);
			
			Vector2f position=this.owner.getPosition();
			position.x+=1000*Math.cos(Math.toRadians(this.owner.getDirection()+diffDir));
			position.y+=1000*Math.sin(Math.toRadians(this.owner.getDirection()+diffDir));
			fire.setPosition(position);
			
			this.owner.getEngine().addEntityToBuff(fire);
		}
		
		this.owner.getEngine().removeEntityToBuff(this.owner);
	}
	
	public void setTime(int time){
		this.time=time;
	}

	public void setInitializer(KeyboardPopBomb initializer){
		this.initializer=initializer;
	}
	
	public void setPower(int power){
		this.power = power<1 ? 1 : power;
	}

	@Override
	public int getColliderType() {
		return CTSCollision.Fire;
	}

	@Override
	public void performCollision(Collidable collidable) {		
		Fire fire=(Fire) collidable;
		this.owner.setOwner(fire);
		bang();
	}
}