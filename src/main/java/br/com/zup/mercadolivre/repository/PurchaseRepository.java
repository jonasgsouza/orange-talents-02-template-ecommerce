package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
}
