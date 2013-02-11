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

package collision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import engine.Abillity;
import engine.Engine;
import engine.EntityListener;


public final class Entity{
	private Logger log=LogManager.getLogger(getClass());
	private static int lastID;
	private int ID;
	private float scale;
	private float direction;
	private Vector2f position;
	private boolean inUpdate=false;
	private boolean modifNCS=true; //NCS NormalCollisionShape, usefull for optimisation
	private List<EntityListener> entityListeners=new ArrayList<EntityListener>();
	private List<CollidableListener> collidableListeners=new ArrayList<CollidableListener>();
	private List<Abillity> abillities=new ArrayList<Abillity>();
	private Set<Abillity> abillitiesAdd=new HashSet<Abillity>();
	private Set<Abillity> abillitiesRemove=new HashSet<Abillity>();
	private List<TouchHandle> touchHandles=new ArrayList<TouchHandle>();
	private List<TouchMarker> touchMarkers=new ArrayList<TouchMarker>();
	private Entity owner=null;
	private Shape normalCollisionShape=null;
	private Shape collisionShape=null;
	private Engine engine=null;
	
	{
		this.ID=Entity.lastID++;
	}
	
	
	public Entity(Engine engine, Shape collisionShape){
		this.normalCollisionShape=collisionShape;
		this.engine=engine;
	}
	
	public Engine getEngine(){
		return this.engine;
	}
	
	public void clear(){
		for(Abillity abillity : this.abillities) notifyAbillityRemoved(abillity);
		this.abillities.clear();
		
		for(TouchHandle handle : this.touchHandles) notifyTouchHandleRemoved(handle);
		this.touchHandles.clear();
		
		for(TouchMarker marker : this.touchMarkers) notifyTouchMarkerRemoved(marker);
		this.touchMarkers.clear();
	}
	
	public void update(int delta){
		inUpdate=true;
		for(Abillity abillity : this.abillities) abillity.update(delta);
		inUpdate=false;
		checkAbillityBuff();
	}
	
	public void checkAbillityBuff(){
		for(Abillity abillity : this.abillitiesAdd) addAbillity(abillity);
		for(Abillity abillity : this.abillitiesRemove) removeAbillity(abillity);
		
		this.abillitiesAdd.clear();
		this.abillitiesRemove.clear();
	}
	
	public void addAbillity(Abillity abillity){
		if(this.inUpdate){
			this.abillitiesAdd.add(abillity);
			return;
		}
		
		abillity.setOwner(this);		
		this.abillities.add(abillity);
		
		notifyAbillityAdded(abillity);
		this.log.debug("abillity: "+abillity.getClass().getSimpleName()+" add to "+getID());
	}
	
	public boolean removeAbillity(Abillity abillity){
		if(this.inUpdate){
			if(!this.abillities.contains(abillity)) return false;
			return this.abillitiesRemove.add(abillity);
		}
		
		if(this.abillities.remove(abillity)){
			notifyAbillityRemoved(abillity);
			this.log.debug("abillity: "+abillity.getClass().getSimpleName()+" remove from "+getID());
			return true;
		}
		
		return false;
	}
	
	public void addTouchHandle(TouchHandle handle){
		this.touchHandles.add(handle);
		notifyTouchHandleAdded(handle);
		this.log.debug("touchHandle: "+handle.getClass().getSimpleName()+" add to "+getID());
	}
	
	public boolean removeTouchHandle(TouchHandle handle){
		if(this.touchHandles.remove(handle)){
			notifyTouchHandleRemoved(handle);
			this.log.debug("touchHandle: "+handle.getClass().getSimpleName()+" remove from "+getID());
			return true;
		}
		
		return false;
	}
	
	public void addTouchMarker(TouchMarker marker){
		this.touchMarkers.add(marker);
		notifyTouchMarkerAdded(marker);
		this.log.debug("touchMarker: "+marker.getClass().getSimpleName()+" add to "+getID());
	}
	
	public boolean removeTouchMarker(TouchMarker marker){
		if(this.touchMarkers.remove(marker)){
			notifyTouchMarkerRemoved(marker);
			this.log.debug("touchMarker: "+marker.getClass().getSimpleName()+" remove from "+getID());
			return true;
		}
		
		return false;
	}
	
	public List<Abillity> getAllAbillity(){
		return new ArrayList<Abillity>(this.abillities);
	}
	
	public List<TouchHandle> getAllTouchHandle() {
		return new ArrayList<TouchHandle>(this.touchHandles);
	}

	public List<TouchMarker> getAllTouchMarker() {
		return new ArrayList<TouchMarker>(this.touchMarkers);
	}
	
	public Abillity getAbillity(int ID){
		for(Abillity abillity : this.abillities){
			if(abillity.getID()==ID) return abillity;
		}
		
		return null;
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
		this.modifNCS=true;
		
		while(this.direction<0) this.direction+=360;
		while(this.direction>=360) this.direction-=360;
	}

	public Vector2f getPosition(){
		return this.position.copy();
	}

	public void setPosition(Vector2f position){
		this.position=position;
		this.modifNCS=true;
	}
	
	public Entity getOwner(){
		return this.owner;
	}
	
	public void setOwner(Entity owner){
		this.owner=owner;
	}
	
	public boolean addEntityListener(EntityListener listener){
		return this.entityListeners.add(listener);
	}
	
	public boolean removeEntityListener(EntityListener listener){
		return this.entityListeners.remove(listener);
	}
	
	protected void notifyAbillityAdded(Abillity abillity){
		for(EntityListener listener : this.entityListeners) listener.abillityAdded(abillity);
	}
	
	protected void notifyAbillityRemoved(Abillity abillity){
		for(EntityListener listener : this.entityListeners) listener.abillityRemoved(abillity);
	}

	public void addCollidableListener(CollidableListener listener) {
		this.collidableListeners.add(listener);		
	}

	public boolean removeCollidableListener(CollidableListener listener) {
		return this.collidableListeners.remove(listener);
	}

	protected void notifyTouchHandleAdded(TouchHandle handle){
		for(CollidableListener listener : this.collidableListeners) listener.touchHandleAdded(handle);
	}
	
	protected void notifyTouchHandleRemoved(TouchHandle handle){
		for(CollidableListener listener : this.collidableListeners) listener.touchHandleRemoved(handle);
	}

	protected void notifyTouchMarkerAdded(TouchMarker marker){
		for(CollidableListener listener : this.collidableListeners) listener.touchMarkerAdded(marker);
	}
	
	protected void notifyTouchMarkerRemoved(TouchMarker marker){
		for(CollidableListener listener : this.collidableListeners) listener.touchMarkerRemoved(marker);
	}
	
	public Shape getNormalCollisionShape(){
		return this.normalCollisionShape;
	}
	
	public Shape getCollisionShape(){
		if(this.modifNCS){
			this.collisionShape=this.collisionShape.transform(Transform.createRotateTransform(
					(float) Math.toRadians(getDirection()), 499.5f, 499.5f));
			this.collisionShape=this.collisionShape.transform(Transform.createTranslateTransform(this.position.x, this.position.y));
			this.modifNCS=false;
		}
		return this.collisionShape;
	}

	public boolean isCollidingWith(Entity collidable){
		return getCollisionShape().intersects(collidable.getCollisionShape());
	}
	
}
