package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewOpinionRequest;
import br.com.zup.mercadolivre.repository.OpinionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class OpinionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OpinionRepository opinionRepository;

    private ResultActions performRequest(NewOpinionRequest request, Long productId) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/products/" + productId + "/opinions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
    }

    @Test
    @DisplayName("Deveria criar uma nova opinião")
    public void shouldCreateNewOpinion() throws Exception {
        var request = new NewOpinionRequest(Short.valueOf("1"), "Minha opinião", "Uma descrição");
        performRequest(request, 1L).andExpect(MockMvcResultMatchers.status().isOk());
        var optional = opinionRepository.findById(1L);
        Assertions.assertTrue(optional.isPresent());
        var product = optional.get();
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals(request.getTitle(), product.getTitle());
    }

    @Test
    @DisplayName("Deveria retornar status 400 com opinião sem nota e título")
    public void shouldCreateReturnBadRequestWithMissingNoteAndTitle() throws Exception {
        var request = new NewOpinionRequest(null, null, "Uma descrição");
        performRequest(request, 1L).andExpect(MockMvcResultMatchers.status().isBadRequest());
        Assertions.assertEquals(0, opinionRepository.count());
    }
}
