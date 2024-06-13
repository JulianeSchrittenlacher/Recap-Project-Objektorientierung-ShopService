import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo =productRepo;
        this.orderRepo =orderRepo;
    }


    public Order addOrder(List<String> productIds) throws IdNotFoundException{
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
                Product productToOrder = productRepo.getProductById(productId).orElseThrow(()->new IdNotFoundException(productId));
                products.add(productToOrder);
        }
//        for (String productId : productIds) {
//            Product productToOrder = productRepo.getProductById(productId);
//            if (productToOrder == null) {
//                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
//                return null;
//            }
//            products.add(productToOrder);
//        }
        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());
        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersOfSpecificOrderStatus(OrderStatus orderStatus) {

        return switch (orderStatus) {
            case PROCESSING -> orderRepo.getOrders().stream()
                    .filter(c -> c.orderStatus().equals(OrderStatus.PROCESSING))
                    .collect(Collectors.toList());
            case IN_DELIVERY -> orderRepo.getOrders().stream()
                    .filter(c -> c.orderStatus().equals(OrderStatus.IN_DELIVERY))
                    .collect(Collectors.toList());
            case COMPLETED -> orderRepo.getOrders().stream()
                    .filter(c -> c.orderStatus().equals(OrderStatus.COMPLETED))
                    .collect(Collectors.toList());
        };
    }

    public OrderStatus updateOrderStatus(String orderId, OrderStatus newOrderStatus) {
        Order order = orderRepo.getOrderById(orderId);
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(order.withOrderStatus(newOrderStatus));
        return newOrderStatus;
    }
}
