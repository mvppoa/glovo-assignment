package com.glovoapp.backender.config;

import com.glovoapp.backender.config.comparators.OrderComparatorLoader;
import com.glovoapp.backender.domain.Order;
import com.glovoapp.backender.util.DistanceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Configuration
@Scope("prototype")
public class OrderComparatorConfig {

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

        loadMaps();

        Comparator comparator = null;

        for (String s : comparableOrder) {
            if (functionMap.containsKey(s)) {
                comparator = functionOrderComparatorLoader.loadIntoVariable(comparator, functionMap.get(s));
            } else if (comparatorMap.containsKey(s)) {
                comparator = comparatorOrderComparatorLoader.loadIntoVariable(comparator, comparatorMap.get(s));
            }
        }

        if (comparator == null) {
            comparator = Comparator.naturalOrder();
        }

        return comparator;
    }

    /**
     * Comparator orderComparator = Comparator.comparing(order -> {
     *             Order order1 = (Order) order;
     *             if (DistanceCalculator.calculateDistance(order1.getPickup(), order1.getDelivery()) < 0.5) {
     *                 return 0;
     *             }
     *             return 1;
     *         }).thenComparing(order -> {
     *             Order order1 = (Order) order;
     *             if (DistanceCalculator.calculateDistance(order1.getPickup(), order1.getDelivery()) >= 0.5 && DistanceCalculator.calculateDistance(order1.getPickup(), order1.getDelivery()) <= 1.0) {
     *                 return 0;
     *             }
     *             return 1;
     *         }).thenComparing(order -> {
     *             Order order1 = (Order) order;
     *             if (order1.getVip()) {
     *                 return 0;
     *             }
     *             return 1;
     *         }).thenComparing(order -> {
     *             Order order1 = (Order) order;
     *             if (order1.getFood()) {
     *                 return 0;
     *             }
     *             return 1;
     *         })
     *                 .thenComparing((o1, o2) -> {
     *                     Order order1 = (Order) o1;
     *                     Order order2 = (Order) o2;
     *
     *                     double order1Distance = DistanceCalculator.calculateDistance(order1.getPickup(), order1.getDelivery());
     *                     double order2Distance = DistanceCalculator.calculateDistance(order2.getPickup(), order2.getDelivery());
     *
     *                     if (order1Distance - order2Distance > 0) {
     *                         return 1;
     *                     }
     *                     return (order1Distance == order2Distance) ? 0 : -1;
     *                 });
     */
    private void loadMaps() {

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


        Function vipOrdersFunction = order -> {
            Order order1 = (Order) order;
            if (order1.getVip()) {
                return 0;
            }
            return 1;
        };
        functionMap.put("vip", vipOrdersFunction);

        Function foodOrdersFunction = order -> {
            Order order1 = (Order) order;
            if (order1.getFood()) {
                return 0;
            }
            return 1;
        };
        functionMap.put("food", foodOrdersFunction);

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
