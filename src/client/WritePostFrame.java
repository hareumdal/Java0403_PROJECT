package client;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class WritePostFrame extends JFrame {
	
	private ClientChat nowCc = null;

	WritePostFrame(ClientChat cc) {
		super("WritePost");
		nowCc = cc;
		createWritePostFrame();
	}

	private void createWritePostFrame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 531, 444);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 10, 491, 274);
		contentPane.add(textPane);

		JButton btnAddPicture = new JButton("addPic");
		btnAddPicture.addActionListener(new ActionListener() { // 사진을 추가할 수 있는 퍼포먼스... 추가하기
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnAddPicture.setBounds(12, 307, 97, 23);
		contentPane.add(btnAddPicture);

		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() { // 뒤로가기 // 내 정보로 가기
			public void actionPerformed(ActionEvent e) {
				setClose();
			}
		});

		btnCancel.setBounds(425, 307, 78, 23);
		contentPane.add(btnCancel);

		JButton btnShare = new JButton("share");
		btnShare.addActionListener(new ActionListener() { // 디비에 저장하기
			public void actionPerformed(ActionEvent e) { // 내 창에서도 뜨고, 친구들 한테도 뜨도록 추가하기!
				nowCc.send("sharePost:" + nowCc.getNowId() + "/" + textPane.getText());
				setClose();
			}
		});
		btnShare.setBounds(325, 307, 88, 23);
		contentPane.add(btnShare);

		this.add(contentPane);

		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);

	}

	public void setClose() {
		this.setVisible(false);
	}

}
