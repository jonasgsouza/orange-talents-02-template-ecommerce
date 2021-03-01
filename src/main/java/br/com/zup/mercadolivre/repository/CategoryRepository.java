package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
