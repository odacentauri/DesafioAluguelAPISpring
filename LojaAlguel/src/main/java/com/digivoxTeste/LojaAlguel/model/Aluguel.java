package com.digivoxTeste.LojaAlguel.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "aluguel")
public class Aluguel extends AbstractEntity implements Serializable {

    private Item itemAlugado;
    private Date dataAluguel;
    private Date dataDevolucao;
    private Cliente cliente;

    public Aluguel(){

    }

    public Aluguel(Item itemAlugado, int qtdAlugada){
        this.itemAlugado = itemAlugado;
        this.dataAluguel = new Date();
        this.dataDevolucao = null;
    }

    public Item getItemAlugado() {
        return itemAlugado;
    }
    public void setItemAlugado(Item itemAlugado) {
        this.itemAlugado = itemAlugado;
    }
    public Date getDataAluguel() {
        return dataAluguel;
    }
    public void setDataAluguel(Date dataAluguel) {
        this.dataAluguel = dataAluguel;
    }
    public Date getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
