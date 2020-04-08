package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import db.MemberDTO;

public class HomeFrame extends JFrame {

	private JTabbedPane tabPane = new JTabbedPane();
	private JList jList = new JList();
	private JPanel tab_1 = new JPanel();
	private JPanel tab_2 = new JPanel();
	private JPanel tab_3 = new JPanel();
	private JPanel tab_4 = new JPanel();

	private JPanel searchP = new JPanel();

	private static ClientChat nowCc = null;
	private static String nowId = null;
	private String getTName = null;

	private static HomeFrame HomeF = null;

	
	private ArrayList<Object> objectList = new ArrayList<>();
//	private ArrayList<MemberDTO> memberList = null;
	
	
	
	private HomeFrame() {
		super("SNS Program");
		setList("member");
		// setList("post");
	}

	public void Frame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);

		createTimeline();
		createProfile();
		createDirectMessage();
		createSearch();
		createTabbledP();

		this.add(tabPane, "Center");

		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}

	public static HomeFrame getInstance(String id, ClientChat cc) {
		nowId = id;
		nowCc = cc;
		if (HomeF == null) {
			HomeF = new HomeFrame();
		}
		return HomeF;
	}

	private void setList(String tName) {
		// TODO Auto-generated method stub
		this.getTName = tName;
		nowCc.send("setList:" + tName + "/" + nowId);
	}

	private void createTimeline() {
		// TODO Auto-generated method stub
		tab_1.setLayout(new BoxLayout(tab_1, BoxLayout.Y_AXIS));

		JTextArea timeline_1 = new JTextArea();
		timeline_1.setEditable(false);
		timeline_1.setText("First");
		tab_1.add(timeline_1);

		JTextArea timeline_2 = new JTextArea();
		timeline_2.setEditable(false);
		timeline_2.setText("Second");
		tab_1.add(timeline_2);

		JTextArea timeline_3 = new JTextArea();
		timeline_3.setEditable(false);
		timeline_3.setText("Third");
		tab_1.add(timeline_3);

		JTextArea timeline_4 = new JTextArea();
		timeline_4.setEditable(false);
		timeline_4.setText("Fourth");
		tab_1.add(timeline_4);

		JTextArea timeline_5 = new JTextArea();
		timeline_5.setEditable(false);
		timeline_5.setText("Fifth");
		tab_1.add(timeline_5);
	}

	private void createProfile() {
		// TODO Auto-generated method stub
		nowCc.send("myPage:" + nowId);
		// nowCc.receiveObject(); // ? 왜 다시 디비를 뒤져쓰까 ??????? // 생각해 보쟈
		objectList.add(nowCc.receiveObject());

		nowCc.send("setList:member/" + nowId);
		
		objectList.add(nowCc.receiveObject());
		//memberList = (ArrayList<MemberDTO>) nowCc.receiveObject();
//		for (MemberDTO m : memberList) { // 리스트가 오는지 확인
//			System.out.println(m.getId());
//		}

		// PostDTO post = (PostDTO) nowCc.receiveObject();
		
		
		
		tab_2.setLayout(new BorderLayout());
		JLabel temp = new JLabel();

		if (objectList.get(0) != null) {
			temp.setText("MyId:" + nowId);
			tab_2.add(temp, "Center");
		} else if (objectList.get(0) == null) {
			temp.setText("");
			tab_2.add(temp, "Center");
		}

	}

	private void createDirectMessage() {
		// TODO Auto-generated method stub

	}

	private void createSearch() { // 문제: 맨첫글자부터 맞아야 검색됨, 리스트가 뜬 후 선택하면 그 사람의 페이지로 넘어가게 만들어 주자
		tab_4.setLayout(new BorderLayout());
		JTextField txtInput = new JTextField();
		searchP.setLayout(new BorderLayout());
		searchP.add(txtInput, "North");
		tab_4.add(searchP, "North");
		createSearchData(txtInput); // 다른 클래스로 빼주기?
	}

	private void createTabbledP() {
		tabPane.add("Timeline", tab_1);
		tabPane.add("MyPage", tab_2);
		tabPane.add("DirectMessage", tab_3);
		tabPane.add("Search", tab_4);
	}

	private void createSearchData(JTextField txtInput) {
		setupAutoComplete(txtInput, objectList.get(1));
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

	private void setupAutoComplete(final JTextField txtInput, final Object items) {
		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		final JComboBox cbInput = new JComboBox(model) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		setAdjusting(cbInput, false);
		//ArrayList<MemberDTO> m= (ArrayList<MemberDTO>) items;
		for (MemberDTO item : (ArrayList<MemberDTO>) items) {
			model.addElement((String)item.getId());
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
						txtInput.setText(cbInput.getSelectedItem().toString());
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
					for (MemberDTO item : (ArrayList<MemberDTO>) items) {
						if (item.getId().toLowerCase().indexOf(input.toLowerCase()) != -1) {
						//if (item.getId().toLowerCase().startsWith(input.toLowerCase())) {
							model.addElement(item.getId());
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

}
