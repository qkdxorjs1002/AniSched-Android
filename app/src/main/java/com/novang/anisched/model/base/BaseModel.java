package com.novang.anisched.model.base;

import java.util.Objects;

public class BaseModel {

    public boolean isNullOrEmpty(Object object) {
        return (object == null || Objects.equals(object, ""));
    }
}
