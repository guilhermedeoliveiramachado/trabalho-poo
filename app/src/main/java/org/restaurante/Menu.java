package org.restaurante;

import org.restaurante.ClienteForm;
import org.restaurante.ItemPedidoView;
import org.restaurante.PedidoView;
import org.restaurante.PratoForm;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Menu extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("NORDESTE VIVO - MENU");
        Label titulo = new Label("Restaurante NORDESTE VIVO");

        BorderPane panePrincipal = new BorderPane();
        FlowPane p = new FlowPane();

        Scene scn = new Scene(panePrincipal, 1200, 600);

        Button btnClientes = new Button("Clientes");
        Button btnPratos = new Button("Pratos");
        Button btnPedidos = new Button("Pedidos");
        Button btnItensPedidos = new Button("Itens do Pedido");

        p.setHgap(5.0);

        p.getChildren().addAll(titulo, btnClientes, btnPratos, btnPedidos, btnItensPedidos);

        panePrincipal.setTop(p);

        btnClientes.setOnAction(e -> {
            panePrincipal.setCenter(new ClienteForm().getPagina());
        });

        btnPratos.setOnAction(e -> {

            panePrincipal.setCenter(new PratoForm().getPagina());
        });

        btnPedidos.setOnAction(e -> {
            panePrincipal.setCenter(new PedidoView().getPagina());
        });

        btnItensPedidos.setOnAction(e -> {
            panePrincipal.setCenter(new ItemPedidoView().getPagina());
        });

        stage.setScene(scn);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(Menu.class, args);
    }
}
