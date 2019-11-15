//import org.jdom2.DataConversionException;
//import org.jdom2.Document;
//import org.jdom2.Element;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.IdentityHashMap;
//import java.util.List;
//
//public class Deserializer {
//
//    private ArrayList<Object> list = new ArrayList<>();
//    private IdentityHashMap ihm = new IdentityHashMap<>();
//
//    public List deserialize(Document doc) {
//        Deserializer deserializer = new Deserializer();
//        List deserializedObjects = null;
//
//        try {
//            Element serializedElement = doc.getRootElement(); //serialized
//            List objectList = serializedElement.getChildren();
//
//            mapObjects(objectList, deserializer);
//
//            for (int i = 0; i < objectList.size(); i++) {
//                Object obj = objectList.get(i);
//                Object result = deserializeObjects(obj);
//                deserializedObjects.add(result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return deserializedObjects;
//    }
//
//    public Object createArrayObject(Element object, Class objectClass) {
//        int length = Integer.valueOf(object.getAttribute("length").getValue());
//        Class arrayType = objectClass.getComponentType();
//        Object arrayObject = Array.newInstance(arrayType, length);
//        return arrayObject;
//    }
//
//    public Object createObject(Class objectClass) throws Exception {
//
//        Constructor objectConstructor = objectClass.getConstructor();
//        Object object = objectConstructor.newInstance();
//        return object;
//    }
//
//    public void mapObjects(List objectList, Deserializer deserializer) throws Exception {
//        for (Object object_ : objectList) {
//            Element object = (Element) object_;
//
//            Class objectClass = getObjectClass(object);
//
//            String objectID = object.getAttribute("id").getValue();
//            Object createdObject = null;
//
//            if (objectClass.isArray())
//                createdObject = deserializer.createArrayObject(object, objectClass);
//            else
//                createdObject = deserializer.createObject(objectClass);
//            ihm.put(objectID, createdObject);
//        }
//    }
//
//    public Object deserializeObjects(Object obj) throws Exception {
//        Element objectElement = (Element) obj;
//        Object object = getObject(objectElement);
//
//        Class objectClass = getObjectClass(objectElement);
//
//        List objectChildren = objectElement.getChildren();
//        Object aObject = null;
//        if (objectClass.isArray()) {
//            aObject = deserializeArray(objectChildren, object, objectClass);
//            Array.set(object,)
//        } else {
//            aObject = deserializeClass(objectChildren, object, objectClass);
//        }
//        return aObject;
//    }
//
//    public Object deserializeValue(Class fieldType, Element leafElement) {
//        Object valueObject = null;
//        if (fieldType.equals(int.class)) {
//            valueObject = Integer.valueOf(leafElement.getText());
//        }
//        else if(fieldType.equals(double.class)) {
//            valueObject = Double.valueOf(leafElement.getText());
//        }
//        else if(fieldType.equals(boolean.class)) {
//            valueObject = Boolean.valueOf(leafElement.getText());
//        }
//        return valueObject;
//    }
//
//    public Object deserializeArray(List objectChildren, Object object, Class objectClass) throws Exception{
//        Object arrayElement = null;
//        Class arrayType = objectClass.getComponentType();
//
//        for (int j = 0; j < objectChildren.size(); j++) {
//            Element objChild = (Element) objectChildren.get(j);
//            String objectName = objChild.getName();
//
//            if (objectName.equals("value")) {
//                Object valueElement = deserializeValue(arrayType, objChild);
//                Array.set(object, j,valueElement);
//
//            } else if (objectName.equals("reference")) {
//                String refID = objChild.getText();
//                Object referenceObject = ihm.get(refID);
//                Object valueElement = deserializeObjects(referenceObject);
//                Array.set(object, j, valueElement);
//            }
//        }
//        return arrayElement;
//    }
//
//    public Object deserializeClass(List objectChildren, Object object, Class objectClass) throws Exception {
//        for (Object objChild_ : objectChildren) {
//            Element objChild = (Element) objChild_;
//            String objectName = objChild.getName();
//
//            Class declaringClass = Class.forName(objChild.getAttribute("declaringclass").getValue());
//            Field field = declaringClass.getDeclaredField(objChild.getAttribute("fieldname").getValue());
//            field.setAccessible(true);
//            Class fieldType = field.getType();
//            Element leafElement = objChild.getChildren().get(0);
//            if (leafElement.getName().equals("value")) {
//                if (fieldType.equals(int.class)) {
//                    field.set(object, Integer.valueOf(leafElement.getText()));
//                    System.out.println(field.getName() + ": " + field.get(object));
//                } else if (fieldType.equals(double.class)) {
//                    field.set(object, Double.valueOf(leafElement.getText()));
//                    System.out.println(field.getName() + ": " + field.get(object));
//                } else if (fieldType.equals(boolean.class)) {
//
//                }
//            } else if (leafElement.getName().equals("reference")) {
//                String refId = leafElement.getText();
//                //Object referenceObject = deserializedObjectFunction();
//                //field.set(object, referenceObject);
//                System.out.println("RefID: " + refId);
//                System.out.println("NOTArray: " + objectClass.getName() + " : " + objectName);
//            }
//        }
//        return object;
//    }
//
//    public Class getObjectClass(Element objectElement) throws ClassNotFoundException {
//        String className = objectElement.getAttribute("class").getValue();
//        Class objectClass = Class.forName(className);
//        return objectClass;
//    }
//
//    public Object getObject(Element objectElement) {
//        String id = objectElement.getAttribute("id").getValue();
//        Object object = ihm.get(id);
//        return object;
//    }
//
//
//}
//
//
////            for (int i = 0; i < objectList.size(); i++) {
////                Element objectElement = (Element) objectList.get(i);
////                String id = objectElement.getAttribute("id").getValue();
////                Object object = ihm.get(id);
////
////                Class objectClass = Class.forName(objectElement.getAttribute("class").getValue());
////
////                List objectChildren = objectElement.getChildren();
////                if (objectClass.isArray()) {
////                    Class arrayType = objectClass.getComponentType();
////                    for (int j=0; j<objectChildren.size();j++) {
////                        Element objChild = (Element) objectChildren.get(j);
////                        String objectName = objChild.getName();
////                        if (objectName.equals("value")) {
////                            if(arrayType.equals(int.class)) {
////                                Array.set(object, j, Integer.valueOf(objChild.getText()));
////                                System.out.println("["+j+"]: "+Array.get(object,j));
////                            }
////
////                            else if(arrayType.equals(double.class)) {
////                                Array.set(object, j, Double.valueOf(objChild.getText()));
////                                System.out.println("[" + j + "]: " + Array.get(object, j));
////                            }
////                            else if (arrayType.equals(boolean.class)){
////
////                            }
////                        } else if (objectName.equals("reference")) {
////                            System.out.println("Array: "+objectClass.getName() + " : " + objectName);
////                        }
////                    }
////                }
////                else {
////                    for (Object objChild_ : objectChildren) {
////                        Element objChild = (Element) objChild_;
////                        String objectName = objChild.getName();
////
////                            Class declaringClass = Class.forName(objChild.getAttribute("declaringclass").getValue());
////                            Field field = declaringClass.getDeclaredField(objChild.getAttribute("fieldname").getValue());
////                            field.setAccessible(true);
////                            Class fieldType = field.getType();
////                            Element leafElement = objChild.getChildren().get(0);
////                            if (leafElement.getName().equals("value")) {
////                                if (fieldType.equals(int.class)) {
////                                    field.set(object, Integer.valueOf(leafElement.getText()));
////                                    System.out.println(field.getName() + ": " + field.get(object));
////                                } else if (fieldType.equals(double.class)) {
////                                    field.set(object, Double.valueOf(leafElement.getText()));
////                                    System.out.println(field.getName() + ": " + field.get(object));
////                                } else if (fieldType.equals(boolean.class)) {
////
////                                }
////                            }
////                            else if (leafElement.getName().equals("reference")) {
////                                String refId = leafElement.getText();
////                                //Object referenceObject = deserializedObjectFunction();
////                                //field.set(object, referenceObject);
////                                System.out.println("RefID: " + refId);
////                                System.out.println("NOTArray: " + objectClass.getName() + " : " + objectName);
////                            }
////                    }
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////}
