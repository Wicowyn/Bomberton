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

package state;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import components.LimitedMenu;
import components.Text;

public class SelectGameState extends BasicGameState {
	private List<SelectGame> listeners=new ArrayList<SelectGame>();
	private ListenMenu listenMenu=new ListenMenu();
	private StateBasedGame baseGame=null;
	private Image background=null;
	private LimitedMenu menu=null;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.baseGame=game;
		this.background=new Image("image/background-menu1.png");
		
		this.menu=new LimitedMenu(container, new Image("ressources/fleche.png"));
		this.menu.setLocation(480, 330);
		this.menu.setMaxShowComponent(6);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);

		List<String> list=GamingState.getPossibleGame();
		AngelCodeFont font=new AngelCodeFont("ressources/Latin.fnt", new Image("ressources/Latin.tga"));
		this.menu.addListener(this.listenMenu);
		
		for(String str : list){
			Text text=new Text(container,font);
			text.setText(str);
			text.setColor(Color.blue);
			this.menu.addElement(text);
		}
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		super.leave(container, game);
		this.menu.removeListener(this.listenMenu);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.background.draw();
		this.menu.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return PageName.SelectGame;
	}

	public void addListener(SelectGame listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(SelectGame listener){
		this.listeners.remove(listener);
	}
	
	protected void notifySelectGame(String game){
		for(SelectGame listener : this.listeners) listener.selectGame(game);
	}
	
	private class ListenMenu implements LayoutMenuActionListener{

		@Override
		public void fieldSelected(int index) {
			SelectGameState.this.notifySelectGame(((Text) SelectGameState.this.menu.getComponent(index)).getText());
			SelectGameState.this.baseGame.enterState(PageName.Gaming);
		}

		@Override
		public void fieldOverfly(int index) {
			
		}
		
	}
}
