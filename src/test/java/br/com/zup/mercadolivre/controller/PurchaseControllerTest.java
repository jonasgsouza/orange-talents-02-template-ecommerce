package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewPurchaseRequest;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.PaymentGateway;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import br.com.zup.mercadolivre.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
@WithUserDetails("admin@email.com")
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    private Product product;

    private ResultActions performRequest(NewPurchaseRequest request) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
    }

    @BeforeEach
    public void setUp() {
        product = productRepository.findById(1L).orElseThrow(() -> new NotFoundException(1L));
        var user = userRepository.findById(1L).orElseThrow(() -> new NotFoundException(1L));
    }

    @Test
    @DisplayName("Deveria criar uma nova compra com o gateway paypal")
    public void shouldCreateNewPurchaseWithPaypal() throws Exception {
        var request = new NewPurchaseRequest(PaymentGateway.PAYPAL, 10, product.getId());
        performRequest(request).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.FOUND.value())
        );
        var optional = purchaseRepository.findById(2L);
        Assertions.assertTrue(optional.isPresent());
        var purchase = optional.get();
        Assertions.assertAll(() -> {
            Assertions.assertEquals(request.getPaymentGateway(), purchase.getPaymentGateway());
            Assertions.assertEquals(request.getQuantity(), purchase.getQuantity());
            Assertions.assertEquals(1, purchaseRepository.count());
        });
    }

    @Test
    @DisplayName("Deveria criar uma nova compra com o gateway pagseguro")
    public void shouldCreateNewPurchaseWithPagseguro() throws Exception {
        var request = new NewPurchaseRequest(PaymentGateway.PAGSEGURO, 10, product.getId());
        performRequest(request).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.FOUND.value())
        );
        var optional = purchaseRepository.findById(1L);
        Assertions.assertTrue(optional.isPresent());
        var purchase = optional.get();
        Assertions.assertAll(() -> {
            Assertions.assertEquals(request.getPaymentGateway(), purchase.getPaymentGateway());
            Assertions.assertEquals(request.getQuantity(), purchase.getQuantity());
            Assertions.assertEquals(1, purchaseRepository.count());
        });
    }

    @Test
    @DisplayName("Deveria retornar status 400 se quantidade maior que o estoque")
    public void shouldReturnBadRequestIfQuantityGreaterThanInventory() throws Exception {
        var request = new NewPurchaseRequest(PaymentGateway.PAYPAL, 40, product.getId());
        performRequest(request).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
        Assertions.assertEquals(0, purchaseRepository.count());
    }
}
