package com.digivoxTeste.LojaAlguel.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidacaoException extends GlobalException {

    private static final long serialVersionUID = -3433474774975980600L;

    public ValidacaoException(int codigoStatus, String erro, String entidade, String servico, String metodo,
                              List<String> mensagens, List<String> descricoes) {
        super(codigoStatus, erro, entidade, servico, metodo, mensagens, descricoes);
    }

    public ValidacaoException(int codigoStatus, String erro, String entidade, String servico, String metodo,
                              String mensagem, String descricao) {
        super(codigoStatus, erro, entidade, servico, metodo, mensagem, descricao);
    }

    public ValidacaoException(String entidade, String servico, String metodo, List<String> mensagens,
                              List<String> descricoes) {
        this(HttpStatus.PRECONDITION_FAILED.value(), ValidacaoException.class.getSimpleName(), entidade, servico,
                metodo, mensagens, descricoes);
    }

    public ValidacaoException(String entidade, String servico, String metodo, String mensagem, String descricao) {
        this(HttpStatus.PRECONDITION_FAILED.value(), ValidacaoException.class.getSimpleName(), entidade, servico,
                metodo, mensagem, descricao);
    }

    public ValidacaoException(GlobalException exception) {
        this(exception.getEntidade(), exception.getServico(), exception.getMetodo(), exception.getAlvos(),
                exception.getDescricoes());
    }
}
