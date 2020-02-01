package com.digivoxTeste.LojaAlguel.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CRUDException  extends GlobalException {

    private static final long serialVersionUID = 1999363697958754655L;

    public CRUDException() {
        super();
    }

    public CRUDException(int codigoStatus, String erro, String entidade, String servico, String metodo,
                         List<String> mensagens, List<String> descricoes) {
        super(codigoStatus, erro, entidade, servico, metodo, mensagens, descricoes);
    }

    public CRUDException(int codigoStatus, String erro, String entidade, String servico, String metodo, String mensagem,
                         String descricao) {
        super(codigoStatus, erro, entidade, servico, metodo, mensagem, descricao);
    }

    public CRUDException(String entidade, String servico, String metodo, List<String> mensagens,
                         List<String> descricoes) {
        this(HttpStatus.PRECONDITION_FAILED.value(), CRUDException.class.getSimpleName(), entidade, servico, metodo,
                mensagens, descricoes);
    }

    public CRUDException(String entidade, String servico, String metodo, String mensagem, String descricao) {
        this(HttpStatus.PRECONDITION_FAILED.value(), CRUDException.class.getSimpleName(), entidade, servico, metodo,
                mensagem, descricao);
    }

    public CRUDException(GlobalException exception) {
        this(exception.getEntidade(), exception.getServico(), exception.getMetodo(), exception.getAlvos(),
                exception.getDescricoes());
    }
}
