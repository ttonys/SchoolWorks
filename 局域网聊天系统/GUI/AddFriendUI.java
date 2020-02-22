
package TalkSocket.GUI;

import TalkSocket.Client.ClientAPI;
import TalkSocket.Database.UsersAPI;
import TalkSocket.GUI.overrideClass.*;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;


public class AddFriendUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton findFriend_btn;
	private JLabel find_friend_lbl;
	private JLabel find_group_lbl;
	private JLabel create_group_lbl;

	private int findType = 0;
	private String headIcon;
	private String user_id;
	private String add_user_id;
	private String user_name;

	private int mouseAtX = 0; // 鼠标x轴
	private int mouseAtY = 0; // 鼠标y轴
	private JPanel mainPanel;
	private JPanel findBefore_panel;
	private JPanel findAfter_panel;
	private JPanel find_result;
	private JLabel headIcon_lbl;
	private JLabel name_lbl;
	private JLabel add_lbl;
	private JButton reback_btn;
	private JLabel tip_lbl;
	private JLabel tip_addOK_lbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFriendUI a = new AddFriendUI();
					a.setType(Type.UTILITY);
					a.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddFriendUI() {
		this.init();
	}

	public AddFriendUI(String user_id) {
		this.user_id = user_id;
		this.init();
		// 圆角
		AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 20.0D, 20.0D));
		this.validate();
	}

	public void init() {
		this.setType(Type.UTILITY);
		this.setUndecorated(true);
		// 窗口拖动
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// 获取点击鼠标时的坐标
				mouseAtX = e.getPoint().x;
				mouseAtY = e.getPoint().y;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				setLocation((e.getXOnScreen() - mouseAtX), (e.getYOnScreen() - mouseAtY));// 设置拖拽后，窗口的位置
			}
		});

		setBounds(100, 100, 700, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(242, 242, 242));
		contentPane.setBorder(new JBorder(25, 25));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		mainPanel = new JPanel();
		mainPanel.setBounds(1, 0, 698, 160);
		mainPanel.setBackground(new Color(242, 242, 242));
		mainPanel.setLayout(null);
		contentPane.add(mainPanel);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AddFriendUI.class.getResource("/TalkSocket/GUI/images/topBg.jpg")));
		lblNewLabel.setBounds(0, 0, 700, 5);
		mainPanel.add(lblNewLabel);

		JButton close_btn = new JButton("");
		close_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}

		});
		close_btn.setBackground(Color.WHITE);
		close_btn.setIcon(new ImageIcon(AddFriendUI.class.getResource("/TalkSocket/GUI/images/close_2.png")));
		close_btn.setBounds(675, 10, 16, 16);
		close_btn.setBorder(null);
		close_btn.setFocusable(false);
		mainPanel.add(close_btn);

		mainPanel.add(this.getFindBefore_panel());
		findBefore_panel.setVisible(true);

	}

	public JPanel getFindBefore_panel() {
		findBefore_panel = new JPanel();
		findBefore_panel.setBounds(0, 30, 698, 130);
		findBefore_panel.setBorder(new JBorder(25, 25,new Color(242, 242, 242)));
//		mainPanel.add(findBefore_panel);
		findBefore_panel.setLayout(null);

		find_friend_lbl = new JLabel("添加好友");
		find_friend_lbl.setBounds(310, 0, 150, 35);
		findBefore_panel.add(find_friend_lbl);
		find_friend_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (findType == 0) {
					return;
				} else {
					findType = 0;
					find_friend_lbl.setForeground(new Color(78, 129, 151));
					find_group_lbl.setForeground(Color.BLACK);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				find_friend_lbl.setForeground(new Color(78, 129, 151));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (findType == 0) {
					find_friend_lbl.setForeground(new Color(78, 129, 151));
				} else {
					find_friend_lbl.setForeground(Color.BLACK);
				}
			}
		});
		find_friend_lbl.setFont(new Font("微软雅黑", Font.BOLD, 20));
		find_friend_lbl.setForeground(new Color(78, 129, 151));



		JPanel panel = new JPanel();
		panel.setBounds(25, 46, 550, 50);
		findBefore_panel.add(panel);
		panel.setBackground(new Color(242, 242, 242));
		panel.setBorder(new JBorder(55, 55));
		panel.setLayout(null);

		JLabel find_png = new JLabel();
		find_png.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/find.png")));
		find_png.setBounds(20, 9, 32, 32);
		panel.add(find_png);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                	if (textField.getText().equals("")) {
    					tip_lbl.setVisible(true);
    					return;
    				}
//    				findUser(findType);
    				textField.setText("");
    				findBefore_panel.setVisible(false);
    				findAfter_panel.setVisible(true);
    				findAfter_panel.updateUI();
                }
            }
        });
		textField.setBounds(65, 2, 450, 46);
		textField.setBackground(new Color(242, 242, 242));
		textField.setBorder(null);
		panel.add(textField);
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		textField.setColumns(10);

		findFriend_btn = new JButton("添 加");
		findFriend_btn.setBounds(600, 54, 72, 35);
		findBefore_panel.add(findFriend_btn);
		findFriend_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean res =  ClientAPI.u.addFriends(Integer.parseInt(textField.getText()));
				System.out.println(res);
				setVisible(false);
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}
		});
		findFriend_btn.setForeground(Color.WHITE);
		findFriend_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		findFriend_btn.setBackground(new Color(105, 155, 178));
		findFriend_btn.setFocusable(false);
		return findBefore_panel;
	}

}
