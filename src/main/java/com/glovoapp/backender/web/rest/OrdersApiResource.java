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
@RequestMapping
public class OrdersApiResource implements OrdersApi {

    private static final Logger log = LoggerFactory.getLogger(OrdersApiResource.class);

    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public OrdersApiResource(OrderService orderService, ObjectMapper objectMapper, HttpServletRequest request) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public List<OrderVM> orders() {
        return orderService.findAllOrders();
    }

    @Override
    public ResponseEntity<List<OrderVM>> ordersByCourierId(@ApiParam(value = "Id of the courier for the orders search", required = true) @PathVariable("courierId") String courierId) {
        ResponseEntity<List<OrderVM>> validateRequest = validateRequest();
        if (validateRequest != null) return validateRequest;
        return ResponseEntity.ok(orderService.findAllByCourierId(courierId));
    }

    @Override
    public ResponseEntity<List<OrderVM>> ordersUsingGET() {
        ResponseEntity<List<OrderVM>> validateRequest = validateRequest();
        if (validateRequest != null) return validateRequest;
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    private ResponseEntity<List<OrderVM>> validateRequest() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<OrderVM>>(objectMapper.readValue("[ {  \"description\" : \"description\",  \"id\" : \"id\"}, {  \"description\" : \"description\",  \"id\" : \"id\"} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<OrderVM>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }
}