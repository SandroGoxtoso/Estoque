package br.com.Projeto.Estock.Telas;

import android.content.Intent;
import android.os.Bundle;
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

public class RecuperaSenhaActivity extends AppCompatActivity {

    EditText et_email, et_dica;
    Button btn_alterar_senha;
    ImageView btn_voltar, ivLogo;
    LinearLayout ll_email;
    TextView ivSubtitle, ivSubtitle2, iv_dica;


    Animation smalltobig, fade_scale_transition;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_senha);

        iniciarAnimacao();
    }

    public void voltarClick(View v) {
        Intent ax = new Intent(RecuperaSenhaActivity.this, LoginActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }

    public void alterarSenhaClick(View view) {
        et_email = findViewById(R.id.et_email);
        btn_alterar_senha = findViewById(R.id.btn_alterar_senha);
        btn_voltar = findViewById(R.id.btn_voltar);

        firebaseAuth = FirebaseAuth.getInstance();

        String email = et_email.getText().toString();
        if (email.equals("")) {
            Toast.makeText(RecuperaSenhaActivity.this, "O campo e-mail é obrigatório!", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(RecuperaSenhaActivity.this, "Por favor verifique seu e-mail!", Toast.LENGTH_SHORT).show();
                    btn_voltar.callOnClick();
                } else {
                    Toast.makeText(RecuperaSenhaActivity.this, "E-mail não encontrado!", Toast.LENGTH_SHORT).show();
                    et_email.setText("");
                }
            });
        }

    }

    public void iniciarAnimacao() {

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        fade_scale_transition = AnimationUtils.loadAnimation(this, R.anim.fade_transition_animation);

        ivLogo = findViewById(R.id.ivLogo);
        ivSubtitle = findViewById(R.id.ivSubtitle);
        ivSubtitle2 = findViewById(R.id.ivSubtitle2);
        ll_email = findViewById(R.id.ll_email);
        iv_dica = findViewById(R.id.iv_dica);
        btn_voltar = findViewById(R.id.btn_voltar);

        ivLogo.startAnimation(smalltobig);

        ivLogo.setTranslationX(400);
        ivSubtitle.setTranslationY(400);
        ivSubtitle2.setTranslationY(400);
        ll_email.setTranslationX(-800);
        iv_dica.setTranslationY(400);
        btn_voltar.setTranslationX(-400);

        ivSubtitle.setAlpha(0);
        ivSubtitle2.setAlpha(0);
        ll_email.setAlpha(0);
        iv_dica.setAlpha(0);

        ivLogo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        ivSubtitle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        ivSubtitle2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        ll_email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        iv_dica.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(1400).start();
        btn_voltar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
    }
}