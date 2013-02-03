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
