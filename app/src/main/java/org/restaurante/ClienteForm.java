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

import javafx.beans.binding.Binding;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.beans.binding.Bindings;
import javafx.util.converter.NumberStringConverter;

public class ClienteForm {
    private ClienteController control = new ClienteController();
    private TableView<Cliente> table = new TableView<>();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BorderPane getPagina() {
        BorderPane panPrincipal = new BorderPane();
        GridPane p = new GridPane();
        Scene scn = new Scene(panPrincipal, 1200, 400);

        Label lbId = new Label("ID:");
        Label lbNome = new Label("Nome:");
        Label lbTelefone = new Label("Telefone:");
        Label lbEmail = new Label("Email:");
        Label lbVip = new Label("Cliente VIP:");
        Label lbDtCad = new Label("Data de Cadastro:");
        Label lbDtVisita = new Label("Data da Última Visita:");

        TextField txtId = new TextField();
        txtId.setEditable(false);
        TextField txtNome = new TextField();
        TextField txtTelefone = new TextField();
        TextField txtEmail = new TextField();
        CheckBox ckClienteVip = new CheckBox();
        DatePicker dtCad = new DatePicker();
        DatePicker dtVisita = new DatePicker();

        Button btnSalvar = new Button("Salvar");
        Button btnPesquisar = new Button("Pesquisar");
        Button btnLimparCampos = new Button("Limpar Campos");

        Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefoneProperty());
        Bindings.bindBidirectional(txtEmail.textProperty(), control.emailProperty());
        Bindings.bindBidirectional(ckClienteVip.selectedProperty(), control.clienteVipProperty());
        Bindings.bindBidirectional(dtCad.valueProperty(), control.dataCadastroProperty());
        Bindings.bindBidirectional(dtVisita.valueProperty(), control.dataUltimaVisitaProperty());

        
        btnSalvar.setOnAction(e -> {
            
            if (control.salvar()) {
                new Alert(
                    Alert.AlertType.INFORMATION, "Cliente salvo com sucesso!",
                    ButtonType.OK
                ).show();
            } else {
                new Alert(
                    Alert.AlertType.ERROR, "Erro ao tentar salvar o cliente!",
                    ButtonType.OK
                ).show();
            }
            
        });

        btnPesquisar.setOnAction(e -> {
            
            if (!control.pesquisar()) {
                new Alert(
                    Alert.AlertType.ERROR, "Não foi possível encontrar o cliente!",
                    ButtonType.OK
                ).show();
            }
            
            
        });

        btnLimparCampos.setOnAction(e -> 
            control.limparCampos()
        );

        TableColumn<Cliente, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(Integer.toString(item.getValue().getId()))
        );

        TableColumn<Cliente, String> colNome = new TableColumn<>("NOME");
        colNome.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(item.getValue().getNome())
        );

        TableColumn<Cliente, String> colTelefone = new TableColumn<>("TELEFONE");
        colTelefone.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(item.getValue().getTelefone())
        );

        TableColumn<Cliente, String> colEmail = new TableColumn<>("EMAIL");
        colEmail.setCellValueFactory( item -> 
            new ReadOnlyStringWrapper(item.getValue().getEmail())
        );

        TableColumn<Cliente, Boolean> colVip = new TableColumn<>("VIP");
        colVip.setCellValueFactory( item -> 
            new ReadOnlyBooleanWrapper(item.getValue().getClienteVip())
        );

        TableColumn<Cliente, String> colDtCad = new TableColumn<>("DATA DE CADASTRO");
        colDtCad.setCellValueFactory(item -> 
            new ReadOnlyStringWrapper(formatador.format(item.getValue().getDataCadastro()))
        );

        TableColumn<Cliente, String> colDtVisi = new TableColumn<>("DATA DA ÚLTIMA VISITA");
        colDtVisi.setCellValueFactory(item -> 
            new ReadOnlyStringWrapper(formatador.format(item.getValue().getDataUltVisita()))
        );

        TableColumn<Cliente, Void> colAcoes = new TableColumn<>("AÇÕES");

        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> callBack = new Callback<>() {
            @Override
            public TableCell<Cliente, Void> call(TableColumn<Cliente, Void> coluna) {
                TableCell<Cliente, Void> celula = new TableCell<>() {
                private final Button btnAtualizar = new Button("Atualizar");
                private final Button btnExcluir = new Button("Excluir");
                private final HBox painelBotoes = new HBox();

                {
                    btnExcluir.setOnAction(e -> {
                        if (control.remover(getTableRow().getItem())) {
                            new Alert(
                                Alert.AlertType.INFORMATION, "Cliente excluído com sucesso!",
                                ButtonType.OK
                            );
                        } else {
                            new Alert(
                                Alert.AlertType.ERROR, "Erro ao tentar excluir o cliente!",
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

        table.getColumns().addAll(colId, colNome, colTelefone, colEmail, colVip, colDtCad, colDtVisi, colAcoes);

        table.setItems(control.getClientes());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                txtId.setText(Integer.toString(novo.getId()));
                txtNome.setText(novo.getNome());
                txtTelefone.setText(novo.getTelefone());
                txtEmail.setText(novo.getEmail());
                ckClienteVip.setSelected(novo.getClienteVip());
                dtCad.setValue(novo.getDataCadastro());
                dtVisita.setValue(novo.getDataUltVisita());
            }
        });

        p.add(lbId, 0, 0);
        p.add(lbNome, 0, 1);
        p.add(lbTelefone, 0, 2);
        p.add(lbEmail, 0, 3);
        p.add(lbVip, 0, 4);
        p.add(lbDtCad, 0, 5);
        p.add(lbDtVisita, 0, 6);
        p.add(txtId, 1, 0);
        p.add(txtNome, 1, 1);
        p.add(txtTelefone, 1, 2);
        p.add(txtEmail, 1, 3);
        p.add(ckClienteVip, 1, 4);
        p.add(dtCad, 1, 5);
        p.add(dtVisita, 1, 6);
        
        p.add(btnSalvar, 0, 7);
        p.add(btnPesquisar, 1, 7);
        p.add(btnLimparCampos, 2, 7);

        p.setHgap(2.0);
        p.setVgap(4.0);

        panPrincipal.setTop(p);
        panPrincipal.setCenter(table);
        
        return panPrincipal;  
    }
}
