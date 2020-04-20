package frame;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub]
		
		
		
		String msg = "makedmRoom:id/nowId/ka";
		String targetID = msg.substring(msg.indexOf(":") + 1, msg.indexOf("/"));
		System.out.println(targetID);
		String reMsg = msg.substring(msg.indexOf("/") + 1, msg.length());
		System.out.println(reMsg);
		String myId = reMsg.substring(0, reMsg.indexOf("/"));
		System.out.println(myId);
		String roomname = reMsg.substring(reMsg.lastIndexOf("/") + 1, reMsg.length());
		System.out.println(roomname);
		
		
		
	}

}
