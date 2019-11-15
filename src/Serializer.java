
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IdentityHashMap;

import org.jdom2.*;


public class Serializer {

    private IdentityHashMap ihm = new IdentityHashMap<>();

    public Document serialize(ArrayList<Object> obj) {

        Element rootElement = new Element("serialized");
        Document doc = new Document(rootElement);
        String id = null;
        for (int i = 0; i<obj.size();i++) {
            id = getIdentifier(obj, ihm);
            rootElement.addContent(serializeObject(obj.get(i), rootElement, id));

        }
        return doc;

    }

    public Element serializeField(Field fields) {
        Element fieldElement = new Element("field");
        fieldElement.setAttribute(new Attribute("fieldname",fields.getName()));
        fieldElement.setAttribute(new Attribute("declaringclass", fields.getDeclaringClass().getName()));
        return fieldElement;
    }

    public Element serializeArray(Class fieldType, Object value, String id) {
        Element arrayElement = new Element("object");
        arrayElement.setAttribute(new Attribute("class",fieldType.getName()));
        arrayElement.setAttribute(new Attribute("id",id));
        arrayElement.setAttribute(new Attribute("length", String.valueOf(Array.getLength(value))));
        return arrayElement;
    }

    public Element serializePrimitive(Object value) {
        Element valueElement = new Element("value");
        valueElement.setText(value.toString());
        return valueElement;
    }

    public Element serializeReference(Object value, String id) {
        Element referenceElement = new Element("reference");
        referenceElement.setText(id);
        return referenceElement;
    }

    public String getIdentifier(Object obj, IdentityHashMap ihm){
        String id = Integer.toString(ihm.size());
        ihm.put(id,obj);
        return id;
    }

    public Element serializeObject(Object obj,Element root, String id) {
        Class c = obj.getClass();

        Element objectElement = new Element("object");
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
                    String arrayId = getIdentifier(value, ihm);
                    Element arrayElement = serializeArray(fieldType, value, arrayId);
                    root.addContent(arrayElement);
                    if(arrayType.isPrimitive()) {
                        for (int i = 0; i < Array.getLength(value); i++) {
                            Element newElement = serializePrimitive(Array.get(value, i));
                            arrayElement.addContent(newElement);
                        }
                    }
                    else{
                        for (int i = 0; i < Array.getLength(value); i++) {
                            Object index = Array.get(value, i);
                            if (index!=null) {
                                String newId = getIdentifier(index, ihm);
                                Element referenceElement = serializeReference(index,newId);
                                arrayElement.addContent(referenceElement);
                                root.addContent(serializeObject(index, root, newId));
                            }
                        }
                    }
                }
                else if (fieldType.isPrimitive()){
                    Element valueElement = serializePrimitive(value);
                    fieldElement.addContent(valueElement);
                }
                else {
                    String newId = getIdentifier(value, ihm);
                    Element referenceElement = serializeReference(value,newId);
                    fieldElement.addContent(referenceElement);

                    Element newElement = serializeObject(value,root, newId);
                    root.addContent(newElement);
                }
            }
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return objectElement;
    }
}
