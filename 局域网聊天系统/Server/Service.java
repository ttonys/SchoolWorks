package TalkSocket.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Service {
    public static Map<String, Socket> clients = new HashMap<>();

    public static void main(String[] args) throws IOException {

        //开启服务
        ServerSocket server = new ServerSocket(8888);
        while (true){
            Socket client = server.accept();
            System.out.println("与客户端连接成功");
            new Thread(new ServerThread(client)).start();
        }
    }
}
