package br.com.Projeto.Estock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    Animation fade_scale_inverted_transition, smalltobig;
    LinearLayout ll_campos;
    ImageView ivLogo, btn_voltar;
    Button btn_registrarse;
    TextView ivSubtitle, ivSubtitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        iniciaAnimacao();
    }

    public void iniciaAnimacao() {

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        fade_scale_inverted_transition = AnimationUtils.loadAnimation(this, R.anim.fade_transition_inverted_animation);

        ivLogo = findViewById(R.id.ivLogo);
        ivSubtitle = findViewById(R.id.ivSubtitle);
        ivSubtitle2 = findViewById(R.id.ivSubtitle2);
        ll_campos = findViewById(R.id.ll_campos);
        btn_registrarse = findViewById(R.id.btn_registrarse);
        btn_voltar = findViewById(R.id.btn_voltar);

        ivLogo.startAnimation(smalltobig);
        ll_campos.startAnimation(fade_scale_inverted_transition);

        ivLogo.setTranslationX(1000);
        ivSubtitle.setTranslationY(400);
        ivSubtitle2.setTranslationY(400);
        btn_registrarse.setTranslationY(400);
        btn_voltar.setTranslationX(-400);

        ivSubtitle.setAlpha(0);
        ivSubtitle2.setAlpha(0);

        ivLogo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        ivSubtitle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        ivSubtitle2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        ll_campos.animate().translationX(0).alpha(1).setDuration(1400).setStartDelay(1300).start();
        btn_registrarse.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600);
        btn_voltar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
    }

    public void voltarClick(View view) {
        Intent ax = new Intent(RegistroActivity.this, LoginActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
    }

}
