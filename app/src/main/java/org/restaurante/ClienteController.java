package org.restaurante;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.DateTimeException;

public class ClienteController {
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private BooleanProperty clienteVip = new SimpleBooleanProperty(false);
    private ObjectProperty<LocalDate> dataCadastro = new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<LocalDate> dataUltimaVisita = new SimpleObjectProperty<>(LocalDate.now());

    private ClienteDAO dao = new ClienteDAOImpl();

    public ClienteController() {
        this.pesquisarTodos();
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }

    public StringProperty telefoneProperty() {
        return this.telefone;
    }

    public StringProperty emailProperty() {
        return this.email;
    }

    public BooleanProperty clienteVipProperty() {
        return this.clienteVip;
    }

    public ObjectProperty<LocalDate> dataCadastroProperty() {
        return this.dataCadastro;
    }

    public ObjectProperty<LocalDate> dataUltimaVisitaProperty() {
        return this.dataUltimaVisita;
    }

    
    public boolean salvar() {
        if (id.get() == 0) {
            Cliente novoCliente = new Cliente();
            novoCliente.setNome(nome.get());
            novoCliente.setTelefone(telefone.get());
            novoCliente.setEmail(email.get());
            novoCliente.setClienteVip(clienteVip.get());

            novoCliente.setDataCadastro(dataCadastro.get());
            novoCliente.setDataUltVisita(dataUltimaVisita.get());
            if (dao.salvar(novoCliente)) {
                this.limparCampos();
                this.pesquisarTodos();
                return true;
            }
        } else {
            if (this.atualizar()) {
                return true;
            }
        }
        

        return false;
        
    }

    public void atualizar(Cliente c) {
        id.set(c.getId());
        nome.set(c.getNome());
        telefone.set(c.getTelefone());
        email.set(c.getEmail());
        dataCadastro.set(c.getDataCadastro());
        dataUltimaVisita.set(c.getDataUltVisita());
        clienteVip.set(c.getClienteVip());
    }

    public boolean atualizar() {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome.get());
        novoCliente.setTelefone(telefone.get());
        novoCliente.setEmail(email.get());
        novoCliente.setClienteVip(clienteVip.get());
        novoCliente.setDataCadastro(dataCadastro.get());
        novoCliente.setDataUltVisita(dataUltimaVisita.get());

        if(dao.atualizar(novoCliente, id.get())) {
            this.limparCampos();
            this.pesquisarTodos();
            return true;
        }

        return false;
    }

    public boolean remover(Cliente c) {
        if (dao.excluir(c.getId())) {
            this.limparCampos();
            this.pesquisarTodos();
            return true;
        }

        return false;
    }

    public boolean pesquisar() {
        Cliente c = dao.pesquisar(nome.get());

        if (c != null) {
            id.set(c.getId());
            nome.set(c.getNome());
            telefone.set(c.getTelefone());
            email.set(c.getEmail());
            clienteVip.set(c.getClienteVip());
            dataCadastro.set(c.getDataCadastro());
            dataUltimaVisita.set(c.getDataUltVisita());

            return true;
        }

        return false;
    }

    public void pesquisarTodos() {
        listaClientes.clear();
        listaClientes.addAll(dao.pesquisarTodos());
    }

    public void limparCampos() {
        id.set(0);
        nome.set("");
        telefone.set("");
        email.set("");
        clienteVip.set(false);
        dataCadastro.set(LocalDate.now());
        dataUltimaVisita.set(LocalDate.now());
    }

    public ObservableList getClientes() {
        return this.listaClientes;
    }

}