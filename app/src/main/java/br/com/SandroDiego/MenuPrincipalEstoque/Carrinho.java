package br.com.SandroDiego.MenuPrincipalEstoque;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Carrinho extends AppCompatActivity {

    List<Produto> listaProdutos;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().hide();

        listaProdutos = new ArrayList<>();

        listaProdutos.add(new Produto("Refrigerante Pepsi 350 ml", 5.50f, somaProduto(), "6", R.mipmap.pepsi));
        listaProdutos.add(new Produto("Refrigerante Guaraná 350 ml", 4.50f, somaProduto(), "8", R.mipmap.guarana));
        listaProdutos.add(new Produto("Fusion Energy Drink 250 ml", 6.50f, somaProduto(), "3", R.mipmap.fusion));
        listaProdutos.add(new Produto("Cerveja Bohemia Pilsen 350ml", 3.50f, somaProduto(), "2", R.mipmap.bohemia));

        TextView tv_valorTotalPedido = findViewById(R.id.tv_valorTotalPedido);
        tv_valorTotalPedido.setText(String.valueOf(somaTotal()));

        TextView tv_totalQuantidade = findViewById(R.id.tv_totalQuantidade);
        tv_totalQuantidade.setText(String.valueOf(somaQtd()));

        RecyclerView mrcv_lista_jogos = findViewById(R.id.rcv_itens_pedido);
        ItemPedido myAdapter = new ItemPedido(this, listaProdutos);

        mrcv_lista_jogos.setLayoutManager(new GridLayoutManager(this, 1));
        mrcv_lista_jogos.setAdapter(myAdapter);
    }

    // Total a ser exibido no final da lista (Soma das quantidades de produtos)
    public int somaQtd() {
        int somaQtd = 0;
        for (int n = 0; n < listaProdutos.size(); n++) {
            somaQtd = somaQtd + Integer.valueOf(listaProdutos.get(n).getQtdProduto());
        }
        return somaQtd;
    }

    // Total a ser exibido no final da lista (Soma dos valores dos produtos)
    public float somaTotal() {
        float somaTotal = 0.00f;
        for (int n = 0; n < listaProdutos.size(); n++) {
            somaTotal = somaTotal + listaProdutos.get(n).getSomaProduto();
        }
        return somaTotal;
    }

    // Total a ser exibido dentro do card de cada produto (Valor * Quantidade)
    public float somaProduto() {
        float somaProduto = 0.00f;
        for (int n = 0; n < listaProdutos.size(); n++) {
            somaProduto = listaProdutos.get(n).getValorProduto() * Float.valueOf(listaProdutos.get(n).getQtdProduto());
        }
        return somaProduto;
    }

}
