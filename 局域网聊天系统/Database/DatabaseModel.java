package TalkSocket.Database;


import org.omg.PortableInterceptor.INACTIVE;

import java.sql.*;
import java.util.ArrayList;

/*
 * 定义对数据库的操作方法
 */
public class DatabaseModel {

    static Connection con = Database.getConnect();

    public DatabaseModel() throws SQLException {
    }

    //添加用户方法
    public static boolean addUser(String user, String pass){
        int is_reday_has = 0;
        //检测是否已注册
        try{
            try{
                String sql = "select * from users where username=?;";
                PreparedStatement temt = con.prepareStatement(sql);
                temt.setString(1, user);
                ResultSet res = temt.executeQuery();
                if(res.next()){
                    is_reday_has = 1;
                }else{
                    is_reday_has = 0;
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }catch (Exception e){
            System.out.println("检测存在异常");
        }
        if(is_reday_has == 0){
            try {
                String sql = "insert into users (username,password) values (?,?);";
                PreparedStatement temt = con.prepareStatement(sql);
                temt.setString(1, user);
                temt.setString(2, pass);
                int result = temt.executeUpdate();
                if(result > 0){
                    return true;
                }
            } catch (Exception e) {
                System.out.println("添加用户异常");
            }
        }
        return false;
    }

    //检查用户名和密码的正确性
    public static boolean checkData(String user, String pass){
        try{
            String sql = "select * from users where username=? and password=?;";
            PreparedStatement temt = con.prepareStatement(sql);
            temt.setString(1, user);
            temt.setString(2, pass);
            ResultSet res = temt.executeQuery();
            if(res.next()){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    //获取uid
    public static int getUid(String user, String pass){
        try{
            String sql = "select uid from users where username=? and password=?;";
            PreparedStatement temt = con.prepareStatement(sql);
            temt.setString(1, user);
            temt.setString(2, pass);
            ResultSet res = temt.executeQuery();
            if(res.next()){
                return res.getInt(1);
            }
        }catch (Exception e){
            System.out.println("获取uid错误");
        }
        return 0;
    }

    //根据uid添加好友
    public static boolean addFriends(int my_uid, int fri_uid){
        //是否注册uid账号
        int is_empty = 1;
        String sql = "select * from users where uid = ?;";
        PreparedStatement temt = null;
        PreparedStatement temt_empty = null;
        try {
            temt = con.prepareStatement(sql);
            temt.setInt(1, fri_uid);
            ResultSet res = temt.executeQuery();
            if(res.next()){
                is_empty = 0;
            }else{
                return false;
            }
            //是否添加好友
            sql = "select * from relation where my_uid = ? and fri_uid = ?;";
            temt_empty = con.prepareStatement(sql);
            temt_empty.setInt(1, my_uid);
            temt_empty.setInt(2, fri_uid);
            ResultSet res_empty = temt_empty.executeQuery();
            if(is_empty == 0 && !res_empty.next()){
                String add_sql = "insert into relation (my_uid,fri_uid) values (?,?);";
                PreparedStatement temt2 = con.prepareStatement(add_sql);
                temt2.setInt(1, my_uid);
                temt2.setInt(2, fri_uid);
                int result = temt2.executeUpdate();
                if(result > 0){
                    String add_sql3 = "insert into relation (my_uid,fri_uid) values (?,?);";
                    PreparedStatement temt3 = con.prepareStatement(add_sql3);
                    temt3.setInt(1, fri_uid);
                    temt3.setInt(2, my_uid);
                    int result3 = temt3.executeUpdate();
                    if(result3 > 0){
                        return  true;
                    }else{
                        return false;
                    }

                }else{
                    System.out.println("=-====");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加好友异常");
        }
        return false;
    }
    
    //删除好友功能
    public static boolean delFriends(int my_uid, int fri_uid){
        String sql = "delete from relation where my_uid = ? and fri_uid = ? ;";
        PreparedStatement temt = null;
        try {
            temt = con.prepareStatement(sql);
            temt.setInt(1,my_uid);
            temt.setInt(2,fri_uid);
            int res = temt.executeUpdate();
            if(res > 0){
                return true;
            }
        }catch (Exception e){
            System.out.println("删除好友异常");
        }
        return false;
    }


    //获取好友uid
    public static ArrayList<Integer> getFriends_uid(int my_uid){
        ArrayList<Integer> friends_uid = new ArrayList<Integer>();

        //uid列表获取
        try{
            String sql = "select fri_uid from relation where my_uid = ?;";
            PreparedStatement temt = con.prepareStatement(sql);
            temt.setInt(1, my_uid);
            ResultSet res = temt.executeQuery();
            while(res.next()){
                friends_uid.add(res.getInt(1));
            }
        }catch (Exception e){
            System.out.println("获取好友列表错误");
        }

        return friends_uid;
    }


    //指定uid获取好友列表
    public static ArrayList<String> getFriends(int my_uid){
        ArrayList<Integer> friends_uid = new ArrayList<Integer>();
        ArrayList<String> friends_name = new ArrayList<String>();

        //uid列表获取
        try{
            String sql = "select fri_uid from relation where my_uid = ?;";
            PreparedStatement temt = con.prepareStatement(sql);
            temt.setInt(1, my_uid);
            ResultSet res = temt.executeQuery();
            while(res.next()){
                friends_uid.add(res.getInt(1));
            }
        }catch (Exception e){
            System.out.println("获取好友列表错误");
        }

        //name获取
        try {
            String sql = "select username from users where uid = ?;";
            for(int fri_uid : friends_uid){
                PreparedStatement temt = con.prepareStatement(sql);
                temt.setInt(1, fri_uid);
                ResultSet res = temt.executeQuery();
                if(res.next()){
                    friends_name.add(res.getString(1));
                }
            }
        }catch (Exception e2){
            System.out.println("好友昵称获取错误");
        }
        return friends_name;
    }

}
