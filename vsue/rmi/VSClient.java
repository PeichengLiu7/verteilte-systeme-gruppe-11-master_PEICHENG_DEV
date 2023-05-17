package vsue.rmi;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class VSClient {
    private VSObjectConnection objectConnection;

    public VSClient(String serverHost, int serverPort) throws IOException {
        Socket socket = new Socket(serverHost, serverPort);
        objectConnection = new VSObjectConnection(socket);
    }

    public Object sendAndReceiveObject(Object obj) throws IOException, ClassNotFoundException {
        objectConnection.sendObject((Serializable) obj);
        return objectConnection.receiveObject();
    }
    public static void main(String[] args) {
        try {
            VSClient client = new VSClient(args[0], 54321);

            // Send test data to the server
//            String testData = "Hello, server!";

            VSTestMessage testData = new VSTestMessage(100, "Test", null);
            System.out.println("Sending data: " + testData);
            Object response = client.sendAndReceiveObject(testData);

            // Receive the server's response and print it to the console
            System.out.println("Received response: " + response);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while communicating with the server: " + e.getMessage());
        }
    }

}


