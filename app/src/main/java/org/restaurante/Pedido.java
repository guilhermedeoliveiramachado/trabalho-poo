package org.restaurante;

/*
    Criado por: Kaique Pereira da Cruz

    Grupo: Guilherme de Oliveira Machado, Kaique Pereira da Cruz
*/

//imports utilizados
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    //Declaração de Variaceis
    private int id;
    private int idCliente;
    private int mesa;
    private Time horaPedido;
    private Double valorTotal;
    private String status = "Pendente";


    //Contrutores
    public Pedido(int id, int idCliente, int mesa, LocalTime horaPedido, Double valorTotal, String status) {
        this.id = id;
        this.idCliente = idCliente;
        this.mesa = mesa;
        this.horaPedido = Time.valueOf(horaPedido);
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public Pedido() { 
        this(0, 0, 0, LocalTime.now(), 0.0, "Pendente");
    }


    //Getters e Setters
    public void setId(int valor) { 
        this.id = valor;
    }
    public int getId() { 
        return this.id;
    }

    public void setIdCliente(int valor) { 
        this.idCliente = valor;
    }
    public int getIdCliente() { 
        return this.idCliente;
    }

    public void setMesa(int valor) { 
        this.mesa = valor;
    }
    public int getMesa() { 
        return this.mesa;
    } 

    public void setHoraPedido(LocalTime tempo) { 
        this.horaPedido = Time.valueOf(tempo);
    }
    public LocalTime getHoraPedido() { 
        return this.horaPedido.toLocalTime();
    }

    public void setValorTotal(Double valor) { 
        this.valorTotal = valor;
    }
    public Double getValorTotal() { 
        return this.valorTotal;
    }

    public void setStatus(String valor) { 
        this.status = valor;
    }
    public String getStatus() { 
        return this.status;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        String strHoraPedido = this.horaPedido.toLocalTime().format( formatador );
        return String.format("[%d, %d, %d, %s, %10.2f, %s]\n", this.id, this.idCliente, this.mesa, strHoraPedido, this.valorTotal, this.status);
    } 
}
