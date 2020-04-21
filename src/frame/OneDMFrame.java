package frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientChat;
import db.DirectMessageDTO;
import db.DmroomDTO;

public class OneDMFrame {

	private ClientChat nowCc = null;
	private String yourid = null;
	private JPanel oneUser = null;
	private JLabel lblUserID = null;
	private JLabel lblUserNewDM = null;
	private JLabel lblSendDate = null;

	public OneDMFrame(ClientChat nowCc, String yourid) {
		this.nowCc = nowCc;
		this.yourid = yourid;
	}

	public void reOneDM(String dm, String color) {
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String time1 = format1.format(time);
		
		dm = dm.substring(dm.indexOf("]")+1, dm.length());
		lblUserNewDM.setText(dm);
		
		lblSendDate.setText(time1);
		
		if (color.equals("noncolor")) {
		} else 	if (color.equals("color")){
			oneUser.setBackground(Color.RED); // todo: MessageFrame이 켜져 있다면 색깔을 바꾸지 말자!(?)
		}
		
	}

	public JPanel oneDM(DmroomDTO dm) {

		oneUser = new JPanel();
		oneUser.setBounds(12, 35, 410, 64);
		oneUser.addMouseListener(new MouseAdapter() { // 생성자가 가진 메소드 사용
			public void mouseClicked(MouseEvent e) { // 마우스 이벤트 감지
				if (e.getButton() == 1) { // 왼쪽 클릭
				}
				if (e.getClickCount() == 2) { // 더블 클릭
					// 메세지를 실제 주고 받을 수 있는 창을 열어준다
					MessageFrame messageFrame = new MessageFrame(nowCc, yourid, dm.getRoomname());
					nowCc.setOpendWindowDM(messageFrame);
					oneUser.setBackground(null);
				}
			}
		});

		ArrayList<Object> dmList = (ArrayList<Object>) nowCc
				.getObject("getList:directmessage/" + dm.getRoomname() + "/");
		DirectMessageDTO dmDTO = null;
		if (dmList.size() > 0) {
			for (int i = 0; i < dmList.size(); i++) {
				dmDTO = (DirectMessageDTO) dmList.get(i);
			}
		}

		lblUserID = new JLabel(yourid);

		lblUserNewDM = new JLabel(dmDTO.getMessage());

		lblSendDate = new JLabel(dmDTO.getDay());

		GroupLayout gl_oneUser = new GroupLayout(oneUser);
		gl_oneUser.setHorizontalGroup(gl_oneUser.createParallelGroup(Alignment.LEADING).addGroup(gl_oneUser
				.createSequentialGroup().addGap(12)
				.addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_oneUser.createSequentialGroup().addComponent(lblUserID).addGap(215)
								.addComponent(lblSendDate, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblUserNewDM, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE))));
		gl_oneUser.setVerticalGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_oneUser
						.createSequentialGroup().addGap(10).addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUserID).addComponent(lblSendDate))
						.addGap(14).addComponent(lblUserNewDM)));
		oneUser.setLayout(gl_oneUser);
		return oneUser;

	}

}
