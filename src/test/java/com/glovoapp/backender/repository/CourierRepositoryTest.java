package com.glovoapp.backender.repository;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.enums.Vehicle;
import com.glovoapp.backender.repositories.CourierRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class CourierRepositoryTest {

    private CourierRepository courierRepository;

    @BeforeAll
    void setUp() throws IOException {
        InputStream input = new FileInputStream("application.properties");
        Properties prop = new Properties();
        prop.load(input);
        this.courierRepository = new CourierRepository(prop.getProperty("backender.resources.couriers.path"));
    }

    @Test
    void findOneExisting() {
        Courier courier = this.courierRepository.findById("courier-1");
        Courier expected = new Courier().withId("courier-1")
                .withBox(true)
                .withName("Manolo Escobar")
                .withVehicle(Vehicle.MOTORCYCLE)
                .withLocation(new Location(41.3965463, 2.1963997));

        assertEquals(expected, courier);
    }

    @Test
    void findOneNotExisting() {
        Courier courier = this.courierRepository.findById("bad-courier-id");
        assertNull(courier);
    }

    @Test
    void findAll() {
        List<Courier> all = this.courierRepository.findAll();
        assertFalse(all.isEmpty());
    }
}