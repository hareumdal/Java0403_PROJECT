package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.DAOCenter;
import db.FriendDTO;
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

	private void sendObject(Object o) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);

			os.writeObject(o);

			byte[] resultByte = bos.toByteArray();
			nowSc.sendDB(resultByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveClientMsg(String msg, ServerChat sc) {
		nowSc = sc;
		this.msg = msg;

		if (msg.indexOf("login:") != -1) { // login/
			login(msg);
		} else if (msg.indexOf("join:") != -1) {
			join(msg);
		} else if (msg.indexOf("idCheck:") != -1) {
			idChk = idCheck(msg);
		} else if (msg.indexOf("setList:") != -1) {
			setList(msg);
		} else if (msg.indexOf("profile:") != -1) {
			viewProfile(msg);
		} else if (msg.indexOf("myPage:") != -1) {
			 myPage(msg);
			// viewMyPage(msg);
			//if (nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf(":") + 1, msg.length()))) {
			//	sendObject(Dc.select("member", nowSc.getNowScId()));
			//}
		} else if (msg.indexOf("follow:") != -1) {
			followFriend(msg);
		} else if (msg.indexOf("sharePost:") != -1) {
			sharePost(msg);
		} else if (msg.indexOf("addFriend:") != -1) { // 친구 목록은 setList:로 얻쟈!!
			//addFriend();
		} else if (msg.indexOf("postList:") != -1) { // 원하는 리스트를 받아오기
			ArrayList<String> info = new ArrayList<>();
			String tName = msg.substring(msg.indexOf(":") + 1, msg.indexOf("/"));
			String id = msg.substring(msg.indexOf("/") + 1, msg.lastIndexOf("/"));
			String postNum = msg.substring(msg.lastIndexOf("/") + 1, msg.length());
			System.out.println(tName+":"+id+":"+postNum);
			info.add(id);
			info.add(postNum);			
			sendObject(Dc.getDBES(tName, info));
		}

	}
	private void sharePost(String msg) { // 작성자와 팔로워들에게 보내주기
		String reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String post = reMsg.substring(reMsg.lastIndexOf("/") + 1, reMsg.length());

		PostDTO pDTO = new PostDTO();
		
		pDTO.setId(id);
		pDTO.setText(post);

		Dc.insert("post", pDTO); // 작성글 받아서 DB에 저장
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
	private void followFriend(String msg) {
		// TODO Auto-generated method stub
		String myId = msg.substring(msg.indexOf(":") + 1, msg.indexOf("/"));
		String yourId = msg.substring(msg.indexOf("/") + 1, msg.length());

		if (msg.contains("add")) {
			FriendDTO f = new FriendDTO();
			f.setMyId(myId);
			f.setyourId(yourId);

			if (Dc.insert("friend", f)) {
				nowSc.send("Follow true");
			} else {
				nowSc.send("Follow false");
			}
		} else if (msg.contains("del")) {
			// follow 풀기

		}
	}

	private void myPage(String msg) {
		// TODO Auto-generated method stub
		if (msg.indexOf("update") != -1) {
			String reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());
			String id = reMsg.substring(0, reMsg.indexOf("/"));
			String pwd = reMsg.substring(reMsg.indexOf("/") + 1, reMsg.lastIndexOf("/"));
			String phone = reMsg.substring(reMsg.lastIndexOf("/") + 1, reMsg.length());

			if (phone.length() == 0) {
				nowSc.send("Wrong PhoneNumber");
			} else {
				if (phone.length() > 11 || phone.substring(0, 3).indexOf("01") == -1) {
					nowSc.send("Wrong PhoneNumber");
				} else {
					MemberDTO a = (MemberDTO) Dc.select("member", phone);

					if (a == null) {
						updateProfile_my(id, pwd, phone);
					} else {
						if (a.getId().equals(id)) {
							updateProfile_my(id, pwd, phone);
						} else {
							nowSc.send("Same PhoneNumber");
						}
					}
				}
			}
		} else if (msg.indexOf("delete") != -1) {
			if (msg.indexOf("sure") != -1) {
				if (Dc.delete("member", nowSc.getNowScId())) {
					for (ServerChat i : sList) {
						if (i.getNowScId().equals(nowSc.getNowScId())) {
							sList.remove(i);
							break;
						}
					}
					nowSc.send("MyPage Delete true");
				}
			} else {
				nowSc.send("MyPage Delete hope");
			}
		} else {
			try {
				String id = msg.substring(msg.indexOf(":") + 1, msg.length());
				sendObject(Dc.select("member", id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateProfile_my(String id, String pwd, String phone) {
		MemberDTO m = new MemberDTO();
		m.setId(id);
		m.setPwd(pwd);
		m.setPhone(phone);

		if (Dc.update("member", m)) {
			nowSc.send("MyPage Update true");
		} else {
			nowSc.send("MyPage Update false");
		}
	}

	private void viewProfile(String msg) {
		// TODO Auto-generated method stub
		String id = msg.substring(msg.indexOf(":") + 1, msg.length());
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);

			os.writeObject(Dc.select("member", id));

			byte[] resultByte = bos.toByteArray();
			nowSc.sendDB(resultByte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setList(String msg) {
		// TODO Auto-generated method stub
		String tName = null;
		String id = null;

		tName = msg.substring(msg.indexOf(":") + 1, msg.lastIndexOf("/"));
		id = msg.substring(msg.indexOf("/") + 1, msg.length());

		if (nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf("/") + 1, msg.length()))) {
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);

				switch (tName) {
				case "member":
					os.writeObject(Dc.getDB("member"));
					break;
				case "post":
					os.writeObject(Dc.getDB("post", id));
					break;
				case "favorite":
					os.writeObject(Dc.getDB("favorite"));
					break;
				case "friend":
					os.writeObject(Dc.getDB("friend", id));
					break;
				}

				byte[] resultByte = bos.toByteArray();
				nowSc.sendDB(resultByte);
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
		} else if (id.length() == 0) {
			nowSc.send("Please input ID");
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

	private void login(String msg) {
		// TODO Auto-generated method stub
		String reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/") + 1, reMsg.length());

		MemberDTO m = new MemberDTO();
		m.setId(id);
		m.setPwd(pwd);

		if (Dc.select("member", m)) {
			nowSc.send("Login true");
		} else {
			nowSc.send("Login false");
		}
	}

	private void join(String msg) {
		String reMsg = msg.substring(msg.indexOf(":") + 1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/") + 1, reMsg.lastIndexOf("/"));
		String phone = reMsg.substring(reMsg.lastIndexOf("/") + 1, reMsg.length());

		if (idChk == true) {
			if (phone.length() > 11) {
				nowSc.send("Wrong PhoneNumber");
			} else {
				if (phone != null && phone.substring(0, 3).indexOf("01") == -1) {
					nowSc.send("Wrong PhoneNumber");
				} else {
					MemberDTO m = new MemberDTO();
					m.setId(id);
					m.setPwd(pwd);
					m.setPhone(phone);

					if (Dc.select("member", m)) {
						if (Dc.insert("member", m)) {
							nowSc.send("Join true");
						} else {
							nowSc.send("Join false");
						}
					} else {
						nowSc.send("Same PhoneNumber");
					}
				}
			}
		} else if (idChk == false) {
			nowSc.send("Wrong Id or Do not Id Check");
		}
	}

}
