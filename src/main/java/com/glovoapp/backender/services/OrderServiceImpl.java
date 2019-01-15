package com.glovoapp.backender.services;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.helper.OrderHelper;
import com.glovoapp.backender.repositories.CourierRepository;
import com.glovoapp.backender.repositories.OrderRepository;
import com.glovoapp.backender.util.DistanceCalculator;
import com.glovoapp.backender.web.rest.vm.OrderVM;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final Comparator orderComparator;
    private final OrderHelper orderHelper;

    public OrderServiceImpl(OrderRepository orderRepository, CourierRepository courierRepository, Comparator orderComparator, OrderHelper orderHelper) {
        this.orderRepository = orderRepository;
        this.courierRepository = courierRepository;
        this.orderComparator = orderComparator;
        this.orderHelper = orderHelper;
    }

    @Override
    public List<OrderVM> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderVM(order.getId(), order.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderVM> findAllByCourierId(String courierId) {

        //Remove forbidden orders
        List<Order> orders = orderHelper.handleCourierOrdersRestriction(orderRepository.findAll(),courierRepository.findById(courierId));

        orders.sort(orderComparator);

        return orders.stream()
                .map(order -> new OrderVM(order.getId(), order.getDescription(), DistanceCalculator.calculateDistance(order.getPickup(), order.getDelivery())
                        , order.getFood(), order.getVip()))
                .collect(Collectors.toList());
    }
}
