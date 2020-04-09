package wrting_Test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class writing_Test extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;
	private JButton btnAddPicture, btnCancel, btnShare;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					writing_Test frame = new writing_Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public writing_Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textPane = new JTextPane();
		textPane.setBounds(12, 10, 491, 274);
		contentPane.add(textPane);

		btnAddPicture = new JButton("addPic");
		btnAddPicture.addActionListener(new ActionListener() { // 사진을 추가할 수 있는 퍼포먼스... 추가하기
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnAddPicture.setBounds(12, 307, 97, 23);
		contentPane.add(btnAddPicture);

		btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() { // 뒤로가기 // 내 정보로 가기
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnCancel.setBounds(425, 307, 78, 23);
		contentPane.add(btnCancel);

		btnShare = new JButton("share");
		btnShare.addActionListener(new ActionListener() { // 디비에 저장하기
			public void actionPerformed(ActionEvent e) {
				if (textPane.getText() != null) {
					
				}
			}
		});
		btnShare.setBounds(325, 307, 88, 23);
		contentPane.add(btnShare);
	}
}
