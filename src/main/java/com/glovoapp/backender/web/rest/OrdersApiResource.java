package com.glovoapp.backender.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glovoapp.backender.services.OrderService;
import com.glovoapp.backender.web.rest.vm.OrderVM;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class OrdersApiResource implements OrdersApi {

    private static final Logger log = LoggerFactory.getLogger(OrdersApiResource.class);

    private final OrderService orderService;

    public OrdersApiResource(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<OrderVM> orders() {
        return orderService.findAllOrders();
    }

    @Override
    public ResponseEntity<List<OrderVM>> ordersByCourierId(@ApiParam(value = "Id of the courier for the orders search", required = true)
                                                               @PathVariable("courierId") String courierId) {
        return ResponseEntity.ok(orderService.findAllByCourierId(courierId));
    }

    @Override
    public ResponseEntity<List<OrderVM>> ordersUsingGET() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }
}