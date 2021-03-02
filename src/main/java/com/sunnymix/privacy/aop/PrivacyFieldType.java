package com.sunnymix.privacy.aop;

import java.util.ArrayList;
import java.util.List;

public class PrivacyFieldType {

    public final static String STRING = "string";

    public final static String INTEGER = "integer";

    public final static String LIST = "list";

    public final static String CUSTOM = "custom";

    public final static String UNSUPPORTED = "unsupported";

    public final static List<String> BASIC_TYPES = new ArrayList<>() {{
        add(STRING);
        add(INTEGER);
    }};

    public final static List<String> INHERITABLE_TYPES = new ArrayList<>() {{
        add(STRING);
        add(INTEGER);
        add(LIST);
    }};

    public static String whichType(Object obj) {
        String type = UNSUPPORTED;
        if (isStringType(obj)) {
            type = STRING;
        } else if (isIntegerType(obj)) {
            type = INTEGER;
        } else if (isListType(obj)) {
            type = LIST;
        } else if (isCustomType(obj)) {
            type = CUSTOM;
        }
        return type;
    }

    public static boolean isInBasicTypes(String type) {
        return BASIC_TYPES.contains(type);
    }

    public static boolean isInInheritableTypes(String type) {
        return INHERITABLE_TYPES.contains(type);
    }

    private static boolean isStringType(Object obj) {
        return isType(obj, String.class);
    }

    private static boolean isIntegerType(Object obj) {
        return isType(obj, Integer.class);
    }

    private static boolean isListType(Object obj) {
        return isType(obj, LIST.getClass());
    }

    private static boolean isType(Object obj, Class<?> type) {
        return obj.getClass().isAssignableFrom(type);
    }

    private static boolean isBuiltinType(Object obj) {
        return obj.getClass().getClassLoader() == null;
    }

    private static boolean isCustomType(Object obj) {
        return obj.getClass().getClassLoader() != null;
    }

    public static void main(String[] args) {
        System.out.println(whichType("Sunny"));
        System.out.println(whichType(1));
        System.out.println(whichType(new ArrayList<>()));
    }

}
