package br.com.Projeto.Estock.Telas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.camerakit.CameraKitView;

import br.com.Projeto.Estock.R;

/**
 * Classe base usada para capturar imagem com a câmera, utilizando a biblioteca CameraKit.
 * Seria utilizado uma BottomSheet para exibir informações adicionais, mas esta foi removida devido
 * a erros em relação a importação do BottomSheetBehavior.
 *
 * @Author André G. Theilacker <andretheilacker@gmail.com>
 * @Since 1.1.0
 */
public abstract class BaseCameraActivity extends AppCompatActivity implements View.OnClickListener {
    protected CameraKitView cameraKitView;
    protected ImageView imagePreview;
    protected Button btnScanCode;
    private ImageButton btnRetry;
    private FrameLayout framePreview;

    /**
     * Inicia a classe BaseCameraActivity.
     * <p>
     * Verifica se o usuário concedeu as permissões necessárias, e em sequência começa a criação da
     * activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_camera);

        btnRetry = findViewById(R.id.btnRetry);
        cameraKitView = findViewById(R.id.cameraView);
        framePreview = findViewById(R.id.framePreview);
        imagePreview = findViewById(R.id.imagePreview);
        btnScanCode = findViewById(R.id.btnScanCode);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (cameraKitView.getVisibility() == View.VISIBLE) {
                    showPreview();
                } else {
                    hidePreview();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    /**
     * Método chamado quando a atividade é resumida, iniciando o preview da câmera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    /**
     * Método chamado quando a atividade é pausada, pausando o preview da câmera por motivos de
     * performance.
     */
    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Chamado quando uma foto é tirada. Define o framePreview como visível (foto tirada) e define
     * o preview da câmera como invisível.
     */
    protected void showPreview() {
        framePreview.setVisibility(View.VISIBLE);
        cameraKitView.setVisibility(View.GONE);
    }

    /**
     * Chamado quando tenta tirar-se uma nova foto. Define o framePreview como invisível (foto tirada) e define
     * o preview da câmera como visível.
     */
    protected void hidePreview() {
        framePreview.setVisibility(View.GONE);
        cameraKitView.setVisibility(View.VISIBLE);
    }
}