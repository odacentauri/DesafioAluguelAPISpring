package com.digivoxTeste.LojaAlguel.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AluguelException extends CRUDException {

    private static final long serialVersionUID = 4482507480143927033L;

    public AluguelException(){
        super();
        super.setErro(getClass().getSimpleName());
    }

    public AluguelException(String entidade, String servico, String metodo, List<String> mensagens,
                             List<String> descricoes) {
        super(HttpStatus.PRECONDITION_FAILED.value(), AluguelException.class.getSimpleName(), entidade, servico, metodo,
                mensagens, descricoes);
    }

    public AluguelException(String entidade, String servico, String metodo, String mensagem, String descricao) {
        super(HttpStatus.PRECONDITION_FAILED.value(), AluguelException.class.getSimpleName(), entidade, servico, metodo,
                mensagem, descricao);
    }

    public AluguelException(GlobalException exception) {
        this(exception.getEntidade(), exception.getServico(), exception.getMetodo(), exception.getAlvos(),
                exception.getDescricoes());
    }
}
