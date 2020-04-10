package frame;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import db.PostDTO;

public class onePost {
	onePost() {

	}

	public JPanel createPost(PostDTO pDTO) {
		JPanel panel = new JPanel();
		panel.setBounds(200, 100, 500, 500);

		JLabel label = new JLabel("comment");

		JButton button = new JButton("like");

		JLabel label_1 = new JLabel("howmanylike");

		JButton button_1 = new JButton("profil"); // 이미지가 있다면 교체

		JButton button_2 = new JButton(pDTO.getId());

		JTextPane textPane = new JTextPane();
		textPane.setText(pDTO.getText());
		textPane.setEditable(false);
		
		JButton btnNewButton = new JButton("New button");
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(109)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
							.addGap(65)
							.addComponent(btnNewButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addGap(36))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_1)
						.addComponent(button_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(btnNewButton)))
						.addComponent(button)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addComponent(label_1))))
		);
		panel.setLayout(gl_panel);
		
		
		
//		JPanel panel = new JPanel();
//		panel.setBounds(37, 35, 376, 315);
//
//		JLabel label = new JLabel("comment");
//
//		JButton button = new JButton("like");
//
//		JLabel label_1 = new JLabel("howmanylike");
//
//		JButton button_1 = new JButton("profil"); // 이미지가 있다면 교체
//
//		JButton button_2 = new JButton(pDTO.getId());
//
//		JTextPane textPane = new JTextPane();
//	//	textPane.setEditable(false);
//		textPane.setText(pDTO.getText());
//		
//		
//		GroupLayout gl_panel = new GroupLayout(panel);
//		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
//				.createSequentialGroup().addGap(12)
//				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
//						.addGroup(gl_panel.createSequentialGroup().addGap(109).addComponent(label,
//								GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
//						.addComponent(button, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
//						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
//						.addGroup(gl_panel.createSequentialGroup()
//								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
//								.addPreferredGap(ComponentPlacement.RELATED)
//								.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
//				.addGap(163))
//				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
//						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE).addContainerGap()));
//		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
//				.createSequentialGroup().addGap(20)
//				.addGroup(
//						gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(button_1).addComponent(button_2))
//				.addPreferredGap(ComponentPlacement.RELATED)
//				.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
//				.addPreferredGap(ComponentPlacement.RELATED)
//				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
//						.addGroup(gl_panel.createSequentialGroup().addGap(4).addComponent(label)).addComponent(button)
//						.addGroup(gl_panel.createSequentialGroup().addGap(19).addComponent(label_1)))));
//		panel.setLayout(gl_panel);
//		
//		
//		
		
		return panel;
	}
	public JPanel createPost() {
		JPanel panel = new JPanel();
		panel.setBounds(37, 35, 376, 315);

		JLabel label = new JLabel("comment");

		JButton button = new JButton("like");

		JLabel label_1 = new JLabel("howmanylike");

		JButton button_1 = new JButton("profil"); // 이미지가 있다면 교체

		JButton button_2 = new JButton("id");

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		//textPane.setText(((PostDTO) pDTO).getText());
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(12)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(109).addComponent(label,
								GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
				.addGap(163))
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(20)
				.addGroup(
						gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(button_1).addComponent(button_2))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(4).addComponent(label)).addComponent(button)
						.addGroup(gl_panel.createSequentialGroup().addGap(19).addComponent(label_1)))));
		panel.setLayout(gl_panel);
		
		
		
		
		return panel;
	}
}
