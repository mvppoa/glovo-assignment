package com.glovoapp.backender.services;

import com.glovoapp.backender.web.rest.vm.OrderVM;

import java.util.List;

public interface OrderService {

    List<OrderVM> findAllOrders();
    List<OrderVM> findAllByCourierId(String courierId);

}
