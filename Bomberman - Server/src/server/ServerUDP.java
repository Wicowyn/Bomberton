package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Vector;

import network.Trame;

public class ServerUDP implements Runnable {
	public Vector<Trame> bufPacket=new Vector<Trame>();
	private DatagramSocket sock;
	
	void listenTo(int port) throws SocketException{
		if(this.sock!=null){
			this.sock.close();
		}
		
		this.sock=new DatagramSocket(port);
	}

	@Override
	public void run() {
		byte[] buffer;
		ByteArrayInputStream array = null;
		ObjectInputStream in = null;
		
		while(true){
			buffer=new byte[1000];
			DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
			
			try {
				this.sock.receive(packet);
				
				array=new ByteArrayInputStream(buffer);
				in=new ObjectInputStream(array);
				
				Trame trame=(Trame) in.readObject();
				this.bufPacket.add(trame);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally{
				if(array!=null)
					try {
						array.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if(in!=null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			
		}
		
	}
	
}
