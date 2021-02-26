package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
