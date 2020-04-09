package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.DAOCenter;
import db.MemberDTO;
import db.PostDTO;

public class ServerCenter {
	private DAOCenter Dc = null;
	private ServerChat nowSc = null;

	private ArrayList<ServerChat> sList = new ArrayList<>();

	private boolean idChk = false;
	private String msg = null;
	private String reMsg = null;

	ServerCenter() {
		Dc = DAOCenter.getInstance();
	}

	public void addSc(ServerChat sc) {
		sList.add(sc);
	}

	public void receiveClientMsg(String msg, ServerChat sc) {
		nowSc = sc;
		this.msg = msg;
		// reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());

		if (msg.indexOf("login:") != -1) {
			Login(msg);
		} else if (msg.indexOf("join:") != -1) {
			Join(msg);
		} else if (msg.indexOf("idCheck:") != -1) {
			idChk = idCheck(msg);
		} else if (msg.indexOf("setList:") != -1) {
			setList(msg);
		} else if (msg.indexOf("myPage:") != -1) {
			// viewMyPage(msg);
			if (nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf(":") + 1, msg.length()))) {
				sendObject(Dc.select("member", nowSc.getNowScId()));
			}
		} else if (msg.indexOf("sharePost:") != -1) {
			sharePost(msg);
		} else if (msg.indexOf("addFriend:") != -1) { // 친구 목록은 setList:로 얻쟈!!
			addFriend();
		} else if (msg.indexOf("postList:") != -1) { // 원하는 리스트를 받아오기
			sendObject(getList());
		}
	}

	private void addFriend() {

	}

	private void sharePost(String msg) { // 작성자와 팔로워들에게 보내주기
		String reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String post = reMsg.substring(reMsg.lastIndexOf("/") + 1, reMsg.length());
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String day = format1.format(System.currentTimeMillis());

		PostDTO pDTO = new PostDTO();
		pDTO.setDay(day);
		pDTO.setId(id);
		pDTO.setText(post);

		Dc.insert("post", pDTO); // 작성글 받아서 DB에 저장
	}

	private void sendObject(Object object) { // 현재 글을 가져다 주는 거 // homeframe 창이 열릴 때 list를 받는 것
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(object);
			byte[] resultByte = bos.toByteArray();
			nowSc.sendList(resultByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void viewMyPage(String msg) {
		// TODO Auto-generated method stub
		if (nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf(":") + 1, msg.length()))) {
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);
				os.writeObject(Dc.select("member", nowSc.getNowScId()));
				byte[] resultByte = bos.toByteArray();
				nowSc.sendList(resultByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Object getList() {
		// TODO Auto-generated method stub
		String tName = msg.substring(msg.indexOf(":") + 1, msg.lastIndexOf("/"));
		if (nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf("/") + 1, msg.length()))) {
			try {
				switch (tName) {
				case "member":
					return Dc.getDB("member", nowSc.getNowScId());
				case "post":
					return Dc.getDB("post", nowSc.getNowScId());
				case "favorite":
					return Dc.getDB("favorite", nowSc.getNowScId());
				case "friend":
					return Dc.getDB("friend", nowSc.getNowScId());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	private void setList(String msg) {
		// TODO Auto-generated method stub
		String tName = msg.substring(msg.indexOf(":") + 1, msg.lastIndexOf("/"));
		if (nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf("/") + 1, msg.length()))) {
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);
				switch (tName) {
				case "member":
					os.writeObject(Dc.getDB("member"));
					break;
				case "post":
					os.writeObject(Dc.getDB("post"));
					break;
				case "favorite":
					os.writeObject(Dc.getDB("favorite"));
					break;
				case "friend":
					os.writeObject(Dc.getDB("friend"));
					break;
				}
				byte[] resultByte = bos.toByteArray();
				nowSc.sendList(resultByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean idCheck(String msg) {
		// TODO Auto-generated method stub
		String id = msg.substring(msg.indexOf(":") + 1, msg.length());

		if (id.length() > 8) {
			nowSc.send("In eight");
		} else {
			MemberDTO m = new MemberDTO();
			m.setId(id);

			if (Dc.select("member", m)) {
				nowSc.send("Same Id");
			} else {
				nowSc.send("Possible Id");
				return true;
			}
		}
		return false;
	}

	private void Login(String msg) {
		// TODO Auto-generated method stub
		String reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/") + 1, reMsg.length());

		MemberDTO m = new MemberDTO();
		m.setId(id);
		m.setPwd(pwd);

		if (Dc.select("member", m)) {
			nowSc.send("login true");
		} else {
			nowSc.send("login false");
		}
	}

	private void Join(String msg) {
		String reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/") + 1, reMsg.lastIndexOf("/"));
		String phone = reMsg.substring(reMsg.lastIndexOf("/") + 1, reMsg.length());

		if (idChk == true) {
			MemberDTO m = new MemberDTO();
			m.setId(id);
			m.setPwd(pwd);
			m.setPhone(phone);

			if (Dc.select("member", m)) {
				if (Dc.insert("member", m)) {
					nowSc.send("join true");
				} else {
					nowSc.send("join false:DBSave");
				}
			} else {
				nowSc.send("Same PhoneNumber");
			}
		} else if (idChk == false) {
			nowSc.send("Wrong Id or Do not Id Check");
		}
	}

}
