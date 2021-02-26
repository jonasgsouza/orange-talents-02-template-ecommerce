package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewQuestionRequest;
import br.com.zup.mercadolivre.repository.QuestionRepository;
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
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions performRequest(NewQuestionRequest request) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
    }

    @Test
    @DisplayName("Deveria criar uma nova pergunta")
    public void shouldCreateNewQuestion() throws Exception {
        var request = new NewQuestionRequest("Esse notebook é bom?", 1L);
        performRequest(request).andExpect(MockMvcResultMatchers.status().isOk());
        var optional = questionRepository.findById(1L);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(request.getTitle(), optional.get().getTitle());
    }
}
