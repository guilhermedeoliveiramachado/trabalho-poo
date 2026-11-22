package restaurante;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class PratoDAOImpl implements PratoDAO {
    private Connection con;

    public PratoDAOImpl() {
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
    public boolean salvar(Prato p) {
        try{
            String sqlInsert = "INSERT INTO Prato (nomePrato, descricao, preco, qtdPessoasServe, categoria, disponivel, tempoPreparo) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmtInsert = con.prepareStatement(sqlInsert);

            stmtInsert.setString(1, p.getNomePrato());
            stmtInsert.setString(2, p.getDescricao());
            stmtInsert.setDouble(3, p.getPreco());
            stmtInsert.setInt(4, p.getQtdPessoasServe());
            stmtInsert.setString(5, p.getCategoria());
            stmtInsert.setBoolean(6, p.getDisponivel());
            stmtInsert.setInt(7, p.getTempoPreparo());

            stmtInsert.executeUpdate();

            return true;
                    
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean atualizar(Prato p, int id) {
        try {
            String sqlUpdate = "UPDATE Prato SET nomePrato = ?, descricao = ?, preco = ?, qtdPessoasServe = ?, categoria = ?, disponivel = ?, tempoPreparo = ? WHERE id = ?";

            PreparedStatement stmtUpdate = con.prepareStatement(sqlUpdate);

            stmtUpdate.setString(1, p.getNomePrato());
            stmtUpdate.setString(2, p.getDescricao());
            stmtUpdate.setDouble(3, p.getPreco());
            stmtUpdate.setInt(4, p.getQtdPessoasServe());
            stmtUpdate.setString(5, p.getCategoria());
            stmtUpdate.setBoolean(6, p.getDisponivel());
            stmtUpdate.setInt(7, p.getTempoPreparo());
            stmtUpdate.setInt(8, id);

            stmtUpdate.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Prato pesquisar(String nomePrato) {
        try {
            String sqlQuery = "SELECT * FROM Prato WHERE nomePrato LIKE ?%";

            PreparedStatement stmtQuery = con.prepareStatement(sqlQuery);

            stmtQuery.setString(1, nomePrato);

            ResultSet rs = stmtQuery.executeQuery();

            if (rs.first()) {
                Prato p = new Prato();
                p.setNomePrato(rs.getString("nomePrato"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setQtdPessoasServe(rs.getInt("qtdPessoasServe"));
                p.setCategoria(rs.getString("categoria"));
                p.setDisponivel(rs.getBoolean("disponivel"));
                p.setTempoPreparo(rs.getInt("tempoPreparo"));

                return p;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean remover(int id) {
        try {
            String sqlDelete = "DELETE FROM Prato WHERE id = ?";

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
    public List<Prato> pesquisarTodos() {
        List<Prato> listaPratos = new ArrayList();

        try {
            String sqlQuery = "SELECT * FROM Prato";

            PreparedStatement stmtQuery = con.prepareStatement(sqlQuery);

            ResultSet rs = stmtQuery.executeQuery();

            while(rs.next()) {
                Prato p = new Prato();
                p.setNomePrato(rs.getString("nomePrato"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setQtdPessoasServe(rs.getInt("qtdPessoasServe"));
                p.setCategoria(rs.getString("categoria"));
                p.setDisponivel(rs.getBoolean("disponivel"));
                p.setTempoPreparo(rs.getInt("tempoPreparo"));

                listaPratos.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaPratos;
    }
}