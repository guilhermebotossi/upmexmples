package br.inpe.climaespacial.swd.commons.helpers;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public interface DeepCloneable extends Serializable {
    @SuppressWarnings("unchecked")
    default <T> T deepClone() {
        return (T) SerializationUtils.clone(this);
    }
}
