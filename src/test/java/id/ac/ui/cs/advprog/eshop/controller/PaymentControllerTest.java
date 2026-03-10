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
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPaymentDetailForm() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentDetailById() throws Exception {
        mockMvc.perform(get("/payment/detail/pay-123"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentAdminList() throws Exception {
        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPaymentAdminDetail() throws Exception {
        mockMvc.perform(get("/payment/admin/detail/pay-123"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostPaymentAdminSetStatus() throws Exception {
        mockMvc.perform(post("/payment/admin/set-status/pay-123")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection());
    }
}