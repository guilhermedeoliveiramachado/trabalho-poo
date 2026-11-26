package org.restaurante;

/*
    Criado por: Kaique Pereira da Cruz

    Grupo: Guilherme de Oliveira Machado, Kaique Pereira da Cruz
*/

public class ItemPedido {
    
    //Declaração de Variaveis
    private int id;
    private int idPedido;
    private int idPrato;
    private int qtdPedido;
    private Double valorTotal;
    private String observacoesClientes;
    private String status = "Pendente";


    //Contrutores
    public ItemPedido() {
        this(0, 0, 0, 0, 0.0, "????", "Pendente");
    }

    public ItemPedido(int id, int idPedido, int idPrato, int qtdPedido, Double valorTotal, String observacoesClientes, String status) {
        this.id = id;
        this.idPedido = idPedido;
        this.idPrato = idPrato;
        this.qtdPedido = qtdPedido;
        this.valorTotal = valorTotal;
        this.observacoesClientes = observacoesClientes;
        this.status = status;
    }


    //Getters e Setters
    public void setId(int valor) { 
        this.id = valor;
    }
    public int getId() { 
        return this.id;
    }

    public void setIdPedido(int valor) { 
        this.idPedido = valor;
    }
    public int getIdPedido() { 
        return this.idPedido;
    }

    public void setIdPrato(int valor) { 
        this.idPrato = valor;
    }
    public int getIdPrato() { 
        return this.idPrato;
    }

    public void setQtdPedido(int valor) { 
        this.qtdPedido = valor;
    }
    public int getQtdPedido() { 
        return this.qtdPedido;
    }

    public void setValorTotal(Double valor) { 
        this.valorTotal = valor;
    }
    public Double getValorTotal() { 
        return this.valorTotal;
    }

    public void setObservacoesClientes(String valor) { 
        this.observacoesClientes = valor;
    }
    public String getObservacoesClientes() { 
        return this.observacoesClientes;
    }

    public void setStatus(String valor) { 
        this.status = valor;
    }
    public String getStatus() { 
        return this.status;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d, %d, %d, %10.2f, %s]\n", this.id, this.idPedido, this.idPrato, this.qtdPedido, this.valorTotal, this.status);
    } 
}
