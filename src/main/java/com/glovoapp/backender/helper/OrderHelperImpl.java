package com.glovoapp.backender.helper;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.util.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderHelperImpl implements OrderHelper {

    private static final Logger log = LoggerFactory.getLogger(OrderHelperImpl.class);

    private final List<String> forbiddenRequestsForStandardCouriers;
    private final List<String> listOfAllowedVehiclesAfterMaxDistance;
    private final boolean restrictBoxFilter;
    private final boolean enableDistanceRestriction;
    private final double courierOrderMaxDistance;

    public OrderHelperImpl(
            @Value("${backender.resources.orders.box.restrictions.enable}") Boolean restrictBoxFilter,
            @Value("${backender.resources.orders.box.restrictions.values}") List<String> forbiddenRequestsForStandardCouriers,
            @Value("${backender.resources.orders.distance.enable}") boolean enableDistanceRestriction,
            @Value("${backender.resources.orders.distance.vehicles}") List<String> listOfAllowedVehiclesAfterMaxDistance,
            @Value("${backender.resources.orders.distance.limit}") double courierOrderMaxDistance) {
        this.forbiddenRequestsForStandardCouriers = forbiddenRequestsForStandardCouriers;
        this.listOfAllowedVehiclesAfterMaxDistance = listOfAllowedVehiclesAfterMaxDistance.stream()
                .map(String::toLowerCase).collect(Collectors.toList());
        this.restrictBoxFilter = restrictBoxFilter;
        this.enableDistanceRestriction = enableDistanceRestriction;
        this.courierOrderMaxDistance = courierOrderMaxDistance;
    }

    @Override
    public List<Order> handleCourierOrdersRestriction(List<Order> orders, Courier currentCourier) {

        if (currentCourier == null) {
            log.debug("Could not find requested courier");
            return new LinkedList<>();
        }

        String courierId = currentCourier.geId();

        log.debug("Entered method to handle courier restrictions. Courier id: {}", courierId);

        List<Order> currentOrderList = orders;
        if (!currentCourier.getBox() && restrictBoxFilter) {
            log.debug("Entered vip box restriction handler. Courier id: {}", courierId);
            currentOrderList = currentOrderList.stream().filter(order ->
                    forbiddenRequestsForStandardCouriers.stream().noneMatch(s -> order.getDescription().toLowerCase().contains(s))).
                    collect(Collectors.toList());
        }

        if (enableDistanceRestriction) {
            log.debug("Entered distance restriction handler. Courier id: {}", courierId);
            currentOrderList = currentOrderList.stream().filter(order -> DistanceCalculator.calculateDistance(currentCourier.getLocation(), order.getPickup()) < courierOrderMaxDistance
                    || listOfAllowedVehiclesAfterMaxDistance.contains(currentCourier.getVehicle().toString().toLowerCase()))
                    .collect(Collectors.toList());
        }
        return currentOrderList;

    }
}
