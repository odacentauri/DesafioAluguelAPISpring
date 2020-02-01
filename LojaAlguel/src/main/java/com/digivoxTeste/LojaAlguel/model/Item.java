package com.digivoxTeste.LojaAlguel.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "item")
public class Item extends AbstractEntity implements Serializable {

    private String descricaoItem;
    private Long valor;
    @OneToOne
    private TipoItem tipoItem;

    public Item(){

    }

    public Item(String descricaoItem, Long valor, int quantidade, TipoItem tipoItem){
        this.descricaoItem = descricaoItem;
        this.valor = valor;
        this.tipoItem = tipoItem;
    }

    public void setDescricaoItem(String descricaoItem) { this.descricaoItem = descricaoItem; }
    public String getDescricaoItem() {
        return descricaoItem;
    }
    public void setValor(Long valor) {
        this.valor = valor;
    }
    public Long getValor() {
        return valor;
    }
    public TipoItem getTipoItem() { return tipoItem; }
    public void setTipoItem(TipoItem tipoItem) { this.tipoItem = tipoItem; }
}
