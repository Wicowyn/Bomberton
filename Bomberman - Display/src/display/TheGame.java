package display;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import state.GamingState;
import state.MenuState;
import state.PageName;

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
		
	}
	public void keyPressed(int key, char c){
		super.keyPressed(key, c);
		 switch(c){
		 	case 'z':
		 		enterState(PageName.Gaming);
		 	break;
		 	case 'd':
		 	
		 	break;
		 	case 's':
		 	break;
		 	case 'q':
		 	break;
		 
		 
		 }
	}

}
