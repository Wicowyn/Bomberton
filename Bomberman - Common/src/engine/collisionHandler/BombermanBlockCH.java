package engine.collisionHandler;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import engine.CTSCollision;
import engine.Collidable;
import engine.CollisionAbillity;
import engine.CollisionHandler;
import engine.CollisionManager;
import engine.entity.Block;
import engine.entity.Bomberman;

public class BombermanBlockCH implements CollisionHandler {
	private int tolerance=30;
	private CollisionManager manager;
	
	public BombermanBlockCH(CollisionManager manager){
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
		return CTSCollision.Block;
	}
	
	@Override
	public void performCollision(Collidable collidable1, Collidable collidable2) {
		if(!collidable1.isCollidingWith(collidable2)) return;
		for(CollisionAbillity collisionAbillity : collidable1.getCollisionAbillity(collidable2.getCollisionType())) collisionAbillity.performCollision(collidable2);
		for(CollisionAbillity collisionAbillity : collidable2.getCollisionAbillity(collidable1.getCollisionType())) collisionAbillity.performCollision(collidable1);
		if(!collidable1.isCollidingWith(collidable2)) return;
		
		Bomberman bomberman;
		Block block;
		
		if(collidable1 instanceof Bomberman){
			bomberman=(Bomberman) collidable1;
			block=(Block) collidable2;
		}
		else{
			bomberman=(Bomberman) collidable2;
			block=(Block) collidable1;
		}
		
		float direction=bomberman.getDirection();
		direction+=180; //reverse it
		
		
		Vector2f initialPos=bomberman.getPosition();
		do{
			Vector2f position=bomberman.getPosition();
			position.x+=Math.cos(Math.toRadians(direction));
			position.y+=Math.sin(Math.toRadians(direction));
			bomberman.setPosition(position);
		}while(bomberman.isCollidingWith(block));
		
		Rectangle rectBomberman=bomberman.getNormalCollisionShape();
		Rectangle rectBlock=block.getNormalCollisionShape();
		Vector2f posBomberman=bomberman.getPosition();
		Vector2f posBlock=block.getPosition();
		Vector2f posTemp=posBlock.copy();
		
		switch((int) bomberman.getDirection()){
		case 90:
			if(rectBomberman.getMinX()+posBomberman.x > rectBlock.getMaxX()+posBlock.x-rectBlock.getWidth()*this.tolerance/100){
				posTemp.x+=rectBlock.getWidth();
				posTemp.y-=rectBomberman.getHeight()-1;
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.x+=initialPos.y-posBomberman.y;
					if(posBomberman.x>posBlock.x+(rectBlock.getWidth())) posBomberman.x=posBlock.x+(rectBlock.getWidth());
				}
			}
			else if(rectBomberman.getMaxX()+posBomberman.x < rectBlock.getMinX()+posBlock.x+rectBlock.getWidth()*this.tolerance/100){
				posTemp.x-=rectBomberman.getWidth();
				posTemp.y-=rectBomberman.getHeight()-1;
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.x-=initialPos.y-posBomberman.y;
					if(posBomberman.x+rectBomberman.getWidth()<posBlock.x) posBomberman.x=posBlock.x-rectBomberman.getWidth();
				}
			}
			break;
		case 270:
			if(rectBomberman.getMinX()+posBomberman.x > rectBlock.getMaxX()+posBlock.x-rectBlock.getWidth()*this.tolerance/100){
				posTemp.x+=rectBlock.getWidth();
				posTemp.y+=rectBlock.getHeight()-1;
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.x+=posBomberman.y-initialPos.y;
					if(posBomberman.x>posBlock.x+(rectBlock.getWidth())) posBomberman.x=posBlock.x+(rectBlock.getWidth());
				}
			}
			else if(rectBomberman.getMaxX()+posBomberman.x < rectBlock.getMinX()+posBlock.x+rectBlock.getWidth()*this.tolerance/100){
				posTemp.x-=rectBomberman.getWidth();
				posTemp.y+=rectBlock.getHeight()-1;
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.x-=posBomberman.y-initialPos.y;
					if(posBomberman.x+rectBomberman.getWidth()<posBlock.x) posBomberman.x=posBlock.x-rectBomberman.getWidth();
				}
			}
			break;
		case 0:
			if(rectBomberman.getMaxY()+posBomberman.y < rectBlock.getMinY()+posBlock.y+rectBlock.getHeight()*this.tolerance/100){
				posTemp.x-=rectBomberman.getWidth()-1;
				posTemp.y-=rectBomberman.getHeight();
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.y-=initialPos.x-posBomberman.x;
					if(posBomberman.y+rectBomberman.getHeight()<posBlock.y) posBomberman.y=posBlock.y-rectBomberman.getHeight();
				}
			}
			else if(rectBomberman.getMinY()+posBomberman.y > rectBlock.getMaxY()+posBlock.y-rectBlock.getHeight()*this.tolerance/100){
				posTemp.x-=rectBomberman.getWidth()-1;
				posTemp.y+=rectBlock.getHeight();
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.y+=initialPos.x-posBomberman.x;
					if(posBomberman.y>posBlock.y+rectBlock.getHeight()) posBomberman.y=posBlock.y+rectBlock.getHeight();
				}
			}
			break;
		case 180:
			if(rectBomberman.getMaxY()+posBomberman.y < rectBlock.getMinY()+posBlock.y+rectBlock.getHeight()*this.tolerance/100){
				posTemp.x-=rectBomberman.getWidth()-1;
				posTemp.y-=rectBomberman.getHeight();
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.y-=posBomberman.x-initialPos.x;
					if(posBomberman.y+rectBomberman.getHeight()<posBlock.y) posBomberman.y=posBlock.y-rectBomberman.getHeight();
				}
			}
			else if(rectBomberman.getMinY()+posBomberman.y > rectBlock.getMaxY()+posBlock.y-rectBlock.getHeight()*this.tolerance/100){
				posTemp.x-=rectBomberman.getWidth()-1;
				posTemp.y+=rectBlock.getHeight();
				bomberman.setPosition(posTemp);
				
				if(!this.manager.isCollideWith(bomberman, CTSCollision.Block)){
					posBomberman.y+=posBomberman.x-initialPos.x;
					if(posBomberman.y>posBlock.y+rectBlock.getHeight()) posBomberman.y=posBlock.y+rectBlock.getHeight();
				}
			}
			break;
		}
		
		bomberman.setPosition(posBomberman);
	}

	@Override
	public void update() {
		
	}
	
}
