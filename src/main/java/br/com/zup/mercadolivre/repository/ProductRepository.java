package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByName(String name);

    @Modifying
    @Query("update Product p set p.quantity = p.quantity - :quantity where p.id = :id and p.quantity >= :quantity")
    Integer decreaseInventory(Long id, Integer quantity);
}
