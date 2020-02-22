package TalkSocket.GUI;

import TalkSocket.Client.ClientAPI;
import TalkSocket.GUI.overrideClass.*;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.sql.SQLException;


public class LoginUI extends JFrame {

    private JPanel contentPane;
    private JTextField textUser;    // 登陆用户名==手机号码
    private JTextField textPsw;
    private int mouseAtX = 0;
    private int mouseAtY = 0;
    private JTextField textField;
    private JLabel loginCaution;
    private JLabel loginCautionText;
    private JLabel codeCaution;
    private JTextField codeField;
    private Object[] codeObjects;
    private JLabel codeImage;
    private JPanel codePanel;
    private JPanel loginPanel;
    private String strUser;        // 输入的账号
    private String strPsw;        // 输入的密码

    /**
     * Launch the application.
     */
//	public static void main(String[] args) {
//		new LoginUI();
//	}

    /**
     * Create the frame.
     * 登陆主界面上部分
     */
    public LoginUI() {
        init();
        try {
            this.setLocationRelativeTo(null);
            // 去除标题栏
            this.setUndecorated(true);
            // 不可改变窗体大小
            this.setResizable(false);
            // 圆角窗体
            AWTUtilities.setWindowShape(this,
                    new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 15.0D, 15.0D));
            this.validate();
            this.setVisible(true);
            // 获得焦点
            this.textUser.requestFocus(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginUI.class.getResource("/TalkSocket/GUI/images/icon.png")));
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

        setTitle("登录窗体");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 532, 410);
//		setBounds(0, 0, 532, 510);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel TopPanel = new JPanel();
        TopPanel.setBounds(0, 0, 532, 200);
        contentPane.add(TopPanel);
        TopPanel.setLayout(null);

        // 关闭按钮
        JButton btnClose = new JButton("");
        // 点击关闭按钮，释放资源
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setBackground(new Color(70, 118, 140));
            }
        });
        btnClose.setBackground(new Color(70, 118, 140));
        btnClose.setIcon(new ImageIcon(LoginUI.class.getResource("/TalkSocket/GUI/images/close.png")));
        btnClose.setBorder(null);
        btnClose.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        btnClose.setBounds(502, 9, 20, 20);
        TopPanel.add(btnClose);

        // 最小化按钮
        JButton btnZxh = new JButton("");
        btnZxh.setBackground(new Color(70, 118, 140));
        btnZxh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnZxh.setIcon(new ImageIcon(LoginUI.class.getResource("/TalkSocket/GUI/images/zxh_1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnZxh.setIcon(new ImageIcon(LoginUI.class.getResource("/TalkSocket/GUI/images/zxh.png")));
            }
        });
        btnZxh.setIcon(new ImageIcon(LoginUI.class.getResource("/TalkSocket/GUI/images/zxh.png")));
        btnZxh.setBorder(null);
        btnZxh.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        btnZxh.setBounds(475, 9, 20, 20);
        TopPanel.add(btnZxh);

        JLabel Bgimg = new JLabel("");
        Bgimg.setIcon(new ImageIcon(LoginUI.class.getResource("/TalkSocket/GUI/images/img.jpg")));
        Bgimg.setBounds(0, 0, 532, 200);
        TopPanel.add(Bgimg);

        // 显示登录界面
        loginPanel();
        // 显示验证码界面
//		codePanel();
    }

    // 登录界面
    public void loginPanel() {
        loginPanel = new JPanel();
        loginPanel.setBackground(new Color(238, 244, 246));
        loginPanel.setBounds(0, 200, 532, 210);
        contentPane.add(loginPanel);
        loginPanel.setVisible(true);
        loginPanel.setLayout(null);

        JLabel User = new JLabel("用户名：");
        User.setFont(new Font("宋体", Font.PLAIN, 20));
        User.setBounds(184, 13, 80, 40);
        loginPanel.add(User);

        JLabel Psw = new JLabel("密  码：");
        Psw.setFont(new Font("宋体", Font.PLAIN, 20));
        Psw.setBounds(184, 66, 80, 40);
        loginPanel.add(Psw);

        textUser = new JTextField();
        textUser.setFont(new Font("宋体", Font.PLAIN, 20));
        textUser.setBounds(270, 13, 200, 40);
        loginPanel.add(textUser);
        textUser.setColumns(10);

        textPsw = new JPasswordField();
        // 回车事件，回车点击登录 按钮
        textPsw.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        loginButtonActionPerformed();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        textPsw.setFont(new Font("宋体", Font.PLAIN, 20));
        textPsw.setColumns(10);
        textPsw.setBounds(270, 66, 200, 40);
        loginPanel.add(textPsw);

        // 登陆失败提示
        loginCaution = new JLabel("");
        loginCaution.setIcon(new ImageIcon(LoginUI.class.getResource("/TalkSocket/GUI/images/caution.png")));
        loginCaution.setBounds(277, 110, 16, 16);
        loginCaution.setVisible(false);
        loginPanel.add(loginCaution);

        loginCautionText = new JLabel("");
        loginCautionText.setForeground(new Color(216, 30, 6));
        loginCautionText.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        loginCautionText.setBounds(295, 109, 135, 18);
        loginCautionText.setVisible(false);
        loginPanel.add(loginCautionText);

        // 头像
        RJPanel TouXiangPanel = new RJPanel();
        TouXiangPanel.setBounds(54, 13, 100, 100);
        loginPanel.add(TouXiangPanel);

        RJLabel TouXiangLabel = new RJLabel("", 0, 0);
        TouXiangPanel.add(TouXiangLabel);
        TouXiangLabel.setIcon(new ImageIcon(LoginUI.class.getResource("/TalkSocket/GUI/images/tx.jpg")));

        // 注册按钮
        RJButton btnRegister = new RJButton("注 册");
        btnRegister.setBounds(335, 150, 115, 40);
        loginPanel.add(btnRegister);
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                registerButtonActionPerformed(arg0);
            }
        });
        btnRegister.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        btnRegister.setBackground(new Color(105, 155, 178));

        // 登录按钮
        RJButton btnLogin = new RJButton("登 录");
        btnLogin.setBounds(74, 150, 115, 40);
        loginPanel.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    loginButtonActionPerformed();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLogin.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        btnLogin.setBackground(new Color(105, 155, 178));
//		return loginPanel;
    }

    // 登陆事件,并与服务端建立连接
    public void loginButtonActionPerformed() throws IOException, SQLException {
        strUser = textUser.getText().trim();
        strPsw = textPsw.getText().trim();
        if (strUser.equals("") || strPsw.equals("")) {
            loginCautionText.setText("请输入账号或密码");
            loginCaution.setVisible(true);
            loginCautionText.setVisible(true);
        } else {

            if(ClientAPI.u.login(strUser, strPsw)){
                //关闭登录界面
                dispose();
                //显示聊天界面
                ClientUI cui = new ClientUI(ClientAPI.u.username, ClientAPI.u.uid);
                cui.setClientUI(cui);
                //与服务器建立连接
                new ClientAPI();
            }else{
                loginCautionText.setText("账号或密码错误");
                loginCaution.setVisible(true);
                loginCautionText.setVisible(true);
            }
        }
    }

    // 注册事件
    public void registerButtonActionPerformed(ActionEvent arg0) {
        final LoginUI login = this;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterUI reg = new RegisterUI(login);
                    reg.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 登陆界面隐藏
        setVisible(false);
    }

}
