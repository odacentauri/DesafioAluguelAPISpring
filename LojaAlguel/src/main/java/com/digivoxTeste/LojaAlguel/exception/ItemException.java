package com.digivoxTeste.LojaAlguel.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ItemException extends CRUDException {

    private static final long serialVersionUID = 4482507480143927033L;

    public ItemException(){
        super();
        super.setErro(getClass().getSimpleName());
    }

    public ItemException(String entidade, String servico, String metodo, List<String> mensagens,
                            List<String> descricoes) {
        super(HttpStatus.PRECONDITION_FAILED.value(), ItemException.class.getSimpleName(), entidade, servico, metodo,
                mensagens, descricoes);
    }

    public ItemException(String entidade, String servico, String metodo, String mensagem, String descricao) {
        super(HttpStatus.PRECONDITION_FAILED.value(), ItemException.class.getSimpleName(), entidade, servico, metodo,
                mensagem, descricao);
    }

    public ItemException(GlobalException exception) {
        this(exception.getEntidade(), exception.getServico(), exception.getMetodo(), exception.getAlvos(),
                exception.getDescricoes());
    }
}
