package TalkSocket.GUI;

import TalkSocket.Client.ClientAPI;
import TalkSocket.Server.ServerCommon;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

public class ClientUI extends JFrame {

    private int mouseAtX = 0;    // 鼠标x轴
    private int mouseAtY = 0;    // 鼠标y轴

    public static int flag = 0;
    public static HashMap<String, String> lis = new HashMap<String, String>();
    public static HashMap<String, FriendListUI> lis_online = new HashMap<String, FriendListUI>();
    public static int i = 0;
    public static String online_res;


    private int inforMsgNum = 0;
    private Color topButtonColor = new Color(66, 73, 153);
    private Color FONT_COLOR = new Color(51, 51, 51);
    private Color BOTTOM_LEFT_BGCOLOR = new Color(238, 238, 238);
    private Color BOTTOM_RIGHT_BGCOLOR = new Color(242, 242, 242);

    private JPanel PERSONPANEL_CLICK = null;
    private JPanel PERSONPANEL_HOVER = null;

    private Socket socket = null;
    private String username;
    private int uid;
    private boolean onlineStatus = false;   // 上线状态
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private StringBuffer myFriendsID = null;    // 所有好友ID
    private StringBuffer myGroupsID = null;     // 所有群ID
    private HashMap<FriendListUI, ChatShowInputUI> personList = new HashMap<FriendListUI, ChatShowInputUI>();     // 好友列表和聊天界面
    private HashMap<String, ChatShowInputUI> person_chat_List = new HashMap<String, ChatShowInputUI>();
    private Set set = null;
    private Iterator it = null;

    private ClientUI clientUI;
    private JPanel contentPane;
    private JButton closeButton;
    private JButton zxhButton;
    private JPanel onlineListPanel;
    private JPanel bottomPanel;
    private JPanel bottomRightPanel;
    private JLabel userNameLabel;
    private ChatShowInputUI chatShowInputUI;
    private AddFriendUI addFriendUI;
    private int ADDFRIENDUI_WIDTH = 0;
    private int ADDFRIENDUI_HEIGHT = 0;
    private JPanel infor_Panel;
    private JPanel b_r_childPanel;
    private JPanel inforInner_Panel;
    private JLabel red_dot;
    

    public void setClientUI(ClientUI clientUI) {
        this.clientUI = clientUI;
    }

    public JPanel getOnlineListPanel() {
        return onlineListPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public JPanel getBottomRightPanel() {
        return bottomRightPanel;
    }

    public ChatShowInputUI getChatShowInputUI() {
        return chatShowInputUI;
    }

    public void setChatShowInputUI(ChatShowInputUI chatShowInputUI) {
        this.chatShowInputUI = chatShowInputUI;
    }

    public void setBottomRightPanel(JPanel bottomRightPanel) {
        this.bottomRightPanel = bottomRightPanel;
    }

    public HashMap<FriendListUI, ChatShowInputUI> getPersonList() {
        return personList;
    }

    public void setPersonList(HashMap<FriendListUI, ChatShowInputUI> personList) {
        this.personList = personList;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public Iterator getIt() {
        return it;
    }

    public void setIt(Iterator it) {
        this.it = it;
    }

    public StringBuffer getMyFriendsID() {
        return myFriendsID;
    }

    public StringBuffer getMyGroupsID() {
        return myGroupsID;
    }

    public void setUser() {
//        System.out.println(this.message.user.getName());
//        this.chatPersonName.setText(this.user.getName());
//        System.out.println(this.chatPersonName.getText());
}

    public void connection() throws SQLException {
        this.loadFriendList(0);      // 加载好友列表
    }


    //用户信息初始化
    public ClientUI(String user, int uid) throws SQLException {
        this.username = user;
        this.uid = uid;
        this.myFriendsID = new StringBuffer();
        this.myGroupsID = new StringBuffer();
        this.init();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 20.0D, 20.0D));
        this.userNameLabel.setText("user:" + this.username + " uid:" + this.uid);
        this.connection();
        this.setVisible(true);
    }

