package com.glovoapp.backender.config.comparators;

import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component("comparatorOrderComparatorLoaderImpl")
public class ComparatorOrderComparatorLoaderImpl implements OrderComparatorLoader<Comparator> {

    /**
     * Load a comparator and return the comparator
     *
     * @param comparator The existing comparator
     * @param value      the value to be passed into the comparator
     * @return the comparator with the new value
     */
    @Override
    public Comparator loadIntoVariable(Comparator comparator, Comparator value) {
        if (comparator == null) {
            return value;
        }
        return comparator.thenComparing(value);
    }

}
