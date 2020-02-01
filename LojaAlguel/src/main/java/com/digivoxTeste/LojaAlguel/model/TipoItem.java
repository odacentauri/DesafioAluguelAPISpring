package com.digivoxTeste.LojaAlguel.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tipoItem")
public class TipoItem extends AbstractEntity implements Serializable {

    private String nome;
    private String descricaoTipoItem;

    public TipoItem(){

    }

    public TipoItem(String descricaoTipoItem){
        this.descricaoTipoItem = descricaoTipoItem;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricaoTipoItem() { return descricaoTipoItem; }
    public void setDescricaoTipoItem(String descricaoTipoItem) { this.descricaoTipoItem = descricaoTipoItem; }
}
