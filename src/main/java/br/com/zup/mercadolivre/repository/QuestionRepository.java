package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByProductId(Long productId);
}
