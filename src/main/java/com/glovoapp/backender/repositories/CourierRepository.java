package com.glovoapp.backender.repositories;

import com.glovoapp.backender.domain.Courier;
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
public class CourierRepository {

    private final List<Courier> couriers;

    public CourierRepository(@Value("${backender.resources.couriers.path}") String couriersPath) {
        try (Reader reader = new InputStreamReader(CourierRepository.class.getResourceAsStream(couriersPath))) {
            Type type = new TypeToken<List<Courier>>() {
            }.getType();
            couriers = new Gson().fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Courier findById(String courierId) {
        return couriers.stream()
                .filter(courier -> courierId.equals(courier.geId()))
                .findFirst()
                .orElse(null);
    }

    public List<Courier> findAll() {
        return new ArrayList<>(couriers);
    }
}
