package edu.school21.reflection.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ClassFactory {
    public class ClassInfo {
        public Class<?> classObject;
        public List<FieldInfo> fieldList = new LinkedList<>();
        public List<MethodInfo> methodList = new LinkedList<>();
    }

    public class FieldInfo {
        public Field field;
        public String name;
        public String type;

        public FieldInfo(Field field, String name, String type) {
            this.field = field;
            this.name = name;
            this.type = type;
        }
    }

    public class MethodInfo {
        public Method method;
        public String name;
        public String fullName;
        public List<String> paramList = new LinkedList<>();
    }

    private final Map<String, ClassInfo> classMap = new TreeMap<>();

    public void add(String name, Class<?> cl) {
        ClassInfo info = new ClassInfo();

        info.classObject = cl;

        Field[] f = cl.getDeclaredFields();
        for (Field field : f) {
            info.fieldList.add(new FieldInfo(field, field.getName(), field.getType().getSimpleName()));
        }

        Method[] m = cl.getDeclaredMethods();
        for (Method method : m) {
            if (!method.getName().equals("toString")) {
                MethodInfo methodInfo = new MethodInfo();
                methodInfo.method = method;

                StringJoiner joiner = new StringJoiner(", ", method.getName() + "(", ")");
                for (Class<?> param : method.getParameterTypes()) {
                    joiner.add(param.getSimpleName());
                    methodInfo.paramList.add(param.getSimpleName());
                }
                methodInfo.name = joiner.toString();
                methodInfo.fullName = String.format("%s %s", method.getReturnType().getSimpleName(), methodInfo.name);
                info.methodList.add(methodInfo);
            }
        }

        classMap.put(name, info);
    }

    public String[] getAvailableClasses() {
        return classMap.keySet().toArray(new String[0]);
    }

    public ClassInfo getClassInfo(String name) {
        ClassInfo cl = classMap.get(name);
        if (cl == null) {
            throw new RuntimeException(String.format("Could not find Class '%s'", name));
        }
        return cl;
    }

    public Object createObject(String name, List<String> valueList) {
        ClassInfo info = classMap.get(name);
        if (info == null) {
            throw new RuntimeException(String.format("Could not find Class '%s'", name));
        }
        Class<?> cl = info.classObject;

        Field[] f = cl.getDeclaredFields();
        Class<?>[] partypes = new Class[cl.getDeclaredFields().length];
        Object[] arglist = new Object[cl.getDeclaredFields().length];
        for (int i = 0; i < partypes.length; ++i) {
            partypes[i] = f[i].getType();
            try {
                arglist[i] = partypes[i].getConstructor(String.class).newInstance(valueList.get(i));
            } catch (Exception e) {
                throw new RuntimeException(String.format("Could not create field '%s'\n", f[i].getName()));
            }
        }

        try {
            Constructor<?> ct = cl.getConstructor(partypes);
            return ct.newInstance(arglist);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to create Object: %s\n", e.getMessage()));
        }
    }

    public static void changeField(FieldInfo fieldInfo, String value, Object obj) {
        try {
            Object valueObj = fieldInfo.field.getType().getConstructor(String.class).newInstance(value);
            fieldInfo.field.setAccessible(true);
            fieldInfo.field.set(obj, valueObj);
            fieldInfo.field.setAccessible(false);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not change value of field '%s'\n", fieldInfo.name));
        }
    }

    public static Object callMethod(Object obj, Method m, List<String> paramList) {
        Class<?>[] paramTypes = m.getParameterTypes();
        Object[] arglist = new Object[paramList.size()];
        try {
            for (int i = 0; i < arglist.length; ++i) {
                if (paramTypes[i].isPrimitive()) {
                    paramTypes[i] = fromPrimitiveToWrapper(paramTypes[i].getSimpleName());
                }
                arglist[i] = paramTypes[i].getConstructor(String.class).newInstance(paramList.get(i));
            }
            return m.invoke(obj, arglist);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to call '%s'", m.getName()));
        }
    }

    private static Class<?> fromPrimitiveToWrapper(String name) {
        try {
            switch (name) {
                case "boolean":
                    return Boolean.class;
                case "byte":
                    return Byte.class;
                case "short":
                    return String.class;
                case "int":
                    return Integer.class;
                case "long":
                    return Long.class;
                case "float":
                    return Float.class;
                case "double":
                    return Double.class;
                case "char":
                    return Character.class;
                case "void":
                    return Void.class;
            }
        } catch (Exception e) {}
        throw new RuntimeException();
    }
}
