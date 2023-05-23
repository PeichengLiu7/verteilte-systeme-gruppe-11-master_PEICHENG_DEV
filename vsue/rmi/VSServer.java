package vsue.rmi;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class VSServer {
    //这里的ServerSocket是java.net.ServerSocket
    //我在服务器的函数
    //这里的ServerSocket是java.net.ServerSocket,通过这个类可以创建一个服务器
    //执行客户端发来的请求，实现调用客户端的功能
    //把客户端的请求转化为服务器的功能
    //注册表接受 你的个人信息过来，然后在服务端做处理
    //然后在给你返回一个结果
    private ServerSocket serverSocket;

    public VSServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            //监听客户端的请求，如果有客户端请求，就创建一个socket与客户端连接
            WorkerThread workerThread = new WorkerThread(socket);
            //创建一个线程，这个线程的作用是，把客户端的请求转化为服务器的功能
            //然后把服务器的功能的结果返回给客户端
            //this thread is used to convert the request from the client to the function of the server
            //and then return the result of the function to the client
            workerThread.start();
            //启动这个线程
        }
    }

    private class WorkerThread extends Thread {
        private Socket socket;

        public WorkerThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                VSObjectConnection objectConnection = new VSObjectConnection(socket);
                while (true) {
                    Object receivedObject = objectConnection.receiveObject();
                    objectConnection.sendObject((Serializable) receivedObject);
                    //这个循环的作用是，服务器不断的接受客户端的请求，然后把客户端的请求转化为服务器的功能
                    //然后把服务器的功能的结果返回给客户端
                    //this is a loop, the server will keep receiving the request from the client
                    //and then convert the request from the client to the function of the server
                    //and then return the result of the function to the client
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            int serverPort = 54321;
            VSServer server = new VSServer(serverPort);
            System.out.println("Server is running on port " + serverPort);
            server.start();
        } catch (IOException e) {
            System.err.println("Error while starting the server: " + e.getMessage());
        }
    }
}
