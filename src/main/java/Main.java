import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderListRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService();

        //Ich komme mit den Produkten durcheinander

        Product product1 = new Product("1", "Blume");
        Product product2 = new Product("2", "Benz");
        Product product3 = new Product("3", "EinDings");

        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);

        Order order1 = new Order("1", List.of(product1,product2),OrderStatus.PROCESSING, Instant.now());
        Order order2 = new Order("2", List.of(product3,product1),OrderStatus.PROCESSING, Instant.now());
        Order order3 = new Order("3", List.of(product3,product2),OrderStatus.PROCESSING, Instant.now());

        try {
            shopService.addOrder(List.of(product1.id(),product2.id()));
            shopService.addOrder(List.of(product3.id(),product1.id()));
            shopService.addOrder(List.of(product2.id(),product3.id()));
        } catch (IdNotFoundException e) {
            System.out.println("Id not found");
        }

        System.out.println(orderRepo.getOrders());

//        shopService.updateOrderStatus("1",OrderStatus.COMPLETED);
//        shopService.updateOrderStatus("2",OrderStatus.IN_DELIVERY);
//
//        System.out.println(shopService.getOrdersOfSpecificOrderStatus(OrderStatus.PROCESSING).toString());


    }
}
