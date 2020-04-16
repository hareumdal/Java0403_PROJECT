package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat extends Thread {
	private Socket withClientDM = null;
	private ServerSocket serverObject = null;
	private Socket withClientObject = null;
	private ServerCenter sc = null;
	private ServerChat nowSc = null;

	private InputStream reMsg = null;
	private OutputStream seMsg = null;

	private String nowId = null;

	ServerChat(Socket c, ServerSocket serverObject, ServerCenter sc) {
		this.withClientDM = c;
		this.serverObject = serverObject;
		this.sc = sc;
		this.nowSc = this;
	}

	public int getPortNum() {
	//	serverObject.getLocalPort()
		return serverObject.getLocalPort();
	}

	@Override
	public void run() {
		// send("port:" + withClientObject.getPort());
		receive();
	}

	public String getNowScId() {
		return nowId;
	}

	public void receiveObject() { // 일단 String을 받고 객체 보내 주는 걸로 쓰자
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					withClientObject = serverObject.accept();
					while (true) {
						reMsg = withClientObject.getInputStream();
						byte[] buffer = new byte[256];
						reMsg.read(buffer);
						String reMsg = new String(buffer);
						reMsg = reMsg.trim();
						System.out.println(reMsg);

						if (reMsg.indexOf("setList:") != -1) {
							nowId = reMsg.substring(reMsg.indexOf("/") + 1, reMsg.length());
						}
						sc.receiveClientMsg(reMsg, nowSc);
					}
				} catch (IOException e) {
					System.out.println("Client Logout");
				}
			}
		}).start();
	}

	private void receive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						reMsg = withClientDM.getInputStream();
						byte[] buffer = new byte[256];
						reMsg.read(buffer);
						String reMsg = new String(buffer);
						reMsg = reMsg.trim();
						System.out.println(reMsg);
						if (reMsg.indexOf("setList:") != -1) {
							nowId = reMsg.substring(reMsg.indexOf("/") + 1, reMsg.length());
						}
						sc.receiveClientMsg(reMsg, nowSc);
					}
				} catch (IOException e) {
					System.out.println("Client Logout");
				}
			}
		}).start();
	}

	public void send(String msg) {
		try {
			String sMsg = msg;
			seMsg = withClientDM.getOutputStream();
			seMsg.write(sMsg.getBytes());
		} catch (IOException e) {
			System.out.println("Client Logout");
		}
	}

//	public void sendObject() {
//		try {
//			seMsg = withClientObject.getOutputStream();
//			seMsg.write(("port:" + withClientObject.getPort()).getBytes());
//		} catch (IOException e) {
//			System.out.println("Client Logout");
//		}
//	}

	public void sendDB(byte[] resultByte) {
		try {
			seMsg = withClientObject.getOutputStream();
			seMsg.write(resultByte);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
