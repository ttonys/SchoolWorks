package TalkSocket.Client;


import TalkSocket.Database.UsersAPI;
import TalkSocket.GUI.ChatShowInputUI;
import TalkSocket.GUI.ClientUI;
import TalkSocket.GUI.FriendListUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class ClientAPI {


    public static UsersAPI u = new UsersAPI(); //数据库操作
    public static Socket cli;                  //连接操控
    public static PrintStream out;             //输出数据到服务器
    public static BufferedReader buf;          //接受服务器数据
    public static ArrayList<String> online_uid = new ArrayList<>(); //在线uid
    public static HashMap<String, ChatShowInputUI> person_chat_List;


    public static void set_personList(HashMap<String, ChatShowInputUI> per){
        person_chat_List = per;
    }


    public ClientAPI() throws IOException {
        cli = new Socket("127.0.0.1", 8888);
        out = new PrintStream(cli.getOutputStream());
        buf =  new BufferedReader(new InputStreamReader(cli.getInputStream()));
        out.println(ClientAPI.u.uid);   //设置用户唯一标示

        new Thread(){
            @Override
            public void run() {
                try {
                    while (true){
                        String res = buf.readLine();
                        if(!res.equals(null)){
                            String ss[] = res.split(":");
                            System.out.println(Arrays.toString(ss));
                            System.out.println(ss[0] + "<><><>" + ss[1] + "<><><>" + ss[2]);
                            //群面板
                            if(ss[1].equals("ALL")){

                                person_chat_List.get(ss[1]).setShowAreaText("===>接受消息<===" + getTime() + "\n" + ss[2]);

                            }else if(ss[1].equals("ERROR") ){  //个人面板

                                person_chat_List.get(ss[0]).setShowAreaText("===>接受消息<===" + getTime() + "\n" + ss[2]);

                            }else if(ss[1].equals("GET")){
                                online_uid.clear();
                                String onss = ss[2].substring(1, ss[2].length() - 1);
                                if(ss[2].length() > 3){
                                    String[] online_ss = onss.split(",");
                                    for(String c : online_ss){
                                        online_uid.add(c.trim());
                                    }
                                }else{
                                       online_uid.add(onss) ;
                                }

                             }else{

                                person_chat_List.get(ss[0]).setShowAreaText("===>接受消息<===" + getTime() + "\n" + ss[2]);

                            }

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static void send_msg(String uid, String uip, String msg){

        out.println(uid + "=" + uip + ":" + msg);

    }





}
