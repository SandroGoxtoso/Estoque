package br.com.SandroDiego.MenuPrincipalEstoque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Tela de login que chama os metodos ue fazem as verificaçoes nescessarias.
 * Splash Screen
 *
 * @autor Felipe M Sant"ana, felipe.santana@hbsis.com.br
 */

public class LoginActivity extends AppCompatActivity {

    //Atributos da classe
    static final int GOOGLE_SIGN = 123;
    GoogleSignInClient googleSignInClient;
    Button btn_login, btn_signin;
    ImageView btn_voltar;

    /**
     * Criacao da classe Handler
     * Handler serve para agendar as tarefas postergadas da classe ou do aplicativo inteiro e
     * chama em post[Tarefa] ou postDelayed[] conforme a necessidade.
     * Estes metodos aceitam objetos Runnable.
     * A Runnable e uma interface deve ser implementada por qualquer
     * classe cujas instâncias sejam executadas por um encadeamento.
     * A classe deve definir um método sem argumentos chamados run.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        iniciarAnimacao();
    }

    public void signInGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            System.out.println(name);
            System.out.println(email);
            System.out.println();
        }
    }

    public void acessarConta(View view) {
        /*List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                GOOGLE_SIGN);*/
        Intent ax = new Intent(LoginActivity.this, CarrinhoActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
    }

    public void voltarClick(View v) {
        Intent ax = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }

    public void iniciarAnimacao() {
        btn_login = findViewById(R.id.btn_login);
        btn_signin = findViewById(R.id.btn_signin);
        btn_voltar = findViewById(R.id.btn_voltar);

        btn_login.setTranslationX(400);
        btn_signin.setTranslationX(-400);
        btn_voltar.setTranslationX(-400);

        btn_login.setAlpha(0);
        btn_signin.setAlpha(0);

        btn_login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        btn_signin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        btn_voltar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
    }
}