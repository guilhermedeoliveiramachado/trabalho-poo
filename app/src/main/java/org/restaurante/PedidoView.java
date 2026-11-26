package org.restaurante;

/*
    Criado por: Kaique Pereira da Cruz

    Grupo: Guilherme de Oliveira Machado, Kaique Pereira da Cruz
*/

//Imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.ChoiceBox;
import javafx.beans.binding.Bindings;

import javafx.scene.control.TableColumn;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;


import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



public class PedidoView {

    private PedidoControl pedidoControl = new PedidoControl();
    private TableView<Pedido> pedidoTable = new TableView<>();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
    
 
    public BorderPane getPagina() {
        //Criacao do corpo do CRUD
        BorderPane panePrincipal = new BorderPane();
        GridPane gridPane = new GridPane();
        Scene scn = new Scene( panePrincipal, 600, 400);

        GridPane gridPaneId = new GridPane();
        GridPane gridPaneInformacoes = new GridPane();


        //Textfield
        TextField txtId = new TextField();
        txtId.setEditable( false );
        txtId.setDisable( true );
        TextField txtIdCliente = new TextField();
        TextField txtMesa = new TextField();
        TextField txtHoraPedido = new TextField();
        txtHoraPedido.setPromptText("HH:mm");
        TextField txtValorTotal = new TextField();
        txtValorTotal.setPromptText("0.00");
        ChoiceBox<String> choiceBoxStatus = new ChoiceBox<>();
        choiceBoxStatus.getItems().addAll("Pendente", "Entrege", "Pago");
        choiceBoxStatus.setValue("Pendente");


        //Colunas
        TableColumn<Pedido, String> colId = new TableColumn<>("Id");
        colId.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( Integer.toString(item.getValue().getId() ) ) );
        
