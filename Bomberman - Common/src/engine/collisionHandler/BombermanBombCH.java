package engine.collisionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.CollisionHandler;
import engine.CollisionManager;
import engine.EngineListener;
import engine.Entity;
import engine.entity.Bomb;
import engine.entity.Bomberman;

public class BombermanBombCH implements CollisionHandler, EngineListener {
	private Map<Collidable, List<Collidable>> mapBegin=new HashMap<Collidable, List<Collidable>>();
	private CollisionManager manager;
	private int tolerance;
	
	public BombermanBombCH(CollisionManager manager) {
		this.manager=manager;
	}
	
	public void setTolerance(int tolerance){
		this.tolerance=tolerance;
		if(this.tolerance>50) this.tolerance=50;
		else if(this.tolerance<0) this.tolerance=0;
	}

	@Override
	public int getCollider1Type() {
		return CTSCollision.Bomberman;
	}

	@Override
	public int getCollider2Type() {
		return CTSCollision.Bomb;
	}

	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		Bomberman bomberman;
		Bomb bomb;
		
		if(collidable1 instanceof Bomberman){
			bomberman=(Bomberman) collidable1;
			bomb=(Bomb) collidable2;
		}
		else{
			bomberman=(Bomberman) collidable2;
			bomb=(Bomb) collidable1;
		}
		
