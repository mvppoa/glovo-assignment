package com.glovoapp.backender.config;

import com.glovoapp.backender.config.comparators.OrderComparatorLoader;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.util.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class OrderComparatorConfig {

    private static final Logger log = LoggerFactory.getLogger(OrderComparatorConfig.class);

    private final List<String> comparableOrder;

    private final Map<String, Function> functionMap;
    private final Map<String, Comparator> comparatorMap;

    private final double closestDistanceStart;
    private final double closestDistanceEnd;

    private final OrderComparatorLoader<Comparator> comparatorOrderComparatorLoader;
    private final OrderComparatorLoader<Function> functionOrderComparatorLoader;

    public OrderComparatorConfig(@Value("${backender.resources.orders.comparable.order}") List<String> comparableOrder,
                                 @Value("${backender.resources.orders.comparable.order.closest.distance.start}") double closestDistanceStart,
                                 @Value("${backender.resources.orders.comparable.order.closest.distance.end}") double closestDistanceEnd,
                                 OrderComparatorLoader<Comparator> comparatorOrderComparatorLoaderImpl,
                                 OrderComparatorLoader<Function> functionOrderComparatorLoaderImpl) {
        this.functionMap = new HashMap<>();
        this.comparatorMap = new HashMap<>();
        this.comparableOrder = comparableOrder;
        this.closestDistanceStart = closestDistanceStart;
        this.closestDistanceEnd = closestDistanceEnd;
        this.comparatorOrderComparatorLoader = comparatorOrderComparatorLoaderImpl;
        this.functionOrderComparatorLoader = functionOrderComparatorLoaderImpl;
    }

    /**
     * Comparator following the comparable order and distance parameters .
     *
     * @return the order comparator
     */
    @Bean("orderComparator")
    public Comparator buildOrderComparator() {

        log.debug("Entered build comparator method");

        loadMaps();

        Comparator comparator = null;

        for (String orderValue : comparableOrder) {
            if (functionMap.containsKey(orderValue)) {
                log.debug("Loading function with value {}", orderValue);
                comparator = functionOrderComparatorLoader.loadIntoVariable(comparator, functionMap.get(orderValue));
            } else if (comparatorMap.containsKey(orderValue)) {
                log.debug("Loading comparator with value {}", orderValue);
                comparator = comparatorOrderComparatorLoader.loadIntoVariable(comparator, comparatorMap.get(orderValue));
            }
        }

        if (comparator == null) {
            log.debug("Empty comparator, applying standard ordering");
            comparator = Comparator.naturalOrder();
        }

        return comparator;
    }

    /**
     * Load the comparators into a map to be used in the builders method
     * buildOrderComparator.
     */
    private void loadMaps() {

        log.debug("Loading closest function for the order comparator");
        Function closestStartOrdersFunction = order -> {
            Order order1 = (Order) order;
            double distance = DistanceCalculator.calculateDistance(order1.getPickup(), order1.getDelivery());

            if (distance < closestDistanceStart) {
                return -1;
            } else if (distance >= closestDistanceStart && distance <= closestDistanceEnd) {
                return 0;
            }
            return 1;
        };
        functionMap.put("closest", closestStartOrdersFunction);

        log.debug("Loading vip function for the order comparator");
        Function vipOrdersFunction = order -> {
            Order order1 = (Order) order;
            if (order1.getVip()) {
                return 0;
            }
            return 1;
        };
        functionMap.put("vip", vipOrdersFunction);

        log.debug("Loading food function for the order comparator");
        Function foodOrdersFunction = order -> {
            Order order1 = (Order) order;
            if (order1.getFood()) {
                return 0;
            }
            return 1;
        };
        functionMap.put("food", foodOrdersFunction);

        log.debug("Loading distance comparator for the order comparator");
        Comparator distanceOrdersComparator = (o1, o2) -> {
            Order order1 = (Order) o1;
            Order order2 = (Order) o2;

            double order1Distance = DistanceCalculator.calculateDistance(order1.getPickup(), order1.getDelivery());
            double order2Distance = DistanceCalculator.calculateDistance(order2.getPickup(), order2.getDelivery());

            if (order1Distance - order2Distance > 0) {
                return 1;
            }
            return (order1Distance == order2Distance) ? 0 : -1;
        };
        comparatorMap.put("distance", distanceOrdersComparator);

    }

}
