package TalkSocket.Server;


import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ServerCommon {

    //数据处理
    public static String[] Message(String s){
        //uid,ip,message
        String id_ip = null, mess = null, ip = null, uid = null;
        int pos = s.indexOf(":");
        id_ip = s.substring(0, pos);
        uid = id_ip.split("=")[0];
        ip = id_ip.split("=")[1];
        mess = s.substring(pos + 1);
        String[] res = new String[]{uid, ip, mess};
        return res;
    }

    //点对点消息发送
    public static boolean SendMessage(String uid, String ip, String message){
        Socket client = Service.clients.get(ip);
        try{
            if(client.isClosed()){
                System.out.println("客户端状态为离线");
                return false;
            }else{
                PrintStream out = new PrintStream(client.getOutputStream());
                out.println(uid + ":" + ip + ":" + message);
                return true;
            }
        }catch (Exception e){
            System.out.println("点对点转发失败");
            return false;
        }

    }

    //群发消息
    public static void All_Send_Message(String uid, String ip, String message){
        for(Map.Entry<String, Socket> cl :  Service.clients.entrySet()){
            System.out.println("群发uid = " + cl.getKey());
            try{
                Socket client = cl.getValue();
                if(client.isClosed()){
                    continue;
                }else{
                    if(!cl.getKey().equals(uid)){
                        PrintStream out = new PrintStream(client.getOutputStream());
                        out.println(uid + ":" + ip + ":" + message);
                    }

                }
            }catch (Exception e){
                System.out.println("群发消息异常");
            }
        }

    }

    //好友在线列表获取
    public static ArrayList<String> Get_Friend_Online(){
        System.out.println("调用get_online函数");
        ArrayList<String> res = new ArrayList<String>();
        for(Map.Entry<String, Socket> cl :  Service.clients.entrySet()){
            try {
                String user_id = cl.getKey();
                Socket client = cl.getValue();
                if(client.isClosed()){
                    continue;
                }else{
                    res.add(user_id);
                }
            }catch (Exception e){
                System.out.println("好友获取异常");
            }
        }
        return res;
    }
}
