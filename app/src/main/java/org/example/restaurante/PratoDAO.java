package restaurante;

import java.util.List;

public interface PratoDAO {
    boolean salvar(Prato p);
    boolean atualizar(Prato p, int id);
    Prato pesquisar(String nomePrato);
    List<Prato> pesquisarTodos();
    boolean remover(int id);
}