package state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import display.KeyAction;

public class MenuState extends BasicGameState implements KeyAction{
	private static final int ID = 1;
	//bouton du menu
	private MouseOverArea quit;
	private MouseOverArea play;
	private MouseOverArea option;
	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
			
			
	}
	

	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString("Menu principale",300,50);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
	}

	@Override
	// ID de la page
	public int getID() {
		return ID;
	}



	@Override
	public void up() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void down() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void left() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void right() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void enter() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void action1() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void action2() {
		// TODO Auto-generated method stub
		
	}
	
}

