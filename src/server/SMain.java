package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket serverDM = new ServerSocket();
		ServerSocket serverObject = new ServerSocket();
		
		Socket withClientDM = null;
		//Socket withClientObject = null;
		
		serverDM.bind(new InetSocketAddress("10.0.0.53", 9999));
		serverObject.bind(new InetSocketAddress("10.0.0.53", 8888));
		
		ServerCenter sc = new ServerCenter();
		
		while(true) {
			System.out.println("Server Waiting");
			withClientDM = serverDM.accept();
			System.out.println("Client : " + withClientDM.getInetAddress());
			ServerChat ServerChat = new ServerChat(withClientDM, serverObject, sc);
			sc.addSc(ServerChat);
			ServerChat.start();
		}

	}

}
