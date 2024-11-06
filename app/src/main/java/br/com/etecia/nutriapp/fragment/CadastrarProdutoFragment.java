package br.com.etecia.nutriapp.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import br.com.etecia.nutriapp.API.Api;
import br.com.etecia.nutriapp.API.RequestHandler;
import br.com.etecia.nutriapp.R;
import br.com.etecia.nutriapp.adapter.EstoqueAdapter;
import br.com.etecia.nutriapp.classes.Produto;
import br.com.etecia.nutriapp.classes.ProdutoStorage;


public class CadastrarProdutoFragment extends Fragment {



    ImageView btnVoltarCadProd;
    Button btnSalvarProd;
    TextInputEditText txtNomeProd, txtQuantProd, txtPrecoProd, txtMultiplicador, txtQuantEstoque, txtDesc, txtDataEntrada, txtValidade;
    List<Produto> produtoList;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
     ArrayList<Produto> produtoListArray = new ArrayList<>();
EstoqueAdapter estoqueAdapter;
    // Produto que será editado
    private Produto produto;
    private int produtoIndex;


    // Método para setar o produto que será editado
    public void setProduto(Produto produto, int index, EstoqueAdapter adapter) {
        this.produto = produto;
        this.produtoIndex = index;
        this.estoqueAdapter = adapter;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastrarproduto, container, false);

        btnVoltarCadProd = view.findViewById(R.id.btnVoltarCadProd);
        btnSalvarProd = view.findViewById(R.id.btnSalvarProd);
        txtNomeProd = view.findViewById(R.id.txtInputProd);
        txtMultiplicador = view.findViewById(R.id.txtInputMult);
        txtQuantProd = view.findViewById(R.id.txtInputQuant);
        txtPrecoProd = view.findViewById(R.id.txtInputPreco);
        txtQuantEstoque = view.findViewById(R.id.txtInputQuantEstoque);
        txtDataEntrada = view.findViewById(R.id.txtInputDataEntrada);
        txtValidade = view.findViewById(R.id.txtInputValidade);
        txtDesc = view.findViewById(R.id.txtInputDesc);

        //final Produto produto = produtoList.get();

        btnVoltarCadProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack(); // Volta para o fragment anterior
                }
            }
        });

        btnSalvarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    cadastrarProdutos();
                    if (estoqueAdapter != null) {
                        estoqueAdapter.notifyItemInserted(ProdutoStorage.listaDeProdutos.size() - 1);
                    }

                    Toast.makeText(getContext(), "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();

                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Erro ao cadastrar o produto", Toast.LENGTH_SHORT).show();
                }

            }

        });

        return view;
    }

    public void cadastrarProdutos() throws ParseException {

        String nome = txtNomeProd.getText().toString().trim();
        String quantidade = txtQuantProd.getText().toString().trim();
        String preco = txtPrecoProd.getText().toString().trim();
        String multiplicador = txtMultiplicador.getText().toString().trim();
        String quantEstoque = txtQuantEstoque.getText().toString().trim();
        String validade = txtValidade.getText().toString().trim();
        String dataEntrada = txtDataEntrada.getText().toString().trim();
        String desc = txtDesc.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            txtNomeProd.setError("Por favor entre com o nome");
            txtNomeProd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantidade)) {
            txtQuantProd.setError("Por favor entre com a quantidade");
            txtQuantProd.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(preco)) {
            txtPrecoProd.setError("Por favor entre com o preço");
            txtPrecoProd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(multiplicador)) {
            txtMultiplicador.setError("Por favor entre com o multiplicador");
            txtMultiplicador.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantEstoque)) {
            txtQuantEstoque.setError("Por favor entre com a quantidade em estoque");
            txtQuantEstoque.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dataEntrada)) {
            txtDataEntrada.setError("Por favor entre com a data de entrada");
            txtDataEntrada.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(validade)) {
            txtValidade.setError("Por favor entre com a validade");
            txtValidade.requestFocus();
            return;
        }
        try {
          double quantidadeDouble = Double.parseDouble(quantidade);
          double precoDouble = Double.parseDouble(preco);
          double multiplicadorDouble = Double.parseDouble(multiplicador);
          double quantEstoqueDouble = Double.parseDouble(quantEstoque);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Por favor, insira números válidos nos campos de quantidade, preço, multiplicador e estoque.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            formato.parse(dataEntrada);
            formato.parse(validade);
        } catch (ParseException e) {
            txtDataEntrada.setError("Formato de data inválido. Use dd/MM/yyyy.");
            txtDataEntrada.requestFocus();
            return;
        }
        Produto novoProduto = new Produto(
                nome,
                Double.parseDouble(preco),
                Double.parseDouble(quantidade),
                Double.parseDouble(multiplicador),
                Double.parseDouble(quantEstoque),
                validade,
                dataEntrada,
                desc
        );
        ProdutoStorage.listaDeProdutos.add(novoProduto);

        // Agora, se você estiver usando um adaptador, notifique ele para atualizar a lista
        // Notifica o adaptador para atualizar a visualização


       // limparCampos();


    };
    private void limparCampos() {
        txtNomeProd.setText("");
        txtQuantProd.setText("");
        txtPrecoProd.setText("");
        txtMultiplicador.setText("");
        txtQuantEstoque.setText("");
        txtValidade.setText("");
        txtDataEntrada.setText("");
        txtDesc.setText("");
    }
}





