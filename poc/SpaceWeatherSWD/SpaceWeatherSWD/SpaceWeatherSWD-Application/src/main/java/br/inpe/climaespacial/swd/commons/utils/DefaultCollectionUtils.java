package br.inpe.climaespacial.swd.commons.utils;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultCollectionUtils implements CollectionUtils {

    @Override
    public void reverse(List<?> list) {
        throwIfNull(list, "list");
        Collections.reverse(list);
    }

}
