package components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class Text extends AbstractComponent {
	private Font font;
	private int x, y;
	private String text;
	private Color color;
	
	public Text(GUIContext container, Font font) {
		super(container);
		
		this.font=font;
	}
	
	public void setText(String text){
		this.text=text;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setColor(Color color){
		this.color=color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	@Override
	public void render(GUIContext container, Graphics g) throws SlickException {
		this.font.drawString(this.x, this.y, this.text, this.color);
		
	}

	@Override
	public void setLocation(int x, int y) {
		this.x=x;
		this.y=y;		
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getWidth() {
		return this.font.getWidth(this.text);
	}

	@Override
	public int getHeight() {
		return this.font.getHeight(this.text);
	}
	
}
