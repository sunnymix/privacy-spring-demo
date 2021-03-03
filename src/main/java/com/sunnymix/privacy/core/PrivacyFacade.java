package com.sunnymix.privacy.core;

public class PrivacyFacade {

    public static Object amend(Object obj) {
        return PrivacyAmendService.amend(null, obj);
    }

}
