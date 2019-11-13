import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Sender {

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 4000;

        ObjectCreator creator = new ObjectCreator();
        Serializer serializer = new Serializer();
        ArrayList<Object> list = creator.Creator();
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("-------------------------------");
            System.out.println("Starting Serialization...");
            Document doc = serializer.serialize(list);
            new XMLOutputter().output(doc,System.out);
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.setFormat(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileWriter("file.xml"));
            System.out.println("Serialization Complete.");
            System.out.println("-------------------------------");

            System.out.println("-------------------------------");
            System.out.println("Sending document to Receiver...");
            sendObject(socket, doc);
            System.out.println("Document Sent.");
            System.out.println("-------------------------------");
            socket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }

//    public static Document serialization(ArrayList<Object> list) {
//        Serializer serializer = new Serializer();
//        System.out.print("Serializing " + obj.getClass().getName() + ".....");
//        Document doc = serializer.serialize(obj);
//        System.out.println("Complete.");
//        return doc;
//    }

    public static void sendObject(Socket socket, Document doc) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        Object object = doc;
        outputStream.writeObject(doc);
        outputStream.flush();
    }
}
