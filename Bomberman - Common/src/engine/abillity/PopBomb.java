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

import engine.Abillity;
import engine.ActionBang;
import engine.Entity;
import engine.entity.Bomb;

public abstract class PopBomb extends Abillity {
	protected int maxBomb=1;
	protected int currentBomb=0;
	protected int power=1;
	
	public PopBomb(Entity owner) {
		super(owner);
	}
	
	public int getMaxBomb() {
		return maxBomb;
	}

	public void setMaxBomb(int maxBomb) {
		this.maxBomb = maxBomb;
	}

	public int getCurrentBomb() {
		return currentBomb;
	}

	public void setCurrentBomb(int currentBomb) {
		this.currentBomb = currentBomb;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power= power<1 ? 1 : power;
	}
	
	protected void popBomb(){
		Bomb bomb=new Bomb(this.owner.getEngine());
		bomb.setPosition(this.owner.getPosition());
		bomb.setDirection(this.owner.getDirection());
		bomb.setOwner(this.owner);
		
		for(Abillity abillity : bomb.getAbillities()){
			if(abillity instanceof Bang){
				Bang bang=(Bang) abillity;
				bang.setPower(this.power);
				bang.startDetonation();
				bang.addListener(new ListenBang());
			}
		}
		
		this.owner.getEngine().addEntityToBuff(bomb);
		this.currentBomb++;
	}
	
	private class ListenBang implements ActionBang{

		@Override
		public void bang(Entity entity) {
			PopBomb.this.currentBomb--;
			
		}
		
	}

}
