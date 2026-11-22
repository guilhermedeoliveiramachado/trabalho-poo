package restaurante;

import java.text.ParseException;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClienteController {
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");
    private StringProperty clienteVip = new SimpleStringProperty("");
    private StringProperty dataCadastro = new SimpleStringProperty("");
    private StringProperty dataUltimaVisita = new SimpleStringProperty("");

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

    public StringProperty clienteVipProperty() {
        return this.clienteVip;
    }

    public StringProperty dataCadastroProperty() {
        return this.dataCadastro;
    }

    public StringProperty dataUltimaVisitaProperty() {
        return this.dataUltimaVisita;
    }

    public boolean salvar() throws ParseException{
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome.get());
        novoCliente.setTelefone(telefone.get());
        novoCliente.setEmail(email.get());
        if (clienteVip.get().equalsIgnoreCase("Sim")) {
            novoCliente.setClienteVip(true);
        } else {
            novoCliente.setClienteVip(false);
        }

        novoCliente.setDataCadastro(formatador.parse(dataCadastro.get()));

        if (dao.salvar(novoCliente)) {
            this.limparCampos();
            this.pesquisarTodos();
            return true;
        }

        return false;
        
    }

    public boolean atualizar() {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome.get());
        novoCliente.setTelefone(telefone.get());
        novoCliente.setEmail(email.get());
        if (clienteVip.get().equalsIgnoreCase("Sim")) {
            novoCliente.setClienteVip(true);
        } else {
            novoCliente.setClienteVip(false);
        }

        if(dao.atualizar(novoCliente, id.get())) {
            this.limparCampos();
            this.pesquisarTodos();
            return true;
        }

        return false;
    }

    public boolean remover() {
        if (dao.excluir(id.get())) {
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

            if (c.getClienteVip()) {
                clienteVip.set("Sim");
            } else {
                clienteVip.set("Não");
            }

            dataCadastro.set(formatador.format(c.getDataCadastro()));
            dataUltimaVisita.set(formatador.format(c.getDataUltVisita()));

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
        clienteVip.set("Sim");
        dataCadastro.set("");
        dataUltimaVisita.set("");
    }

}