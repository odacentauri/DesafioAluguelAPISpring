package com.digivoxTeste.LojaAlguel.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva extends AbstractEntity implements Serializable {

    private Item itemReservado;
    private Date dataReserva;

    public Reserva(){

    }

    public Reserva(Item itemReservado, int qtdReservada, Date dataReserva){
        this.itemReservado = itemReservado;
        this.dataReserva = dataReserva;
    }

    public Item getItemReservado() { return itemReservado; }
    public void setItemReservado(Item itemReservado) { this.itemReservado = itemReservado; }
    public Date getDataReserva() {
        return dataReserva;
    }
    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }
}
