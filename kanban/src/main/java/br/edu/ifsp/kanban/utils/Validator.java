package br.edu.ifsp.kanban.utils;

import java.util.function.Consumer;

public class Validator {
    public static <T> void atribuirSeNaoNulo (T valor, Consumer<T> setter) {
        if (valor != null) {
            setter.accept(valor);
        }
    }

    public static <T> void erroSeNulo(T valor, String mensagem) {
        if (valor == null) {
            throw new RuntimeException(mensagem);
        }
    }
}
