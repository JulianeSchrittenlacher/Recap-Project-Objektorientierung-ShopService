import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    private static final Instant TEST_TIME_OF_ORDER = Instant.now();
//    //mein Test
//    @Test
//    void addOrderTest() throws IdNotFoundException {
//        //GIVEN
//        ShopService shopService = new ShopService();
//        List<String> productsIds = List.of("1");
////        ProductRepo repo = new ProductRepo();
////        repo.addProduct(new Product("1","Apfel"));
////        repo.addProduct(new Product("2","Birne"));
////        repo.addProduct(new Product("3","Pflaume"));
//
//        //WHEN
//        //Order actual = null;
//
//         Order actual = shopService.addOrder(productsIds);
//
//         //THEN
//        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, TEST_TIME_OF_ORDER);
//        assertEquals(expected.products(), actual.products());
//        assertNotNull(expected.id());
//    }
//    //Meine Tests
//    @Test
//    void addOrderTest_whenInvalidProductId_thenThrowException() {
//        //GIVEN
//        ShopService shopService = new ShopService();
//        List<String> productsIds = List.of("1", "2","254");
//
//        //WHEN
//        try {
//            shopService.addOrder(productsIds);
//            fail("Expected IdNotFoundException, but was not thrown");
//        } catch (IdNotFoundException e) {
//        }
//
////        //THEN
////        assertThrows(IdNotFoundException.class, () -> shopService.addOrder(productsIds));
//    }
    @Test
    void addOrder() throws IdNotFoundException{


    }

    @Test
    void getOrdersOfSpecificOrderStatus_ListOfOrdersWithProgressing_whenCalledWithProgressing() throws IdNotFoundException {
        //GIVEN
        ShopService shopService = new ShopService();

        Order newOrder1 = shopService.addOrder(List.of("1", "3"));
        OrderStatus updatedOrderStatus = shopService.updateOrderStatus(newOrder1.id(),OrderStatus.IN_DELIVERY);

        Order newOrder2 = shopService.addOrder(List.of("1"));

        //WHEN
        List<Order> actual1 = shopService.getOrdersOfSpecificOrderStatus(OrderStatus.IN_DELIVERY);
        List<Order> actual2 = shopService.getOrdersOfSpecificOrderStatus(OrderStatus.PROCESSING);
        List<Order> actual3 = shopService.getOrdersOfSpecificOrderStatus(OrderStatus.COMPLETED);

        //THEN
        assertEquals(1, actual1.size());
        assertEquals(1, actual2.size());
        assertEquals(0, actual3.size());
    }

    @Test
    void updateOrderStatus() throws IdNotFoundException {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Order oldOrder = shopService.addOrder(productsIds);

        //WHEN
        shopService.updateOrderStatus(oldOrder.id(), OrderStatus.COMPLETED);

        //THEN
        List<Order> completedOrders = shopService.getOrdersOfSpecificOrderStatus(OrderStatus.COMPLETED);
        assertEquals(1,completedOrders.size());
        List<Order> processingOrders = shopService.getOrdersOfSpecificOrderStatus(OrderStatus.PROCESSING);
        assertEquals(0,processingOrders.size());
    }
}
