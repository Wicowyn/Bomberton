package server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import network.Trame;

public class ClientTCP implements Runnable{
	private List<NetworkEvent> listeners=new ArrayList<NetworkEvent>();
	private Socket sock;
	private int ID; //hash du string de l'ip;
			
	public ClientTCP(Socket sock){
		this.sock=sock;
		this.ID=this.sock.getInetAddress().getHostAddress().hashCode();
	}

	@Override
	public void run() {
		try {
			ObjectInputStream in=new ObjectInputStream(this.sock.getInputStream());
			while(true){
				try {
					Trame trame=(Trame) in.readObject();
					//buf.add(trame);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InterruptedIOException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void close(){
		try {
			this.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public int getID(){
		return this.ID;
	}
	
	public void addListener(NetworkEvent listener){
		
	}
}