package org.restaurante;

/*
    Criado por: Kaique Pereira da Cruz

    Grupo: Guilherme de Oliveira Machado, Kaique Pereira da Cruz
*/

import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class ItemPedidoDAOImpl implements ItemPedidoDAO {
    
    private Connection con;

    public ItemPedidoDAOImpl() { 
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/restaurante",
                "root",
                "123456" );
            System.out.println("Conectado no database");
        } catch (Exception e) { 
            System.out.println("Erro ao conectar no database");
            mostrarAlerta(e);
        }
    }

    @Override
    public ItemPedido salvar(ItemPedido ip) {
        try {
            String sql = "INSERT INTO ItemPedido (idPedido, idPrato, qtdPedido, valorTotal, observacoesCliente, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, ip.getIdPedido());
            stmt.setInt(2, ip.getIdPrato());
            stmt.setInt(3, ip.getQtdPedido());
            stmt.setDouble(4, ip.getValorTotal());
            stmt.setString(5, ip.getObservacoesClientes());
            stmt.setString(6, ip.getStatus());
            stmt.executeUpdate();
            System.out.println("Pedido salvo na tabela");
            new Alert(
                Alert.AlertType.INFORMATION, "Contato salvo com sucesso",
                ButtonType.OK
            ).showAndWait();
        } catch (Exception e) { 
            System.out.println("Erro ao salvar ItemPedido");
            mostrarAlerta(e);
        }
        return ip;
    }

    @Override
    public void remover(ItemPedido ip) {
        try {
            String sql = "DELETE FROM ItemPedido WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, ip.getId());
            stmt.executeUpdate();
            System.out.println("Pedido excluido com sucesso tabela");
            new Alert(
                Alert.AlertType.INFORMATION, "Contato excluido com sucesso",
                ButtonType.OK
            ).showAndWait();
        } catch (Exception e) { 
            System.out.println("Erro ao remover ItemPedido");
            mostrarAlerta(e);
        }
    }

    @Override
    public List<ItemPedido> pesquisarTodos() {
        List<ItemPedido> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ItemPedido";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idPedido = rs.getInt("idPedido");
                int idPrato = rs.getInt("idPrato");
                int qtdPedido = rs.getInt("qtdPedido");
                Double valorTotal = rs.getDouble("valorTotal");
                String observacoesClientes = rs.getString("observacoesCliente");
                String status = rs.getString("status");
                ItemPedido ip = new ItemPedido(id, idPedido, idPrato, qtdPedido, valorTotal, observacoesClientes, status);
                lista.add(ip);
            }
        } catch (Exception e) { 
            System.out.println("Erro ao pesquisar todos os ItemPedidos");
            mostrarAlerta(e);
        }
        return lista;
    }

    @Override
    public void atualizar(long id, ItemPedido ip) {
        try {
            String sql = "UPDATE ItemPedido SET idPedido = ?, idPrato = ?, qtdPedido = ?, valorTotal = ?, observacoesCliente = ?, status = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, ip.getIdPedido());
            stmt.setInt(2, ip.getIdPrato());
            stmt.setInt(3, ip.getQtdPedido());
            stmt.setDouble(4, ip.getValorTotal());
            stmt.setString(5, ip.getObservacoesClientes());
            stmt.setString(6, ip.getStatus());
            stmt.setLong(7, id);
            stmt.executeUpdate();
            new Alert(
                Alert.AlertType.INFORMATION, "Contato atualizado com sucesso",
                ButtonType.OK
            ).showAndWait();
        } catch (Exception e) { 
            System.out.println("Erro ao atualizar ItemPedido");
            mostrarAlerta(e);
        }
    }


    public void mostrarAlerta(Exception e) {
        if (e instanceof java.sql.SQLException) {
            new Alert(
                Alert.AlertType.ERROR, "Erro de SQL: " + e.getMessage(),
                javafx.scene.control.ButtonType.OK
            ).showAndWait();
        } else if (e instanceof java.lang.NullPointerException) {
            new Alert(
                Alert.AlertType.ERROR, "Erro de ponteiro nulo: " + e.getMessage(),
                javafx.scene.control.ButtonType.OK
            ).showAndWait();
        } else if (e instanceof java.lang.ClassNotFoundException) {
            new Alert(
                Alert.AlertType.ERROR, "Erro de classe não encontrada: " + e.getMessage(),
                javafx.scene.control.ButtonType.OK
            ).showAndWait();
        } else {
            new Alert(
                Alert.AlertType.ERROR, "Erro não identificado: " + e.getMessage(),
                javafx.scene.control.ButtonType.OK
            ).showAndWait();
        }
    }
}
