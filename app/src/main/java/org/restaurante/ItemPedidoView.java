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



public class ItemPedidoView {
    
    private ItemPedidoControl itemPedidoControl = new ItemPedidoControl();
    private TableView<ItemPedido> itemPedidoTable = new TableView<>();

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
        TextField txtIdPedido = new TextField();
        TextField txtIdPrato = new TextField();
        TextField txtQtdPedido = new TextField();
        TextField txtValorTotal = new TextField();
        TextField txtObservacoesClientes = new TextField();
        txtObservacoesClientes.setPromptText("Eu como cliente me senti ...");
        ChoiceBox<String> choiceBoxStatus = new ChoiceBox<>();
        choiceBoxStatus.getItems().addAll("Pendente", "Entrege", "Pago");
        choiceBoxStatus.setValue("Pendente");


        //Colunas
        TableColumn<ItemPedido, String> colId = new TableColumn<>("Id:");
        colId.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( Integer.toString(item.getValue().getId() ) ) );

        TableColumn<ItemPedido, String> colIdPedido = new TableColumn<>("Id Pedido:");
        colIdPedido.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( Integer.toString(item.getValue().getIdPedido() ) ) );

        TableColumn<ItemPedido, String> colIdPrato = new TableColumn<>("Id Prato:");
        colIdPrato.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( Integer.toString(item.getValue().getIdPrato() ) ) );
            
        TableColumn<ItemPedido, String> colQtdPedido = new TableColumn<>("Quantidade:");
        colQtdPedido.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( Integer.toString(item.getValue().getQtdPedido() ) ) );

        TableColumn<ItemPedido, String> colValorTotal = new TableColumn<>("Valor Total:");
        colValorTotal.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( Double.toString(item.getValue().getValorTotal() ) ) );
            
        TableColumn<ItemPedido, String> colObservacoesClientes = new TableColumn<>("Observações:");
        colObservacoesClientes.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( item.getValue().getObservacoesClientes() ) );
            
        TableColumn<ItemPedido, String> colStatus = new TableColumn<>("Status:");
        colStatus.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper( item.getValue().getStatus() ) );
        
        
        TableColumn<ItemPedido, Void> colAcoes = new TableColumn<>("Ações");


        Callback< TableColumn<ItemPedido, Void>, TableCell<ItemPedido, Void> > callBack = new Callback<>() {
            @Override
            public TableCell<ItemPedido, Void> call( TableColumn<ItemPedido, Void> coluna ) { 
                TableCell<ItemPedido, Void> celula = new TableCell<>() { 
                    private final Button btnExcluir = new Button( "Excluir");
                    private final Button btnAtualizar = new Button( "Atualizar");
                    private final HBox painelBotoes = new HBox();

                    {
                        btnExcluir.setOnAction( e -> { 
                            itemPedidoControl.excluir( getTableRow().getItem() );
                        });
                        btnAtualizar.setOnAction( e -> { 
                            itemPedidoControl.atualizar( getTableRow().getItem() );
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
        itemPedidoTable.getColumns().add( colId );
        itemPedidoTable.getColumns().add( colIdPedido );
        itemPedidoTable.getColumns().add( colIdPrato );
        itemPedidoTable.getColumns().add( colQtdPedido );
        itemPedidoTable.getColumns().add( colValorTotal );
        itemPedidoTable.getColumns().add( colObservacoesClientes );
        itemPedidoTable.getColumns().add( colStatus );
        itemPedidoTable.getColumns().add( colAcoes );



        itemPedidoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        itemPedidoTable.setItems( itemPedidoControl.getLista() );


        itemPedidoTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, antigoItemPedido, novoItemPedido) -> { 
                if (novoItemPedido != null) { 
                    txtId.setText( String.valueOf(novoItemPedido.getId()) );
                    txtIdPedido.setText( String.valueOf(novoItemPedido.getIdPedido()) );
                    txtIdPrato.setText( String.valueOf(novoItemPedido.getIdPrato()) );
                    txtQtdPedido.setText( String.valueOf(novoItemPedido.getQtdPedido()) );
                    txtValorTotal.setText( String.valueOf(novoItemPedido.getValorTotal()));
                    txtObservacoesClientes.setText( novoItemPedido.getObservacoesClientes() );
                    choiceBoxStatus.setValue( novoItemPedido.getStatus() );
                }
            }
        );


        Bindings.bindBidirectional( txtId.textProperty(), itemPedidoControl.idProperty(), new NumberStringConverter()  );
        Bindings.bindBidirectional( txtIdPedido.textProperty(), itemPedidoControl.idPedidoProperty(), new NumberStringConverter() );
        Bindings.bindBidirectional( txtIdPrato.textProperty(), itemPedidoControl.idPratoProperty(), new NumberStringConverter());
        Bindings.bindBidirectional( txtQtdPedido.textProperty(), itemPedidoControl.qtdPedidoProperty(), new NumberStringConverter() );
        Bindings.bindBidirectional( txtValorTotal.textProperty(), itemPedidoControl.valorTotalProperty(), new NumberStringConverter() );
        Bindings.bindBidirectional( txtObservacoesClientes.textProperty(), itemPedidoControl.observacoesClientesProperty() );
        Bindings.bindBidirectional( choiceBoxStatus.valueProperty(), itemPedidoControl.statusProperty() );


        //Criando os Labels

        //gridPaneId
        gridPaneId.add(new Label("id:"), 0, 0);
        gridPaneId.add(new Label("Pedido:"), 0, 1);
        gridPaneId.add(new Label("Prato:"), 0, 2);
        gridPaneId.add(txtId, 1, 0);
        gridPaneId.add(txtIdPedido, 1, 1);
        gridPaneId.add(txtIdPrato, 1, 2);

        //gridPaneInformacoes
        gridPaneInformacoes.add(new Label("Quantidade:"), 0, 0);
        gridPaneInformacoes.add(new Label("Valor Total:"), 0, 1);
        gridPaneInformacoes.add(new Label("Observações do Cliente:"), 0, 2);
        gridPaneInformacoes.add(new Label("Status:"), 0, 3);
        gridPaneInformacoes.add(txtQtdPedido, 1, 0);
        gridPaneInformacoes.add(txtValorTotal, 1, 1);
        gridPaneInformacoes.add(txtObservacoesClientes, 1, 2);
        gridPaneInformacoes.add(choiceBoxStatus, 1, 3);

        //gridPane
        gridPane.add( gridPaneId, 0, 0 );
        gridPane.add( gridPaneInformacoes, 1, 0 );

        
        //Criando os Botoes
        Button btnSalvar = new Button( "Salvar" );
        Button btnPesquisar = new Button( "Pesquisar" );
        gridPane.add( btnSalvar, 0, 3 );
        gridPane.add( btnPesquisar, 1, 3 );

        btnSalvar.setOnAction( e -> itemPedidoControl.salvar() );

        btnPesquisar.setOnAction( e -> itemPedidoControl.pesquisar() );

        //Arrumando o GridPaneId
        gridPaneId.setHgap(10);
        gridPaneId.setVgap(10);
        gridPaneId.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        gridPaneId.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ffd519; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        //Arrumando o GridPaneInformacoes
        gridPaneInformacoes.setHgap(10);
        gridPaneInformacoes.setVgap(10);
        gridPaneInformacoes.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        gridPaneInformacoes.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ffd519; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        //Arrumando o GridPane
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");


        //Constuindo a visualização
        panePrincipal.setTop( gridPane );
        panePrincipal.setCenter( itemPedidoTable );

        return panePrincipal;
    }
}
