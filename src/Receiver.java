import org.jdom2.Document;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {
    public static void main(String[] args) {
        try{
            int port = 4000;
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Document doc = (Document)inputStream.readObject();
            Deserializer deserializer = new Deserializer();
            deserializer.deserialize(doc);
            socket.close();
        }catch  (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
