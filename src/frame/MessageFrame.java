package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import client.ClientChat;
import db.DirectMessageDTO;
import db.DmroomDTO;

public class MessageFrame extends JFrame {

	private ClientChat nowCc = null;
	private String nowId = null;
	private String yourid = null;

	public MessageFrame(ClientChat nowCc, String nowId, String roomname) {
		this.nowCc = nowCc;
		this.nowId = nowId;

		openChatingRoom(nowId, roomname);

	}

	JTextPane textPane = null;

	private void openChatingRoom(String nowId, String roomname) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 120, 480, 285);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(450, 600));

		textPane = new JTextPane();
		textPane.setBounds(0, 0, 400, 200);
		
		
		scrollPane.setViewportView(textPane);
	
		
		// 두 번 클릭했었을 때 처음 뙇! 창이 뜰 때 디비에 있던 dm 관련 정보를 가져옴
		// =>
		// 이 방에 있는 사람들 목록을 불러오고 대화 목록에 추가하기
		
		
		
		
		
		ArrayList<Object> dmRommList = (ArrayList<Object>) nowCc.getObject("getList:dmroom/" + nowId + "/" + roomname + "\n");
		// 이 방에서 대화한 정보를 모두 불러옴
		if (dmRommList.size() > 0) {
			for (int i = 0; i < dmRommList.size(); i++) {
				DmroomDTO dmroom = (DmroomDTO) dmRommList.get(i);
				if (dmroom.getId().equals(nowId)) {
				} else {
					yourid = dmroom.getId();
				}
			}
		} else {
			System.out.println("정보 없음");
		}
		
		ArrayList<Object> dmList = (ArrayList<Object>) nowCc.getObject("getList:directmessage/" + roomname);
		String msg = "";
		if (dmList.size() > 0) {
			for (int i = 0; i < dmList.size(); i++) {
				DirectMessageDTO dm = (DirectMessageDTO) dmList.get(i);
				msg = msg + "[" + dm.getId() + "] " + dm.getMessage() + " " + dm.getDay() + "\n";
				setMsg(msg);
			}
		}
		//textPane.setText(msg);

		JButton btnSend = new JButton("Send");
		JTextField textField = new JTextField();
		textField.setColumns(10);

		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (textField.getText().length() > 0) {
					String msg = textField.getText();
					textField.setText("");
					nowCc.send("senddm:" + yourid + "/t" + nowId + "/" + msg + "/" + roomname);
					setMsg(msg);
				}
			}
		});

		scrollPane.add(textPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE).addGap(1)
						.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))));
		contentPane.setLayout(gl_contentPane);

		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}

	public void setMsg(String reMsg) {
		reMsg = reMsg + "\n";
		Document document = textPane.getDocument();

		try {
			document.insertString(document.getLength(), reMsg, null /* or attrubute set */);

		} catch (BadLocationException ble) {
			System.err.println("Bad Location. Exception:" + ble);
		}
	}

}
