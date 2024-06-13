import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2", "3");
//        ProductRepo repo = new ProductRepo();
//        repo.addProduct(new Product("1","Apfel"));
//        repo.addProduct(new Product("2","Birne"));
//        repo.addProduct(new Product("3","Pflaume"));

        //WHEN
        //Order actual = null;
        Order actual = null;
        try {
            actual = shopService.addOrder(productsIds);
        } catch (IdNotFoundException e) {
        }

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel"), new Product("2", "Birne"),
                new Product("3", "Pflaume")), OrderStatus.PROCESSING, ZonedDateTime.now());
        assertEquals(expected, actual);
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_thenThrowException() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2","254");

        //WHEN
        try {
            shopService.addOrder(productsIds);
            fail("Expected IdNotFoundException, but was not thrown");
        } catch (IdNotFoundException e) {

        }

//        //THEN
//        assertThrows(IdNotFoundException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersOfSpecificOrderStatus_ListOfOrdersWithProgressing_whenCalledWithProgressing() {
        //GIVEN
        ShopService shopService = new ShopService();
        Order order1 = new Order("1", List.of(), OrderStatus.PROCESSING, ZonedDateTime.now());
        Order order2 = new Order("2", List.of(), OrderStatus.PROCESSING, ZonedDateTime.now());
        Order order3 = new Order("3", List.of(), OrderStatus.IN_DELIVERY, ZonedDateTime.now());
        Order order4 = new Order("4", List.of(), OrderStatus.COMPLETED, ZonedDateTime.now());
        try {
            shopService.addOrder(List.of(order1.id(), order2.id(), order3.id(), order4.id()));
        } catch (IdNotFoundException e) {}

        //WHEN
        List<Order> actual = shopService.getOrdersOfSpecificOrderStatus(OrderStatus.IN_DELIVERY);

        //THEN
        assertEquals(List.of(order3), actual);


    }

    @Test
    void updateOrderStatus() {
    }
}