    public void init() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginUI.class.getResource("/TalkSocket/GUI/images/icon.png")));
        this.setUndecorated(true);
        // 圆角窗体
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(20.0D, 20.0D, this.getWidth(), this.getHeight(), 16.0D, 16.0D));
        this.validate();
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

        setTitle("ChatRoom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1150, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel topPanel = new JPanel();
//		topPanel.setBackground(Color.PINK);
        topPanel.setBounds(0, 0, 1150, 70);
        contentPane.add(topPanel);
        topPanel.setLayout(null);

        zxhButton = new JButton("");
        zxhButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                zxhButton.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/zxh_1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                zxhButton.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/zxh.png")));
            }
        });
        zxhButton.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/zxh.png")));
        zxhButton.setBounds(1050, 20, 32, 32);
        zxhButton.setBorder(null);
        zxhButton.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        zxhButton.setBackground(topButtonColor);
        topPanel.add(zxhButton);

        closeButton = new JButton("");
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                try {
//                    message.sendClose("close");
//                } catch (NullPointerException e1) {
//                    e1.getMessage();
//                } finally {
//                    CloseUtil.closeAll(socket, dis, dos);
//                    System.exit(0);
//                }
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(topButtonColor);
            }
        });
        closeButton.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/close.png")));
        closeButton.setBounds(1100, 20, 32, 32);
        closeButton.setBorder(null);
        closeButton.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        closeButton.setBackground(topButtonColor);
        topPanel.add(closeButton);
        // 登录用户的用户名
        userNameLabel = new JLabel("昵称：" + this.username);
        userNameLabel.setForeground(Color.BLACK);
        userNameLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        userNameLabel.setBounds(74, 20, 205, 30);
        topPanel.add(userNameLabel);

        JLabel topBgLabel = new JLabel("");
        topBgLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/topBg.jpg")));
        topBgLabel.setBounds(0, 0, 1150, 70);
        topPanel.add(topBgLabel);

        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 70, 1150, 680);
        bottomPanel.setLayout(null);
        contentPane.add(bottomPanel);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.MAGENTA);
        bottomLeftPanel.setBounds(0, 0, 310, 680);
        bottomPanel.add(bottomLeftPanel);
        bottomLeftPanel.setLayout(null);

        // 好友列表Top固定Bar
        JPanel botLeftTopBar = new JPanel();
        botLeftTopBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
        botLeftTopBar.setBounds(0, 0, 310, 45);
        botLeftTopBar.setBackground(BOTTOM_LEFT_BGCOLOR);
        botLeftTopBar.setLayout(null);
        bottomLeftPanel.add(botLeftTopBar);

        JLabel onlinTopBar_name = new JLabel("  好友列表");
        onlinTopBar_name.setForeground(FONT_COLOR);
        onlinTopBar_name.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/user_icon.png")));
        onlinTopBar_name.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        onlinTopBar_name.setBounds(30, 0, 146, 45);
        botLeftTopBar.add(onlinTopBar_name);
        bottomLeftPanel.add(botLeftTopBar);

        JButton refreshFriendsBtn = new JButton("");
        refreshFriendsBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                System.out.println("===========");
                try {
                    ClientAPI.send_msg("GET", "GET", "GET");
                    loadFriendList(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //刷新好友数据以及是否在线
        refreshFriendsBtn.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/refresh.png")));
        refreshFriendsBtn.setBounds(253, 7, 32, 32);
        refreshFriendsBtn.setBorder(null);
        refreshFriendsBtn.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        refreshFriendsBtn.setBackground(BOTTOM_LEFT_BGCOLOR);
        botLeftTopBar.add(refreshFriendsBtn);

        // 在线列表 滚动面板
        onlineListPanel = new JPanel();
        onlineListPanel.setBackground(BOTTOM_LEFT_BGCOLOR);
        onlineListPanel.setPreferredSize(new Dimension(300, 800));
        onlineListPanel.setLayout(null);


        JScrollPane scrollPane = new JScrollPane(onlineListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 45, 310, 575);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(null);
        bottomLeftPanel.add(scrollPane);

        JPanel botLeftBottomPanel = new JPanel();
        botLeftBottomPanel.setBounds(0, 620, 310, 60);
        bottomLeftPanel.add(botLeftBottomPanel);
        botLeftBottomPanel.setLayout(null);

        JLabel addFriendLabel = new JLabel("");
        addFriendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (addFriendUI == null) {
                    addFriendUI = new AddFriendUI();
                }
                addFriendUI.setLocationRelativeTo(clientUI);
                addFriendUI.setVisible(true);
                red_dot.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                addFriendLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/add_friend_1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                addFriendLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/add_friend.png")));
            }
        });

        addFriendLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/add_friend.png")));
        addFriendLabel.setBounds(250, 10, 40, 40);
        botLeftBottomPanel.add(addFriendLabel);

        red_dot = new JLabel("");
        red_dot.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/red_dot.png")));
        red_dot.setBounds(220, 5, 16, 16);
        red_dot.setVisible(false);
        botLeftBottomPanel.add(red_dot);

        JLabel inforLabel = new JLabel("");
        inforLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (chatShowInputUI != null) {
                    bottomPanel.remove(chatShowInputUI);
                }
                if (b_r_childPanel != null) {
                    bottomRightPanel.remove(b_r_childPanel);
                    b_r_childPanel = null;
                }
                if (bottomRightPanel.isVisible() == false) {
                    bottomRightPanel.setVisible(true);
                    bottomPanel.add(bottomRightPanel);
                }
                red_dot.setVisible(false);
                bottomPanel.updateUI();
                infor_Panel.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                inforLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/infor_1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                inforLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/infor.png")));
            }
        });
        inforLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/TalkSocket/GUI/images/infor.png")));
        inforLabel.setBounds(186, 10, 40, 40);
        botLeftBottomPanel.add(inforLabel);

        bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.PINK);
        bottomRightPanel.setLayout(null);
        bottomRightPanel.setBounds(310, 0, 840, 680);
        bottomPanel.add(bottomRightPanel);

        b_r_childPanel = new JPanel();
        b_r_childPanel.setBounds(0, 0, 840, 680);
        bottomRightPanel.add(b_r_childPanel);
        b_r_childPanel.setLayout(null);

        JLabel b_r_childTitel = new JLabel("欢迎使用聊天室");
        b_r_childTitel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        b_r_childTitel.setBounds(335, 180, 203, 40);
        b_r_childPanel.add(b_r_childTitel);

        infor_Panel = new JPanel();
        infor_Panel.setBounds(0, 0, 840, 680);
        infor_Panel.setVisible(false);
        bottomRightPanel.add(infor_Panel);
        infor_Panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("消息通知");
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        lblNewLabel.setBounds(20, 10, 90, 25);
        infor_Panel.add(lblNewLabel);

        inforInner_Panel = new JPanel();
        inforInner_Panel.setPreferredSize(new Dimension(840, 600));
        inforInner_Panel.setLayout(null);

        JScrollPane infor_scrollPane = new JScrollPane(inforInner_Panel);
        infor_scrollPane.setBounds(0, 45, 840, 635);
        infor_Panel.add(infor_scrollPane);
        infor_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        infor_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        infor_scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        infor_scrollPane.setBorder(null);

