package com.petrov;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        Map<String, List<Order>> orders1 = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct));

        orders1.forEach((product, productOrders) -> {
            System.out.println("Product: " + product);
            productOrders.forEach(order -> System.out.println("\t" + order));
        });

        System.out.println("-----------------------------------------------------");

        Map<String, Double> orders2 = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct, Collectors.summingDouble(Order::getCost)));

        orders2.forEach((product, sum) -> {
            System.out.println("Product: " + product);
             System.out.println("Sum: " + sum);
        });

        System.out.println("-----------------------------------------------------");

        Map<String, Double> totalCosts = orders.stream()
                .flatMap(order -> Stream.of(Map.entry(order.getProduct(), order.getCost())))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(Map.Entry::getValue)));

        totalCosts.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        System.out.println("-----------------------------------------------------");

        List<Order> orders3 = orders.stream()
                .sorted(Comparator.comparing(Order::getCost).reversed())
                .limit(3)
                .toList();

        orders3.forEach(order -> System.out.println(order.getProduct() + ": " + order.getCost()));

        System.out.println("-----------------------------------------------------");

        List<Order> orders4 = orders.stream()
                .sorted(Comparator.comparing(Order::getCost).reversed())
                .limit(3)
                .toList();

        double totalCost = orders4.stream()
                .mapToDouble(Order::getCost)
                .sum();

        orders4.forEach(order -> System.out.println(order.getProduct() + ": " + order.getCost()));
        System.out.println("Общая стоимость: " + totalCost);

    }
}
