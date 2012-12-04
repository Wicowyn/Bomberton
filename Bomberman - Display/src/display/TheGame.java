package display;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import state.MenuState;

public class TheGame extends StateBasedGame{

	public TheGame(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		MenuState state=new MenuState();
		addState(state);
	}
	public void keyPressed(int key, char c){
		super.keyPressed(key, c);
		 System.out.println(c);
		 switch(c){
		 	case 'z':
		 		
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
