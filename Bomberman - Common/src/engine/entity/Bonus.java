package engine.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

import engine.Abillity;
import engine.CTSCollision;
import engine.Engine;
import engine.Entity;

public class Bonus extends Entity {
	private static Rectangle shape=new Rectangle(0, 0, 1000, 1000);
	private int bomb;
	private int power;
	private int speed;
	protected List<Abillity> listAbbillity=new ArrayList<Abillity>();
	
	
	public Bonus(Engine engine) {
		super(engine, Bonus.shape);
		this.collisionType=CTSCollision.Bonus;
	}
	
	public int getBomb() {
		return bomb;
	}

	public void setBomb(int bomb) {
		this.bomb = bomb;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public List<Abillity> getListAbillity(){
		return this.listAbbillity;
	}
	
	@Override
	public Rectangle getNormalCollisionShape(){
		return (Rectangle) super.getNormalCollisionShape();
	}

}
