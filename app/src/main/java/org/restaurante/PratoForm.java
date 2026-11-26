package org.restaurante;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.DatePicker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.beans.binding.Bindings;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;

public class PratoForm {
    private PratoController control = new PratoController();
    private TableView<Prato> table = new TableView<>();
    
    public BorderPane getPagina() {

        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();
        Scene scn = new Scene(panPrincipal, 1200, 400);

        Label lbId = new Label("ID:");
        Label lbNomePrato = new Label("Nome do Prato:");
        Label lbDescricao = new Label("Descricao:");
        Label lbPreco = new Label("Preço:");
        Label lbQtdPessoas = new Label("Quantidade de Pessoas que Serve:");
        Label lbCategoria = new Label("Categoria:");
        Label lbDisponivel = new Label("Disponibilidade:");
        Label lbTempoPreparo = new Label("Tempo de preparo (min):");

        TextField txtId = new TextField();
        txtId.setEditable(false);
        TextField txtNomePrato = new TextField();
        TextArea txtDescricao = new TextArea();
        TextField txtPreco = new TextField();
        TextField txtQtdPessoas = new TextField();
        ComboBox<String> cbCategoria = new ComboBox<>();
        cbCategoria.getItems().addAll("Prato Principal", "Acompanhamento", "Sobremesa");
        CheckBox ckDisponivel = new CheckBox();
        TextField txtTempoPreparo = new TextField();

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");
        Button btnLimparCampos = new Button("Limpar Campos");

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNomePrato.textProperty(), control.nomePratoProperty());
        Bindings.bindBidirectional(txtDescricao.textProperty(), control.descricaoProperty());
        Bindings.bindBidirectional(txtPreco.textProperty(), control.precoProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtQtdPessoas.textProperty(), control.qtdPessoasProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(ckDisponivel.selectedProperty(), control.disponivelProperty());
        Bindings.bindBidirectional(cbCategoria.valueProperty(), control.categoriaProperty());
        Bindings.bindBidirectional(txtTempoPreparo.textProperty(), control.tempoPreparoProperty(), new NumberStringConverter());


        btnSalvar.setOnAction(e -> {
            
            if (control.salvar()) {
                new Alert(
                    Alert.AlertType.INFORMATION, "Prato salvo com sucesso!",
                    ButtonType.OK
                ).show();
            } else {
                new Alert(
                    Alert.AlertType.ERROR, "Erro ao tentar salvar o prato!",
                    ButtonType.OK
                ).show();
            }
    
            
        });

        btnPesquisar.setOnAction(e -> {
            
            if (!control.pesquisar()) {
                new Alert(
                    Alert.AlertType.ERROR, "Não foi possível encontrar o prato!",
                    ButtonType.OK
                ).show();
            }
            
            
        });

        btnLimparCampos.setOnAction(e -> 
            control.limparCampos()
        );

        TableColumn<Prato, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(Integer.toString(item.getValue().getId()))
        );

        TableColumn<Prato, String> colNome = new TableColumn<>("NOME DO PRATO");
        colNome.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(item.getValue().getNomePrato())
        );

        TableColumn<Prato, String> colDescricao = new TableColumn<>("DESCRIÇÃO");
        colDescricao.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(item.getValue().getDescricao())
        );

        TableColumn<Prato, String> colPreco = new TableColumn<>("PREÇO (R$)");
        colPreco.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(Double.toString(item.getValue().getPreco()).replace(".", ","))
        );

        TableColumn<Prato, String> colCategoria = new TableColumn<>("CATEGORIA");
        colCategoria.setCellValueFactory(item ->
            new ReadOnlyStringWrapper(item.getValue().getCategoria())
        );

        TableColumn<Prato, String> colQtdPessoas = new TableColumn<>("QTDE PESSOAS QUE SERVE");
        colQtdPessoas.setCellValueFactory(item ->
            new ReadOnlyStringWrapper(Integer.toString(item.getValue().getQtdPessoasServe()))
        );

        TableColumn<Prato, Boolean> colDisponivel = new TableColumn<>("DISPÓNIVEL");
        colDisponivel.setCellValueFactory( item -> 
            new ReadOnlyBooleanWrapper(item.getValue().getDisponivel())
        );

        TableColumn<Prato, String> colTempoPreparo = new TableColumn<>("TEMPO DE PRAPARO (MIN)");
        colTempoPreparo.setCellValueFactory(item -> 
            new ReadOnlyStringWrapper(Integer.toString(item.getValue().getTempoPreparo()))
        );

        TableColumn<Prato, Void> colAcoes = new TableColumn<>();

        Callback<TableColumn<Prato, Void>, TableCell<Prato, Void>> callBack = new Callback<>() {
            @Override
            public TableCell<Prato, Void> call(TableColumn<Prato, Void> coluna) {
                TableCell<Prato, Void> celula = new TableCell<>() {
                    private final Button btnAtualizar = new Button("Atualizar");
                    private final Button btnExcluir = new Button("Excluir");
                    private final HBox painelBotoes = new HBox();

                    {
                        btnExcluir.setOnAction(e -> {
                            if (control.remover(getTableRow().getItem())) {
                                new Alert(
                                    Alert.AlertType.INFORMATION, "Prato excluído com sucesso!",
                                    ButtonType.OK
                                );
                            } else {
                                new Alert(
                                    Alert.AlertType.ERROR, "Erro ao tentar excluir o prato!",
                                    ButtonType.OK
                                );
                            }
                        });

                        btnAtualizar.setOnAction(e -> 
                            control.atualizar(getTableRow().getItem())
                        );

                        painelBotoes.getChildren().addAll(btnExcluir, btnAtualizar);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            setGraphic(painelBotoes);
                        } else {
                            setGraphic(null);
                        }
                    }
                    
                };
                return celula;
            }
            
        };

        colAcoes.setCellFactory(callBack);

        table.getColumns().addAll(colId, colNome, colDescricao, colPreco, colCategoria, colQtdPessoas, colDisponivel, colTempoPreparo, colAcoes);

        table.setItems(control.getPratos());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                txtId.setText(Integer.toString(novo.getId()));
                txtNomePrato.setText(novo.getNomePrato());
                txtDescricao.setText(novo.getDescricao());
                txtPreco.setText(Double.toString(novo.getPreco()).replace(".",","));
                cbCategoria.setValue(novo.getCategoria());
                ckDisponivel.setSelected(novo.getDisponivel());
                txtQtdPessoas.setText(Integer.toString(novo.getQtdPessoasServe()));
                txtTempoPreparo.setText(Integer.toString(novo.getTempoPreparo()));
            }
        });

        p.add(lbId, 0, 0);
        p.add(lbNomePrato, 0, 1);
        p.add(lbDescricao, 0, 2);
        p.add(lbPreco, 0, 3);
        p.add(lbCategoria, 0, 4);
        p.add(lbQtdPessoas, 0, 5);
        p.add(lbDisponivel, 0, 6);
        p.add(lbTempoPreparo, 0,7);

        p.add(txtId, 1, 0);
        p.add(txtNomePrato, 1, 1);
        p.add(txtDescricao, 1, 2);
        p.add(txtPreco, 1, 3);
        p.add(cbCategoria, 1, 4);
        p.add(txtQtdPessoas, 1, 5);
        p.add(ckDisponivel, 1, 6);
        p.add(txtTempoPreparo, 1, 7);

        p.add(btnSalvar, 0, 8);
        p.add(btnPesquisar, 1, 8);
        p.add(btnLimparCampos, 2, 8);

        p.setHgap(2.0);
        p.setVgap(4.0);

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        
        return panPrincipal;
    }
}
