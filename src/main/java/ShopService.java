import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Data
@RequiredArgsConstructor

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final UtilService utilService;

//    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
//        this.productRepo =productRepo;
//        this.orderRepo =orderRepo;
//    }


    public Order addOrder(List<String> productIds) throws IdNotFoundException{
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
                Product productToOrder = productRepo.getProductById(productId).orElseThrow(()->new IdNotFoundException(productId));
                products.add(productToOrder);
        }
        Order newOrder = new Order(utilService.generateRandomId(), products, OrderStatus.PROCESSING, utilService.getCurrentTime());
        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersOfSpecificOrderStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream()
                .filter(c->c.orderStatus().equals(orderStatus))
                .toList();
    }

    public OrderStatus updateOrderStatus(String orderId, OrderStatus newOrderStatus) {
        Order order = orderRepo.getOrderById(orderId);
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(order.withOrderStatus(newOrderStatus));
        return newOrderStatus;
    }
}
