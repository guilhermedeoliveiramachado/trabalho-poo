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

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



public class PedidoDAOImpl implements PedidoDAO {

    private Connection con;
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm[:ss]");


    public PedidoDAOImpl() { 
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/restaurante",
                "root",
                "123456" );
            System.out.println("Conectado no database");
        } catch (Exception e) { 
            System.out.println("Erro ao conectar no database");
            e.printStackTrace();
        }
    }

    @Override
    public Pedido salvar(Pedido p) { 
        try {
            String sql = "INSERT INTO Pedido (idCliente, mesa, horaPedido, valorTotal, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p.getIdCliente());
            stmt.setInt(2, p.getMesa());
            stmt.setTime(3, Time.valueOf(p.getHoraPedido()));
            stmt.setDouble(4, p.getValorTotal());
            stmt.setString(5, p.getStatus());
            stmt.executeUpdate();
            System.out.println("Pedido salvo na tabela");
            new Alert(
                Alert.AlertType.INFORMATION, "Contato salvo com sucesso",
                ButtonType.OK
            ).showAndWait();
            return p;
        } catch (Exception e) { 
            System.out.println("Erro ao conectar no database");
            mostrarAlerta(e);
            return null;
        }
    }


    @Override
    public void remover( Pedido p) {
        try {
            String sql = "DELETE FROM Pedido WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
            System.out.println("Pedido excluido com sucesso tabela");
            new Alert(
                Alert.AlertType.INFORMATION, "Contato excluido com sucesso",
                ButtonType.OK
            ).showAndWait();
        } catch (Exception e) { 
            System.out.println("Erro ao conectar no database");
            mostrarAlerta(e);
        }
    }


    @Override
    public List<Pedido> pesquisarTodos() {
        List<Pedido> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Pedido";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Pedidos lidos da tabela");
            while (rs.next()) { 
                Pedido p = new Pedido();
                p.setId( rs.getInt("id") );
                p.setIdCliente( rs.getInt("idCliente") );
                p.setMesa( rs.getInt("mesa") );
                p.setHoraPedido( LocalTime.parse(rs.getString("horaPedido"), formatador) );
                p.setValorTotal( rs.getDouble("valorTotal") );
                p.setStatus( rs.getString("status") ); 
                lista.add( p );
            }
        } catch (Exception e) { 
            System.out.println("Erro ao conectar no database");
            mostrarAlerta(e);
            return null;
        }
        return lista;
    }


    @Override
    public void atualizar(long id, Pedido p) { 
        try {
            String sql = "UPDATE Pedido SET idCliente=?, mesa=?, horaPedido=?, valorTotal=?, status=? WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p.getIdCliente());
            stmt.setInt(2, p.getMesa());
            stmt.setTime(3, Time.valueOf(p.getHoraPedido()));
            stmt.setDouble(4, p.getValorTotal());
            stmt.setString(5, p.getStatus());
            stmt.setLong(6, id);
            stmt.executeUpdate();
            System.out.println("Pedido atualizado na tabela");
            new Alert(
                Alert.AlertType.INFORMATION, "Contato atualizado com sucesso",
                ButtonType.OK
            ).showAndWait();
        } catch (Exception e) { 
            System.out.println("Erro ao conectar no database");
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
