package com.glovoapp.backender.repository;

import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderRepositoryTest {

    private OrderRepository orderRepository;

    @Before
    public void setUp() throws IOException {
        InputStreamReader input = new InputStreamReader(OrderRepositoryTest.class.getResourceAsStream("/application.properties"));
        Properties prop = new Properties();
        prop.load(input);
        this.orderRepository = new OrderRepository(prop.getProperty("backender.resources.orders.path"));
    }
    
    @Test
    public void findAll() {
        List<Order> orders = this.orderRepository.findAll();
        assertFalse(orders.isEmpty());

        Order firstOrder = orders.get(0);

        Order expected = new Order().withId("order-d22b76a4cddc")
                .withDescription("2x Pizza with Fries")
                .withFood(false)
                .withVip(false)
                .withPickup(new Location(41.40440729233118, 2.1780786691277085))
                .withDelivery(new Location(41.38883091953211, 2.165498064910881));

        assertEquals(expected, firstOrder);
    }
}