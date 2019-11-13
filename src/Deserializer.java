import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Deserializer {

    private ArrayList<Object> list = new ArrayList<>();

    public void deserialize(Document doc) {
        try {
            Element rootElement = doc.getRootElement(); //serialized
            List children = rootElement.getChildren();
            for (Object child_ : children) {//object
                Element child = (Element) child_;
                Class objClass = Class.forName(child.getAttribute("class").getValue());
                Object obj = null;
                List objChildren = child.getChildren();
                if (objClass.isArray()) {
                    int length = child.getAttribute("length").getIntValue();
                    Class arrayType = objClass.getComponentType();
                    obj = Array.newInstance(arrayType, length);
                    for (int i = 0; i < length; i++) {
                        Element index = (Element) objChildren.get(i);
                        if (index.getName().equals("value")) {
                            if (arrayType.equals(java.lang.String.class)) {
                                System.out.println("String");
                                Array.set(obj, i, String.valueOf(index.getText()));
                            }
                            else if (arrayType.equals(java.lang.Double.class))
                                Array.set(obj, i, Double.valueOf(index.getText()));
                            else if (arrayType.equals(java.lang.Integer.class))
                                Array.set(obj, i, Integer.valueOf(index.getText()));
                        }
                        System.out.println(Array.get(obj,0));
                        System.out.println(Array.get(obj,1));
                        System.out.println(Array.get(obj,2));
                        System.out.println(Array.get(obj,3));
                        System.out.println(Array.get(obj,4));

                    }
                } else {
                    obj = objClass.getConstructor().newInstance();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