        TableColumn<Pedido, String> colIdCliente = new TableColumn<>("Cliente");
        colIdCliente.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( Integer.toString(item.getValue().getIdCliente() ) ) );

        TableColumn<Pedido, String> colMesa = new TableColumn<>("Mesa");
        colMesa.setCellValueFactory( item ->
            new ReadOnlyStringWrapper( Integer.toString(item.getValue().getMesa() ) ) );
        
        TableColumn<Pedido, String> colHoraPedido = new TableColumn<>("Hora Pedido");
        colHoraPedido.setCellValueFactory( item ->
            new ReadOnlyStringWrapper( (item.getValue().getHoraPedido()).format(formatador) ) );

        TableColumn<Pedido, String> colValorTotal = new TableColumn<>("Valor Total");
        colValorTotal.setCellValueFactory( item ->
            new ReadOnlyStringWrapper( Double.toString(item.getValue().getValorTotal() ) ) );

        TableColumn<Pedido, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory( item ->
            new ReadOnlyStringWrapper( item.getValue().getStatus() ) );
        
        TableColumn<Pedido, Void> colAcoes = new TableColumn<>("Ações");


        Callback< TableColumn<Pedido, Void>, TableCell<Pedido, Void> > callBack = new Callback<>() {
            @Override
            public TableCell<Pedido, Void> call( TableColumn<Pedido, Void> coluna ) { 
                TableCell<Pedido, Void> celula = new TableCell<>() { 
                    private final Button btnExcluir = new Button( "Excluir");
                    private final Button btnAtualizar = new Button( "Atualizar");
                    private final HBox painelBotoes = new HBox();

                    {
                        btnExcluir.setOnAction( e -> { 
                            pedidoControl.excluir( getTableRow().getItem() );
                        });
                        btnAtualizar.setOnAction( e -> { 
                            pedidoControl.atualizar( getTableRow().getItem() );
                        });
                        painelBotoes.getChildren().addAll( btnAtualizar, btnExcluir );
                    }

                    @Override
                    protected void updateItem( Void item, boolean empty ) { 
                        super.updateItem( item, empty );
                        if (!empty) { 
                            setGraphic( painelBotoes  );
                        } else { 
                            setGraphic( null );
                        }
                    }
                };
                return celula;
            }
        };


        colAcoes.setCellFactory( callBack );

        //colocando colunas na tabela (foi criada desse jeito por segurança)
        pedidoTable.getColumns().add(colId);
        pedidoTable.getColumns().add(colIdCliente);
        pedidoTable.getColumns().add(colMesa);
        pedidoTable.getColumns().add(colHoraPedido);
        pedidoTable.getColumns().add(colValorTotal);
        pedidoTable.getColumns().add(colStatus);
        pedidoTable.getColumns().add(colAcoes);

        pedidoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        pedidoTable.setItems( pedidoControl.getLista() );


        pedidoTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, antigoPedido, novoPedido) -> { 
                if (novoPedido != null) { 
                    txtId.setText( String.valueOf(novoPedido.getId()) );
                    txtIdCliente.setText( String.valueOf(novoPedido.getIdCliente()) );
                    txtMesa.setText( String.valueOf(novoPedido.getMesa()) ); 
                    txtHoraPedido.setText( (novoPedido.getHoraPedido()).format(formatador) );
                    txtValorTotal.setText( String.valueOf(novoPedido.getValorTotal()));
                    choiceBoxStatus.setValue( novoPedido.getStatus() );
                }
            }
        );


        Bindings.bindBidirectional( txtId.textProperty(), pedidoControl.idProperty(), new NumberStringConverter()  );
        Bindings.bindBidirectional( txtIdCliente.textProperty(), pedidoControl.idClienteProperty(), new NumberStringConverter() );
        Bindings.bindBidirectional( txtMesa.textProperty(), pedidoControl.mesaProperty(), new NumberStringConverter());
        Bindings.bindBidirectional( txtHoraPedido.textProperty(), pedidoControl.horaPedidoProperty());
        Bindings.bindBidirectional( txtValorTotal.textProperty(), pedidoControl.valorTotalProperty(), new NumberStringConverter() );
        Bindings.bindBidirectional( choiceBoxStatus.valueProperty(), pedidoControl.statusProperty() );



        //Criando os Labels

        //gridPaneId
        gridPaneId.add(new Label("id:"), 0, 0);
        gridPaneId.add(new Label("Cliente:"), 0, 1);
        gridPaneId.add(txtId, 1, 0);
        gridPaneId.add(txtIdCliente, 1, 1);

        //gridPaneInformacoes
        gridPaneInformacoes.add(new Label("Mesa:"), 0, 0);
        gridPaneInformacoes.add(new Label("Hora:"), 0, 1);
        gridPaneInformacoes.add(new Label("Valor Total:"), 0, 2);
        gridPaneInformacoes.add(new Label("Status:"), 0, 3);
        gridPaneInformacoes.add(txtMesa, 1, 0);
        gridPaneInformacoes.add(txtHoraPedido, 1, 1);
        gridPaneInformacoes.add(txtValorTotal, 1, 2);
        gridPaneInformacoes.add(choiceBoxStatus, 1, 3);

        //gridPane
        gridPane.add( gridPaneId, 0, 0 );
        gridPane.add( gridPaneInformacoes, 1, 0 );

        //Criando os Botoes
        Button btnSalvar = new Button( "Salvar" );
        Button btnPesquisar = new Button( "Pesquisar" );
        gridPane.add( btnSalvar, 0, 7 );
        gridPane.add( btnPesquisar, 1, 7 );

        btnSalvar.setOnAction( e ->  pedidoControl.salvar());

        btnPesquisar.setOnAction( e -> pedidoControl.pesquisar() );
        

        //Arrumando o GridPaneId
        gridPaneId.setHgap(10);
        gridPaneId.setVgap(10);
        gridPaneId.setPadding(new javafx.geometry.Insets(30, 10, 30, 10));
        gridPaneId.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #1bddff; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        //Arrumando o GridPaneInformacoes
        gridPaneInformacoes.setHgap(10);
        gridPaneInformacoes.setVgap(10);
        gridPaneInformacoes.setPadding(new javafx.geometry.Insets(30, 10, 30, 10));
        gridPaneInformacoes.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #1bddff; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        //Arrumando o GridPane
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");


        //Constuindo a visualização
        panePrincipal.setTop( gridPane );
        panePrincipal.setCenter( pedidoTable );

        return panePrincipal;
    }
}
