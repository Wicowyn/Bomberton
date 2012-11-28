import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

import state.MenuState;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");
		MenuState menu = new MenuState();
		menu.gamecontainer(menu);
	}
	
}
