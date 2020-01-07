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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 7117;
    Button btn_login, btn_signin;
    ImageView btn_voltar, ivLogo;
    List<AuthUI.IdpConfig> providers;
    Animation smalltobig, fade_scale_transition;
    LinearLayout ll_campos;
    TextView ivSubtitle, ivSubtitle2;

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

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        fade_scale_transition = AnimationUtils.loadAnimation(this, R.anim.fade_transition_animation);

        ivLogo = findViewById(R.id.ivLogo);
        ivSubtitle = findViewById(R.id.ivSubtitle);
        ivSubtitle2 = findViewById(R.id.ivSubtitle2);
        btn_login = findViewById(R.id.btn_login);
        btn_signin = findViewById(R.id.btn_signin);
        btn_voltar = findViewById(R.id.btn_voltar);
        ll_campos = findViewById(R.id.ll_campos);

        ivLogo.startAnimation(smalltobig);
        ll_campos.startAnimation(fade_scale_transition);

        ivLogo.setTranslationX(-1000);
        ivSubtitle.setTranslationY(400);
        ivSubtitle2.setTranslationY(400);
        btn_login.setTranslationX(400);
        btn_signin.setTranslationX(-400);
        btn_voltar.setTranslationX(-400);

        ivSubtitle.setAlpha(0);
        ivSubtitle2.setAlpha(0);
        btn_login.setAlpha(0);
        btn_signin.setAlpha(0);

        ivLogo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        ivSubtitle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        ivSubtitle2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        ll_campos.animate().translationX(0).alpha(1).setDuration(1400).setStartDelay(1300).start();
        btn_login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        btn_signin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        btn_voltar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
    }

    public void registrarse(View view) {
        Intent ax = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }
}