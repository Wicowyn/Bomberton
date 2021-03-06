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

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import components.LayoutMenu;

public class MenuState extends BasicGameState{
	private StateBasedGame baseGame;
	private Map<MouseOverArea, Integer> mapMenuPage=new HashMap<MouseOverArea, Integer>();
	private ObserveLayout listenMenu=new ObserveLayout();
	private Input input;
	private Image menu;
	private LayoutMenu menu2;
	
	private Animation courir;
	private SpriteSheet perso;

	
	
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		
		this.baseGame=arg1;
		
		input = container.getInput();
			
		menu = new Image("image/background-menu1.png");
		
		menu2=new LayoutMenu(container, new Image("ressources/fleche.png"));
		menu2.setLocation(480, 330);
			
		MouseOverArea play = new MouseOverArea(container,new Image("image/element1.png"), 0, 0);
		play.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		play.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
		menu2.addElement(play);
		this.mapMenuPage.put(play, PageName.SelectGame);
		
		MouseOverArea option = new MouseOverArea(container,new Image("image/element2.png"), 0, 0);
		option.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		option.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
		menu2.addElement(option);
		
		MouseOverArea quit = new MouseOverArea(container,new Image("image/element3.png"), 0, 0);
		quit.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		quit.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
		menu2.addElement(quit);
		//this.mapMenuPage.put(quit, value)
		
		perso = new SpriteSheet("image/KarabounChicken.gif", 80,80); //80 = taille de l'image
		
		courir = new Animation(perso, 0,0,0,3,true, 100, false);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.menu2.addListener(this.listenMenu);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.menu2.removeListener(this.listenMenu);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		menu.draw();
		g.drawString("Menu principale",500,280);
		menu2.render(container, g);
		courir.draw(200+200,200);
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
			
			if(this.input.isKeyDown(Input.KEY_Z)) arg1.enterState(PageName.Gaming);
		
			if(this.input.isKeyDown(Input.KEY_ESCAPE)){
				container.exit();
			}
			if(this.input.isKeyDown(Input.KEY_D)){
				courir.setCurrentFrame(3);
			}
			if(this.input.isKeyDown(Input.KEY_Q)){
				courir.setCurrentFrame(2);
			}
		
	}

	@Override
	public int getID() {
		return PageName.Menu;
	}
	
	private class ObserveLayout implements LayoutMenuActionListener{

		@Override
		public void fieldSelected(int index) {
			MenuState.this.baseGame.enterState(MenuState.this.mapMenuPage.get(MenuState.this.menu2.getComponent(index)));
			
		}

		@Override
		public void fieldOverfly(int index) {
			
			
		}
	}

}

