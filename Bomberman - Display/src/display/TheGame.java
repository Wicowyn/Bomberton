package display;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import state.GamingState;
import state.MenuState;
import state.SelectGameState;

public class TheGame extends StateBasedGame{

	public TheGame(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		MenuState state=new MenuState();
		addState(state);
		GamingState gaming=new GamingState();
		addState(gaming);
		SelectGameState selectGame=new SelectGameState();
		addState(selectGame);
		selectGame.addListener(gaming);
		
	}

}
