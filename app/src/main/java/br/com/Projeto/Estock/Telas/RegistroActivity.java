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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import br.com.Projeto.Estock.R;

public class RegistroActivity extends AppCompatActivity {

    Animation fade_scale_inverted_transition, smalltobig;
    LinearLayout ll_usuario, ll_email, ll_senha, ll_termos;
    ImageView ivLogo, btn_voltar;
    Button btn_registrarse;
    TextView ivSubtitle, ivSubtitle2;
    RadioButton rb_termos;
    EditText et_usuario, et_email, et_senha;

    FirebaseAuth autenticador;
    DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        iniciaAnimacao();

        et_usuario = findViewById(R.id.et_usuario);
        et_email = findViewById(R.id.et_email);
        et_senha = findViewById(R.id.et_senha);
        btn_registrarse = findViewById(R.id.btn_registrarse);

        autenticador = FirebaseAuth.getInstance();
    }

    public void regitrarse(final String usuario, String email, String senha) {
        btn_registrarse.setEnabled(false);
        autenticador.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser usuarioFirebase = autenticador.getCurrentUser();
                assert usuarioFirebase != null;
                String idUsuario = usuarioFirebase.getUid();

                referencia = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuario);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", idUsuario);
                hashMap.put("usuario", usuario);
                hashMap.put("email", email);
                hashMap.put("foto", "padrao");
                hashMap.put("permisao", "usuario");

                referencia.setValue(hashMap).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Intent ax = new Intent(RegistroActivity.this, LoginActivity.class);
                        ax.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ax);
                        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
                        Toast.makeText(this, "Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                        btn_registrarse.setEnabled(true);
                    }
                });
            } else {
                Toast.makeText(RegistroActivity.this, "Não foi possível realizar o cadastro!", Toast.LENGTH_SHORT).show();
                et_usuario.setText("");
                et_email.setText("");
                et_senha.setText("");
                et_usuario.requestFocus();
                btn_registrarse.setEnabled(true);
            }
        });
    }

    public void iniciaAnimacao() {

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        fade_scale_inverted_transition = AnimationUtils.loadAnimation(this, R.anim.fade_transition_inverted_animation);

        ivLogo = findViewById(R.id.ivLogo);
        ivSubtitle = findViewById(R.id.ivSubtitle);
        ivSubtitle2 = findViewById(R.id.ivSubtitle2);
        ll_usuario = findViewById(R.id.ll_usuario);
        ll_email = findViewById(R.id.ll_email);
        ll_senha = findViewById(R.id.ll_senha);
        ll_termos = findViewById(R.id.ll_termos);
        btn_registrarse = findViewById(R.id.btn_registrarse);
        btn_voltar = findViewById(R.id.btn_voltar);

        ivLogo.startAnimation(smalltobig);

        ivLogo.setTranslationX(400);
        ivSubtitle.setTranslationY(400);
        ivSubtitle2.setTranslationY(400);
        ll_usuario.setTranslationX(-800);
        ll_email.setTranslationX(-800);
        ll_senha.setTranslationX(-800);
        ll_termos.setTranslationY(800);
        btn_registrarse.setTranslationY(400);
        btn_voltar.setTranslationX(-400);

        ivSubtitle.setAlpha(0);
        ivSubtitle2.setAlpha(0);
        ll_usuario.setAlpha(0);
        ll_email.setAlpha(0);
        ll_senha.setAlpha(0);
        ll_termos.setAlpha(0);

        ivLogo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        ivSubtitle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        ivSubtitle2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        ll_usuario.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        ll_email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1400).start();
        ll_senha.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1600).start();
        ll_termos.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(1800).start();
        btn_registrarse.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(2200);
        btn_voltar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
    }

    public void voltarClick(View view) {
        Intent ax = new Intent(RegistroActivity.this, LoginActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
    }

    public void registroClick(View view) {
        String usuario = et_usuario.getText().toString();
        String email = et_email.getText().toString();
        String senha = et_senha.getText().toString();

        if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show();
        } else if (senha.length() < 6) {
            Toast.makeText(this, "A senha deve conter mais de 6 dígitos!", Toast.LENGTH_SHORT).show();
        } else {
            regitrarse(usuario, email, senha);
        }
    }
}
