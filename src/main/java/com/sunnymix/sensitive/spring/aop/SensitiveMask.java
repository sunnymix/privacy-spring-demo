package com.sunnymix.sensitive.spring.aop;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class SensitiveMask {

    public static Object maskAny(Object obj, boolean isSensitiveField) throws Throwable {
        if (Objects.isNull(obj)) {
            return null;
        }

        if (obj instanceof List) {
            return maskList(obj, isSensitiveField);
        } else if (obj.getClass().isArray()) {
            // Todo Support Array type
            return obj;
        } else {
            return maskOne(obj, isSensitiveField);
        }
    }

    public static Object maskList(Object obj, boolean isSensitiveField) throws Throwable {
        List<Object> list = (List<Object>) obj;
        int size = list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Object ele = list.get(i);
                Object maskEle = maskAny(ele, isSensitiveField);
                list.set(i, maskEle);
            }
        }
        return list;
    }

    public static Object maskOne(Object obj, boolean isSensitiveField) throws Throwable {
        if (Objects.isNull(obj)) {
            return null;
        }

        if (isUserObj(obj)) {
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object maskField = maskAny(field.get(obj), isSensitiveField(field));
                field.set(obj, maskField);
            }
            return obj;
        } else {
            if (isSensitiveField) {
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

    public static boolean isSensitive(Object obj) {
        return obj.getClass().isAnnotationPresent(SensitiveField.class);
    }

    public static boolean isSensitiveField(Field field) {
        return field.isAnnotationPresent(SensitiveField.class);
    }

}
