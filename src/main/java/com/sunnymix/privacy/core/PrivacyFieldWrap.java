package com.sunnymix.privacy.core;

import com.sunnymix.privacy.exception.PrivacyException;
import lombok.Getter;

import java.lang.reflect.Field;

import com.sunnymix.privacy.integrate.annotation.PrivacyField;

import static java.lang.String.format;

@Getter
public class PrivacyFieldWrap {

    private final Object fieldValue;
    private final String fieldType;
    private final String privacyType;
    private final boolean isSelfPrivacy;
    private final boolean isRoot;
    private final boolean isInheritPrivacy;

    private PrivacyFieldWrap(Object fieldValue,
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

    @Override
    public String toString() {
        return "PrivacyFieldInfo{" +
                "fieldValue=" + fieldValue +
                ", fieldType='" + fieldType + '\'' +
                ", privacyType='" + privacyType + '\'' +
                ", isSelfPrivacy=" + isSelfPrivacy +
                ", isRoot=" + isRoot +
                ", isInheritPrivacy=" + isInheritPrivacy +
                '}';
    }

    public boolean isNullValue() {
        return null == this.fieldValue;
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

    public static PrivacyFieldWrap of(PrivacyFieldWrap parent, Object obj, Field objField) {
        PrivacyField annotation = getAnnotation(objField);
        Object fieldValue = null;
        try {
            fieldValue = objField.get(obj);
        } catch (IllegalAccessException cause) {
            throw new PrivacyException("cannot get field value", cause);
        }
        return of(parent, fieldValue, annotation);
    }

    public static PrivacyFieldWrap of(PrivacyFieldWrap parent, Object obj) {
        PrivacyField annotation = getAnnotation(obj);
        return of(parent, obj, annotation);
    }

    private static PrivacyFieldWrap of(PrivacyFieldWrap parent, Object obj,
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
            if (null == privacyType) {
                privacyType = parent.getPrivacyType();
            }
        }

        return new PrivacyFieldWrap(
                obj, fieldType, privacyType, isPrivacy, isRoot, isInheritPrivacy);
    }

    public static PrivacyField getAnnotation(Object obj) {
        return obj.getClass().getAnnotation(PrivacyField.class);
    }

    public static PrivacyField getAnnotation(Field objField) {
        return objField.getAnnotation(PrivacyField.class);
    }

}
