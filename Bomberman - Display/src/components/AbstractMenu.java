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

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

import state.LayoutMenuActionListener;

public abstract class AbstractMenu extends AbstractComponent{
	private List<LayoutMenuActionListener> listen = new ArrayList<LayoutMenuActionListener>();
	private int keyUp=Input.KEY_UP;
	private int keyDown=Input.KEY_DOWN;
	private int keyEnter = Input.KEY_ENTER;
	private int x, y;
	private int positionCursor = 0;
	
	protected List<AbstractComponent> components = new ArrayList<AbstractComponent>();
	protected Image cursor;
	protected int space=5;
	
	
	public AbstractMenu(GUIContext container, Image cursor) {
		super(container);
		this.cursor=cursor;
	}
	
	public void setKeyUp(int key){
		this.keyUp=key;
	}
	
	public void setKeyDown(int key){
		this.keyDown=key;
	}
	
	public void setKeyEnter(int key){
		this.keyEnter=key;
	}

	public void setSpace(int space){	
		this.space=space;
	}
	
	public void setPositionCursor(int position){
		this.positionCursor=position;
		
		if(this.positionCursor<0) this.positionCursor=0;
		else if(this.positionCursor>=this.components.size()) this.positionCursor=this.components.size()-1;
	}
	
	public int getPositionCursor(){
		return this.positionCursor;
	}
	
	public void addElement(AbstractComponent component){
		this.components.add(component);
	}
	
	public void removeElement(AbstractComponent component){
		this.components.remove(component);
	}
	
	public void removeAll(){
		this.components.clear();
	}
	
	public AbstractComponent getComponent(int position){
		return this.components.get(position);
	}
	
	public void addListener(LayoutMenuActionListener listener){
		this.listen.add(listener);
	}
	
	public void removeListener(LayoutMenuActionListener listener){
		this.listen.remove(listener);
	}
	
	protected void notifyFieldSelected(int index){
		for(LayoutMenuActionListener listener : this.listen ) listener.fieldSelected(index);
	}
	
	protected void notifyFieldOverfly(int index){
		for(LayoutMenuActionListener listener : this.listen ) listener.fieldOverfly(index);
	}
	
	@Override
	public void keyReleased(int key, char c){
		super.keyReleased(key, c);
		if(key == this.keyEnter) enterKey();
		if(key==this.keyUp && this.positionCursor>0) upKey();
		else if(key==this.keyDown && this.positionCursor<this.components.size()-1) downKey();
	}
	
	protected void upKey(){
		this.positionCursor--;
		notifyFieldOverfly(this.positionCursor);
	}
	
	protected void downKey(){
		this.positionCursor++;
		notifyFieldOverfly(this.positionCursor);
	}
	
	protected void enterKey(){
		notifyFieldSelected(this.positionCursor);
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
}
