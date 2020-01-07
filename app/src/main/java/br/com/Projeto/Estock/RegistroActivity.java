package br.com.Projeto.Estock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class RegistroActivity extends AppCompatActivity {

    Animation fade_scale;
    LinearLayout ll_campos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        iniciaAnimacao();
    }

    public void iniciaAnimacao() {

        ll_campos = findViewById(R.id.ll_campos);
        fade_scale = AnimationUtils.loadAnimation(this, R.anim.fade_scale_animation);

        ll_campos.startAnimation(fade_scale);

        ll_campos.animate().translationX(0).alpha(1).setDuration(1400).setStartDelay(1300).start();
    }

    public void voltarClick(View view) {
        Intent ax = new Intent(RegistroActivity.this, LoginActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }
}
