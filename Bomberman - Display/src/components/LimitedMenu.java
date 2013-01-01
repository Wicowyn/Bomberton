package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class LimitedMenu extends AbstractMenu {
	//private List<AbstractComponent> showComponent=new ArrayList<AbstractComponent>();
	private int maxComponent=4;
	private int positionUpShow=0;
	
	public LimitedMenu(GUIContext container, Image cursor) {
		super(container, cursor);
		
	}
	
	public void setMaxShowComponent(int maxVal){
		this.maxComponent=maxVal;
		
		if(this.maxComponent<1) this.maxComponent=1;
	}

	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		int posY=getY();
		
		for(int i=this.positionUpShow; i<this.positionUpShow+this.maxComponent && i<this.components.size(); i++){
			AbstractComponent component=this.components.get(i);
			
			if(i==getPositionCursor()){
				this.cursor.draw(getX(), posY+component.getHeight()/2-this.cursor.getHeight()/2);
			}
			
			component.setLocation(getX()+this.cursor.getWidth()+this.space, posY);
			component.render(container, g);
			posY+=this.components.get(i).getHeight()+this.space;
		}
	}
	
	@Override
	protected void upKey(){
		super.upKey();
		
		if(getPositionCursor()<this.positionUpShow) this.positionUpShow=getPositionCursor();
	}
	
	@Override
	protected void downKey(){
		super.downKey();
		
		if(getPositionCursor()>this.positionUpShow+(this.maxComponent-1)) this.positionUpShow=getPositionCursor()-(this.maxComponent-1);
	}

	@Override
	public int getWidth() {
		if(this.components.size() == 0) return 0;
		int width = this.space+cursor.getWidth();
		int maxWidth = 0;
		
		for(int i=this.positionUpShow; i<this.positionUpShow+this.maxComponent && i<this.components.size(); i++){
			if(maxWidth < this.components.get(i).getWidth()){
				maxWidth = this.components.get(i).getWidth();
			}
		}
		width += maxWidth;
		
		return width;
	}

	@Override
	public int getHeight() {
		int height = (this.components.size()-1)*this.space;
		
		for(int i=this.positionUpShow; i<this.positionUpShow+this.maxComponent && i<this.components.size(); i++){
			height += this.components.get(i).getHeight();
		}
		
		return height<0 ? 0 : height;
	}

}
