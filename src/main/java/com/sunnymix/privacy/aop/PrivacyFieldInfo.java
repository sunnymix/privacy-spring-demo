package com.sunnymix.privacy.aop;

import com.sunnymix.privacy.exception.PrivacyException;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.List;

import static java.lang.String.format;

@Getter
public class PrivacyFieldInfo {

    private final Object fieldValue;
    private final String fieldType;
    private final String privacyType;
    private final boolean isSelfPrivacy;
    private final boolean isRoot;
    private final boolean isInheritPrivacy;

    private PrivacyFieldInfo(Object fieldValue,
                             String fieldType,
                             String privacyType,
                             boolean isSelfPrivacy,
                             boolean isRoot,
                             boolean isInheritPrivacy) {
        this.fieldValue = fieldValue;
        this.fieldType = fieldType;
        this.privacyType = privacyType;
        this.isSelfPrivacy = isSelfPrivacy;
        this.isRoot = isRoot;
        this.isInheritPrivacy = isInheritPrivacy;
    }

    public boolean isUnsupportedType() {
        return this.fieldType.equals(PrivacyFieldType.UNSUPPORTED);
    }

    public boolean isBasicType() {
        return PrivacyFieldType.isInBasicTypes(this.fieldType);
    }

    public boolean isListType() {
        return this.fieldType.equals(PrivacyFieldType.LIST);
    }

    public boolean isCustomType() {
        return this.fieldType.equals(PrivacyFieldType.CUSTOM);
    }

    public boolean isPrivacy() {
        return isSelfPrivacy || isInheritPrivacy;
    }

    public static Object amend(PrivacyFieldInfo parent, Object obj) {
        if (null == obj) {
            return null;
        }

        Object result = obj;
        PrivacyFieldInfo field = of(parent, obj);

        if (field.isListType()) {
            // 1.list
            result = amendList(parent, obj);
        } else if (field.isCustomType()) {
            // 2.custom
            result = amendCustom(parent, obj);
        } else if (field.isBasicType()) {
            // 3.basic
            result = amendBasic(parent, obj);
        } else {
            // 4.other
            if (field.isPrivacy()) {
                String message = format("unsupported field-type:'%s'", field.getFieldType());
                throw new PrivacyException(message);
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static Object amendList(PrivacyFieldInfo parent, Object obj) {
        List<Object> listObj = (List<Object>) obj;
        int size = listObj.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Object ele = listObj.get(i);
                Object maskEle = amend(parent, ele);
                listObj.set(i, maskEle);
            }
        }
        return listObj;
    }

    public static Object amendCustom(PrivacyFieldInfo parent, Object obj) {
        if (null == obj) {
            return null;
        }

        for (Field objField : obj.getClass().getDeclaredFields()) {
            PrivacyFieldInfo fieldInfo = of(null, obj, objField);
            if (null == fieldInfo) {
                continue;
            }
            Object amendResult = amend(null, fieldInfo.fieldValue);
            objField.setAccessible(true);
            try {
                objField.set(obj, amendResult);
            } catch (IllegalAccessException cause) {
                throw new PrivacyException("cannot set field value", cause);
            }
        }
        return obj;
    }

    public static Object amendBasic(PrivacyFieldInfo parent, Object obj) {
        throw new PrivacyException("error");
    }

    public static PrivacyFieldInfo of(PrivacyFieldInfo parent, Object obj, Field objField) {
        PrivacyField annotation = getAnnotation(objField);
        Object fieldValue = null;
        try {
            fieldValue = objField.get(obj);
        } catch (IllegalAccessException cause) {
            throw new PrivacyException("cannot get field value", cause);
        }
        return of(parent, fieldValue, annotation);
    }

    public static PrivacyFieldInfo of(PrivacyFieldInfo parent, Object obj) {
        PrivacyField annotation = getAnnotation(obj);
        return of(parent, obj, annotation);
    }

    private static PrivacyFieldInfo of(PrivacyFieldInfo parent, Object obj,
                                       PrivacyField annotation) {
        if (null == obj) {
            return null;
        }
        String fieldType = PrivacyFieldType.whichType(obj);
        String privacyType = null;
        boolean isPrivacy = false;
        boolean isRoot = null != parent;
        boolean isInheritPrivacy = false;

        if (null != annotation) {
            privacyType = annotation.privacyType();
            isPrivacy = true;
        }

        if (null != parent) {
            isInheritPrivacy = parent.isPrivacy() &&
                    PrivacyFieldType.isInInheritableTypes(fieldType);
        }

        return new PrivacyFieldInfo(
                obj, fieldType, privacyType, isPrivacy, isRoot, isInheritPrivacy);
    }

    public static PrivacyField getAnnotation(Object obj) {
        return obj.getClass().getAnnotation(PrivacyField.class);
    }

    public static PrivacyField getAnnotation(Field objField) {
        return objField.getAnnotation(PrivacyField.class);
    }

}
