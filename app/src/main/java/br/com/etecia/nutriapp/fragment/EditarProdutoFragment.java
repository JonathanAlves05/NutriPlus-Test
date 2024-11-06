package br.com.etecia.nutriapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import br.com.etecia.nutriapp.API.Api;
import br.com.etecia.nutriapp.R;
import br.com.etecia.nutriapp.adapter.EstoqueAdapter;
import br.com.etecia.nutriapp.classes.Produto;
import br.com.etecia.nutriapp.classes.ProdutoStorage;


public class EditarProdutoFragment extends Fragment {
    ImageView btnVoltarEditProd;
    TextInputEditText txtEditNomeProd, txtEditQuantProd, txtEditPrecoProd, txtEditMultProd, txtEditQuantEstoqueProd, txtEditDescProd, txtEditDataEntProd, txtEditValidProd;
    Button btnEditProd;
    EstoqueAdapter estoqueAdapter;
    private int produtoIndex;
    private Produto produto;





    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editarproduto, container, false);

        // Recuperar o índice diretamente
        produto = ProdutoStorage.listaDeProdutos.get(produtoIndex); // Acesse o produto usando o índice

        btnVoltarEditProd = view.findViewById(R.id.btnVoltarEditProduto);
        btnEditProd = view.findViewById(R.id.btnEditarProd);

        txtEditNomeProd = view.findViewById(R.id.txtEditNomeProd);
        txtEditQuantProd = view.findViewById(R.id.txtEditQuantProd);
        txtEditMultProd = view.findViewById(R.id.txtEditMultProd);
        txtEditQuantEstoqueProd = view.findViewById(R.id.txtEditQuantEstoqueProd);
        txtEditDescProd = view.findViewById(R.id.txtEditDescProd);
        txtEditDataEntProd = view.findViewById(R.id.txtEditDataEntProd);
        txtEditValidProd = view.findViewById(R.id.txtEditValidProd);
        txtEditPrecoProd = view.findViewById(R.id.txtEditPrecoProd);

        // Preenche os campos de edição com os dados do produto
        if (produto != null) {
            txtEditNomeProd.setText(produto.getNome());
            txtEditQuantProd.setText(String.valueOf(produto.getQuantidade()));
            txtEditPrecoProd.setText(String.valueOf(produto.getPreco()));
            txtEditMultProd.setText(String.valueOf(produto.getMultiplicador()));
            txtEditQuantEstoqueProd.setText(String.valueOf(produto.getQuantEstoque()));
            txtEditDescProd.setText(produto.getDescricao());
            txtEditDataEntProd.setText(produto.getDataEntrada());
            txtEditValidProd.setText(produto.getValidade());
        }

        btnVoltarEditProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack(); // Volta para o fragment anterior
                }
            }
        });




        btnEditProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    editarProdutos();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Erro ao editar o produto", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
    public void setProdutoIndex(int index) {
        this.produtoIndex = index;
    }

    public void editarProdutos() {
        produto.setNome(txtEditNomeProd.getText().toString().trim());
        produto.setQuantidade(Double.parseDouble(txtEditQuantProd.getText().toString().trim()));
        produto.setPreco(Double.parseDouble(txtEditPrecoProd.getText().toString().trim()));
        produto.setMultiplicador(Double.parseDouble(txtEditMultProd.getText().toString().trim()));
        produto.setQuantEstoque(Double.parseDouble(txtEditQuantEstoqueProd.getText().toString().trim()));
        produto.setDescricao(txtEditDescProd.getText().toString().trim());
        produto.setDataEntrada(txtEditDataEntProd.getText().toString().trim());
        produto.setValidade(txtEditValidProd.getText().toString().trim());



        ProdutoStorage.atualizarProduto(produtoIndex, produto);

        if (estoqueAdapter != null) {
            estoqueAdapter.notifyItemChanged(produtoIndex);
        }

        getActivity().getSupportFragmentManager().popBackStack();
        Toast.makeText(getContext(), "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show();

    }
}