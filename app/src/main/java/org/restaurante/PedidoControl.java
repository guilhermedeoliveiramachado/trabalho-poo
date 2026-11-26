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



public class PedidoControl {


    private ObservableList<Pedido> lista = FXCollections.observableArrayList();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");


    //Criando as Properties
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private IntegerProperty idCliente = new SimpleIntegerProperty(0);
    private IntegerProperty mesa = new SimpleIntegerProperty(0);
    private StringProperty horaPedido = new SimpleStringProperty("");
    private DoubleProperty valorTotal = new SimpleDoubleProperty(0.0);
    private StringProperty status = new SimpleStringProperty("");

    private PedidoDAO dao = new PedidoDAOImpl();

    public PedidoControl() { 
        pesquisarTodos();
    }

    public IntegerProperty idProperty() { 
        return this.id;
    }

    public IntegerProperty idClienteProperty() { 
        return this.idCliente;
    }

    public IntegerProperty mesaProperty() { 
        return this.mesa;
    }

    public StringProperty horaPedidoProperty() { 
        return this.horaPedido;
    }

    public DoubleProperty valorTotalProperty() { 
        return this.valorTotal;
    }

    public StringProperty statusProperty() { 
        return this.status;
    }


    //Funcionalidades
    public boolean salvar() {
        try {

            //Verificador de erros
            if (idCliente.get() == 0 && mesa.get() == 0 && horaPedido.get().isBlank() && valorTotal.get() == 0.0 && status.get().isBlank()) { 
                new Alert(
                    Alert.AlertType.ERROR, "Todos os campos devem ser preenchidos com valores válidos (menos o id)",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (idCliente.get() <= 0) { 
                new Alert(
                    Alert.AlertType.ERROR, "O id do cliente deve ser maior que 0",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (mesa.get() <= 0 || mesa.get() > 40) { 
                new Alert(
                    Alert.AlertType.ERROR, "O número da mesa deve ser entre 1 e 40",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (horaPedido.get().isBlank()) { 
                new Alert(
                    Alert.AlertType.ERROR, "A hora do pedido deve ser preenchida no formato HH:mm",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (valorTotal.get() <= 0.0 ) { 
                new Alert(
                    Alert.AlertType.ERROR, "O valor total não pode ser negativo e deve ser maior que 0.00",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            } else if (!status.get().equals("Pendente") && !status.get().equals("Entrege") && !status.get().equals("Pago")) { 
                new Alert(
                    Alert.AlertType.ERROR, "O status deve ser Pendente, Entrege ou Pago",
                    javafx.scene.control.ButtonType.OK
                ).showAndWait();
                return false;
            }

            Pedido p = new Pedido();
            p.setIdCliente( idCliente.get() );
            p.setMesa( mesa.get() );
            p.setHoraPedido( LocalTime.parse(horaPedido.get(), formatador));
            p.setValorTotal( valorTotal.get() );        
            p.setStatus( status.get() );
            if (id.get() != 0) {
                dao.atualizar( id.get(), p );
            } else {
                dao.salvar( p );
            }
            pesquisarTodos();
            limparCampos();
            return true;
        } catch (Exception e) { 
            new Alert(
                Alert.AlertType.ERROR, "Erro não identificado ao salvar o pedido: " + e.getMessage(),
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
        for (Pedido p : lista) {
            if (p != null && p.getIdCliente() == idCliente.get()) {
                id.set( p.getId() );
                idCliente.set( p.getIdCliente() );
                mesa.set( p.getMesa() );
                horaPedido.set( (p.getHoraPedido()).format(formatador) );
                valorTotal.set( p.getValorTotal() );
                status.set( p.getStatus() );
            }   
        }
    }

    public void limparCampos() { 
        id.set( 0 );
        idCliente.set( 0 );
        mesa.set( 0 );
        horaPedido.set( "" );
        valorTotal.set( 0.0 );
        status.set( "" );
    }

    public void excluir( Pedido p ) {
        dao.remover( p );
        pesquisarTodos();
    }

    public void atualizar( Pedido p ) {
        id.set( p.getId() );
        idCliente.set( p.getIdCliente() );
        mesa.set( p.getMesa() );
        horaPedido.set( (p.getHoraPedido()).format(formatador) );
        valorTotal.set( p.getValorTotal() );
        status.set( p.getStatus() );
    }


    public ObservableList<Pedido> getLista() { 
        return lista;
    }
}
