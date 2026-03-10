package id.ac.ui.cs.advprog.eshop.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void testContainsValidStatus() {
        assertTrue(OrderStatus.contains("WAITING_PAYMENT"));
        assertTrue(OrderStatus.contains("SUCCESS"));
        assertTrue(OrderStatus.contains("FAILED"));
        assertTrue(OrderStatus.contains("CANCELLED"));
    }

    @Test
    void testContainsInvalidStatus() {
        assertFalse(OrderStatus.contains("INVALID"));
        assertFalse(OrderStatus.contains("MEOW"));
        assertFalse(OrderStatus.contains(""));
    }

    @Test
    void testGetValue() {
        assertEquals("WAITING_PAYMENT", OrderStatus.WAITING_PAYMENT.getValue());
        assertEquals("SUCCESS", OrderStatus.SUCCESS.getValue());
        assertEquals("FAILED", OrderStatus.FAILED.getValue());
        assertEquals("CANCELLED", OrderStatus.CANCELLED.getValue());
    }
}
