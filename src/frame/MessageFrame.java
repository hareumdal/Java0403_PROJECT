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
	// private String nowId = null;
	private String yourid = null;

	public MessageFrame(ClientChat nowCc, String yourid, String roomname) {
		super(nowCc.getNowCcId());
		this.nowCc = nowCc;
		this.yourid = yourid;

		openChatingRoom(yourid, roomname);

	}

	JTextPane textPane = null;

	private void openChatingRoom(String nowId, String roomname) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 120, 428, 306);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(450, 600));

		JTextField textField = new JTextField();
		textField.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() > 0) {
					String msg = textField.getText();
					textField.setText("");
					nowCc.send("senddm:" + yourid + "/t" + nowCc.getNowCcId() + "/" + msg + "/" + roomname);
					setMsg(msg);
				}
			}
		});

		textPane = new JTextPane();
		textPane.setBounds(12, 5, 383, 220);
		textPane.setEnabled(false);

		System.out.println("MessageF:::" + roomname);
		ArrayList<Object> dmList = (ArrayList<Object>) nowCc.getObject("getList:directmessage/" + roomname);
		String msg = "";
		if (dmList.size() > 0) {
			for (int i = 0; i < dmList.size(); i++) {
				DirectMessageDTO dm = (DirectMessageDTO) dmList.get(i);
				msg = msg + "[" + dm.getId() + "] " + dm.getMessage() + " " + dm.getDay() + "\n";
			}
			setMsg(msg);
		}

		scrollPane.setViewportView(textPane);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(7)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
								.addGap(12)
								.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))));
		contentPane.setLayout(gl_contentPane);

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void setMsg(String reMsg) {
		System.out.println("");
		reMsg = reMsg + "\n";
		Document document = textPane.getDocument();
		try {
			document.insertString(document.getLength(), reMsg, null /* or attrubute set */);
		} catch (BadLocationException ble) {
			System.err.println("Bad Location. Exception:" + ble);
		}
	}

}
