package br.com.zup.mercadolivre.repository;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

    Optional<Purchase> findByUuid(UUID uuid);
}
