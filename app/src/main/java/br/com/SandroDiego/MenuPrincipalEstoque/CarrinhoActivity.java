package br.com.SandroDiego.MenuPrincipalEstoque;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {

    List<Produto> listaProdutos;

    public static String decimalFormat(Float num) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        return df.format(num);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().hide();

        listaProdutos = new ArrayList<>();

        listaProdutos.add(new Produto("Refrigerante Pepsi 350 ml", 5.55f, somaProduto(), "6", "01234-12345-53212-23412", R.mipmap.pepsi_lata));
        listaProdutos.add(new Produto("Refrigerante Guaraná 350 ml", 4.50f, somaProduto(), "8", "12345-01234-23412-53212", R.mipmap.guarana_lata));
        listaProdutos.add(new Produto("Fusion Energy Drink 250 ml", 6.50f, somaProduto(), "3", "23412-53212-01234-12345", R.mipmap.fusion_lata));
        listaProdutos.add(new Produto("Cerveja Bohemia Pilsen 350ml", 3.55f, somaProduto(), "2", "53212-23412-12345-01234", R.mipmap.bohemia));
        listaProdutos.add(new Produto("Cerveja Bohemia Long Neck 600ml", 6.00f, somaProduto(), "5", "82214-26412-12647-11230", R.mipmap.bohemia_long_neck));
        listaProdutos.add(new Produto("Refrigerante Sukita 350ml", 3.55f, somaProduto(), "12", "65745-43253-97865-75445", R.mipmap.sukita_lata));

        TextView tv_valorTotalPedido = findViewById(R.id.tv_valorTotalPedido);
        tv_valorTotalPedido.setText(String.valueOf("R$ " + decimalFormat(somaTotal())));

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
        float somaTotal = 0.0f;
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
