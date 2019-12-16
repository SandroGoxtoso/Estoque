package br.com.SandroDiego.MenuPrincipalEstoque;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

public class AddProdutosActivity extends AppCompatActivity {

    Animation smalltobig, stb2;
    private TextView tv_nomeProduto, tv_valorUnitarioProduto, tv_qtdProduto;
    private EditText et_codigoBarra;
    private ImageView img_imagemProduto, btn_voltar, btn_adicionar, btn_remover;
    private Button btn_lerCodigoBarra, btn_alterar;
    private CardView cv_item_produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprodutoscarrinho);
        // Deixa a Action Bar invisível
        getSupportActionBar().hide();
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);

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
        int qtdProduto = intent.getExtras().getInt("QtdProduto");
        int imagemProduto = intent.getExtras().getInt("ImagemProduto");

        // Atribuição de valores
        tv_nomeProduto.setText(nomeProduto);
        tv_valorUnitarioProduto.setText("R$ " + valorUnitarioProduto + " uni.");
        et_codigoBarra.setHint(codigoBarra);
        tv_qtdProduto.setText("" + qtdProduto);
        img_imagemProduto.setImageResource(imagemProduto);

        iniciarAnimacao();
    }

    public void fecharActivity(View view) {
        /*Intent ax = new Intent(AddProdutosActivity.this, CarrinhoActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);*/
        finish();
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

    public void iniciarAnimacao() {
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        stb2 = AnimationUtils.loadAnimation(this, R.anim.stb2);
        btn_voltar = findViewById(R.id.btn_voltar);
        btn_lerCodigoBarra = findViewById(R.id.btn_lerCodigoBarra);
        btn_adicionar = findViewById(R.id.btn_adicionar);
        btn_remover = findViewById(R.id.btn_remover);
        btn_alterar = findViewById(R.id.btn_alterar);
        tv_qtdProduto = findViewById(R.id.tv_qtdProduto);
        tv_nomeProduto = findViewById(R.id.tv_nomeProduto);
        tv_valorUnitarioProduto = findViewById(R.id.tv_valorUnitarioProduto);
        img_imagemProduto = findViewById(R.id.img_imagemProduto);
        et_codigoBarra = findViewById(R.id.et_codigoBarra);
        cv_item_produto = findViewById(R.id.cv_item_produto);

        btn_voltar.setTranslationY(-200);
        btn_voltar.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();

        img_imagemProduto.setTranslationY(-1200);
        img_imagemProduto.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();

        et_codigoBarra.setTranslationY(-400);
        et_codigoBarra.setAlpha(0);
        et_codigoBarra.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();

        btn_lerCodigoBarra.setTranslationY(-100);
        btn_lerCodigoBarra.setAlpha(0);
        btn_lerCodigoBarra.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        cv_item_produto.setTranslationY(1000);
        cv_item_produto.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(800).start();

        tv_nomeProduto.setTranslationY(-200);
        tv_nomeProduto.setAlpha(0);
        tv_nomeProduto.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(1000).start();

        tv_valorUnitarioProduto.setTranslationX(-200);
        tv_valorUnitarioProduto.setAlpha(0);
        tv_valorUnitarioProduto.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();

        btn_remover.setTranslationX(-500);
        btn_remover.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();

        btn_adicionar.setTranslationX(500);
        btn_adicionar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();

        tv_qtdProduto.setTranslationY(200);
        tv_qtdProduto.setAlpha(0);
        tv_qtdProduto.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(1300).start();

        btn_alterar.setTranslationY(1000);
        btn_alterar.setAlpha(0);
        btn_alterar.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(1300).start();
    }

    public void adicionar(View view) {
        Intent intent = getIntent();
        int qtdProduto = intent.getExtras().getInt("QtdProduto");
        int soma = qtdProduto + 1;
        tv_qtdProduto.setText(soma + "");
    }

    public void remover(View view) {
        Intent intent = getIntent();
        int qtdProduto = intent.getExtras().getInt("QtdProduto");
        int soma = qtdProduto - 1;
        tv_qtdProduto.setText(soma + "");
    }

}
