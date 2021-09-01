package edu.school21.reflection.app;

import sun.jvm.hotspot.debugger.NoSuchSymbolException;

import java.lang.reflect.*;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = null;

        System.out.println("Classes:");
        System.out.println("User");
        System.out.println("Car");
        System.out.println("---------------------");
        System.out.println("Enter class name:");

        input = s.nextLine();
        System.out.println("---------------------");
        Class<?> cl = null;
        try {
            switch (input) {
                case "User":
                    cl = Class.forName("edu.school21.reflection.classes.user.User");
                    break;
                case "Car":
                    cl = Class.forName("edu.school21.reflection.classes.car.Car");
                    break;
                default:
                    System.out.printf("Unknown class '%s'\n", input);
                    return;
            }
        } catch (ClassNotFoundException e) {
            System.out.printf("Could not find Class '%s'\n", input);
            return;
        }

        Field[] f = cl.getDeclaredFields();
        System.out.println("fields:");
        for (int i = 0; i < f.length; i++) {
            System.out.printf("\t%s %s\n", f[i].getType().getSimpleName(), f[i].getName());
        }

        Method[] m = cl.getDeclaredMethods();

        System.out.println("methods:");
        for (int i = 0; i < m.length; i++) {
            if (!m[i].getName().equals("toString")) {
                System.out.printf("%s %s(", m[i].getReturnType().getSimpleName(), m[i].getName());
                for (int j = 0; j < m[i].getParameterCount(); ++j) {
                    System.out.printf("%s", m[i].getParameterTypes()[j].getSimpleName());
                    if (j < m[i].getParameterCount() - 1) {
                        System.out.print(", ");
                    }
                    System.out.println(")");
                }
            }
        }
        System.out.println("---------------------");
        System.out.println("Let's create an object.");
        Class<?>[] partypes = new Class[cl.getDeclaredFields().length];
        Object[] arglist = new Object[cl.getDeclaredFields().length];
        for (int i = 0; i < partypes.length; ++i) {
            System.out.printf("%s:\n", f[i].getName());
            input = s.nextLine();

            partypes[i] = f[i].getType();
            try {
                arglist[i] = partypes[i].getConstructor(String.class).newInstance(input);
            } catch (Exception e) {
                System.out.printf("Could not create field %s\n", f[i].getName());
                return;
            }
        }

        try {
            Constructor<?> ct = cl.getConstructor(partypes);
            Object created = ct.newInstance(arglist);
            System.out.printf("Object created: %s\n", created);
        } catch (Exception e) {
            System.out.printf("Failed to create Object: %s\n", e.getMessage());
            return;
        }
        System.out.println("---------------------");
    }
}
