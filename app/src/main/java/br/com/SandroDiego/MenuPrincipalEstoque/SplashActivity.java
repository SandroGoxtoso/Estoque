package br.com.SandroDiego.MenuPrincipalEstoque;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    Thread objectThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        startSplash();
    }

    private void startSplash() {
        try {
            Animation objectAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
            objectAnimation.reset();
            ImageView objecImageView = findViewById(R.id.logo);
            objecImageView.clearAnimation();
            objecImageView.startAnimation(objectAnimation);
            objectThread = new Thread() {
                @Override
                public void run() {
                    int pauseIt = 0;
                    while (pauseIt < 5000) {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pauseIt += 100;
                    }
                    startActivity(new Intent(SplashActivity.this, CarrinhoActivity.class));
                    SplashActivity.this.finish();
                }
            };
            objectThread.start();
        } catch (Exception e) {

        }
    }
}
