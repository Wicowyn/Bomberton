package engine.collisionHandler;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Shape;

import engine.Abillity;
import engine.Engine;
import engine.Entity;

public abstract class Item extends Entity {
	protected int time;
	protected int life;
	protected int bomb;
	protected int power;
	protected int speed;
	protected List<Abillity> listAbbillity=new ArrayList<Abillity>();
	
	public Item(Engine engine, Shape collisionShape) {
		super(engine, collisionShape);
	}

}
