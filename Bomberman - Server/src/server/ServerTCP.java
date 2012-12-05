package server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import network.Trame;

public class ServerTCP implements Runnable{
	public Vector<Trame> buf=new Vector<Trame>();
	private ServerSocket server;
	private Map<ClientTCP, Thread> map=new HashMap<ClientTCP, Thread>();
	
	public void listenTo(int port) throws IOException{
		close();
		this.server=new ServerSocket(port);
	}
	
	@Override
	public void run() {
		while(true){
			try {
				ClientTCP sock=new ClientTCP(server.accept());
				Thread thread=new Thread(sock);
				thread.start();
				this.map.put(sock, thread);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void close(){
		for(ClientTCP key : this.map.keySet()){
			this.map.get(key).interrupt();
			key.close();
		}
		
		this.map.clear();
		
		try {
			this.server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
