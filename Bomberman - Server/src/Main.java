import java.io.IOException;

import mapping.Engine;

import org.jdom2.JDOMException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Engine engine=new Engine();
		try {
			engine.loadGame("Classic");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}
