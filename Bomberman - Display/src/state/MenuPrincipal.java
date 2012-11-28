package state;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class MenuPrincipal {

}
public void setTitle(java.lang.String Bomberman-Menu){}

public static void main(String[] args){
	
	try {
		AppGameContainer app = new AppGameContainer(new MenuState);
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(30);
		app.start();
	} catch (SlickException e) {
		e.printStackTrace();
	}		
}
