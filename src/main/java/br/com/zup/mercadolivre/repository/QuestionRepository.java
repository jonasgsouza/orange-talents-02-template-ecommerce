package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
