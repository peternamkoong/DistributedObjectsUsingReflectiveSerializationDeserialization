import org.jdom2.Element;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

public class Deserializer2 {

    private ArrayList<Object> list = new ArrayList<>();
    private HashMap ihm = new HashMap<>();

    public ArrayList<Object> deserialize(Document doc) {


        Element serializedElement = doc.getRootElement(); //Serialized
        List objectList = serializedElement.getChildren(); //all the Objects

        mapObjects(objectList); //map each object to the id

        for (int i = 0; i<objectList.size(); i++) {
            try {
                Object object = objectList.get(i);
                Object result = deserializeObject(object);
                list.add(result);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void mapObjects(List objectList) {
        try{
            for (Object object_ : objectList) {
                Element object = (Element) object_;

                String className = object.getAttribute("class").getValue();
                String objectID = object.getAttribute("id").getValue();

                Class objectClass = Class.forName(className);
                Object initializedObject = null;
                if (objectClass.isArray())
                    initializedObject = createArrayObject(object, objectClass);
                else
                    initializedObject = createObject(objectClass);
                ihm.put(objectID, initializedObject);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object createArrayObject(Element object, Class objectClass) {
        int length = Integer.parseInt(object.getAttribute("length").getValue());
        Class arrayType = objectClass.getComponentType();
        Object arrayObject = Array.newInstance(arrayType,length);
        return arrayObject;
    }

    public Object createObject(Class objectClass) throws Exception {
        Constructor objectConstructor = objectClass.getConstructor();
        Object constructorObject = objectConstructor.newInstance();
        return constructorObject;
    }

    public Object deserializeObject(Object object) throws Exception{
        Element objectElement = (Element) object;
        String id = objectElement.getAttribute("id").getValue();
        String className = objectElement.getAttribute("class").getValue();
        Object initializedObject = ihm.get(id);

        Class objectClass = Class.forName(className);

        List objectChildren = objectElement.getChildren();
        if (objectClass.isArray()) {
            Class arrayType = objectClass.getComponentType(); //type of array
            for (int j = 0; j<objectChildren.size(); j++) {
                Element objectChild = (Element) objectChildren.get(j);
                String objectName = objectChild.getName();

                if (objectName.equals("value")) {
                    Object valueElement = deserializeValue(arrayType, objectChild);
                    Array.set(initializedObject, j, valueElement);
                }
                else if(objectName.equals("reference")) {
                    String refID = objectChild.getText();
                    Object referenceObject = ihm.get(refID);
                    Object valueElement = referenceObject;
                    Array.set(initializedObject, j, valueElement);
                }
            }
        }
        else {
            for (Object objectChild_ : objectChildren) {
                Element objectChild = (Element) objectChild_;

                String objectName = objectChild.getName();
                String declaringClassName = objectChild.getAttribute("declaringclass").getValue();
                String fieldName = objectChild.getAttribute("fieldname").getValue();

                Class declaringClass = Class.forName(declaringClassName);

                Field field = declaringClass.getDeclaredField(fieldName);
                field.setAccessible(true);

                Class fieldType = field.getType();

                List leafElement = objectChild.getChildren();//either a value or reference
                for(Object leaf_ : leafElement){
                    Element leaf = (Element) leaf_;
                    String leafElementType = leaf.getName();
                    if (leafElementType.equals("value")) {
                        Object valueElement = deserializeValue(fieldType,leaf);
                        field.set(initializedObject,valueElement);
                    }
                    else if(leafElementType.equals("reference")){
                        String refID = objectChild.getValue();
                        Object referenceObject = ihm.get(refID);
                        Object valueElement = referenceObject;
                        field.set(initializedObject,valueElement);
                    }
                }
                }

        }
        return initializedObject;
    }

    public Object deserializeValue(Class fieldType, Element leafElement) {
        Object valueObject = null;
        if (fieldType.equals(int.class))
            valueObject = Integer.parseInt(leafElement.getText());

        else if(fieldType.equals(double.class))
            valueObject = Double.parseDouble(leafElement.getText());

        else if(fieldType.equals(boolean.class))
            valueObject = Boolean.parseBoolean(leafElement.getText());

        return valueObject;
    }
}
