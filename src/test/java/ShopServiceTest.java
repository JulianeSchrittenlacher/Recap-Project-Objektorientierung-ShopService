import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = null;
        try {
            actual = shopService.addOrder(productsIds);
        } catch (IdNotFoundException e) {
            assertNull(actual);
        }

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
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
}
