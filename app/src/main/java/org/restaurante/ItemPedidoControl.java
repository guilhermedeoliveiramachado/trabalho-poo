package org.restaurante;

/*
    Criado por: Kaique Pereira da Cruz

    Grupo: Guilherme de Oliveira Machado, Kaique Pereira da Cruz
*/

//Imports

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



public class ItemPedidoControl {


    private ObservableList<ItemPedido> lista = FXCollections.observableArrayList();


    //Criando as Properties
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private IntegerProperty idPedido = new SimpleIntegerProperty(0);
    private IntegerProperty idPrato = new SimpleIntegerProperty(0);
    private IntegerProperty qtdPedido = new SimpleIntegerProperty(0);
    private DoubleProperty valorTotal = new SimpleDoubleProperty(0.0);
    private StringProperty observacoesClientes = new SimpleStringProperty("");
    private StringProperty status = new SimpleStringProperty("");

    private ItemPedidoDAO dao = new ItemPedidoDAOImpl();


    public ItemPedidoControl() { 
        pesquisarTodos();
    }

    public IntegerProperty idProperty() { 
        return this.id;
    }

    public IntegerProperty idPedidoProperty() { 
        return this.idPedido;
    }

    public IntegerProperty idPratoProperty() { 
        return this.idPrato;
    }

    public IntegerProperty qtdPedidoProperty() { 
        return this.qtdPedido;
    }

    public DoubleProperty valorTotalProperty() { 
        return this.valorTotal;
    }

    public StringProperty observacoesClientesProperty() { 
        return this.observacoesClientes;
    }
    
    public StringProperty statusProperty() { 
        return this.status;
    }


    //Funcionalidades
    public boolean salvar() {

        try {
            
            //Verificador de erros
            if (idPedido.get() == 0 && idPrato.get() == 0 && qtdPedido.get() == 0 && valorTotal.get() == 0.0 && observacoesClientes.get().isBlank() && status.get().isBlank()) { 
                new Alert(
                    Alert.AlertType.ERROR, "Todos os campos devem ser preenchidos com valores válidos (menos o id)",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (idPedido.get() <= 0) { 
                new Alert(
                    Alert.AlertType.ERROR, "O id do pedido deve ser maior que 0",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (idPrato.get() <= 0) { 
                new Alert(
                    Alert.AlertType.ERROR, "O id do prato deve ser maior que 0",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (qtdPedido.get() <= 0) { 
                new Alert(
                    Alert.AlertType.ERROR, "A quantidade do pedido deve ser maior que 0",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (valorTotal.get() <= 0.0) { 
                new Alert(
                    Alert.AlertType.ERROR, "O valor total do pedido deve ser maior que 0",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (observacoesClientes.get().isBlank()) { 
                new Alert(
                    Alert.AlertType.ERROR, "As observações do cliente não podem estar em branco",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (status.get().isBlank()) { 
                new Alert(
                    Alert.AlertType.ERROR, "O status do pedido não pode estar em branco",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            }

            ItemPedido ip = new ItemPedido();
            ip.setIdPedido( idPedido.get() );
            ip.setIdPrato( idPrato.get() );
            ip.setQtdPedido( qtdPedido.get() );
            ip.setValorTotal( valorTotal.get() );
            ip.setObservacoesClientes( observacoesClientes.get() );
            ip.setStatus( status.get() );
            if (id.get() != 0) {
                dao.atualizar( id.get(), ip );
            } else {
                dao.salvar( ip );
            }
            pesquisarTodos();
            limparCampos();
            return true;
        } catch (Exception e) { 
            new Alert(
                Alert.AlertType.ERROR, "Erro não identificado ao salvar o item do pedido: " + e.getMessage(),
                javafx.scene.control.ButtonType.OK
            ).showAndWait();
            return false;
        }
    }

    public void pesquisarTodos() { 
        lista.clear();
        lista.addAll( dao.pesquisarTodos() );
    }

    public void pesquisar() {
        for (ItemPedido ip : lista) {
            if (ip != null && ip.getIdPedido() == idPedido.get()) {
                id.set( ip.getId() );
                idPedido.set( ip.getIdPedido() );
                idPrato.set( ip.getIdPrato() );
                qtdPedido.set( ip.getQtdPedido() );
                valorTotal.set( ip.getValorTotal() );
                observacoesClientes.set( ip.getObservacoesClientes() );
                status.set( ip.getStatus() );
            }   
        }
    }

    public void limparCampos() { 
        id.set( 0 );
        idPedido.set( 0 );
        idPrato.set( 0 );
        qtdPedido.set( 0 );
        valorTotal.set( 0.0 );
        observacoesClientes.set( "" );
        status.set( "" );
    }

    public void excluir( ItemPedido ip ) {
        dao.remover( ip );
        pesquisarTodos();
    }

    public void atualizar( ItemPedido ip ) {
        id.set( ip.getId() );
        idPedido.set( ip.getIdPedido() );
        idPrato.set( ip.getIdPrato() );
        qtdPedido.set( ip.getQtdPedido() );
        valorTotal.set( ip.getValorTotal() );
        observacoesClientes.set( ip.getObservacoesClientes() );
        status.set( ip.getStatus() );
    }


    public ObservableList<ItemPedido> getLista() { 
        return lista;
    }
}
