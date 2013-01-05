package engine;


public class KickBomb extends Abillity implements CollisionAbillity {

	public KickBomb(Entity owner) {
		super(owner);
		
	}

	@Override
	public int getColliderType() {
		return CTSCollision.Bomb;
	}

	@Override
	public void performCollision(Collidable collidable) {
		Bomb bomb=(Bomb) collidable;
		
		for(Abillity abillity : bomb.getAbillities()){
			if(abillity instanceof BeamMove) bomb.removeAbillityToBuff(abillity);
		}
		
		BeamMove move=new BeamMove(bomb);
		move.setSpeed(5);
		bomb.setDirection(this.owner.getDirection());
		bomb.addAbillityToBuff(move);
	}

	@Override
	public void update(int delta) {
		
	}

}
