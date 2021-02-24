package com.sunnymix.privacy.spring.aop;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class PrivacyMask {

    public static Object maskAny(Object obj, boolean isPrivacyField) throws Throwable {
        if (Objects.isNull(obj)) {
            return null;
        }

        if (obj instanceof List) {
            return maskList(obj, isPrivacyField);
        } else if (obj.getClass().isArray()) {
            // Todo Support Array type
            return obj;
        } else {
            return maskOne(obj, isPrivacyField);
        }
    }

    public static Object maskList(Object obj, boolean isPrivacyField) throws Throwable {
        List<Object> list = (List<Object>) obj;
        int size = list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Object ele = list.get(i);
                Object maskEle = maskAny(ele, isPrivacyField);
                list.set(i, maskEle);
            }
        }
        return list;
    }

    public static Object maskOne(Object obj, boolean isPrivacyField) throws Throwable {
        if (Objects.isNull(obj)) {
            return null;
        }

        if (isUserObj(obj)) {
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object maskField = maskAny(field.get(obj), isPrivacyField(field));
                field.set(obj, maskField);
            }
            return obj;
        } else {
            if (isPrivacyField) {
                String value = (String) obj;
                String maskValue = String.format("%s****%s",
                        value.substring(0, 3), value.substring(value.length() - 4));
                return maskValue;
            }
            return obj;
        }
    }

    public static boolean isUserObj(Object obj) throws Throwable {
        return obj.getClass().getClassLoader() != null;
    }

    public static boolean isPrivacy(Object obj) {
        return obj.getClass().isAnnotationPresent(PrivacyField.class);
    }

    public static boolean isPrivacyField(Field field) {
        return field.isAnnotationPresent(PrivacyField.class);
    }

}
