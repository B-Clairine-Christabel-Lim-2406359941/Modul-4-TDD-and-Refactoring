package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostOrderHistory() throws Exception {
        mockMvc.perform(post("/order/history")
                        .param("author", "clairine.christabel"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentOrderPage() throws Exception {
        mockMvc.perform(get("/order/pay/ord-123"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostPaymentOrder() throws Exception {
        mockMvc.perform(post("/order/pay/ord-123"))
                .andExpect(status().isOk());
    }
}