import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");
		BasicConfigurator.configure();
		Logger logger=Logger.getLogger(Main.class);
    	logger.debug("Hello world.");
    	logger.info("What a beatiful day.");
    	
    }
}
