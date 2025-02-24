package com.optimal.api.settings;

import java.io.Serializable;

public interface BO<T> extends Serializable {
    T toRO();
}
