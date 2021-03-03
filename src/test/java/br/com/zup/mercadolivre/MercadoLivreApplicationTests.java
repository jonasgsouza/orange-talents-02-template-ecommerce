package br.com.zup.mercadolivre;

import br.com.zup.mercadolivre.controller.request.PaypalPaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MercadoLivreApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Deveria serializar corretamente para sucesso")
    void testPaypalPaymentStatusSerializingToSucess() throws JsonProcessingException {
        var result = objectMapper.writeValueAsString(PaypalPaymentStatus.SUCESSO);
        Assertions.assertEquals("1", result);
    }

    @Test
    @DisplayName("Deveria serializar corretamente para erro")
    void testPaypalPaymentStatusSerializingToError() throws JsonProcessingException {
        var result = objectMapper.writeValueAsString(PaypalPaymentStatus.SUCESSO);
        Assertions.assertEquals("1", result);
    }

    @Test
    @DisplayName("Deveria deserializar corretamente para sucesso")
    void testPaypalPaymentStatusDeserializingFromSuccess() throws JsonProcessingException {
        var status = (PaypalPaymentStatus) objectMapper.readerFor(PaypalPaymentStatus.class).readValue("1");
        Assertions.assertEquals(PaypalPaymentStatus.SUCESSO, status);
    }

    @Test
    @DisplayName("Deveria deserializar corretamente para erro")
    void testPaypalPaymentStatusDeserializingFromError() throws JsonProcessingException {
        var status = (PaypalPaymentStatus) objectMapper.readerFor(PaypalPaymentStatus.class).readValue("0");
        Assertions.assertEquals(PaypalPaymentStatus.ERRO, status);
    }

}
