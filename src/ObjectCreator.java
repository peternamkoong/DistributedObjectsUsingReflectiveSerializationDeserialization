import java.util.ArrayList;
import java.util.Scanner;

public class ObjectCreator {
    private static ArrayList<Object> list = new ArrayList<Object>();
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
        for (int i = 0; i<list.size(); i++) {
            System.out.println("*****************");
            System.out.println(list.get(i).toString());
        }
    }
    public static void PrintObjectCreation(String object){
        System.out.println("------------------------------------------------------------\n" +
                           "Creating Object with " + object+ "...\n" +
                           "------------------------------------------------------------");
    }


    public static ObjectPrimitiveVariables CreateObjectPrimitiveVariables() {
        PrintObjectCreation("Primitive Variables");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an integer value: ");
        while (!scanner.hasNextInt()) {
            System.out.print("That is not an integer. Please try again: ");
            scanner.next();
        }
        int A = scanner.nextInt();
        System.out.println("Integer A has been set to " + A + ".");

        System.out.print("Enter a Double value: ");
        while (!scanner.hasNextDouble()) {
            System.out.print("That is not a Double. Please try again: ");
            scanner.next();
        }
        Double B = scanner.nextDouble();
        System.out.println("Double B has been set to " + B + ".");

        System.out.print("Enter a Boolean value: ");
        while (!scanner.hasNextBoolean()) {
            System.out.print("That is not a Boolean. Please try again: ");
            scanner.next();
        }
        Boolean C = scanner.nextBoolean();
        System.out.println("Boolean C has been set to " + C + ".");
        ObjectPrimitiveVariables opv = new ObjectPrimitiveVariables(A,B,C);
        System.out.println("------------------------------------------------------------\n" +
                           "Object Created. Values are: " +
                           "\nInt A = " + A +
                           "\nDouble B = " + B +
                           "\nBoolean C = " + C +
                           "\n------------------------------------------------------------");
        return opv;
    }

    public static ObjectReferenceObjects CreateObjectReferenceObjects() {
        PrintObjectCreation("Referential Object");
        System.out.println("Initializing Object...");
        ObjectPrimitiveVariables refOPV = CreateObjectPrimitiveVariables();
        ObjectReferenceObjects oro = new ObjectReferenceObjects(refOPV);
        System.out.println(
                "------------------------------------------------------------\n" +
                "Object Created. References: " + refOPV.getClass().getName() +
                "\n------------------------------------------------------------");
        return oro;
    }

    public static ObjectPrimitiveArray CreateObjectPrimitiveArray() {
        PrintObjectCreation("Primitive Array");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length of array: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid entry. Please try again: ");
            scanner.next();
        }
        int length = scanner.nextInt();
        int[] primArray = new int[length];
        System.out.println("Integer Array of length " + length + " initialized.");

        for (int i = 0; i<length;i++) {
            System.out.print("Array[" + i + "]: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid entry. Please try again: ");
                scanner.next();
            }
            int index = scanner.nextInt();
            primArray[i] = index;
        }
        ObjectPrimitiveArray opa = new ObjectPrimitiveArray(primArray);
        return opa;
    }

    public static ObjectReferenceArray CreateObjectReferenceArray() {
        PrintObjectCreation("Reference Array");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length of array: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid entry. Please try again: ");
            scanner.next();
        }
        int length = scanner.nextInt();
        ObjectPrimitiveVariables[] refArray = new ObjectPrimitiveVariables[length];
        System.out.println("Integer Array of length " + length + " initialized.");

        for (int i = 0; i<length;i++) {
            System.out.println("Array[" + i + "]: ");
            refArray[i] = CreateObjectPrimitiveVariables();
        }
        ObjectReferenceArray ora = new ObjectReferenceArray(refArray);
        return ora;

    }

    public static ObjectJavaCollection CreateObjectJavaCollection() {
        PrintObjectCreation("Java Collection");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of objects to add to collection: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid entry. Please try again: ");
            scanner.next();
        }
        int length = scanner.nextInt();
        ArrayList<ObjectPrimitiveVariables> list = new ArrayList<>();
        for (int i = 0; i<length; i++) {
            list.add(CreateObjectPrimitiveVariables());
        }
        ObjectJavaCollection ojc = new ObjectJavaCollection(list);
        return ojc;
    }

    public static int Decision() {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Please select the type of object you would like to create:\n" +
                    "1) Object with only primitive instance variables.\n" +
                    "2) Object that contains references to other objects.\n" +
                    "3) Object that contains an array of primitives.\n" +
                    "4) Object that contains an array of object references.\n" +
                    "5) Object that uses an instance of one of Java's collection classes to refer to several other objects.\n" +
                    "6) Finish Adding Objects.");
            return scanner.nextInt();
    }
}
