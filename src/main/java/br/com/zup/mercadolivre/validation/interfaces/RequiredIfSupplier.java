package br.com.zup.mercadolivre.validation.interfaces;

import javax.persistence.EntityManager;

public interface RequiredIfSupplier<T> {

    Boolean isRequired(EntityManager manager, T value);

}
