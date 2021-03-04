package com.sunnymix.privacy.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> {

    boolean success;
    private T data;

    public static <T> JsonResult<T> of(T data) {
        JsonResult<T> result = new JsonResult<T>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

}
