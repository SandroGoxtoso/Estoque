package br.com.Projeto.Estock.Telas;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import br.com.Projeto.Estock.Interpolator.BounceAnimInterpolator;
import br.com.Projeto.Estock.Model.Usuarios;
import br.com.Projeto.Estock.R;

public class PrincipalActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    Animation animation, smalltobig;
    StorageReference storageReference;
    BounceAnimInterpolator interpolator;
    FirebaseUser usuarioFirebase;
    DatabaseReference referencia;
    Context context;
    private Uri imageUri;
    private StorageTask uploadTask;
    private ImageView iv_foto;
    private TextView tv_usuario, tv_email, tv_divisor;
    private Button btn_sair;
    private LinearLayout ll_pedido, ll_estoque, ll_conta, ll_estatistica;
    private CardView cv_menu;
    private ImageButton ib_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        iniciaAnimacao();

        iv_foto = findViewById(R.id.iv_foto);
        tv_usuario = findViewById(R.id.tv_usuario);
        tv_email = findViewById(R.id.tv_email);
        btn_sair = findViewById(R.id.btn_sair);
        ib_admin = findViewById(R.id.ib_admin);

        usuarioFirebase = FirebaseAuth.getInstance().getCurrentUser();
        referencia = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioFirebase.getUid());

        storageReference = FirebaseStorage.getInstance().getReference("Armazenamento");

        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                tv_usuario.setText(usuarios.getUsuario());
                tv_email.setText(usuarios.getEmail());
                if (usuarios.getFoto().equals("padrao")) {
                    iv_foto.setImageResource(R.mipmap.padrao);
                } else {
                    Glide.with(PrincipalActivity.this).load(usuarios.getFoto()).into(iv_foto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        iv_foto.setOnClickListener(view -> abrirImagem());

    }

    private void abrirImagem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = PrincipalActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // Está com problema verifica
    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Carregando!");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return fileReference.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    referencia = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuarioFirebase.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("foto", "" + mUri);
                    referencia.updateChildren(map);

                    pd.dismiss();
                } else {
                    Toast.makeText(PrincipalActivity.this.getApplicationContext(), "Falhou!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(PrincipalActivity.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            });
        } else {
            Toast.makeText(PrincipalActivity.this.getApplicationContext(), "Nenhuma imagem selecionada!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(PrincipalActivity.this.getApplicationContext(), "Upload em processo!", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }


    public void sairClick(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent ax = new Intent(PrincipalActivity.this, LoginActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fright, R.anim.fhelper2);
    }

    public void iniciaAnimacao() {
        cv_menu = findViewById(R.id.cv_menu);
        animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        interpolator = new BounceAnimInterpolator(0.5, 20);
        animation.setInterpolator(interpolator);
        cv_menu.startAnimation(animation);

        iv_foto = findViewById(R.id.iv_foto);
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        iv_foto.startAnimation(smalltobig);

        tv_usuario = findViewById(R.id.tv_usuario);
        tv_email = findViewById(R.id.tv_email);
        ll_estoque = findViewById(R.id.ll_estoque);
        ll_pedido = findViewById(R.id.ll_pedido);
        ll_estatistica = findViewById(R.id.ll_estatistica);
        ll_conta = findViewById(R.id.ll_conta);
        btn_sair = findViewById(R.id.btn_sair);
        tv_divisor = findViewById(R.id.tv_divisor);

        tv_usuario.setTranslationY(200);
        tv_email.setTranslationY(200);
        ll_estoque.setTranslationY(-800);
        ll_pedido.setTranslationY(-800);
        ll_estatistica.setTranslationY(800);
        ll_conta.setTranslationY(800);
        tv_divisor.setTranslationX(800);
        btn_sair.setTranslationY(400);

        tv_usuario.setAlpha(0);
        tv_email.setAlpha(0);
        ll_estoque.setAlpha(0);
        ll_pedido.setAlpha(0);
        ll_estatistica.setAlpha(0);
        ll_conta.setAlpha(0);
        tv_divisor.setAlpha(0);
        btn_sair.setAlpha(0);

        tv_usuario.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        tv_email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        ll_estoque.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(2000).start();
        ll_pedido.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(2000).start();
        tv_divisor.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2400).start();
        ll_estatistica.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(2800).start();
        ll_conta.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(2800).start();
        btn_sair.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(3100).start();
    }

    public void carrinhoClick(View view) {
        Intent ax = new Intent(PrincipalActivity.this, CarrinhoActivity.class);
        startActivity(ax);
        overridePendingTransition(R.anim.fleft, R.anim.fhelper);
    }
}
