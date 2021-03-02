package com.sunnymix.privacy.aop;

import lombok.Getter;

import java.util.List;

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

    public boolean isPrivacy() {
        return isSelfPrivacy || isInheritPrivacy;
    }

    public static Object amend(Object obj, PrivacyFieldInfo parent) {
        Object result = obj;
        PrivacyFieldInfo field = of(obj, parent);

        if (field.isListType()) {
            result = amendList(obj, parent);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static Object amendList(Object obj, PrivacyFieldInfo parent) {
        List<Object> listObj = (List<Object>) obj;
        int size = listObj.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Object ele = listObj.get(i);
                Object maskEle = amend(ele, parent);
                listObj.set(i, maskEle);
            }
        }
        return listObj;
    }

    public static PrivacyFieldInfo of(Object obj, PrivacyFieldInfo parent) {
        PrivacyField annotation = getAnnotation(obj);
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
                obj,
                fieldType,
                privacyType,
                isPrivacy,
                isRoot,
                isInheritPrivacy);
    }

    public static PrivacyField getAnnotation(Object obj) {
        return obj.getClass().getAnnotation(PrivacyField.class);
    }

}
