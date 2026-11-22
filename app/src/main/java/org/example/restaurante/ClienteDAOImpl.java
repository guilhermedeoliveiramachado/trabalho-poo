package restaurante;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ClienteDAOImpl implements ClienteDAO {
    private Connection con;

    public ClienteDAOImpl() {
        try {
            Class.forName("org.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/restaurante",
                "root",
                "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean salvar(Cliente c) {
        try{
            String sqlInsert = "INSERT INTO Cliente (nome, telefone, email, clienteVip, dataCadastro, dataUltimaVisita) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmtInsert = con.prepareStatement(sqlInsert);

            stmtInsert.setString(1, c.getNome());
            stmtInsert.setString(2, c.getTelefone());
            stmtInsert.setString(3, c.getEmail());
            stmtInsert.setBoolean(4, c.getClienteVip());
            stmtInsert.setDate(5, new java.sql.Date(c.getDataCadastro().getTime()));
            stmtInsert.setDate(6, new java.sql.Date(c.getDataUltVisita().getTime()));

            stmtInsert.executeUpdate();

            return true;
                    
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean atualizar(Cliente c, int id) {
        try {
            String sqlUpdate = "UPDATE Cliente SET nome = ?, telefone = ?, email = ?, clienteVip = ?, dataCadastro = ?, dataUltimaVisita = ? WHERE id = ?";

            PreparedStatement stmtUpdate = con.prepareStatement(sqlUpdate);

            stmtUpdate.setString(1, c.getNome());
            stmtUpdate.setString(2, c.getTelefone());
            stmtUpdate.setString(3, c.getEmail());
            stmtUpdate.setBoolean(4, c.getClienteVip());
            stmtUpdate.setDate(5, new java.sql.Date(c.getDataCadastro().getTime()));
            stmtUpdate.setDate(6, new java.sql.Date(c.getDataUltVisita().getTime()));
            stmtUpdate.setInt(7, id);

            stmtUpdate.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Cliente pesquisar(String nome) {
        try {
            String sqlQuery = "SELECT * FROM Cliente WHERE nome LIKE ?%";

            PreparedStatement stmtQuery = con.prepareStatement(sqlQuery);

            stmtQuery.setString(1, nome);

            ResultSet rs = stmtQuery.executeQuery();

            if (rs.first()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                c.setClienteVip(rs.getBoolean("clienteVip"));
                c.setDataCadastro(rs.getDate("dataCadastro"));
                c.setDataUltVisita(rs.getDate("dataUltimaVisita"));

                return c;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean excluir(int id) {
        try {
            String sqlDelete = "DELETE FROM Cliente WHERE id = ?";

            PreparedStatement stmtDelete = con.prepareStatement(sqlDelete);

            stmtDelete.setInt(1, id);

            stmtDelete.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Cliente> pesquisarTodos() {
        List<Cliente> listaClientes = new ArrayList();

        try {
            String sqlQuery = "SELECT * FROM Cliente";

            PreparedStatement stmtQuery = con.prepareStatement(sqlQuery);

            ResultSet rs = stmtQuery.executeQuery();

            while(rs.next()) {
                Cliente c = new Cliente();

                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                c.setClienteVip(rs.getBoolean("clienteVip"));
                c.setDataCadastro(rs.getDate("dataCadastro"));
                c.setDataUltVisita(rs.getDate("dataUltimaVisita"));

                listaClientes.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes;
    }
}