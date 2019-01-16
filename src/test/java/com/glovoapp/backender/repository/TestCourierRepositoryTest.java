package com.glovoapp.backender.repository;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Location;
import com.glovoapp.backender.enums.Vehicle;
import com.glovoapp.backender.repositories.CourierRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class TestCourierRepositoryTest {

    private CourierRepository courierRepository;

    @Before
    public void setUp() throws IOException {
        InputStreamReader input = new InputStreamReader(TestCourierRepositoryTest.class.getResourceAsStream("/application.properties"));
        Properties prop = new Properties();
        prop.load(input);
        this.courierRepository = new CourierRepository(prop.getProperty("backender.resources.couriers.path"));
    }

    @Test
    public void findOneExisting() {
        Courier courier = this.courierRepository.findById("courier-faa2186e65f2");

        Courier expected = new Courier().withId("courier-faa2186e65f2")
                .withBox(true)
                .withName("Esteban Golson")
                .withVehicle(Vehicle.ELECTRIC_SCOOTER)
                .withLocation(new Location(41.39087699632735, 2.1784116992018028));

        assertEquals(expected, courier);
    }

    @Test
    public void findOneNotExisting() {
        Courier courier = this.courierRepository.findById("bad-courier-id");
        assertNull(courier);
    }

    @Test
    public void findAll() {
        List<Courier> all = this.courierRepository.findAll();
        assertFalse(all.isEmpty());
    }
}