package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewPaymentRequest;
import br.com.zup.mercadolivre.controller.request.NewPaypalPaymentRequest;
import br.com.zup.mercadolivre.controller.request.PaypalPaymentStatus;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.PaymentGateway;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.TransactionStatus;
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
public class PaymentControllerTest {

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
    private Purchase purchase;

    private ResultActions performRequest(NewPaymentRequest request, String url) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
    }

    @BeforeEach
    public void setUp() {
        product = productRepository.findById(1L).orElseThrow(() -> new NotFoundException(1L));
        var user = userRepository.findById(1L).orElseThrow(() -> new NotFoundException(1L));
        purchase = new Purchase(10, PaymentGateway.PAYPAL, product, user);
        purchaseRepository.save(purchase);
    }

    @Test
    @DisplayName("Deveria processar o pagamento via paypal com status de sucesso")
    public void shouldProcessPaypalPaymentSuccessfully() throws Exception {
        var request = new NewPaypalPaymentRequest("id_do_gateway", PaypalPaymentStatus.SUCESSO);
        performRequest(request, "/payments/paypal/" + purchase.getUuid()).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
        Assertions.assertAll(() -> {
            Assertions.assertTrue(purchase.isFinished());
            Assertions.assertEquals(1, purchase.getTransactions().size());
        });

    }

    @Test
    @DisplayName("Deveria processar o pagamento via paypal com status de erro")
    public void shouldProcessPaypalPaymentWithError() throws Exception {
        var request = new NewPaypalPaymentRequest("id_do_gateway", PaypalPaymentStatus.ERRO);
        performRequest(request, "/payments/paypal/" + purchase.getUuid()).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
        Assertions.assertAll(() -> {
            Assertions.assertFalse(purchase.isFinished());
            Assertions.assertEquals(1, purchase.getTransactions().size());
            purchase.getTransactions().forEach(e ->
                    Assertions.assertEquals(TransactionStatus.FAILED, e.getStatus()));
        });

    }


}
