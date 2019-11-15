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
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Document doc = (Document)inputStream.readObject();
            Deserializer2 deserializer = new Deserializer2();
            ArrayList<Object> deserializedObjects = deserializer.deserialize(doc);
            Inspector inspect = new Inspector();
            for (int i = 0; i<deserializedObjects.size();i++){
                inspect.inspect(deserializedObjects.get(i),false);
            }
            socket.close();
        }catch  (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
