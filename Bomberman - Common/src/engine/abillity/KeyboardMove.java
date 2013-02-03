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

package engine.abillity;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import engine.Entity;

public class KeyboardMove extends Move{
	private Input input=null;
	private int keyUp=Input.KEY_UP;
	private int keyRight=Input.KEY_RIGHT;
	private int keyDown=Input.KEY_DOWN;
	private int keyLeft=Input.KEY_LEFT;
	
	
	public KeyboardMove(Entity owner, Input input) {
		super(owner);
		this.input=input;
	}
	
	@Override
	public void update(int delta) {
		if((this.input.isKeyDown(this.keyUp) == this.input.isKeyDown(this.keyDown)) && (this.input.isKeyDown(this.keyRight) == this.input.isKeyDown(this.keyLeft))) return;
		
		if(this.input.isKeyPressed(this.keyDown)) this.owner.setDirection(90);
		else if(this.input.isKeyPressed(this.keyLeft)) this.owner.setDirection(180);
		else if(this.input.isKeyPressed(this.keyUp)) this.owner.setDirection(-90);
		else if(this.input.isKeyPressed(this.keyRight)) this.owner.setDirection(0);
		
		Vector2f position=this.owner.getPosition();
		float hip=this.speed*delta;
		
		position.x+=hip*Math.cos(Math.toRadians(this.owner.getDirection()));
		position.y+=hip*Math.sin(Math.toRadians(this.owner.getDirection()));
		
		this.owner.setPosition(position);
	}
	
	public void setKeyUp(int key){
		this.keyUp=key;
	}
	
	public void setKeyRight(int key){
		this.keyRight=key;
	}
	
	public void setKeyDown(int key){
		this.keyDown=key;
	}
	
	public void setKeyLeft(int key){
		this.keyLeft=key;
	}
}
