package com.digivoxTeste.LojaAlguel.exception;

import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GlobalException extends Exception {

    private static final long serialVersionUID = 4687060279524127179L;

    private static final String DATA_HORA_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private String dataHora;

    private int codigoStatus;

    private String erro;

    private String entidade;

    private String servico;

    private String metodo;

    private List<String> alvos;

    private List<String> descricoes;

    public GlobalException() {
        this.dataHora = new SimpleDateFormat(DATA_HORA_FORMAT).format(Calendar.getInstance().getTime());
        this.codigoStatus = 0;
        this.erro = getClass().getSimpleName();
        this.entidade = "";
        this.servico = "";
        this.metodo = "";
        this.alvos = new ArrayList<>();
        this.descricoes = new ArrayList<>();
    }

    public GlobalException(int codigoStatus, String erro, String entidade, String servico, String metodo,
                           List<String> alvos, List<String> descricoes) {
        super(erro, null, false, false);

        this.dataHora = new SimpleDateFormat(DATA_HORA_FORMAT).format(Calendar.getInstance().getTime());
        this.codigoStatus = codigoStatus;
        this.erro = erro;
        this.entidade = entidade;
        this.servico = servico;
        this.metodo = metodo;
        this.alvos = alvos;
        this.descricoes = descricoes;
    }

    public GlobalException(int codigoStatus, String erro, String entidade, String servico, String metodo, String alvo,
                           String descricao) {
        super(erro, null, false, false);

        this.dataHora = new SimpleDateFormat(DATA_HORA_FORMAT).format(Calendar.getInstance().getTime());
        this.codigoStatus = codigoStatus;
        this.erro = erro;
        this.entidade = entidade;
        this.servico = servico;
        this.metodo = metodo;
        this.alvos = new ArrayList<>();
        this.descricoes = new ArrayList<>();
        this.alvos.add(alvo);
        this.descricoes.add(descricao);
    }

    public GlobalException(String erro, String entidade, String servico, String metodo, List<String> alvos,
                           List<String> descricoes) {
        this(HttpStatus.PRECONDITION_FAILED.value(), erro, entidade, servico, metodo, alvos, descricoes);
    }

    public GlobalException(String erro, String entidade, String servico, String metodo, String alvo, String descricao) {
        this(HttpStatus.PRECONDITION_FAILED.value(), erro, entidade, servico, metodo, alvo, descricao);
    }

    public GlobalException(String entidade, String servico, String metodo, List<String> alvos,
                           List<String> descricoes) {
        this(HttpStatus.PRECONDITION_FAILED.value(), GlobalException.class.getSimpleName(), entidade, servico, metodo,
                alvos, descricoes);
    }

    public GlobalException(String entidade, String servico, String metodo, String alvo, String descricao) {
        this(HttpStatus.PRECONDITION_FAILED.value(), GlobalException.class.getSimpleName(), entidade, servico, metodo,
                alvo, descricao);
    }

    public String getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public int getCodigoStatus() {
        return this.codigoStatus;
    }

    public void setCodigoStatus(int codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public String getErro() {
        return this.erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getEntidade() {
        return this.entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getServico() {
        return this.servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getMetodo() {
        return this.metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public List<String> getAlvos() {
        return this.alvos;
    }

    public void setAlvos(List<String> alvos) {
        this.alvos = alvos;
    }

    public void addAlvo(String alvo) {
        this.alvos.add(alvo);
    }

    public void addAlvos(List<String> alvos) {
        this.alvos.addAll(alvos);
    }

    public List<String> getDescricoes() {
        return this.descricoes;
    }

    public void setDescricoes(List<String> descricoes) {
        this.descricoes = descricoes;
    }

    public void addDescricao(String descricao) {
        this.descricoes.add(descricao);
    }

    public void addDescricoes(List<String> descricoes) {
        this.descricoes.addAll(descricoes);
    }

    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
