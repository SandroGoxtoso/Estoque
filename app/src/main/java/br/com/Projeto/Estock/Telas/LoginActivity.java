package br.com.Projeto.Estock.Telas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.Projeto.Estock.R;

public class LoginActivity extends AppCompatActivity {

    Button btn_login, btn_signin;
    ImageView btn_voltar, ivLogo;
    Animation smalltobig, fade_scale_transition;
    LinearLayout ll_email, ll_senha;
    TextView ivSubtitle, ivSubtitle2;
    EditText et_email, et_senha;

    FirebaseAuth autenticador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarAnimacao();

        autenticador = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.et_email);
        et_senha = findViewById(R.id.et_senha);
    }

    public void acessarConta(View view) {

        String email = et_email.getText().toString();
        String senha = et_senha.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show();
        } else {
            autenticador.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Teste
                    Intent ax = new Intent(LoginActivity.this, PrincipalActivity.class);
                    //ax.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ax);
                    overridePendingTransition(R.anim.fleft, R.anim.fhelper);
                    // Original
                    /*Intent ax = new Intent(LoginActivity.this, CarrinhoActivity.class);
                    ax.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ax);
                    overridePendingTransition(R.anim.fleft, R.anim.fhelper);*/
                } else {
                    Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos!", Toast.LENGTH_SHORT).show();
                    et_email.setText("");
                    et_senha.setText("");
                    et_email.requestFocus();
                }
            });
        }
    }

    public void voltarClick(View v) {
        Intent ax = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }

    public void iniciarAnimacao() {

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        fade_scale_transition = AnimationUtils.loadAnimation(this, R.anim.fade_transition_animation);

        ivLogo = findViewById(R.id.ivLogo);
        ivSubtitle = findViewById(R.id.ivSubtitle);
        ivSubtitle2 = findViewById(R.id.ivSubtitle2);
        btn_login = findViewById(R.id.btn_login);
        btn_signin = findViewById(R.id.btn_signin);
        btn_voltar = findViewById(R.id.btn_voltar);
        ll_email = findViewById(R.id.ll_email);
        ll_senha = findViewById(R.id.ll_senha);

        ivLogo.startAnimation(smalltobig);

        ivLogo.setTranslationX(400);
        ivSubtitle.setTranslationY(400);
        ivSubtitle2.setTranslationY(400);
        btn_login.setTranslationX(400);
        btn_signin.setTranslationX(-400);
        btn_voltar.setTranslationX(-400);
        ll_email.setTranslationX(-800);
        ll_senha.setTranslationX(-800);

        ivSubtitle.setAlpha(0);
        ivSubtitle2.setAlpha(0);
        btn_login.setAlpha(0);
        btn_signin.setAlpha(0);
        ll_email.setAlpha(0);
        ll_senha.setAlpha(0);

        ivLogo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        ivSubtitle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        ivSubtitle2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        ll_email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        ll_senha.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1400).start();
        btn_login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2000).start();
        btn_signin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2000).start();
        btn_voltar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
    }

    public void registrarse(View view) {
        Intent ax = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }
}