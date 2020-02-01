package com.digivoxTeste.LojaAlguel.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ReservaException extends CRUDException {

    private static final long serialVersionUID = 4482507480143927033L;

    public ReservaException(){
        super();
        super.setErro(getClass().getSimpleName());
    }

    public ReservaException(String entidade, String servico, String metodo, List<String> mensagens,
                             List<String> descricoes) {
        super(HttpStatus.PRECONDITION_FAILED.value(), ReservaException.class.getSimpleName(), entidade, servico, metodo,
                mensagens, descricoes);
    }

    public ReservaException(String entidade, String servico, String metodo, String mensagem, String descricao) {
        super(HttpStatus.PRECONDITION_FAILED.value(), ReservaException.class.getSimpleName(), entidade, servico, metodo,
                mensagem, descricao);
    }

    public ReservaException(GlobalException exception) {
        this(exception.getEntidade(), exception.getServico(), exception.getMetodo(), exception.getAlvos(),
                exception.getDescricoes());
    }
}
