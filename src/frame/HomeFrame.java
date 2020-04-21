package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import client.ClientChat;
import db.DirectMessageDTO;
import db.DmroomDTO;
import db.MemberDTO;
import db.PostDTO;

public class HomeFrame extends JFrame {
	private JTabbedPane tabPane = new JTabbedPane();

	private JPanel tab_1 = new JPanel();
	private JPanel tab_2 = new JPanel();
	private JPanel tab_3 = new JPanel();
	private JPanel tab_4 = new JPanel();

	private JPanel oneUserPanel = null;
	private JPanel searchP = new JPanel();

	private JButton searchM = new JButton();

	private static ClientChat nowCc = null;
	private static String nowId = null;

	private static HomeFrame HomeF = null;

	private Random r = new Random();

	private HomeFrame() {
		super("SNS Program");
	}

	public static HomeFrame getInstance(String id, ClientChat cc) {
		nowId = id;
		nowCc = cc;
		if (HomeF == null) {
			HomeF = new HomeFrame();
		}
		return HomeF;
	}

	public void Frame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);

		createTimeline();
		createProfile(tab_2, nowId, nowCc);
		createDirectMessage();
		createSearch();

		createTabbledP();

		this.add(tabPane, "Center");

		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);

		// 윈도우 동작을 읽는 리스너 : HomeFrame 창을 X 버튼을 눌러 닫을시 로그아웃 처리
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				nowCc.send("logout:" + nowCc.getNowCcId());
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void createTimeline() {
		// TODO Auto-generated method stub
		tab_1.setLayout(null);
		tab_1.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel timeline = new JPanel();
		timeline.setLayout(new BorderLayout());
		timeline.setBounds(0, 0, 480, 435);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 480, 435);
		scrollPane.setPreferredSize(new Dimension(450, 1000));
		timeline.add(scrollPane);

		JPanel myPost = new JPanel();
		myPost.setLayout(new BoxLayout(myPost, BoxLayout.Y_AXIS));

		scrollPane.setViewportView(myPost);

		Object o = nowCc.getObject("getList:" + "post" + "/" + nowId + "/t");
		ArrayList<Object> pList = (ArrayList<Object>) o;

		settingPostView(pList, myPost, nowId);

		JPanel Btn = new JPanel();
		Btn.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 3));

		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.setPreferredSize(new Dimension(97, 23));

		RefreshBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("/RefreshBtn Click:Timeline");

				myPost.removeAll();

				Object o = nowCc.getObject("getList:" + "post" + "/" + nowId + "/t");
				ArrayList<Object> pList = (ArrayList<Object>) o;

				settingPostView(pList, myPost, nowId);

				// 레이아웃 변경을 적용하고 다시 그림
				revalidate();
				repaint();
			}
		});

		JButton PostWriteBtn = new JButton("Write");
		PostWriteBtn.setPreferredSize(new Dimension(97, 23));

		PostWriteBtnListner(PostWriteBtn, nowCc);

		Btn.add(PostWriteBtn);
		Btn.add(RefreshBtn);

		timeline.add(scrollPane, "Center");
		timeline.add(Btn, "South");

		tab_1.add(timeline);
	}

	// PostList, PostList를 띄우는 그룹화 된 Panel이 들어갈 JPanel, 글을 보고 있는 사용자 Id
	private void settingPostView(ArrayList<Object> pList, JPanel postPanel, String id) {
		OnePostFrame pF = new OnePostFrame(nowCc, nowId);
		if (pList.size() > 0) {
			ArrayList<Object> fvList = (ArrayList<Object>) nowCc.getObject("getList:favorite/" + nowId);
			for (int i = 0; i < pList.size(); i++) {
				PostDTO p = (PostDTO) pList.get(i);
				postPanel.add(pF.viewPost(p, fvList));

			}
		} else if (pList.size() == 0 || pList == null) {
			JPanel temp = new JPanel();
			temp.setLayout(new BorderLayout());
			JLabel empty = new JLabel("Empty Post");
			empty.setHorizontalAlignment(JLabel.CENTER);
			temp.add(empty, "Center");
			postPanel.add(temp);
		}
	}

	private void settingViewDM(JPanel oneUserPanel) {
		ArrayList<Object> dmList = (ArrayList<Object>) nowCc.getObject("getList:dmroom/" + nowId);
		if (dmList.size() > 0) {
			for (int i = 0; i < dmList.size(); i++) {
				DmroomDTO dm = (DmroomDTO) dmList.get(i);
				if (!dm.getId().equals(nowId)) {
					ArrayList<Object> dmsgList = (ArrayList<Object>) nowCc.getObject("getList:directmessage/" + dm.getRoomname() + "/");
					if (dmsgList.size() > 0 && (DirectMessageDTO) dmsgList.get(0) != null) {
						OneDMFrame oneDMFrame = new OneDMFrame(nowCc, dm.getId());
						nowCc.setOneDMFrame(oneDMFrame);
						oneUserPanel.add(oneDMFrame.oneDM(dm));
					} else {
						//nowCc.send("deldmroom:" + dm.getRoomname());
					}
				}
			}
		} else {
			JPanel temp = new JPanel();
			temp.setLayout(new BorderLayout());
			JLabel empty = new JLabel("Empty Post");
			empty.setHorizontalAlignment(JLabel.CENTER);
			temp.add(empty, "Center");
			oneUserPanel.add(temp);
		}
	}

	public void createProfile(JPanel tab_2, String id, ClientChat nowCc) {
		// TODO Auto-generated method stub
		Object receiveObject = nowCc.getObject("profile:" + id);
		MemberDTO my = (MemberDTO) receiveObject;

		tab_2.setLayout(null);
		tab_2.setBorder(new EmptyBorder(5, 5, 5, 5));

		// 현재 사용자 Id
		JLabel IdLabel = new JLabel(my.getId());
		IdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IdLabel.setBounds(30, 55, 120, 15);
		IdLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		tab_2.add(IdLabel);
//
		JButton DMBtn = new JButton("DM");
		DMBtn.setBounds(140, 55, 65, 20);
		DMBtn.setBorderPainted(false);
		DMBtn.setContentAreaFilled(false);
		tab_2.add(DMBtn);

		char[] rAlphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z' };

		String setRoomName = "" + rAlphabet[r.nextInt(rAlphabet.length)] + rAlphabet[r.nextInt(rAlphabet.length)] + r.nextInt(9999);

		DMBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean tf = false;
				if (nowId.equals(id)) { // 내 거면 디엠 ㄴㄴ
				} else if (!nowId.equals(id)) { // 타 유저의 프로필이 맞다면 디엠 ㅇㅇ
					ArrayList<Object> dmRoomName = (ArrayList<Object>) nowCc.getObject("getList:" + "dmroom/" + nowId);
					if (dmRoomName.size() > 0) {
						for (int i = 0; i < dmRoomName.size(); i++) {
							DmroomDTO dmroom = (DmroomDTO) dmRoomName.get(i);
							if (dmroom.getId().equals(id)) {
								tf = true;
								MessageFrame messageFrame = new MessageFrame(nowCc, id, dmroom.getRoomname());
								nowCc.setOpendWindowDM(messageFrame);
								break;
							}
						}
					} 
//					
					if (!tf) {
						nowCc.send("makedmRoom:" + id + "/" + nowId + "/" + setRoomName);
						OneDMFrame oneDMFrame = new OneDMFrame(nowCc, id);
						MessageFrame messageFrame = new MessageFrame(nowCc, id, setRoomName);
						nowCc.setOneDMFrame(oneDMFrame);
						nowCc.setOpendWindowDM(messageFrame);
						settingViewDM(oneUserPanel);
					}

				}
			}
		});

		// 친구 목록 Button
		JButton FrListBtn = new JButton("Follow List");
		FrListBtn.setBounds(190, 55, 95, 20);
		FrListBtn.setBorderPainted(false);
		FrListBtn.setContentAreaFilled(false);
		Font font = FrListBtn.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		FrListBtn.setFont(font.deriveFont(attributes));
		tab_2.add(FrListBtn);

		FrListBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object receiveObject = null;

				if (nowId.equals(id)) {
					receiveObject = nowCc.getObject("getList:" + "friend" + "/" + nowId);
				} else if (!nowId.equals(id)) {
					receiveObject = nowCc.getObject("getList:" + "friend" + "/" + id);
				}

				new FriendFrame(HomeF, nowCc, receiveObject);
			}
		});

		// 관심글 목록 Button
		JButton FListBtn = new JButton("Favorite");
		FListBtn.setBounds(345, 55, 95, 20);
		FListBtn.setBorderPainted(false);
		FListBtn.setContentAreaFilled(false);
		font = FListBtn.getFont();
		attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		FListBtn.setFont(font.deriveFont(attributes));
		tab_2.add(FListBtn);

		FListBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// getList - 조건 : 관심글 등록자=대상자 Id
			}
		});

		// 내가 쓴 글 (스크롤 기능)
		JPanel myPostAll = new JPanel();
		myPostAll.setLayout(null);
		myPostAll.setBounds(0, 120, 480, 285);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 120, 480, 285);
		scrollPane.setPreferredSize(new Dimension(450, 1000));
		myPostAll.add(scrollPane);

		JPanel myPost = new JPanel();
		myPost.setLayout(new BoxLayout(myPost, BoxLayout.Y_AXIS));

		scrollPane.setViewportView(myPost);

		// 자신이 쓴 글 List 받아오기
		Object receiveObject_myPost = nowCc.getObject("getList:" + "post" + "/" + id);
		ArrayList<Object> myPList = (ArrayList<Object>) receiveObject_myPost;

		settingPostView(myPList, myPost, id);

		tab_2.add(scrollPane);

		if (nowId.equals(id)) {
			JButton MyPageBtn = new JButton("MyPage");
			MyPageBtn.setBounds(12, 410, 97, 23);
			tab_2.add(MyPageBtn);

			MyPageBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new MyPageFrame(nowCc, id);
				}
			});

			JButton PostWriteBtn = new JButton("Write");
			PostWriteBtn.setBounds(270, 410, 97, 23);
			tab_2.add(PostWriteBtn);

			PostWriteBtnListner(PostWriteBtn, nowCc);
		} else {
			JButton FollowBtn = new JButton();
			FollowBtn.setBounds(12, 410, 97, 23);

			nowCc.send("chkfollow:" + nowId + "/" + id);
			System.out.println("HomeF:::chkfollow:::" + nowCc.getReceiveMessage());
			if (nowCc.getReceiveMessage().contains("true")) {
				FollowBtn.setText("Unfollow");
			} else if (nowCc.getReceiveMessage().contains("false")) {
				FollowBtn.setText("Follow");
			}

			tab_2.add(FollowBtn);
			FollowBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (FollowBtn.getText().equals("Follow")) {
						nowCc.send("addfollow:" + nowId + "/" + id);

						if (nowCc.getReceiveMessage().indexOf("true") != -1) {
							FollowBtn.setText("Unfollow");
						}
					} else if (FollowBtn.getText().equals("Unfollow")) {
						nowCc.send("delfollow:" + nowId + "/" + id);

						if (nowCc.getReceiveMessage().indexOf("true") != -1) {
							FollowBtn.setText("Follow");
						}
					}
				}
			});
		}

		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.setBounds(375, 410, 97, 23);
		tab_2.add(RefreshBtn);

		RefreshBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("/RefreshBtn Click:Profile");

				myPost.removeAll();

				Object receiveObject_Post = nowCc.getObject("getList:" + "post" + "/" + id);
				ArrayList<Object> pList = (ArrayList<Object>) receiveObject_Post;

				settingPostView(pList, myPost, id);

				myPost.revalidate();
				myPost.repaint();
			}
		});
	}

	private void PostWriteBtnListner(JButton PostWriteBtn, ClientChat nowCc) {
		PostWriteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WritePostFrame writePost = new WritePostFrame(nowCc);
			}
		});
	}

	private void createDirectMessage() {

		tab_3.setLayout(null);
		tab_3.setBorder(new EmptyBorder(5, 5, 5, 5));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 480, 435);
		scrollPane.setPreferredSize(new Dimension(450, 1000));
		tab_3.add(scrollPane);

		oneUserPanel = new JPanel();
		oneUserPanel.setLayout(new BoxLayout(oneUserPanel, BoxLayout.Y_AXIS));

		settingViewDM(oneUserPanel);
		scrollPane.setViewportView(oneUserPanel);

	}

	private void createSearch() {
		// TODO Auto-generated method stub
		tab_4.setLayout(new BorderLayout());

		JTextField txtInput = new JTextField();
		searchP.setLayout(new BorderLayout());
		searchP.add(txtInput, "North");

		tab_4.add(searchP, "North");

		createSearchData(txtInput);

		JPanel searchR = new JPanel();
		searchR.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		searchM.setPreferredSize(new Dimension(80, 30));
		searchR.add(searchM);
		tab_4.add(searchR, "Center");

		searchM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (searchM.getText().length() > 0) {
					new ProfileFrame(searchM.getText(), nowCc, HomeF);
				}
			}
		});
	}

	private void createSearchData(JTextField txtInput) {
		Object receiveObject = nowCc.getObject("setList:" + "member" + "/" + nowId);
		setupAutoComplete(txtInput, (ArrayList<Object>) receiveObject);
		txtInput.setColumns(30);
	}

	private boolean isAdjusting(JComboBox cbInput) {
		if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
			return (Boolean) cbInput.getClientProperty("is_adjusting");
		}
		return false;
	}

	private void setAdjusting(JComboBox cbInput, boolean adjusting) {
		cbInput.putClientProperty("is_adjusting", adjusting);
	}

	private void setupAutoComplete(final JTextField txtInput, final ArrayList<Object> items) {
		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		final JComboBox cbInput = new JComboBox(model) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};

		setAdjusting(cbInput, false);

		for (int i = 0; i < items.size(); i++) {
			MemberDTO m = (MemberDTO) items.get(i);
			model.addElement(m.getId());
		}

		cbInput.setSelectedItem(null);

		cbInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isAdjusting(cbInput)) {
					if (cbInput.getSelectedItem() != null) {
						txtInput.setText(cbInput.getSelectedItem().toString());
					}
				}
			}
		});

		txtInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				setAdjusting(cbInput, true);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (cbInput.isPopupVisible()) {
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.setSource(cbInput);
					cbInput.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (cbInput.getSelectedItem() != null && cbInput.getSelectedItem().toString() != null) {
							txtInput.setText(cbInput.getSelectedItem().toString());
							for (int i = 0; i < items.size(); i++) {
								MemberDTO m = (MemberDTO) items.get(i);
								if (m.getId().equals(txtInput.getText())) {
									searchM.setText(txtInput.getText());
									txtInput.setText("");
									break;
								}
							}
						}
						cbInput.setPopupVisible(false);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cbInput.setPopupVisible(false);
				}
				setAdjusting(cbInput, false);
			}
		});

		txtInput.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				updateList();
			}

			public void removeUpdate(DocumentEvent e) {
				updateList();
			}

			public void changedUpdate(DocumentEvent e) {
				updateList();
			}

			private void updateList() {
				setAdjusting(cbInput, true);
				model.removeAllElements();
				String input = txtInput.getText();
				if (!input.isEmpty()) {
					for (int i = 0; i < items.size(); i++) {
						MemberDTO m = (MemberDTO) items.get(i);
						if (m.getId().toLowerCase().indexOf(input.toLowerCase()) != -1) {
							model.addElement(m.getId());
						}
					}
				}
				cbInput.setPopupVisible(model.getSize() > 0);
				setAdjusting(cbInput, false);
			}
		});

		txtInput.setLayout(new BorderLayout());
		txtInput.add(cbInput, BorderLayout.SOUTH);
	}

	private void createTabbledP() {
		// TODO Auto-generated method stub
		tabPane.add("Timeline", tab_1);
		tabPane.add("Profile", tab_2);
		tabPane.add("DirectMessage", tab_3);
		tabPane.add("Search", tab_4);
	}

}
