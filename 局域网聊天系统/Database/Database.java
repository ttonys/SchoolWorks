package TalkSocket.Database;


import java.sql.Connection;
import java.sql.DriverManager;

/*
    数据库连接
    单例模式
 */
public class Database {

    private static Connection con = null;
    //数据库的url地址；?useUnicode=true&characterEncoding=utf-8解决数据库中文乱码问题
    private static String url = "jdbc:mysql://localhost:3306/socketqq?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
    //驱动的完整包名
    private static String driver = "com.mysql.cj.jdbc.Driver";
    //数据库的用户名
    private static String user = "root";
    //数据库的用户名密码
    private static String password = "root";

    static{
        try {
            Class.forName(driver);  //使用Class.forName()加载驱动
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //单例
    private Database(){

    }

    public static Connection getConnect(){
        return con;
    }
}