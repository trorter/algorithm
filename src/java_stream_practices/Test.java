package java_stream_practices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {

    private final List<Customer> customers = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

//    public List<Customer> getCustomers() {
//        return customers;
//    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    private void init() {
        initCusromers();
        initProducts();
        initOrders();
    }

    private void initCusromers() {
        this.customers.add(new Customer(1L, "Tom", 1));
        this.customers.add(new Customer(2L, "Jack", 2));
        this.customers.add(new Customer(3L, "Lilly", 3));
        this.customers.add(new Customer(4L, "Sally", 1));
        this.customers.add(new Customer(5L, "Dick", 2));
        this.customers.add(new Customer(6L, "Red", 3));
        this.customers.add(new Customer(7L, "Nancy", 4));
        this.customers.add(new Customer(8L, "Andrey", 1));
        this.customers.add(new Customer(9L, "Edward", null));
        this.customers.add(new Customer(10L, "Edison", 3));
        this.customers.add(new Customer(11L, "Radle", 2));
        this.customers.add(new Customer(12L, "Stive", 1));
    }

    private void initProducts() {
        this.products.add(new Product(1L, "Book", "A", 400.12));
        this.products.add(new Product(2L, "Toy", "B", 300.12));
        this.products.add(new Product(3L, "Shelf", "C", 200.12));
        this.products.add(new Product(4L, "TV", "D", 100.12));
        this.products.add(new Product(5L, "Washing machine", "A", 5.12));
        this.products.add(new Product(6L, "Minn Car", "B", 1000.12));
        this.products.add(new Product(7L, "Glass", "C", 900.12));
        this.products.add(new Product(8L, "Book", "D", 800.12));
        this.products.add(new Product(9L, "Toy", "A", 700.12));
        this.products.add(new Product(11L, "Shelf", "B", 600.12));
        this.products.add(new Product(12L, "TV", "C", 500.12));
        this.products.add(new Product(13L, "Washing machine", "D", 400.12));
        this.products.add(new Product(14L, "Minn Car", "A", 300.12));
        this.products.add(new Product(15L, "Glass", "B", 200.12));
        this.products.add(new Product(16L, "Book", "C", 100.12));
        this.products.add(new Product(17L, "Toy", "D", 50.12));
        this.products.add(new Product(18L, "Shelf", "A", 600.12));
        this.products.add(new Product(19L, "TV", "B", 500.12));
        this.products.add(new Product(20L, "Washing machine", "C", 400.12));
        this.products.add(new Product(21L, "Minn Car", "D", 200.12));
        this.products.add(new Product(22L, "Glass", "A", 100.12));
    }

    private void initOrders() {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            for (int i = 1; 
                 i < 30; 
                 i++) {
                Order order = new Order((long) i, sdf.parse(String.format("%02d", i) + "-01-2022"), sdf.parse(String.format("%02d", i) + "-03-2022"), "open", this.customers.get(i % 11 + 1));
                this.orders.add(order);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Order order: this.orders) {
            int countOfProducts = new Random().nextInt(this.products.size());
            if (countOfProducts == 0) {
                continue;
            }

            for (int i = 1; i < countOfProducts; i++) {
                order.getProducts().add(this.products.get(new Random().nextInt(this.products.size())));
            }
        }
    }

    public static void main (String[] args) {
        System.out.println("START!");

        final Test test = new Test();
        test.init();

        //Exercise 1 — Obtain a list of products belongs to category “A” with price > 100
        System.out.println("Ex. 1");
        final List<Product> selectedProducts1 = test.getProducts().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("A"))
                .filter(product -> product.getPrice() > 100).collect(Collectors.toList());
        System.out.println(selectedProducts1);

        //Exercise 2 — Obtain a list of order with products belong to category “D”
        System.out.println("Ex. 2");
        final List<Order> selectedOrders = test.getOrders().parallelStream()
                .filter(order -> order.getProducts().parallelStream().anyMatch(product -> product.getCategory().equalsIgnoreCase("D")))
                .collect(Collectors.toList());
        System.out.println(selectedOrders);

        //Exercise 3 — Obtain a list of product with category = “B” and then apply 10% discount
        System.out.println("Ex. 3");
        final List<Product> selectedProducts2 = test.getProducts().parallelStream()
                .filter(product -> product.getCategory().equalsIgnoreCase("B"))
                .map(product -> product.withPrice(product.getPrice() * 0.9))
                .collect(Collectors.toList());
        System.out.println(selectedProducts2);

        //Exercise 4 — Obtain a list of products ordered by customer of tier 1 and after 25-Mar-2022
        System.out.println("Ex. 4");
        final List<Product> selectedProducts3 = test.getOrders().parallelStream()
                .filter(order -> order.getProducts() != null)
                .filter(order -> (order.getCustomer().getTier() != null && order.getCustomer().getTier().equals(1)))
                .filter(order -> {
                    try {
                        return order.getDeliveryDate().after(new SimpleDateFormat("dd-MM-yyyy").parse("25-03-2022"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());
        System.out.println(selectedProducts3);

        //Exercise 5 — Get the cheapest products of “D” category
        System.out.println("Ex. 5");
        final double minPriceForD = test.getProducts().parallelStream()
                .filter(product -> product.getCategory().equalsIgnoreCase("D"))
                .min(Comparator.comparing(Product::getPrice))
                .orElse(new Product(Long.MIN_VALUE, "not found", "not found", Double.MIN_VALUE))
                .getPrice();
        System.out.println("minPriceForD=" + minPriceForD);

        //Exercise 6 — Get the 3 most recent placed order
        System.out.println("Ex. 6");
        final List<Order> selectedOrders2 = test.getOrders().parallelStream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(selectedOrders2);

        //Exercise 7 — Get a list of orders which were ordered on 15-Jan-2022, log the order records to the console and then return its product list
        System.out.println("Ex. 7");
        final List<Product> selectedProducts4 = test.getOrders().stream()
                .filter(order -> {
                    try {
                        return order.getOrderDate().equals(
                                new SimpleDateFormat("dd-MM-yyyy").parse("15-01-2022")
                        );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .peek(System.out::println)
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(selectedProducts4);

        //Exercise 8 — Calculate total lump sum of all orders placed after 20-Jab-2022
        System.out.println("Ex. 8");
        final double totalAmountOfPlacedOrders = test.getOrders().stream()
                .filter(order -> {
                    try {
                        return order.getOrderDate().after(new SimpleDateFormat("dd-MM-yyyy").parse("20-01-2022"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
        System.out.println("totalAmountOfPlacedOrders=" + totalAmountOfPlacedOrders);

        //Exercise 9 — Calculate order average payment placed on 14-01-2022
        System.out.println("Ex. 9");
        final double orderAveragePayment = test.getOrders().parallelStream()
                .filter(order -> {
                    try {
                        return order.getOrderDate().equals(new SimpleDateFormat("dd-MM-yyyy").parse("14-01-2022"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(Double.MIN_VALUE);
        System.out.println("orderAveragePayment=" + orderAveragePayment);

        //Exercise 10 — Obtain a collection of statistic figures (i.e. sum, average, max, min, count) for all products of category “A”
        System.out.println("Ex. 10");
        DoubleSummaryStatistics statistics = test.getProducts().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("A"))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
        System.out.println(
                "count=" + statistics.getCount() +
                ", average" + statistics.getAverage() +
                ", max=" + statistics.getMax() +
                ", min=" + statistics.getMin() +
                ", sum=" + statistics.getSum()
        );

        //Exercise 11 — Obtain a data map with order id and order’s product count
        System.out.println("Ex. 11");
        Map<Long, Integer> productsInOrderCount = test.getOrders().stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts().size()));
        System.out.println(productsInOrderCount);

        //Exercise 12 — Produce a data map with order records grouped by customer
        System.out.println("Ex. 12");
        Map<Customer, List<Order>> listOfOrdersByCustomer = test.getOrders().stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
        System.out.println("listOfOrdersByCustomer=" + listOfOrdersByCustomer);
        
        //Exercise 13 — Produce a data map with order record and product total sum
        System.out.println("Ex. 13");
        Map<Order, Double> ordersTotalSumList = test.getOrders().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum())
                );
        System.out.println("ordersTotalSumList=" + ordersTotalSumList);

        //Exercise 14 — Obtain a data map with list of product name by category
        System.out.println("Ex. 14");
        Map<String, List<String>> listOfProductsByCategory = test.getProducts().stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())
                ));
        System.out.println("listOfProductsByCategory=" + listOfProductsByCategory);

        //Exercise 15 — Get the most expensive product by category
        System.out.println("Ex. 15");
        Map<String, Optional<Product>> mostExpProductByCategory = test.getProducts().stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.maxBy(Comparator.comparingDouble(Product::getPrice))
                ));
        System.out.println("mostExpProductByCategory=" + mostExpProductByCategory);

        System.out.println("FINISHED!");
    }
}
