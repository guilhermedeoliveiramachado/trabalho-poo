package org.restaurante;

/*
    Criado por: Kaique Pereira da Cruz

    Grupo: Guilherme de Oliveira Machado, Kaique Pereira da Cruz
*/

import java.util.List;



public interface PedidoDAO {
    Pedido salvar(Pedido p);
    void remover( Pedido p);
    List<Pedido> pesquisarTodos();
    void atualizar( long id, Pedido p);
}
