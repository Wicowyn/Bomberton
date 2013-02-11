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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CollisionManager{
	private List<ListenEntity> listens=new ArrayList<ListenEntity>();
	private Map<Integer, Set<Entity>> colliHandle=new HashMap<Integer, Set<Entity>>();
	private Map<Integer, Set<Entity>> colliMarker=new HashMap<Integer, Set<Entity>>();
	
	
	public void addEntity(Entity Entity){
		ListenEntity listen=new ListenEntity(Entity);
		this.listens.add(listen);
		
		for(TouchHandle handle : Entity.getAllTouchHandle()) listen.touchHandleAdded(handle);
		for(TouchMarker marker : Entity.getAllTouchMarker()) listen.touchMarkerAdded(marker);
	}
	
	public boolean removeEntity(Entity Entity){		
		for(Iterator<ListenEntity> it=this.listens.iterator(); it.hasNext();){
			ListenEntity listen=it.next();
			
			if(listen.Entity==Entity){
				for(TouchHandle handle : Entity.getAllTouchHandle()) listen.touchHandleRemoved(handle);
				for(TouchMarker marker : Entity.getAllTouchMarker()) listen.touchMarkerRemoved(marker);
				
				it.remove();
				return true;
			}
		}
		
		return false;
	}
	
	public void performCollision(){
		List<DataCollide> toCollides=new ArrayList<DataCollide>();
		
		for(Iterator<Map.Entry<Integer, Set<Entity>>> itH=this.colliHandle.entrySet().iterator(); itH.hasNext();){
			Map.Entry<Integer, Set<Entity>> entry=itH.next();
			Set<Entity> setM=this.colliMarker.get(entry.getKey());
			
			if(setM==null) continue;
			
			for(Entity colliH : entry.getValue()){
				for(Entity  colliM : setM){
					if(colliH.isCollidingWith(colliM) && colliH!=colliM){
						//TODO à optimiser, potiellement 2 même collision peuvent être prise en compte. Mais ne pose pas de problème.
						for(TouchHandle handle : colliH.getAllTouchHandle()){
							for(TouchMarker marker : colliM.getAllTouchMarker()){
								if(handle.getType()==marker.getType()) toCollides.add(new DataCollide(handle, marker));
							}
						}
					}
				}
			}
		}
		
		Collections.sort(toCollides);
		for(DataCollide data : toCollides) data.perform();
	}
	
	
	public class DataCollide implements Comparable<DataCollide>{
		public TouchHandle handle;
		public TouchMarker marker;
		
		
		public DataCollide(TouchHandle handle, TouchMarker marker){
			this.handle=handle;
			this.marker=marker;
		}
		
		@Override
		public int compareTo(DataCollide o) {
			return handle.compareTo(o.handle);
		}
		
		public void perform(){
			this.handle.perform(this.marker);
		}
		
	}
	
	
	private class ListenEntity implements CollidableListener{
		public Entity Entity;
		
		
		public ListenEntity(Entity Entity){
			this.Entity=Entity;
			this.Entity.addCollidableListener(this);
		}
		
		@Override
		public void touchHandleAdded(TouchHandle handle) {
			Set<Entity> set=CollisionManager.this.colliHandle.get(handle.getType());
			
			if(set==null){
				set=new HashSet<Entity>();
				CollisionManager.this.colliHandle.put(handle.getType(), set);
			}
			
			set.add(this.Entity);
		}

		@Override
		public void touchHandleRemoved(TouchHandle handle) {
			Set<Entity> set=CollisionManager.this.colliHandle.get(handle.getType());
			
			set.remove(this.Entity);
			if(set.isEmpty()) CollisionManager.this.colliHandle.remove(handle.getType());
		}

		@Override
		public void touchMarkerAdded(TouchMarker marker) {
			Set<Entity> set=CollisionManager.this.colliMarker.get(marker.getType());
			
			if(set==null){
				set=new HashSet<Entity>();
				CollisionManager.this.colliMarker.put(marker.getType(), set);
			}
			
			set.add(this.Entity);
		}

		@Override
		public void touchMarkerRemoved(TouchMarker marker) {
			Set<Entity> set=CollisionManager.this.colliMarker.get(marker.getType());
			
			set.remove(this.Entity);
			if(set.isEmpty()) CollisionManager.this.colliMarker.remove(marker.getType());
		}		
	}
}
