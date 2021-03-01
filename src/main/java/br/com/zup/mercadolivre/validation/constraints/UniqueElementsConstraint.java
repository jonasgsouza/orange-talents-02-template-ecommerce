package br.com.zup.mercadolivre.validation.constraints;

import br.com.zup.mercadolivre.validation.annotation.UniqueElements;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.HashSet;

public class UniqueElementsConstraint implements ConstraintValidator<UniqueElements, Object> {

    @Override
    public boolean isValid(Object objects, ConstraintValidatorContext constraintValidatorContext) {
        if (!(objects instanceof Collection))
            throw new IllegalArgumentException("Attributes validated by UniqueElements must be a Collection");
        var set = new HashSet<>();
        for (Object o : (Collection) objects) {
            if (!set.add(o)) {
                return false;
            }
        }
        return true;
    }
}
