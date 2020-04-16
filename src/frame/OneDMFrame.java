package frame;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientChat;
import db.DirectMessageDTO;

import javax.swing.GroupLayout.Alignment;

public class OneDMFrame {

	
	
	public void oneDM(JPanel oneUserPanel, DirectMessageDTO dm, ClientChat nowCc) {
		JPanel oneUser = new JPanel();
		oneUser.setBounds(12, 35, 410, 64);
		oneUserPanel.add(oneUser);
	
		JLabel lblUserID = new JLabel("userID");
		
		JLabel lblUserNewDM = new JLabel("userNewDM");
		
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
	}
}
