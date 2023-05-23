package vsue.rmi;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import java.io.*;
import java.net.Socket;

public class VSConnection {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public VSConnection(Socket socket) throws IOException {
        this.socket = socket;//创建一个socket与服务器连接
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
        outputStream.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
        //close的意思是关闭socket，inputStream，outputStream
    }

    public void sendChunk(byte[] chunk) throws IOException {
        // Write the length of the chunk (as an integer) before the actual data
        outputStream.write(chunk.length >> 24);
        outputStream.write(chunk.length >> 16);
        outputStream.write(chunk.length >> 8);
        outputStream.write(chunk.length);

        // Write the actual data
        outputStream.write(chunk);
        outputStream.flush();
    }

    public byte[] receiveChunk() throws IOException {
        // Read the length of the chunk (as an integer)
        int length = (inputStream.read() << 24) | (inputStream.read() << 16) | (inputStream.read() << 8) | inputStream.read();

        // Read the actual data
        byte[] chunk = new byte[length];
        //byte[] chunk = new byte[length]的意思是创建一个长度为length的字节数组,用来接收数据
        //chunk是要读取的字节数组
        //length是要读取的字节数
        int bytesRead = 0;
        while (bytesRead < length) {
            bytesRead += inputStream.read(chunk, bytesRead, length - bytesRead);//lenght的意思是要读取的字节数
            //bytesRead是已经读取的字节数
            //bytesRead = inputStream.read(chunk, bytesRead, length - bytesRead)的意思是从chunk的bytesRead位置开始读取，读取的字节数是length - bytesRead
        }

        return chunk;
    }
}


//public class VSConnection {
//    private Socket socket;
//    private InputStream inputStream;
//    private OutputStream outputStream;
//
//    public VSConnection(Socket socket) throws IOException {
//        this.socket = socket;
//        this.inputStream = socket.getInputStream();
//        this.outputStream = socket.getOutputStream();
//    }
//
//    public void sendChunk(byte[] chunk) throws IOException {
//        // Write the length of the chunk (as an integer) before the actual data
//        outputStream.write(chunk.length >> 24);
//        outputStream.write(chunk.length >> 16);
//        outputStream.write(chunk.length >> 8);
//        outputStream.write(chunk.length);
//
//        // Write the actual data
//        outputStream.write(chunk);
//        outputStream.flush();
//    }
//
//    public byte[] receiveChunk() throws IOException {
//        // Read the length of the chunk (as an integer)
//        int length = (inputStream.read() << 24) | (inputStream.read() << 16) | (inputStream.read() << 8) | inputStream.read();
//
//        // Read the actual data
//        byte[] chunk = new byte[length];
//        int bytesRead = 0;
//        while (bytesRead < length) {
//            bytesRead += inputStream.read(chunk, bytesRead, length - bytesRead);
//        }
//
//        return chunk;
//    }
//}


