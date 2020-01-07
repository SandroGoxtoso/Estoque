package br.com.Projeto.Estock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.Login;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 7117;
    Button btn_login, btn_signin;
    ImageView btn_voltar;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarAnimacao();
    }

    public void mostrarOpcoesLogin() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTheme(R.style.TemaAuth).build(), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void acessarConta(View view) {
        /*providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        mostrarOpcoesLogin();*/
        Intent ax = new Intent(LoginActivity.this, CarrinhoActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
    }

    public void voltarClick(View v) {
        /*AuthUI.getInstance().signOut(LoginActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mostrarOpcoesLogin();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
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

    public void registrarse(View view) {
        Intent ax = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper2);
    }
}