package engine;

import org.newdawn.slick.geom.Vector2f;

public class BeamFire extends Abillity {
	private int power;

	public BeamFire(Entity owner) {
		super(owner);
	}
	
	@Override
	public void update(int delta) {
		Fire fire=new Fire(this.owner.getEngine());
		fire.setOwner(this.owner);
		fire.setDirection(this.owner.getDirection());
		
		if(this.power>1){
			BeamFire beamFire=new BeamFire(fire);
			beamFire.setOwner(fire);
			beamFire.setPower(this.power-1);
			fire.addAbillity(beamFire);
		}
		
		Kamikaze kamikaze=new Kamikaze(fire, 1500);
		fire.addAbillity(kamikaze);
				
		Vector2f position=this.owner.getPosition();
		position.x+=1000*Math.cos(Math.toRadians(this.owner.getDirection()));
		position.y+=1000*Math.sin(Math.toRadians(this.owner.getDirection()));
		fire.setPosition(position);
		
		this.owner.getEngine().addEntityToBuff(fire);
		this.owner.removeAbillityToBuff(this);
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
}
