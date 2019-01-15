package com.glovoapp.backender.helper;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;

import java.util.List;

public interface OrderHelper {

    List<Order> handleCourierOrdersRestriction(List<Order> orders, Courier currentCourier);

}
