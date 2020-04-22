package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import db.DmroomDTO;
import frame.ChkFrame;
import frame.HomeFrame;
import frame.JoinFrame;
import frame.LoginFrame;
import frame.MessageFrame;
import frame.OneDMFrame;

public class ClientChat { 
	// TODO: 클라이언트가 이 클래스 안에 있는 Frame들을 다 알아야 할까?
	
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

	private MessageFrame opendWindowDM = null;
	private OneDMFrame oneDMFrame = null;

	

	ClientChat(Socket s) {
		this.withServerDM = s;
		receive();
		login(this);
	}

	public MessageFrame getOpendWindowDM() {
		return opendWindowDM;
	}

	public void setOpendWindowDM(MessageFrame opendWindowDM) {
		this.opendWindowDM = opendWindowDM;
	}
	public OneDMFrame getOneDMFrame() {
		return oneDMFrame;
	}

	public void setOneDMFrame(OneDMFrame oneDMFrame) {
		this.oneDMFrame = oneDMFrame;
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
		// TODO: 서버에게서 받은 메세지를 체크프레임으로 들고 가서 처리(분석...)해주자 // 체크프레임으로 가져가서 처리하면 실시간 채팅이 불가
		// 채팅방이 만들어져 있지 않고 채팅창이 열려 있지 않으면 처리가 이상해짐
		// 탭을 선택했을 때 리프레쉬/db에 접근하게
		
		// 채팅방이 처음으로 만들어졌을 때 대화상대의 디엠탭에 다른 사람의 이름으로 뜨는 거 수정해야 함

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

						if (reMsg.indexOf("port") != -1) {
							port = Integer.valueOf(reMsg.substring(reMsg.indexOf(":") + 1, reMsg.length()));
							withServerObject = new Socket("10.0.0.53", port);
						} else if (reMsg.contains("[")) {
							if (getOpendWindowDM() == null) { 
								homeF.setDMTabAlertColor();
								//getOneDMFrame().reOneDM(reMsg, "color");
							} else if (getOpendWindowDM() != null){
								if (getOpendWindowDM().isVisible()) { // 채팅창이 열려 있으면 색깔 바꾸지ㄴㄴ
									getOpendWindowDM().setMsg(reMsg);
									getOneDMFrame().reOneDM(reMsg, "noncolor");
								} else { // 채팅창이 닫혀 있으면 색깔 바꾸긱ㄱ
									homeF.setDMTabAlertColor();
									getOneDMFrame().reOneDM(reMsg, "color");
								}
							} 
						} else {
							if (reMsg.contains("MyPage Delete true")) {
								System.exit(0);
							}
							receiveMsg = reMsg;
							
							ChkFrame chkF = new ChkFrame(reMsg, c);
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
			byte[] reBuffer = new byte[2048];
			reMsg.read(reBuffer);

			ByteArrayInputStream bis = new ByteArrayInputStream(reBuffer);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object o = ois.readObject();
			return o;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void Join() {
		JoinFrame joinF = new JoinFrame(this);
	}

//	public String getChkMessage() {
//		return chk;
//	}

	public void loginSet(String id, String pwd) {
		String user = "login:" + id + "/" + pwd;
		nowId = id;
		System.out.println("id:" + id);
		System.out.println("pwd:" + pwd);
		send(user);
	}

}
