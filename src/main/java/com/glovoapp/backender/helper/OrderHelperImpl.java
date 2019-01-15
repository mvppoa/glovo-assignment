package com.glovoapp.backender.helper;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.util.DistanceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderHelperImpl implements OrderHelper {

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
        List<Order> currentOrderList = orders;
        if (!currentCourier.getBox() && restrictBoxFilter) {
            currentOrderList = currentOrderList.stream().filter(order ->
                    forbiddenRequestsForStandardCouriers.stream().noneMatch(s -> order.getDescription().toLowerCase().contains(s))).
                    collect(Collectors.toList());
        }

        if (enableDistanceRestriction) {
            currentOrderList = currentOrderList.stream().filter(order -> DistanceCalculator.calculateDistance(currentCourier.getLocation(), order.getPickup()) < courierOrderMaxDistance
                    || listOfAllowedVehiclesAfterMaxDistance.contains(currentCourier.getVehicle().toString().toLowerCase()))
                    .collect(Collectors.toList());
        }
        return currentOrderList;

    }
}
