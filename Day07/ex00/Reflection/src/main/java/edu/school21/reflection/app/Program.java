package edu.school21.reflection.app;

import edu.school21.reflection.factory.ClassFactory;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String className;
        String input;

        ClassFactory factory = new ClassFactory();
        try {
            factory.add("User", Class.forName("edu.school21.reflection.classes.user.User"));
            factory.add("Car", Class.forName("edu.school21.reflection.classes.car.Car"));

            System.out.println("Classes:");
            String[] classArray = factory.getAvailableClasses();
            for (String cl : classArray) {
                System.out.println(cl);
            }
            System.out.println("---------------------");
            System.out.println("Enter class name:");
            className = s.nextLine().trim();

            System.out.println("---------------------");
            ClassFactory.ClassInfo info = factory.getClassInfo(className);

            System.out.println("fields:");
            for (ClassFactory.FieldInfo field : info.fieldList) {
                System.out.printf("\t%s %s\n", field.type, field.name);
            }
            System.out.println("methods:");
            for (ClassFactory.MethodInfo method : info.methodList) {
                System.out.printf("\t%s\n", method.fullName);
            }

            System.out.println("---------------------");
            System.out.println("Let's create an object.");
            List<String> valueList = new LinkedList<>();
            for (ClassFactory.FieldInfo field : info.fieldList) {
                System.out.printf("%s\n", field.name);
                input = s.nextLine();
                if (!field.type.equals("String")) {
                    input = input.trim();
                }
                valueList.add(input);
            }
            Object obj = factory.createObject(className, valueList);

            System.out.printf("Object created: %s\n", obj);

            System.out.println("---------------------");
            System.out.println("Enter name of the field for changing:");
            final String fieldName = s.nextLine().trim();

            Optional<ClassFactory.FieldInfo> fieldInfo =
                    info.fieldList.stream()
                    .filter(field -> field.name.equals(fieldName))
                    .findFirst();
            if (!fieldInfo.isPresent()) {
                System.out.printf("Class '%s' does not have field '%s'\n", className, fieldName);
                return;
            }

            String fieldTypeStr = fieldInfo.get().type;
            System.out.printf("Enter %s value:\n", fieldTypeStr);
            input = s.nextLine().trim();
            if (!fieldTypeStr.equals("String")) {
                input = input.trim();
            }
            ClassFactory.changeField(fieldInfo.get(), input, obj);
            System.out.printf("Object updated: %s\n", obj);

            System.out.println("---------------------");
            System.out.println("Enter name of the method for call:");
            final String methodName = s.nextLine().trim();

            Optional<ClassFactory.MethodInfo> methodInfo =
                    info.methodList.stream()
                    .filter(method -> method.name.equals(methodName))
                    .findFirst();
            if (!methodInfo.isPresent()) {
                System.out.printf("Class '%s' does not have method '%s'\n", className, methodName);
                return;
            }

            List<String> paramList = new LinkedList<>();
            for (String type : methodInfo.get().paramList) {
                if (type.equalsIgnoreCase("void")) {
                    continue;
                }
                System.out.printf("Enter %s value:\n", type);
                input = s.nextLine();
                if (!type.equals("String")) {
                    input = input.trim();
                }
                paramList.add(input);
            }
            Object ret = ClassFactory.callMethod(obj, methodInfo.get().method, paramList);
            if (ret != null) {
                System.out.println("Method returned: ");
                System.out.println(ret);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Could not init ClassFactory");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
