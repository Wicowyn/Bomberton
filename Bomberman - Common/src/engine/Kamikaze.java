package engine;

public class Kamikaze extends Abillity {
	private int time=0;
	private int currentTime=0;
	
	public Kamikaze(Entity owner, int time) {
		super(owner);
		this.time=time;
	}

	@Override
	public void update(int delta) {
		this.currentTime+=delta;
		if(this.currentTime>this.time){
			this.owner.getEngine().removeEntityToBuff(this.owner);
		}
	}

}
