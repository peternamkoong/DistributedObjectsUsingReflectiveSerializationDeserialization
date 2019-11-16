import static org.junit.Assert.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SerializerTest {

    @Test
    public void SerializeTest() {
        Serializer serializer = new Serializer();
        ArrayList<Object> list = new ArrayList<>();
        Object primitiveVariables = new ObjectPrimitiveVariables(1, 2.0, true);
        list.add(primitiveVariables);
        Document doc = serializer.serialize(list);

        Element docElement = doc.getRootElement();
        List objectList = docElement.getChildren();
        Element object = (Element) objectList.get(0);
        List fieldList = object.getChildren();

        Element aField = (Element) fieldList.get(0);
        assertEquals("A", aField.getAttribute("fieldname").getValue());
        assertEquals("1", aField.getChildren().get(0).getText());

        Element bField = (Element) fieldList.get(1);
        assertEquals("B", bField.getAttribute("fieldname").getValue());
        assertEquals("2.0", bField.getChildren().get(0).getText());

        Element cField = (Element) fieldList.get(2);
        assertEquals("C", cField.getAttribute("fieldname").getValue());
        assertEquals("true", cField.getChildren().get(0).getText());
    }
}
