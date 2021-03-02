package com.sunnymix.privacy.core;

public class PrivacyProcess {

    public static Object _process(Object obj) {
        if (obj instanceof String) {
            String value = (String) obj;
            String maskValue = String.format("%s****%s",
                    value.substring(0, 3), value.substring(value.length() - 4));
            return maskValue;
        }
        return obj;
    }

}
