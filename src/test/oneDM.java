package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class oneDM extends JFrame {

	private JPanel containerDM;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					oneDM frame = new oneDM();
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
	public oneDM() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 469);
		
		
		containerDM = new JPanel();
		containerDM.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(containerDM);
		containerDM.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 480, 435);
		scrollPane.setPreferredSize(new Dimension(450, 1000));
		containerDM.add(scrollPane);
		
		JPanel oneUserPanel = new JPanel();
		oneUserPanel.setLayout(new BoxLayout(oneUserPanel, BoxLayout.Y_AXIS));
		
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
		
		scrollPane.setViewportView(oneUserPanel);
		
		
		
		
		
		
	}
}
