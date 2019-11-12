import java.util.ArrayList;
import java.util.Scanner;

public class ObjectCreator {

    private static ArrayList<Object> list = new ArrayList<Object>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean check = true;
        while(check) {
            int selection = Decision();
            switch (selection) {
                case 1:
                    list.add(CreateObjectPrimitiveVariables());
                    break;
                case 2:
                    list.add(CreateObjectReferenceObjects());
                    break;
                case 3:
                    list.add(CreateObjectPrimitiveArray());
                    break;
                case 4:
                    list.add(CreateObjectReferenceArray());
                    break;
                case 5:
                    list.add(CreateObjectJavaCollection());
                    break;
                case 6:
                    check = false;
                    break;
                default:
                    System.out.println("Incorrect Selection.\n");
                    break;
            }
        }
    }

    public static int Decision() {
        System.out.println("Please select the type of object you would like to create:\n" +
                "1) Object with only primitive instance variables.\n" +
                "2) Object that contains references to other objects.\n" +
                "3) Object that contains an array of primitives.\n" +
                "4) Object that contains an array of object references.\n" +
                "5) Object that uses an instance of one of Java's collection classes to refer to several other objects.\n" +
                "6) Finish Adding Objects.");
        return scanner.nextInt();
    }

    public static void PrintObjectCreation(String object){
        System.out.println("------------------------------------------------------------\n" +
                           "Creating Object with " + object+ "...\n" +
                           "------------------------------------------------------------");
    }

    public static void printObjectCreated(String createdObject){
        System.out.println(
                        "------------------------------------------------------------\n" +
                        "Finished Creation: Object of " + createdObject +
                        "\n------------------------------------------------------------");
    }

    public static int validIntegerCheck(){
        while(!scanner.hasNextInt()) {
            System.out.print("Invalid Entry. Please try again: ");
            scanner.next();
        }
        int A = scanner.nextInt();
        return A;
    }

    public static double validDoubleCheck(){
        while(!scanner.hasNextDouble()) {
            System.out.print("Invalid Entry. Please try again: ");
            scanner.next();
        }
        double B = scanner.nextDouble();
        return B;
    }

    public static boolean validBooleanCheck(){
        while (!scanner.hasNextBoolean()) {
            System.out.print("Invalid Entry. Please try again: ");
            scanner.next();
        }
        Boolean C = scanner.nextBoolean();
        return C;
    }

    public static ObjectPrimitiveVariables CreateObjectPrimitiveVariables() {
        PrintObjectCreation("Primitive Variables");

        System.out.print("Enter an integer value: ");
        int A = validIntegerCheck();
        System.out.println("Integer A has been set to " + A + ".");

        System.out.print("Enter a Double value: ");
        double B = validDoubleCheck();
        System.out.println("Double B has been set to " + B + ".");

        System.out.print("Enter a Boolean value: ");
        boolean C = validBooleanCheck();
        System.out.println("Boolean C has been set to " + C + ".");

        ObjectPrimitiveVariables opv = new ObjectPrimitiveVariables(A,B,C);
        printObjectCreated("Primitive Variables");
        return opv;
    }

    public static ObjectReferenceObjects CreateObjectReferenceObjects() {
        PrintObjectCreation("Referential Object");
        System.out.println("Initializing Reference: Object of Primitive Variables...");
        ObjectPrimitiveVariables refOPV = CreateObjectPrimitiveVariables();
        ObjectReferenceObjects oro = new ObjectReferenceObjects(refOPV);
        printObjectCreated("Referential Object");
        return oro;
    }

    public static ObjectPrimitiveArray CreateObjectPrimitiveArray() {
        PrintObjectCreation("Primitive Array");
        System.out.print("Enter length of array: ");
        int length = validIntegerCheck();
        int[] primArray = new int[length];
        System.out.println("Integer Array of length " + length + " initialized.");

        for (int i = 0; i<length;i++) {
            System.out.print("Array[" + i + "]: ");
            int index = validIntegerCheck();
            primArray[i] = index;
        }

        ObjectPrimitiveArray opa = new ObjectPrimitiveArray(primArray);
        printObjectCreated("Primitive Array");
        return opa;
    }

    public static ObjectReferenceArray CreateObjectReferenceArray() {
        PrintObjectCreation("Reference Array");
        System.out.print("Enter length of array: ");
        int length = validIntegerCheck();

        ObjectPrimitiveVariables[] refArray = new ObjectPrimitiveVariables[length];
        System.out.println("Integer Array of length " + length + " initialized.");

        for (int i = 0; i<length;i++) {
            System.out.println("Array[" + i + "]: ");
            refArray[i] = CreateObjectPrimitiveVariables();
        }

        ObjectReferenceArray ora = new ObjectReferenceArray(refArray);
        printObjectCreated("Reference Array");
        return ora;

    }

    public static ObjectJavaCollection CreateObjectJavaCollection() {
        PrintObjectCreation("Java Collection");

        System.out.print("Enter number of objects to add to collection: ");
        int length = validIntegerCheck();

        ArrayList<ObjectPrimitiveVariables> list = new ArrayList<>();
        for (int i = 0; i<length; i++) {
            list.add(CreateObjectPrimitiveVariables());
        }

        ObjectJavaCollection ojc = new ObjectJavaCollection(list);
        printObjectCreated("Java Collection");
        return ojc;
    }
}