//        chatShowInputUI = new ChatShowInputUI();
//        bottomPanel.add(chatShowInputUI);
    }

    /**
     * 加载好友
     */

    public void loadFriendList(int type) throws SQLException {


        i = 0;
        //群发消息实现
        ChatShowInputUI all_csUI = new ChatShowInputUI("ALL", "ALL", 1);     // 群界面
        FriendListUI all_person = new FriendListUI("群发消息", "群发", 1, this, all_csUI);    //左侧信息显示
        all_person.setBounds(0, this.getPersonList().size() * 70, 310, 70);

        if(flag == 0){
            this.personList.put(all_person, all_csUI);
            this.onlineListPanel.add(all_person);
            this.person_chat_List.put("ALL", all_csUI);
            myFriendsID.append("ALL,");
            flag = 1;
        }

        //单个好友实现
        ArrayList<String> friends = ClientAPI.u.getFriends();
        ArrayList<Integer> friends_uid = ClientAPI.u.getFriends_uid();

        System.out.println("在线ip:" + ClientAPI.online_uid.toString());
        System.out.println("好友id:" + friends_uid.toString());
        if (friends == null) return;
        for (String s : friends) {
            // 实例化一个好友Panel
//            String strID = String.valueOf(user.getId());
            String strName = s;

            ChatShowInputUI csUI = new ChatShowInputUI(friends_uid.get(i).toString(), strName, 0);     // 聊天界面
            FriendListUI person = null;
            person = new FriendListUI(friends_uid.get(i).toString(), strName, 0, this, csUI);    // 左侧好友列表的好友
            person.setBounds(0, this.getPersonList().size() * 70, 310, 70);


            // 添加进好友Map集合
            if(!lis.containsKey(strName)){

                this.personList.put(person, csUI);
                this.person_chat_List.put(friends_uid.get(i).toString(), csUI);
                this.lis_online.put(Integer.toString(friends_uid.get(i)), person);
                lis.put(strName, "离线");

                // 添加到主界面2
                this.onlineListPanel.add(person);      // 好友面板添加进界面
                // 添加到myFriendsID
                myFriendsID.append(friends_uid.get(i) + ",");

            }
            i = i + 1;
        }

        for(Integer uid : friends_uid){
            if(ClientAPI.online_uid.contains(Integer.toString(uid))){
                System.out.println("在线设置" + uid);
                this.lis_online.get(Integer.toString(uid)).set_online(1);
            }else{
                System.out.println("离线设置" + uid);
                this.lis_online.get(Integer.toString(uid)).set_online(0);
            }
        }
        //用于控制服务端消息显示
        ClientAPI.set_personList(person_chat_List);
    }



    //测试模块
    public static void main(String[] args) throws SQLException {
        ClientAPI.u.login("test", "test");
        ClientUI c = new ClientUI("admin", 22);
        c.setClientUI(c);
        c.setVisible(true);
    }
}
