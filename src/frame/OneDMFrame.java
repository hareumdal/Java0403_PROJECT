package frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientChat;
import db.DmroomDTO;

public class OneDMFrame {

	private ClientChat nowCc = null;
	private String yourid = null;
	
	public OneDMFrame(ClientChat nowCc, String yourid) {
		this.nowCc = nowCc;
		this.yourid = yourid;
	}
	
	
	public JPanel oneDM(DmroomDTO dm) {
		
		JPanel oneUser = new JPanel();
		oneUser.setBounds(12, 35, 410, 64);
		oneUser.addMouseListener(new MouseAdapter() { // 생성자가 가진 메소드 사용
			public void mouseClicked(MouseEvent e) { // 마우스 이벤트 감지
				if (e.getButton() == 1) { // 왼쪽 클릭
				}
				if (e.getClickCount() == 2) { // 더블 클릭
					// 메세지를 실제 주고 받을 수 있는 창을 열어준다
					MessageFrame messageFrame = new MessageFrame(nowCc, yourid, dm.getRoomname());
					nowCc.setOpendWindowDM(messageFrame);
				}
			}
		});
		
		
		JLabel lblUserID = new JLabel(yourid);
		
		JLabel lblUserNewDM = new JLabel("메세지를 확인하세요!");
		
		JLabel lblSendDate = new JLabel("sendDate");
		
		GroupLayout gl_oneUser = new GroupLayout(oneUser);
		gl_oneUser.setHorizontalGroup(
			gl_oneUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_oneUser.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_oneUser.createSequentialGroup()
							.addComponent(lblUserID)
							.addGap(215)
							.addComponent(lblSendDate, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblUserNewDM, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)))
		);
		gl_oneUser.setVerticalGroup(
			gl_oneUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_oneUser.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUserID)
						.addComponent(lblSendDate))
					.addGap(14)
					.addComponent(lblUserNewDM))
		);
		oneUser.setLayout(gl_oneUser);
		
		
		return oneUser;
		
	}
}