		if(!this.mapBegin.containsKey(bomb) || !this.mapBegin.get(bomb).contains(bomberman)){
			if(!collidable1.isCollidingWith(collidable2)) return;
			for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
			for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
			if(!collidable1.isCollidingWith(collidable2)) return;
			
			float direction=bomberman.getDirection();
			direction+=180; //reverse it
			
			
			Vector2f initialPos=bomberman.getPosition();
			do{
				Vector2f position=bomberman.getPosition();
				position.x+=Math.cos(Math.toRadians(direction));
				position.y+=Math.sin(Math.toRadians(direction));
				bomberman.setPosition(position);
			}while(bomberman.isCollidingWith(bomb));
			
			Rectangle rectBomberman=bomberman.getNormalCollisionShape();
			Rectangle rectBomb=bomb.getNormalCollisionShape();
			Vector2f posBomberman=bomberman.getPosition();
			Vector2f posBomb=bomb.getPosition();
			Vector2f posTemp=posBomb.copy();
			
			switch((int) bomberman.getDirection()){
			case 90:
				if(rectBomberman.getMinX()+posBomberman.x > rectBomb.getMaxX()+posBomb.x-rectBomb.getWidth()*this.tolerance/100){
					posTemp.x+=rectBomb.getWidth();
					posTemp.y-=rectBomberman.getHeight()-1;
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.x+=initialPos.y-posBomberman.y;
						if(posBomberman.x>posBomb.x+(rectBomb.getWidth())) posBomberman.x=posBomb.x+(rectBomb.getWidth());
					}
				}
				else if(rectBomberman.getMaxX()+posBomberman.x < rectBomb.getMinX()+posBomb.x+rectBomb.getWidth()*this.tolerance/100){
					posTemp.x-=rectBomberman.getWidth();
					posTemp.y-=rectBomberman.getHeight()-1;
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.x-=initialPos.y-posBomberman.y;
						if(posBomberman.x+rectBomberman.getWidth()<posBomb.x) posBomberman.x=posBomb.x-rectBomberman.getWidth();
					}
				}
				break;
			case 270:
				if(rectBomberman.getMinX()+posBomberman.x > rectBomb.getMaxX()+posBomb.x-rectBomb.getWidth()*this.tolerance/100){
					posTemp.x+=rectBomb.getWidth();
					posTemp.y+=rectBomb.getHeight()-1;
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.x+=posBomberman.y-initialPos.y;
						if(posBomberman.x>posBomb.x+(rectBomb.getWidth())) posBomberman.x=posBomb.x+(rectBomb.getWidth());
					}
				}
				else if(rectBomberman.getMaxX()+posBomberman.x < rectBomb.getMinX()+posBomb.x+rectBomb.getWidth()*this.tolerance/100){
					posTemp.x-=rectBomberman.getWidth();
					posTemp.y+=rectBomb.getHeight()-1;
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.x-=posBomberman.y-initialPos.y;
						if(posBomberman.x+rectBomberman.getWidth()<posBomb.x) posBomberman.x=posBomb.x-rectBomberman.getWidth();
					}
				}
				break;
			case 0:
				if(rectBomberman.getMaxY()+posBomberman.y < rectBomb.getMinY()+posBomb.y+rectBomb.getHeight()*this.tolerance/100){
					posTemp.x-=rectBomberman.getWidth()-1;
					posTemp.y-=rectBomberman.getHeight();
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.y-=initialPos.x-posBomberman.x;
						if(posBomberman.y+rectBomberman.getHeight()<posBomb.y) posBomberman.y=posBomb.y-rectBomberman.getHeight();
					}
				}
				else if(rectBomberman.getMinY()+posBomberman.y > rectBomb.getMaxY()+posBomb.y-rectBomb.getHeight()*this.tolerance/100){
					posTemp.x-=rectBomberman.getWidth()-1;
					posTemp.y+=rectBomb.getHeight();
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.y+=initialPos.x-posBomberman.x;
						if(posBomberman.y>posBomb.y+rectBomb.getHeight()) posBomberman.y=posBomb.y+rectBomb.getHeight();
					}
				}
				break;
			case 180:
				if(rectBomberman.getMaxY()+posBomberman.y < rectBomb.getMinY()+posBomb.y+rectBomb.getHeight()*this.tolerance/100){
					posTemp.x-=rectBomberman.getWidth()-1;
					posTemp.y-=rectBomberman.getHeight();
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.y-=posBomberman.x-initialPos.x;
						if(posBomberman.y+rectBomberman.getHeight()<posBomb.y) posBomberman.y=posBomb.y-rectBomberman.getHeight();
					}
				}
				else if(rectBomberman.getMinY()+posBomberman.y > rectBomb.getMaxY()+posBomb.y-rectBomb.getHeight()*this.tolerance/100){
					posTemp.x-=rectBomberman.getWidth()-1;
					posTemp.y+=rectBomb.getHeight();
					bomberman.setPosition(posTemp);
					
					if(!this.manager.isCollideWith(bomberman, CTSCollision.Bomb)){
						posBomberman.y+=posBomberman.x-initialPos.x;
						if(posBomberman.y>posBomb.y+rectBomb.getHeight()) posBomberman.y=posBomb.y+rectBomb.getHeight();
					}
				}
				break;
			}
			
			bomberman.setPosition(posBomberman);
		}

	}
	
	protected void checkMapBegin(){
		for(Iterator<Entry<Collidable, List<Collidable>>> itM=this.mapBegin.entrySet().iterator(); itM.hasNext();){
			Entry<Collidable, List<Collidable>> entry=itM.next();
			
			for(Iterator<Collidable> itL=entry.getValue().iterator(); itL.hasNext();){
				if(!entry.getKey().isCollidingWith(itL.next())) itL.remove();
			}
			
			if(entry.getValue().isEmpty()) itM.remove();
		}
	}

	@Override
	public void entityAdded(Entity entity) {
		if(entity instanceof Bomb){
			List<Collidable> list=this.manager.collideWith(entity, CTSCollision.Bomberman);
			if(list==null || list.isEmpty()) return;
			
			this.mapBegin.put(entity, list);
		}
		else if(entity instanceof Bomberman){
			List<Collidable> list=this.manager.collideWith(entity, CTSCollision.Bomb);
			if(list==null || list.isEmpty()) return;
			
			for(Collidable collider : list){
				List<Collidable> listSecond=new ArrayList<Collidable>();
				listSecond.add(entity);
				
				this.mapBegin.put(collider, listSecond);
			}
		}
	}

	@Override
	public void entityRemoved(Entity entity) {
		
	}

	@Override
	public void update() {
		checkMapBegin();
		
	}

}
