package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import frame.ChkFrame;
import frame.HomeFrame;
import frame.JoinFrame;
import frame.LoginFrame;

public class ClientChat {
	private Socket withServerDM = null;
	private Socket withServerObject = null;
	private int port = -1;

	private InputStream reMsg = null;
	private OutputStream seMsg = null;

	private LoginFrame loginF = null;
	private HomeFrame homeF = null;

	private String nowId = null;
	private String chk = null;
	private String receiveMsg = null;

	private ClientChat c = this;

	ClientChat(Socket s) {
		this.withServerDM = s;
		receive();
		login(this);
	}

	public String getMsg(String msg) {
		send(msg);
		receive();
		return getReceiveMessage();
	}

	public String getNowScId() {
		return nowId;
	}

	public String getNowCcId() {
		return nowId;
	}

	public void receive() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						reMsg = withServerDM.getInputStream();
						byte[] buffer = new byte[256];
						reMsg.read(buffer);
						String reMsg = new String(buffer);
						reMsg = reMsg.trim();
						System.out.println(reMsg);
						System.out.println("CC:::::" + reMsg);
						if (reMsg.indexOf("port") != -1) {
							port = Integer.valueOf(reMsg.substring(reMsg.indexOf(":") + 1, reMsg.length()));
							withServerObject = new Socket("10.0.0.53", port);
						} else {
							if (reMsg.contains("MyPage Delete true")) {
								System.exit(0);
							}
							ChkFrame chkF = new ChkFrame(reMsg, c);
							receiveMsg = reMsg;
						}
					}
				} catch (IOException e) {
					System.out.println("Server Out");
				}
			}
		}).start();
	}

	public String getReceiveMessage() {
		return receiveMsg;
	}

	public void send(String msg) {
		try {
			seMsg = withServerDM.getOutputStream();
			seMsg.write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void login(ClientChat cc) {
		loginF = new LoginFrame(cc);
	}

	public void Home(String chk, ClientChat cc) {
		if (chk.indexOf("Login true") != -1) {
			loginF.dispose();
			homeF = HomeFrame.getInstance(nowId, cc);
			homeF.Frame();
		} else if (chk.indexOf("Login false") != -1) {
			nowId = null;
		}
	}

	public Object getObject(String msg) {
		try {
			seMsg = withServerObject.getOutputStream();
			seMsg.write(msg.getBytes());
			reMsg = withServerObject.getInputStream();
			byte[] reBuffer = new byte[1024];
			reMsg.read(reBuffer);

			ByteArrayInputStream bis = new ByteArrayInputStream(reBuffer);
			ObjectInputStream ois = new ObjectInputStream(bis);

			//Object o = ois.readObject();
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}

//	public void sendRequestObject(String msg) {
//		try {
//			seMsg = withServerObject.getOutputStream();
//			seMsg.write(msg.getBytes());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public Object receiveObject() {
//		try {
//			reMsg = withServerObject.getInputStream();
//			byte[] reBuffer = new byte[1024];
//			reMsg.read(reBuffer);
//
//			ByteArrayInputStream bis = new ByteArrayInputStream(reBuffer);
//			ObjectInputStream ois = new ObjectInputStream(bis);
//
//			Object o = ois.readObject();
//
//			return o;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public void Join() {
		JoinFrame joinF = new JoinFrame(this);
	}

//	public void chkSet(String msg) {
//		try {
//			seMsg = withServerDM.getOutputStream();
//			seMsg.write(msg.getBytes());
//
//			reMsg = withServerDM.getInputStream();
//			byte[] buffer = new byte[256];
//			reMsg.read(buffer);
//			chk = new String(buffer);
//			chk = chk.trim();
//
//			System.out.println("/CheckMessage:" + chk);
//
//			if (chk.contains("MyPage Delete true")) {
//				System.exit(0);
//			}
//			ChkFrame chkF = new ChkFrame(chk, this);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public String getChkMessage() {
		return chk;
	}

	public void loginSet(String id, String pwd) {
		String user = "login:" + id + "/" + pwd;
		nowId = id;
		System.out.println("id:" + id);
		System.out.println("pwd:" + pwd);
		send(user);
	}

}
