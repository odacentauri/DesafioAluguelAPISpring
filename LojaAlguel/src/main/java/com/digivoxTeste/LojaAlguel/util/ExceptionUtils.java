package com.digivoxTeste.LojaAlguel.util;

import com.digivoxTeste.LojaAlguel.exception.GlobalException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ExceptionUtils {

    public static GlobalException montarConstraintViolationException(ConstraintViolationException exception,
                                                                     String entidade, String servico, String metodo) {
        Set<ConstraintViolation<?>> constraints = exception.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraints.iterator();
        List<String> alvos = new ArrayList<>();
        List<String> descricoes = new ArrayList<>();

        while (iterator.hasNext()) {
            ConstraintViolation<?> constraint = iterator.next();

            alvos.add(constraint.getPropertyPath().toString());
            descricoes.add(constraint.getMessage());
        }

        return new GlobalException(entidade, servico, metodo, alvos, descricoes);
    }
}
