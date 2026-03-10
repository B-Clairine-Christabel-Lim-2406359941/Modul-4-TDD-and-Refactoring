package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    CarServiceImpl carService;

    @Mock
    CarRepository carRepository;

    Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-1");
        car.setCarName("Pajero");
    }

    @Test
    void testCreate() {
        when(carRepository.create(car)).thenReturn(car);
        Car result = carService.create(car);
        assertEquals(car, result);
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        Iterator<Car> iterator = List.of(car).iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();
        assertEquals(1, result.size());
        assertEquals("car-1", result.get(0).getCarId());
    }

    @Test
    void testFindById() {
        when(carRepository.findById("car-1")).thenReturn(car);
        Car result = carService.findById("car-1");
        assertEquals(car, result);
    }

    @Test
    void testUpdate() {
        carService.update("car-1", car);
        verify(carRepository, times(1)).update("car-1", car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("car-1");
        verify(carRepository, times(1)).delete("car-1");
    }
}
