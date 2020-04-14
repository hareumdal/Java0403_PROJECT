package frame;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import client.ClientChat;
import db.FavoriteDTO;
import db.PostDTO;

public class OnePostFrame {
	private ClientChat nowCc = null;
	private String nowId = null;

	OnePostFrame(ClientChat cc, String id) {
		nowCc = cc;
		this.nowId = id;
	}

	public Panel viewPost(PostDTO p, ArrayList<Object> fvList) {
		Panel viewPost = new Panel();
		viewPost.setBounds(10, 120, 465, 240);

		JTextPane postContent = new JTextPane();
		postContent.setEditable(false);
		postContent.setText(p.getText());

		boolean flag = false;

		JButton postfavoriteBtn = new JButton();
		if (fvList != null) {
			for (int i = 0; i < fvList.size(); i++) {
				FavoriteDTO f = (FavoriteDTO) fvList.get(i);
				if (f.getNo().equals(p.getNo())) { // 내가 누른 포스트라면
					flag = true;
					break;
				}
			}
			if (flag) {
				postfavoriteBtn.setText("unfavorite");
			} else {
				postfavoriteBtn.setText("favorite");
			}
		} else {
			postfavoriteBtn.setText("favorite");
		}

		postfavoriteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 관심글(좋아요) 등록
				if (postfavoriteBtn.getText().equals("favorite")) { // 맨처음 좋아요를 누르지 않은 상태였다면
					nowCc.send("addfavorite:" + nowId + "/" + p.getNo());
					postfavoriteBtn.setText("unfavorite");

				} else if (postfavoriteBtn.getText().equals("unfavorite")) {
					nowCc.send("delfavorite:" + nowId + "/" + p.getNo());
					postfavoriteBtn.setText("favorite");
				}
			}
		});

		JButton postModifyBtn = new JButton("Modify");

		postModifyBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}
		});

		// 글 작성자 이름
		JLabel writerId = new JLabel(p.getId());

		// 글 작성 시간
		JLabel writeTime = new JLabel(p.getDay());

		// getList - 조건 : 글 작성자=대상 Id
		JLabel postFavoriteNum = new JLabel("Favorite num");

		JButton postDeleteBtn = new JButton("Delete");

		postDeleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nowCc.chkSet("deletePost:" + nowId + "/" + p.getNo());
			}
		});

		if (nowCc.getNowScId().equals(p.getId())) {
			GroupLayout gl_panel = new GroupLayout(viewPost);
			gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
					.createSequentialGroup().addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup().addComponent(writerId).addGap(255)
									.addComponent(writeTime))
							.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup().addComponent(postfavoriteBtn).addGap(8)
									.addComponent(postFavoriteNum).addGap(98).addComponent(postDeleteBtn).addGap(1)
									.addComponent(postModifyBtn)))));
			gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup().addGap(19)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(writerId)
									.addComponent(writeTime))
							.addGap(10)
							.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(postfavoriteBtn)
									.addGroup(gl_panel.createSequentialGroup().addGap(4).addComponent(postFavoriteNum))
									.addComponent(postDeleteBtn).addComponent(postModifyBtn))));
			viewPost.setLayout(gl_panel);

		} else if (!nowCc.getNowScId().equals(p.getId())) {
			GroupLayout gl_panel = new GroupLayout(viewPost);
			gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
					.createSequentialGroup().addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup().addComponent(writerId).addGap(255)
									.addComponent(writeTime))
							.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup().addComponent(postfavoriteBtn).addGap(8)
									.addComponent(postFavoriteNum)))));
			gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
					.createSequentialGroup().addGap(19)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(writerId)
							.addComponent(writeTime))
					.addGap(10).addComponent(postContent, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(10).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(postfavoriteBtn)
							.addGroup(gl_panel.createSequentialGroup().addGap(4).addComponent(postFavoriteNum)))));
			viewPost.setLayout(gl_panel);
		}

		return viewPost;
	}

}
