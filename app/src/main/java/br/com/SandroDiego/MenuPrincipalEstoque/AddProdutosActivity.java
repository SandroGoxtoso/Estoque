package br.com.SandroDiego.MenuPrincipalEstoque;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class AddProdutosActivity extends AppCompatActivity {

    private TextView tv_nomeProduto, tv_valorUnitarioProduto, tv_qtdProduto;
    private EditText et_codigoBarra;
    private ImageView img_imagemProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprodutoscarrinho);
        // Deixa a Action Bar invisível
        getSupportActionBar().hide();

        // Definições de variáveis
        tv_nomeProduto = findViewById(R.id.tv_nomeProduto);
        tv_valorUnitarioProduto = findViewById(R.id.tv_valorUnitarioProduto);
        tv_qtdProduto = findViewById(R.id.tv_qtdProduto);
        et_codigoBarra = findViewById(R.id.et_codigoBarra);
        img_imagemProduto = findViewById(R.id.img_imagemProduto);

        // Variáveis Chaves
        Intent intent = getIntent();
        String nomeProduto = intent.getExtras().getString("NomeProduto");
        Float valorUnitarioProduto = intent.getExtras().getFloat("ValorUnitarioProduto");
        String codigoBarra = intent.getExtras().getString("CodigoBarra");
        String qtdProduto = intent.getExtras().getString("QtdProduto");
        int imagemProduto = intent.getExtras().getInt("ImagemProduto");

        // Atribuição de valores
        tv_nomeProduto.setText(nomeProduto);
        tv_valorUnitarioProduto.setText("R$ " + valorUnitarioProduto + " uni.");
        et_codigoBarra.setHint(codigoBarra);
        tv_qtdProduto.setText(qtdProduto);
        img_imagemProduto.setImageResource(imagemProduto);
    }

    public void fecharActivity(View view) {
        this.finish();
    }

    public void notificacao(View view) {
        String mensagem = "Sucesso! Clique aqui para retornar ao seu carrinho!";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(AddProdutosActivity.this).setSmallIcon(R.drawable.ic_check_black_24dp).setContentTitle("Alteração de pedido").setContentText(mensagem).setAutoCancel(true);
        Intent intent = new Intent(AddProdutosActivity.this, CarrinhoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Mensagem", mensagem);
        PendingIntent pendingIntent = PendingIntent.getActivity(AddProdutosActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
