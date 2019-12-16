package br.com.SandroDiego.MenuPrincipalEstoque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    ImageView imgLogo;
    Button signin, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        iniciarAnimacao();
    }

    public void iniciarAnimacao() {
        imgLogo = findViewById(R.id.img_logo);
        imgLogo.setTranslationY(-600);
        imgLogo.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();

        signin = findViewById(R.id.btn_signin);
        signin.setTranslationX(-400);
        signin.setAlpha(0);
        signin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();

        login = findViewById(R.id.btn_login);
        login.setTranslationX(400);
        login.setAlpha(0);
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
    }

    public void entrarClick(View v) {
        Intent ax = new Intent(LoginActivity.this, CarrinhoActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
    }

    public void voltarClick(View v) {
        Intent ax = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }
}
