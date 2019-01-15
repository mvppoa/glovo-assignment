package com.glovoapp.backender.config.comparators;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.function.Function;

@Component("functionOrderComparatorLoaderImpl")
public class FunctionOrderComparatorLoaderImpl implements OrderComparatorLoader<Function> {

    /**
     * Load a function and return the comparator
     *
     * @param comparator The existing comparator
     * @param value      the value to be passed into the comparator
     * @return the comparator with the new value
     */
    @Override
    public Comparator loadIntoVariable(Comparator comparator, Function value) {
        if (comparator == null) {
            return Comparator.comparing(value);
        }
        return comparator.thenComparing(value);
    }

}
