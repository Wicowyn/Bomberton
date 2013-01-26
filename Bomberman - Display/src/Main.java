import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import display.TheGame;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");

		AppGameContainer app;
		try {
			app = new AppGameContainer(new TheGame("Bomberman"));
			app.setDisplayMode(1000, 600, false);
			app.setTargetFrameRate(30);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
