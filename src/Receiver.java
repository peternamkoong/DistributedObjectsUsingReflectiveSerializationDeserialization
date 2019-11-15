import org.jdom2.Document;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Receiver {

    public static void main(String[] args) {
        try{
            int port = 4000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("-------------------------------------------------------");
            System.out.println("Server initialized on port: " + port);
            Socket socket = serverSocket.accept();
            System.out.println("Sender has connected to the server.");
            System.out.println("-------------------------------------------------------");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Document being transfer from Sender...");
            Document doc = (Document)inputStream.readObject();
            System.out.println("Transfer complete.");
            System.out.println("-------------------------------------------------------");
            Deserializer deserializer = new Deserializer();
            System.out.println("Deserializing document...");
            ArrayList<Object> deserializedObjects = deserializer.deserialize(doc);
            System.out.println("Deserialization complete.");
            System.out.println("-------------------------------------------------------");
            Inspector inspect = new Inspector();
            System.out.println("Inspecting objects...");
            for (int i = 0; i<deserializedObjects.size();i++){
                inspect.inspect(deserializedObjects.get(i),false);
            }
            System.out.println("Inspection complete.");
            System.out.println("-------------------------------------------------------");
            socket.close();
        }catch  (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
