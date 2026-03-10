package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {
    Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-1");
        car.setCarName("Avanza");
        car.setCarColor("Black");
        car.setCarQuantity(5);
    }

    @Test
    void testGetCarId() {
        assertEquals("car-1", car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Avanza", car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Black", car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(5, car.getCarQuantity());
    }
}
