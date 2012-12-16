package engine;

public abstract class PopBomb extends Abillity {
	protected int maxBomb=1;
	protected int currentBomb=0;
	protected int power=1;
	
	public PopBomb(Entity owner) {
		super(owner);
	}
	
	public int getMaxBomb() {
		return maxBomb;
	}

	public void setMaxBomb(int maxBomb) {
		this.maxBomb = maxBomb;
	}

	public int getCurrentBomb() {
		return currentBomb;
	}

	public void setCurrentBomb(int currentBomb) {
		this.currentBomb = currentBomb;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power= power<1 ? 1 : power;
	}

}
