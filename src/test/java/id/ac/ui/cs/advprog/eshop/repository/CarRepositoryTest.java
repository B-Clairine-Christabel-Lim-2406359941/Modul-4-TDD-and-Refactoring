package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateWithId() {
        Car car = new Car();
        car.setCarId("UUID");
        car.setCarName("Ferari");
        car.setCarColor("Red");
        car.setCarQuantity(2);

        Car savedCar = carRepository.create(car);
        assertEquals("UUID", savedCar.getCarId());
    }

    @Test
    void testCreateWithoutIdGeneratesId() {
        Car car = new Car();
        car.setCarName("Honda");
        car.setCarColor("Blue");
        car.setCarQuantity(1);

        Car savedCar = carRepository.create(car);
        assertNotNull(savedCar.getCarId());
    }

    @Test
    void testFindAll() {
        Car car = new Car();
        car.setCarId("car-1");
        carRepository.create(car);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals("car-1", iterator.next().getCarId());
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        car.setCarId("1");
        carRepository.create(car);

        Car foundCar = carRepository.findById("1");
        assertNotNull(foundCar);
        assertEquals("1", foundCar.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car car = new Car();
        car.setCarId("1");
        carRepository.create(car);

        Car foundCar = carRepository.findById("nonexistent");
        assertNull(foundCar);
    }

    @Test
    void testUpdateFound() {
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("OldName");
        carRepository.create(car);

        Car updateData = new Car();
        updateData.setCarName("NewName");
        updateData.setCarColor("Red");
        updateData.setCarQuantity(5);

        Car result = carRepository.update("1", updateData);
        assertNotNull(result);
        assertEquals("NewName", result.getCarName());
        assertEquals("Red", result.getCarColor());
        assertEquals(5, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("OldName");
        carRepository.create(car);

        Car updateData = new Car();
        Car result = carRepository.update("nonexistent", updateData);
        assertNull(result);
    }

    @Test
    void testDeleteFound() {
        Car car = new Car();
        car.setCarId("1");
        carRepository.create(car);

        carRepository.delete("1");
        assertNull(carRepository.findById("1"));
    }

    @Test
    void testDeleteNotFoundDoesNothing() {
        Car car = new Car();
        car.setCarId("1");
        carRepository.create(car);

        carRepository.delete("nonexistent");
        assertNotNull(carRepository.findById("1")); // Car 1 should still exist
        assertNull(carRepository.findById("nonexistent"));
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> iterator = carRepository.findAll();
        assertFalse(iterator.hasNext());
    }
}
