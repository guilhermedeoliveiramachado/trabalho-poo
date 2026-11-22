package restaurante;

import java.util.Date;

public class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private boolean clienteVip;
    private Date dataCadastro;
    private Date dataUltVisita;

    public Cliente() {
        this.id = 0;
        this.nome = "";
        this.telefone = "";
        this.email = "";
        this.clienteVip = false;
        this.dataCadastro = new Date();
        this.dataUltVisita = new Date();
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

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    } 

    public Date getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataUltVisita(Date dataUltVisita) {
        this.dataUltVisita = dataUltVisita;
    } 

    public Date getDataUltVisita() {
        return this.dataUltVisita;
    }
}