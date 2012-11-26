package server;

import java.io.IOException;
import java.net.ServerSocket;
/*salut yapiti*/
public class Engine {
	private ServerSocket socket;

	public Engine() throws IOException {
		this.socket=new ServerSocket(4789, 8);
	}

}
