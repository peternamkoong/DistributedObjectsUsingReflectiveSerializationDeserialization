package tutorial;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveParser {

    public static void main(String[] args) {
        String fileName = "file3.xml";
        SAXBuilder builder = new SAXBuilder();

        try {
            Document doc = builder.build(new File(fileName));
            Element rootElement = doc.getRootElement(); //comapny

            List children = rootElement.getChildren();
            for (Object child_ : children) { //employee
                Element child = (Element) child_;

                Class objClass = Class.forName(child.getAttribute("class").getValue());
                Object obj = objClass.getConstructor().newInstance();
                List objChildren = child.getChildren();
                for(Object field_ : objChildren) { //fields
                    Element field = (Element)field_;
                    Field objField = objClass.getDeclaredField(field.getAttribute("name").getValue());
                    objField.setAccessible(true);
                    Class fieldType = objField.getType();
                    if (fieldType == java.lang.String.class) //check for primitives in assignment
                        objField.set(obj, field.getValue());
                    else
                        objField.set(obj, Integer.valueOf(field.getValue()));
                }
                System.out.println(obj.toString());
            }
        }

        catch(IOException | JDOMException | ClassNotFoundException | NoSuchMethodException
                | IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}