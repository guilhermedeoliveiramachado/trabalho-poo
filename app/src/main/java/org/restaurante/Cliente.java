package org.restaurante;

import java.time.LocalDate;

public class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private boolean clienteVip;
    private LocalDate dataCadastro;
    private LocalDate dataUltVisita;

    public Cliente() {
        this.id = 0;
        this.nome = "";
        this.telefone = "";
        this.email = "";
        this.clienteVip = false;
        this.dataCadastro = LocalDate.now();
        this.dataUltVisita = LocalDate.now();
    }

    public void setId(int id) {
        this.id = id;
    } 

    public int getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    } 

    public String getNome() {
        return this.nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    } 

    public String getTelefone() {
        return this.telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    } 

    public String getEmail() {
        return this.email;
    }

    public void setClienteVip(boolean clienteVip) {
        this.clienteVip = clienteVip;
    } 

    public boolean getClienteVip() {
        return this.clienteVip;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    } 

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataUltVisita(LocalDate dataUltVisita) {
        this.dataUltVisita = dataUltVisita;
    } 

    public LocalDate getDataUltVisita() {
        return this.dataUltVisita;
    }
}