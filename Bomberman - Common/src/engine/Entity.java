/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2012-2013  Nicolas Barranger <wicowyn@gmail.com>

    Bomberton is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Bomberton is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bomberton.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import engine.abillity.Render;


public class Entity implements Collidable {
	private Logger log=LogManager.getLogger(getClass());
	private static int lastID;
	private int ID;
	private float scale;
	private float direction;
	private Vector2f position;
	private List<EntityListener> listeners=new ArrayList<EntityListener>();
	private List<Abillity> abillities=new ArrayList<Abillity>();
	private List<Abillity> abillitiesAdd=new ArrayList<Abillity>();
	private List<Abillity> abillitiesRemove=new ArrayList<Abillity>();
	private Map<Integer, List<CollisionAbillity>> collisionAbillities=new HashMap<Integer, List<CollisionAbillity>>();
	private Render renderAbillity=null;
	private Entity owner=null;
	protected Shape collisionShape;
	protected int collisionType;
	protected Engine engine;
	
	{
		this.ID=Entity.lastID++;
	}
	
	
	public Entity(Engine engine, Shape collisionShape){
		this.collisionShape=collisionShape;
		this.engine=engine;
	}
	
	public Engine getEngine(){
		return this.engine;
	}
	
	public void clear(){
		this.abillities.clear();
		this.collisionAbillities.clear();
		this.renderAbillity=null;
	}
	
	public void update(int delta){
		for(Abillity abillity : this.abillities) abillity.update(delta);
		checkAbillityBuff();
	}
	
	public void checkAbillityBuff(){
		for(Abillity abillity : this.abillitiesAdd) addAbillity(abillity);
		for(Abillity abillity : this.abillitiesRemove) removeAbillity(abillity);
		
		this.abillitiesAdd.clear();
		this.abillitiesRemove.clear();
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr){
		if(this.renderAbillity!=null){
			this.renderAbillity.render(gc, sb, gr);
		}
	}
	
	public void addAbillity(Abillity abillity){
		abillity.setOwner(this);
		
		if(abillity instanceof Render){
			this.renderAbillity=(Render) abillity;
		}
		else if(abillity instanceof CollisionAbillity){
			CollisionAbillity collisionAbillity=(CollisionAbillity) abillity;
			List<CollisionAbillity> list=this.collisionAbillities.get(collisionAbillity.getColliderType());
			
			if(list==null){
				list=new ArrayList<CollisionAbillity>();
				this.collisionAbillities.put(collisionAbillity.getColliderType(), list);
			}
			
			list.add(collisionAbillity);
		}
		
		this.abillities.add(abillity);
		notifyAbillityAdded(abillity);
		this.log.debug("add "+abillity.getClass().getSimpleName()+" to "+getClass().getSimpleName());
	}
	
	public void removeAbillity(Abillity abillity){
		this.abillities.remove(abillity);
		
		if(abillity==this.renderAbillity) this.renderAbillity=null;
		if(abillity instanceof CollisionAbillity){
			CollisionAbillity collisionAbillity=(CollisionAbillity) abillity;
			List<CollisionAbillity> list=this.collisionAbillities.get(collisionAbillity.getColliderType());
			
			list.remove(collisionAbillity);
		}
		
		notifyAbillityRemoved(abillity);
		this.log.debug("remove "+abillity.getClass().getSimpleName()+" from "+getClass().getSimpleName());
	}
	
	public void addAbillityToBuff(Abillity abillity){
		this.abillitiesAdd.add(abillity);
	}
	
	public void removeAbillityToBuff(Abillity abillity){
		this.abillitiesRemove.add(abillity);
	}
	
	public Abillity getAbillity(int ID){
		for(Abillity abillity : this.abillities){
			if(abillity.getID()==ID) return abillity;
		}
		
		return null;
	}
	
	public List<Abillity> getAbillities(){
		return this.abillities;
	}
	
	public List<CollisionAbillity> getCollisionAbillity(int colliderType){
		List<CollisionAbillity> list=this.collisionAbillities.get(colliderType);
		
		if(list==null){ //histoire de ne jamais avoir à tester le retour pour l'utilisateur;
			list=new ArrayList<CollisionAbillity>();
			this.collisionAbillities.put(colliderType, list);
		}
		
		return list;
	}
	
	public static Entity getRootOwner(Entity entity){
		while(entity.getOwner()!=null){
			entity=entity.getOwner();
		}
			
		return entity;
	}

	public int getID(){
		return ID;
	}

	public float getScale(){
		return scale;
	}

	public void setScale(float scale){
		this.scale=scale;
	}

	public float getDirection(){
		return direction;
	}

	public void setDirection(float direction){
		this.direction=direction;
		
		while(this.direction<0) this.direction+=360;
		while(this.direction>=360) this.direction-=360;
	}

	public Vector2f getPosition(){
		return this.position.copy();
	}

	public void setPosition(Vector2f position){
		this.position=position;
	}
	
	public Entity getOwner(){
		return this.owner;
	}
	
	public void setOwner(Entity owner){
		this.owner=owner;
	}
	
	public boolean addListener(EntityListener listener){
		return this.listeners.add(listener);
	}
	
	public boolean removeListener(EntityListener listener){
		return this.listeners.remove(listener);
	}
	
	protected void notifyAbillityAdded(Abillity abillity){
		for(EntityListener listener : this.listeners) listener.abillityAdded(abillity);
	}
	
	protected void notifyAbillityRemoved(Abillity abillity){
		for(EntityListener listener : this.listeners) listener.abillityRemoved(abillity);
	}

	@Override
	public Shape getNormalCollisionShape(){
		return this.collisionShape;
	}

	@Override
	public Shape getCollisionShape(){
		Shape shape=this.collisionShape.transform(Transform.createRotateTransform(
				(float) Math.toRadians(getDirection()), 499.5f, 499.5f));
		return shape.transform(Transform.createTranslateTransform(this.position.x, this.position.y));
	}

	@Override
	public int getCollisionType(){
		return this.collisionType;
	}

	@Override
	public boolean isCollidingWith(Collidable collidable){
		return getCollisionShape().intersects(collidable.getCollisionShape());
	}
	
}
