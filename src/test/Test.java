package test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import client.onePost;

public class Test extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 687, 468);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setBorder(null);
//		scrollPane.setBounds(12, 0, 533, 430);
//		scrollPane.setPreferredSize(new Dimension(450, 1000));
//		contentPane.add(scrollPane);
//
//		JPanel panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//		
//		// scrollPane.add(panel); // 직접 추가하지 않아도 되나봄 걍 보여주는 건=> scrollPane.setViewportView(panel);
//		scrollPane.setViewportView(panel);
//
//		onePost post = new onePost();
//
//		for (int i = 0; i < 20; i++) { // 내가 팔로잉 하는 사람들이 올린 글 만큼 추가하자!!
//			panel.add(post.createPost());
//		}
	}
}
