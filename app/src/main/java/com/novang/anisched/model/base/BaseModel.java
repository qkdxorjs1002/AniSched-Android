package com.novang.anisched.model.base;

import java.util.Collection;
import java.util.Objects;

public class BaseModel {

    public boolean isNullOrEmpty(String string) {
        return (string == null || Objects.equals(string, ""));
    }

    public boolean isNullOrEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

}
