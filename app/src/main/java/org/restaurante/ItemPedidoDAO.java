package org.restaurante;

/*
    Criado por: Kaique Pereira da Cruz

    Grupo: Guilherme de Oliveira Machado, Kaique Pereira da Cruz
*/

import java.util.List;


public interface ItemPedidoDAO {
    ItemPedido salvar(ItemPedido ip);
    void remover( ItemPedido ip);
    List<ItemPedido> pesquisarTodos();
    void atualizar( long id, ItemPedido ip);
}