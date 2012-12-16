package engine;

public abstract class Abillity {
	protected int ID;
	protected Entity owner;
	
	
	public Abillity(Entity owner){
		setOwner(owner);
	}
	
	public int getID(){
		return this.ID;
	}
	
	public void setOwner(Entity entity){
		this.owner=entity;
	}
	
	public abstract void update(int delta);
}
