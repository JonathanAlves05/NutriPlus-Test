package br.com.etecia.nutriapp.classes;

import java.util.ArrayList;
import java.util.List;

public class ProdutoStorage {
    public static List<Produto> listaDeProdutos = new ArrayList<>();
    // Método para adicionar produtos (caso não tenha já)
    public static void addProduto(Produto produto) {
        listaDeProdutos.add(produto);
    }

    // Método para atualizar o produto na lista
    public static void atualizarProduto(int index, Produto produto) {
        listaDeProdutos.set(index, produto);

    }
}
