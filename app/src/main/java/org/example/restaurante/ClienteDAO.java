package restaurante;

import java.util.List;

public interface ClienteDAO {
    boolean salvar(Cliente c);
    boolean atualizar(Cliente c, int id);
    boolean excluir(int id);
    Cliente pesquisar(String nome);
    List<Cliente> pesquisarTodos();
}