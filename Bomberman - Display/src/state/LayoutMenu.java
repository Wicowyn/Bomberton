package state;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class LayoutMenu extends AbstractComponent{
	private List<AbstractComponent> lesElementsMenu = new ArrayList<AbstractComponent>();
	private Image cursor;
	private int x, y;
	private int keyUp=Input.KEY_UP;
	private int keyDown=Input.KEY_DOWN;
	private int positionCursor = 0;
	private int space=5;
	
	public LayoutMenu(GUIContext container, Image cursor) {
		super(container);
		this.cursor=cursor;
	}
	
	public void setKeyUp(int key){
		this.keyUp=key;
	}
	
	public void setKeyDown(int key){
		this.keyDown=key;
	}
	
	public void setSpace(int space){
		this.space=space;
	
	}
	
	public void addElement(AbstractComponent component){
		this.lesElementsMenu.add(component);
		
	}
	
	public void removeElement(AbstractComponent component){
		this.lesElementsMenu.remove(component);
	}
	
	
	public int getHeight() {
		int height = (this.lesElementsMenu.size()-1)*this.space;
		
		for(AbstractComponent element : this.lesElementsMenu){
			height += element.getHeight();
		}
		if(height <= 0) return 0;
		else return height;
	}

	@Override
	public int getWidth() {
		if(this.lesElementsMenu.size() == 0) return 0;
		int width = this.space+cursor.getWidth();
		int i = 0;
		
		for(AbstractComponent element : this.lesElementsMenu){
			if(i < element.getWidth()){
				i = element.getWidth();
			}
		}
		width += i;
		
		return width;
	}

	@Override
	public int getX() {
		return this.x;
	}

	
	public int getY() {
		return this.y;
	}

	@Override
	public void render(GUIContext arg0, Graphics g) throws SlickException {
		int posY=this.y;
		
		for(int i=0; i<this.lesElementsMenu.size(); i++){
			AbstractComponent component=this.lesElementsMenu.get(i);
			
			if(i==this.positionCursor){
				this.cursor.draw(this.x, posY+component.getHeight()/2-this.cursor.getHeight()/2);
			}
			
			component.setLocation(this.x+this.cursor.getWidth()+this.space, posY);
			component.render(arg0, g);
			posY+=this.lesElementsMenu.get(i).getHeight()+this.space;
		}
		
	}

	@Override
	public void setLocation(int x, int y) {
		this.x=x;
		this.y=y;
	}

	@Override
	public void keyReleased(int key, char c){
		if(key==this.keyUp && this.positionCursor>0) this.positionCursor--;
		else if(key==this.keyDown && this.positionCursor<this.lesElementsMenu.size()-1) this.positionCursor++;
	}

}
