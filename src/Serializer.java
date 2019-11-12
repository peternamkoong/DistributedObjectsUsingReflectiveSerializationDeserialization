
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {


    public Document serialize(Object obj) {

        Element rootElement = new Element("serialized");
        Document doc = new Document(rootElement);
        IdentityHashMap  ihm = new IdentityHashMap();


        rootElement.addContent(serializeObject(obj,ihm));


        try {
            new XMLOutputter().output(doc, System.out);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("Serialized.xml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return doc;

    }

    public static Element serializeField(Field fields) {
        Element fieldElement = new Element("field");
        fieldElement.setAttribute(new Attribute("fieldname",fields.getName()));
        fieldElement.setAttribute(new Attribute("declaringclass", fields.getDeclaringClass().getSimpleName()));
        return fieldElement;
    }

    public static Element serializeArray(Class fieldType, Object value, IdentityHashMap ihm) {
        Element arrayElement = new Element("Object");
        String id = Integer.toString(ihm.size());
        ihm.put(id,value);
        arrayElement.setAttribute(new Attribute("class",fieldType.getSimpleName()));
        arrayElement.setAttribute(new Attribute("id",id));
        arrayElement.setAttribute(new Attribute("length", String.valueOf(Array.getLength(value))));
        return arrayElement;
    }

    public static Element serializePrimitive(Object value) {
        Element valueElement = new Element("value");
        valueElement.setText(value.toString());
        return valueElement;
    }

    public static Element serializeReference(Object value) {
        Element referenceElement = new Element("Reference");
        return referenceElement;
    }

    public static Element serializeObject(Object obj, IdentityHashMap ihm) {
        Class c = obj.getClass();
        String id = Integer.toString(ihm.size());
        ihm.put(id, obj);
        Element objectElement = new Element("Object");
        objectElement.setAttribute(new Attribute("class", c.getName()));
        objectElement.setAttribute(new Attribute("id", id));

        try {
            Field[] objectFields = c.getDeclaredFields();
            for(Field fields : objectFields){
                fields.setAccessible(true);
                Element fieldElement = serializeField(fields);
                objectElement.addContent(fieldElement);

                Class fieldType = fields.getType();
                Object value = fields.get(obj);

                if(fieldType.isArray()) {
                    Class arrayType = fieldType.getComponentType();
                    Element arrayElement = serializeArray(fieldType, value, ihm);
                    fieldElement.addContent(arrayElement);
                    if(arrayType.isPrimitive()) {
                        for (int i = 0; i < Array.getLength(value); i++) {
                            Element newElement = serializePrimitive(Array.get(value, i));
                            arrayElement.addContent(newElement);
                        }
                    }
                    else{
                        System.out.println("Length:" + Array.getLength(value));
                        for (int i = 0; i < Array.getLength(value); i++) {
                            Object index = Array.get(value, i);
                            if (index!=null) {
                                Element newElement = serializeReference(index);
                                newElement.addContent(serializeObject(index, ihm));
                                arrayElement.addContent(newElement);
                            }
                        }
                    }
                }
                else if (fieldType.isPrimitive()){
                    Element valueElement = serializePrimitive(value);
                    fieldElement.addContent(valueElement);
                }
                else {
                    Element referenceElement = serializeReference(value);
                    fieldElement.addContent(referenceElement);

                    Element newElement = serializeObject(value, ihm);
                    referenceElement.addContent(newElement);
                }
            }
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return objectElement;
    }
}
