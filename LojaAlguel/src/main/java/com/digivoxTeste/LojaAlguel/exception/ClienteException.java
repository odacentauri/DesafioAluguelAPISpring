package com.digivoxTeste.LojaAlguel.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ClienteException extends CRUDException {

    private static final long serialVersionUID = 4482507480143927033L;

    public ClienteException(){
        super();
        super.setErro(getClass().getSimpleName());
    }

    public ClienteException(String entidade, String servico, String metodo, List<String> mensagens,
                             List<String> descricoes) {
        super(HttpStatus.PRECONDITION_FAILED.value(), ClienteException.class.getSimpleName(), entidade, servico, metodo,
                mensagens, descricoes);
    }

    public ClienteException(String entidade, String servico, String metodo, String mensagem, String descricao) {
        super(HttpStatus.PRECONDITION_FAILED.value(), ClienteException.class.getSimpleName(), entidade, servico, metodo,
                mensagem, descricao);
    }

    public ClienteException(GlobalException exception) {
        this(exception.getEntidade(), exception.getServico(), exception.getMetodo(), exception.getAlvos(),
                exception.getDescricoes());
    }
}
