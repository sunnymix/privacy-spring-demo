package com.sunnymix.privacy.core;

import com.sunnymix.privacy.PrivacyService;
import com.sunnymix.privacy.exception.PrivacyException;
import com.sunnymix.privacy.spring.model.User;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class PrivacyFieldAmend {

    public static Object amend(PrivacyFieldWrap parent, Object obj) {
        if (null == obj) {
            return null;
        }
        PrivacyFieldWrap field = PrivacyFieldWrap.of(parent, obj);
        return amendField(field);
    }

    public static Object amendField(PrivacyFieldWrap field) {
        if (null == field || field.isNullValue()) {
            return null;
        }

        Object result = field.getFieldValue();

        if (field.isListType()) {
            // 1.list
            result = amendListField(field);
        } else if (field.isCustomType()) {
            // 2.custom
            result = amendCustomObjectField(field);
        } else if (field.isBasicType()) {
            // 3.basic
            result = amendBasicField(field);
        } else {
            // 4.other
            if (field.isPrivacy()) {
                throw new PrivacyException(format(
                        "unsupported field-type: '%s', field-class: '%s'",
                        field.getFieldType(), field.getFieldValue().getClass()));
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static Object amendListField(PrivacyFieldWrap field) {
        if (null == field || field.isNullValue()) {
            return null;
        }
        List<Object> listObj = (List<Object>) field.getFieldValue();
        int size = listObj.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Object ele = listObj.get(i);
                Object maskEle = amend(field, ele);
                listObj.set(i, maskEle);
            }
        }
        return listObj;
    }

    public static Object amendCustomObjectField(PrivacyFieldWrap field) {
        if (null == field || field.isNullValue()) {
            return null;
        }
        Object obj = field.getFieldValue();
        for (Field objField : obj.getClass().getDeclaredFields()) {
            objField.setAccessible(true);
            PrivacyFieldWrap fieldInfo = PrivacyFieldWrap.of(null, obj, objField);
            if (null == fieldInfo) {
                continue;
            }
            Object amendResult = amendField(fieldInfo);
            try {
                objField.set(obj, amendResult);
            } catch (IllegalAccessException cause) {
                throw new PrivacyException("cannot set field value", cause);
            }
        }
        return obj;
    }

    public static Object amendBasicField(PrivacyFieldWrap field) {
        if (null == field || field.isNullValue()) {
            return null;
        }
        Object result = field.getFieldValue();
        if (field.isPrivacy()) {
            result = PrivacyService.process(field.getFieldValue(), field.getPrivacyType());
        }
        return result;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("Sunny");
        user.setPhone("12300001111");
        user.setPhoneList(Arrays.asList("12300001111", "12300001112"));
        User amendUser = (User) amend(null, user);
        System.out.println(amendUser);
    }

}
