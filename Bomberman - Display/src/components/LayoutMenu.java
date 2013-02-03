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

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class LayoutMenu extends AbstractMenu{

	public LayoutMenu(GUIContext container, Image cursor) {
		super(container, cursor);
		
	}
	
	@Override
	public int getHeight() {
		int height = (this.components.size()-1)*this.space;
		
		for(AbstractComponent element : this.components){
			height += element.getHeight();
		}
		
		return height<0 ? 0 : height;
	}

	@Override
	public int getWidth() {
		if(this.components.size() == 0) return 0;
		int width = this.space+cursor.getWidth();
		int maxWidth = 0;
		
		for(AbstractComponent element : this.components){
			if(maxWidth < element.getWidth()){
				maxWidth = element.getWidth();
			}
		}
		width += maxWidth;
		
		return width;
	}

	@Override
	public void render(GUIContext arg0, Graphics g) throws SlickException {
		int posY=getY();
		
		for(int i=0; i<this.components.size(); i++){
			AbstractComponent component=this.components.get(i);
			
			if(i==getPositionCursor()){
				this.cursor.draw(getX(), posY+component.getHeight()/2-this.cursor.getHeight()/2);
			}
			
			component.setLocation(getX()+this.cursor.getWidth()+this.space, posY);
			component.render(arg0, g);
			posY+=this.components.get(i).getHeight()+this.space;
		}
	}

	
}
