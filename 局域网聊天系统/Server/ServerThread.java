package TalkSocket.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 多线程服务端
 */

public class ServerThread implements Runnable {


    private Socket client = null;
    private Thread t = null;
    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {
            //输出流
            PrintStream out = new PrintStream(client.getOutputStream());

            //获取Socket的输入流，用来接收从客户端发送过来的数据
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String ThreadName = buf.readLine();

            //线程名字,client into map
            Thread.currentThread().setName(ThreadName);
            Service.clients.put(ThreadName, client);
            System.out.println("当前线程名字=" + Thread.currentThread().getName());

            //数据接收与处理
            boolean flag = true;
            while (flag) {

                //客户端数据处理
                String[] strs = ServerCommon.Message(buf.readLine());
                String uid = strs[0];
                String ip = strs[1];
                String mess = strs[2];
                System.out.println("uid=" + uid + ":  ip=" + ip + ":  mess=" + mess);


                //消息发送
                if(ip.equals("ALL")){

                    ServerCommon.All_Send_Message(uid, ip, mess);

                }else if(ip.equals("GET")){

                    ArrayList<String> res = ServerCommon.Get_Friend_Online();
                    String res_online = res.toString();
                    System.out.println("服务端发送结果" + res_online );
                    out.println(ip + ":" +  "GET" + ":" + res_online);

                }else{

                    boolean isOK = ServerCommon.SendMessage(uid, ip, mess);
                    if(isOK){
                        continue;
                    }else{
                        out.println(ip + ":" +  "ERROR" + ":" + "发送失败,对方不在线");
                    }
                }

            }
        } catch (Exception e) {
            //连接状态判断
            System.out.println("关闭连接");
            try {
                client.close();
            } catch (IOException ex) {
                System.out.println("client关闭");
            }
        }
    }

}  