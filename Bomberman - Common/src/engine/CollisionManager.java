package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class CollisionManager {
	private HashMap<Integer, List<Collidable>> collidables=new HashMap<Integer, List<Collidable>>();
	private Map<Integer, List<Integer>> collisionsTypes=new HashMap<Integer, List<Integer>>();
	private Map<String, CollisionHandler> collisionHandlers=new HashMap<String, CollisionHandler>();
	
	public static String getKey(int type1, int type2){
		return (type1 < type2) ? type1+"-"+type2 : type2+"-"+type1; 
	}
	
	public void clear(){
		this.collidables.clear();
		this.collisionsTypes.clear();
		this.collisionHandlers.clear();
	}

	public void addCollidable(Collidable collidable){
		List<Collidable> list=this.collidables.get(collidable.getCollisionType());
		
		if(list==null){
			list=new ArrayList<Collidable>();
			this.collidables.put(collidable.getCollisionType(), list);
		}
		
		list.add(collidable);
	}
	
	public void removeCollidable(Collidable collidable){
		List<Collidable> list=this.collidables.get(collidable.getCollisionType());
		
		if(list!=null){
			list.remove(collidable);
		}
	}
	
	public void addHandler(CollisionHandler handler){
		String key=getKey(handler.getCollider1Type(), handler.getCollider2Type());
		
		this.collisionHandlers.put(key, handler);
		addTypesToCollision(handler.getCollider1Type(), handler.getCollider2Type());
		addTypesToCollision(handler.getCollider2Type(), handler.getCollider1Type());
	}
	
	private void addTypesToCollision(int type1, int type2){
		List<Integer> list=this.collisionsTypes.get(type1);
		
		if(list==null){
			list=new ArrayList<Integer>();
			this.collisionsTypes.put(type1, list);
		}
		
		list.add(type2);
	}
	
	public boolean isCollideWith(Collidable collidable, int type){
		List<Integer> list=this.collisionsTypes.get(type);
		if(list==null || !list.contains(collidable.getCollisionType())) return false;
		
		List<Collidable> colliders=this.collidables.get(type);
		if(colliders==null) return false;
		
		for(Collidable collider : colliders){
			if(collidable.isCollidingWith(collider)) return true;
		}
		
		return false;
	}
	
	public List<Collidable> collideWith(Collidable collidable, int type){
		List<Collidable> listRes=new ArrayList<Collidable>();
		
		List<Integer> list=this.collisionsTypes.get(type);
		if(list==null || !list.contains(collidable.getCollisionType())) return listRes;
		
		List<Collidable> colliders=this.collidables.get(type);
		if(colliders==null) return listRes;
		
		for(Collidable collider : colliders){
			if(collidable.isCollidingWith(collider)) listRes.add(collider);
		}
		
		return listRes;
	}
	
	public void performCollision(){
		for(Iterator<Entry<String, CollisionHandler>> itM=this.collisionHandlers.entrySet().iterator(); itM.hasNext();){
			Entry<String, CollisionHandler> entry=itM.next();
			entry.getValue().update();
		}
		
		List<CollisionData> toProcess=new ArrayList<CollisionData>();
		List<String> alreadyProcessed=new ArrayList<String>();
		
		Set<Integer> types=this.collisionsTypes.keySet();
		for(Integer type1 : types){
			List<Integer> collidesWith=this.collisionsTypes.get(type1);
			
			for(Integer type2 : collidesWith){
				String key=getKey(type1, type2);
				if(alreadyProcessed.contains(key)) continue; //déja fait
				
				List<Collidable> collideres1=this.collidables.get(type1);
				List<Collidable> collideres2=this.collidables.get(type2);
				if(collideres1==null || collideres2==null) continue; //s'il en éxiste aucun

				for(Collidable collider1 : collideres1){
					for(Collidable collider2 : collideres2){
						if(collider1.isCollidingWith(collider2)){
							CollisionData collision=new CollisionData();
							
							collision.collider1=collider1;
							collision.collider2=collider2;
							collision.handler=this.collisionHandlers.get(key);
							
							toProcess.add(collision);
						}
					}
				}
				
				alreadyProcessed.add(key);
			}
		}
		
		for(CollisionData collision : toProcess){
			collision.handler.performCollision(collision.collider1, collision.collider2);
		}
	}
	
	private class CollisionData{
		public Collidable collider1;
		public Collidable collider2;
		public CollisionHandler handler;
	}
}

