package TalkSocket.GUI;

import TalkSocket.Client.ClientAPI;
import TalkSocket.Database.UsersAPI;

import java.io.IOException;
import java.sql.SQLException;

public class RunGUI {

    public String user;
    public String pass;
    public int uid;
    public ClientAPI cli;       //连接控制

    public RunGUI(String user, String pass) throws IOException {
        this.user = user;
        this.pass = pass;
        this.uid = ClientAPI.u.uid;
    }

    public static void main(String[] args) {
        LoginUI loginui = new LoginUI();
        loginui.setVisible(true);
    }

}
