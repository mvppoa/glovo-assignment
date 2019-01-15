package com.glovoapp.backender.repository;


import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OrderRepositoryTest {

    private OrderRepository orderRepository;

    @BeforeAll
    void setUp() throws IOException {
        InputStream input = new FileInputStream("application.properties");
        Properties prop = new Properties();
        prop.load(input);
        this.orderRepository = new OrderRepository(prop.getProperty("backender.resources.orders.path"));
    }
    
    @Test
    void findAll() {
        List<Order> orders = this.orderRepository.findAll();

        assertFalse(orders.isEmpty());

        Order firstOrder = orders.get(0);

        Order expected = new Order().withId("order-1")
                .withDescription("I want a pizza cut into very small slices")
                .withFood(true)
                .withVip(false)
                .withPickup(new Location(41.3965463, 2.1963997))
                .withDelivery(new Location(41.407834, 2.1675979));

        assertEquals(expected, firstOrder);
    }
}