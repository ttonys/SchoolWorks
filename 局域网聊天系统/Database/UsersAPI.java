package TalkSocket.Database;

import java.util.ArrayList;

public class UsersAPI {
    public int uid;  //用户唯一识别标志
    public String username;
    public String password;


    //注册接口，判断是否成功
    public boolean register(String user, String pass) {
        this.username = user;
        this.password = pass;
        try {
            boolean res = DatabaseModel.addUser(user, pass);
            return res;
        } catch (Exception e) {
            System.out.println("注册异常");
        }

        return false;
    }


    //登录接口，检测用户名和密码正确性
    public  boolean login(String user, String pass) {
        try {

            boolean res = DatabaseModel.checkData(user, pass);
            if(res){
                this.username = user;
                this.uid = DatabaseModel.getUid(user, pass);
                return res;
            }else{
                return res;
            }
        } catch (Exception e) {
            System.out.println("登录异常");
        }

        return false;
    }

    //添加好友接口， 成功与(已添加or不存在)
    public boolean addFriends(int fri_uid){
        boolean res = DatabaseModel.addFriends(this.uid, fri_uid);
        return res;
    }

    //删除好友接口
    public  boolean delFriends(int fri_uid){
        boolean res = DatabaseModel.delFriends(this.uid, fri_uid);
        return res;
    }

    //获取好友列表
    public ArrayList<String> getFriends(){
        ArrayList<String> res = new ArrayList<String>();
        res = DatabaseModel.getFriends(this.uid);
        return res;
    }

    //获取好友uid
    public ArrayList<Integer> getFriends_uid(){
        ArrayList<Integer> res = new ArrayList<Integer>();
        res = DatabaseModel.getFriends_uid(this.uid);
        return res;
    }

}
