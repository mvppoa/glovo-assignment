package com.glovoapp.backender.services;

import com.glovoapp.backender.web.rest.vm.OrderVM;

import java.util.List;

public interface OrderService {

    /**
     * Find all orders
     * @return list of orderVMs
     */
    List<OrderVM> findAllOrders();

    /**
     * Find all orders by courier ids.
     * Restrictions regarding distance and vip may be applied.
     * Comparisons may follow order comparators.
     * @see com.glovoapp.backender.helper.OrderHelper
     * @see com.glovoapp.backender.config.OrderComparatorConfig
     * @return list of orderVMs
     */
    List<OrderVM> findAllByCourierId(String courierId);

}
