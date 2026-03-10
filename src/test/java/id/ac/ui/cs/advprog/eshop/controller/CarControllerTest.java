package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    // Need to mock ProductService since CarController extends ProductController
    @MockBean
    private ProductService productService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Toyota")
                        .param("carColor", "Red")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        when(carService.findAll()).thenReturn(List.of(car));

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attribute("cars", List.of(car)));
    }

    @Test
    void testEditCarPage() throws Exception {
        when(carService.findById("1")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void testEditCarPost() throws Exception {
        mockMvc.perform(post("/car/editCar")
                        .param("carId", "1")
                        .param("carName", "Honda"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).update(eq("1"), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).deleteCarById("1");
    }
}
