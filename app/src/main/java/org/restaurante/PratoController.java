package org.restaurante;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PratoController {
    private ObservableList<Prato> listaPratos = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private StringProperty categoria = new SimpleStringProperty("");
    private DoubleProperty preco = new SimpleDoubleProperty(0);
    private IntegerProperty qtdPessoas = new SimpleIntegerProperty(0);
    private BooleanProperty disponivel = new SimpleBooleanProperty(false);
    private IntegerProperty tempoPreparo = new SimpleIntegerProperty(0);

    private PratoDAO dao = new PratoDAOImpl();

    public PratoController() {
        this.pesquisarTodos();
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty nomePratoProperty() {
        return this.nome;
    }

    public StringProperty descricaoProperty() {
        return this.descricao;
    }

    public StringProperty categoriaProperty() {
        return this.categoria;
    }

    public BooleanProperty disponivelProperty() {
        return this.disponivel;
    }

    public IntegerProperty tempoPreparoProperty() {
        return this.tempoPreparo;
    }

    public DoubleProperty precoProperty() {
        return this.preco;
    }

    public IntegerProperty qtdPessoasProperty() {
        return this.qtdPessoas;
    }

    public boolean salvar() {

        if (id.get() == 0) {
            Prato novoPrato = new Prato();
            novoPrato.setNomePrato(nome.get());
            novoPrato.setDescricao(descricao.get());
            novoPrato.setCategoria(categoria.get());
            novoPrato.setPreco(preco.get());
            novoPrato.setQtdPessoasServe(qtdPessoas.get());
            novoPrato.setTempoPreparo(tempoPreparo.get());
            novoPrato.setDisponivel(disponivel.get());

            if (dao.salvar(novoPrato)) {
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

    public boolean atualizar() {
        Prato novoPrato = new Prato();
        novoPrato.setNomePrato(nome.get());
        novoPrato.setDescricao(descricao.get());
        novoPrato.setCategoria(categoria.get());
        novoPrato.setPreco(preco.get());
        novoPrato.setQtdPessoasServe(qtdPessoas.get());
        novoPrato.setTempoPreparo(tempoPreparo.get());
        novoPrato.setDisponivel(disponivel.get());

        if(dao.atualizar(novoPrato, id.get())) {
            this.limparCampos();
            this.pesquisarTodos();
            return true;
        }

        return false;
    }

    public void atualizar(Prato p) {
        id.set(p.getId());
        nome.set(p.getNomePrato());
        descricao.set(p.getDescricao());
        categoria.set(p.getCategoria());
        preco.set(p.getPreco());
        qtdPessoas.set(p.getQtdPessoasServe());
        disponivel.set(p.getDisponivel());
    }

    public boolean remover() {
        if (dao.remover(id.get())) {
            this.limparCampos();
            this.pesquisarTodos();
            return true;
        }

        return false;
    }

    public boolean remover(Prato p) {
         if (dao.remover(p.getId())) {
            this.limparCampos();
            this.pesquisarTodos();
            return true;
        }

        return false;
    }

    public boolean pesquisar() {
        Prato p = dao.pesquisar(nome.get());

        if (p != null) {
            id.set(p.getId());
            nome.set(p.getNomePrato());
            descricao.set(p.getDescricao());
            categoria.set(p.getCategoria());
            preco.set(p.getPreco());
            qtdPessoas.set(p.getQtdPessoasServe());
            disponivel.set(p.getDisponivel());

            return true;
        }

        return false;
    }

    public void pesquisarTodos() {
        listaPratos.clear();
        listaPratos.addAll(dao.pesquisarTodos());
    }

    public void limparCampos() {
        id.set(0);
        nome.set("");
        descricao.set("");
        categoria.set("");
        preco.set(0.0);
        qtdPessoas.set(0);
        disponivel.set(false);
    }

    public ObservableList<Prato> getPratos() {
        return this.listaPratos;
    }

}