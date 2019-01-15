package com.glovoapp.backender.repositories;

import com.glovoapp.backender.domain.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private final List<Order> orders;

    public OrderRepository(@Value("${backender.resources.orders.path}") String ordersPath) {
        try (Reader reader = new InputStreamReader(OrderRepository.class.getResourceAsStream(ordersPath))) {
            Type type = new TypeToken<List<Order>>() {
            }.getType();
            orders = new Gson().fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }
}
