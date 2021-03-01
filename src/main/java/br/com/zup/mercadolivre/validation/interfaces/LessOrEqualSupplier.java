package br.com.zup.mercadolivre.validation.interfaces;

public interface LessOrEqualSupplier<T, C> {

    Comparable<C> get(T o);

    default String message() {
        return "{messages.LessOrEqual}";
    }
}
