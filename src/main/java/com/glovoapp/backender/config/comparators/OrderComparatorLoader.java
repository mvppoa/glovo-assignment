package com.glovoapp.backender.config.comparators;

import java.util.Comparator;

public interface OrderComparatorLoader<T> {

    /**
     * Function to add variables into the comparator;
     *
     * @param comparator The existing comparator
     * @param value the value to be passed into the comparator
     */
    Comparator loadIntoVariable(Comparator comparator, T value);

}
