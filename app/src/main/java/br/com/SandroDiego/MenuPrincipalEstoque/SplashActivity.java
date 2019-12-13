package br.com.SandroDiego.MenuPrincipalEstoque;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    TextView ivSubtitle, ivSubtitle2, ivSubtitle3, ivBtn;
    ImageView ivSplash, ivLogo, ivBtn2;
    Animation smalltobig, fleft, fhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        fleft = AnimationUtils.loadAnimation(this, R.anim.fleft);
        fhelper = AnimationUtils.loadAnimation(this, R.anim.fhelper);

        ivSubtitle = findViewById(R.id.ivSubtitle);
        ivSubtitle2 = findViewById(R.id.ivSubtitle2);
        ivSubtitle3 = findViewById(R.id.ivSubtitle3);
        ivBtn = findViewById(R.id.ivBtn);
        ivBtn2 = findViewById(R.id.ivBtn2);

        ivSplash = findViewById(R.id.ivSplash);
        ivLogo = findViewById(R.id.ivLogo);

        ivSplash.startAnimation(smalltobig);
        ivLogo.startAnimation(smalltobig);

        ivLogo.setTranslationX(1000);
        ivSubtitle.setTranslationY(400);
        ivSubtitle2.setTranslationY(400);
        ivSubtitle3.setTranslationY(400);
        ivBtn.setTranslationX(-800);
        ivBtn2.setTranslationX(800);

        ivSubtitle.setAlpha(0);
        ivSubtitle2.setAlpha(0);
        ivBtn.setAlpha(0);

        ivLogo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        ivSubtitle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        ivSubtitle2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        ivSubtitle3.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        ivBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1400).start();
        ivBtn2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1400).start();
    }

    public void entrarClick(View v) {
        Intent ax = new Intent(SplashActivity.this, CarrinhoActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
    }
}
