package br.com.SandroDiego.MenuPrincipalEstoque;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
    //public BottomSheetBehavior bottomSheetBehavior;
    private ImageButton btnRetry;
    //private ViewStub viewStub;
    private FrameLayout framePreview;

    /**
     * Inicia a classe BaseCameraActivity.
     * <p>
     * Verifica se o usuário concedeu as permissões necessárias, e em sequência começa a criação da
     * activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    50);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_camera);

        btnRetry = findViewById(R.id.btnRetry);
        cameraKitView = findViewById(R.id.cameraView);
        //viewStub = findViewById(R.id.viewStub)
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

    /*
    Código que seria utilizado para definir um elemento BottomSheet no xml, que exibiria informações
    do código de barras lido. Removido do projeto devido a conflito de importações.
    public void setupBottonSheet(@LayoutRes int id){
        viewStub.setLayoutResource(id);
        View inflatedView = viewStub.inflate();
        //Set layout parameters for the inflated bottomsheet
        CoordinatorLayout.LayoutParams lParam = (CoordinatorLayout.LayoutParams) inflatedView.getLayoutParams();
        lParam.setBehavior(new BottomSheetBehavior());
        inflatedView.setLayoutParams(lParam);
        bottomSheetBehavior = BottomSheetBehavior.from(inflatedView);
        bottomSheetBehavior.setPeekHeight(224);
    }
    */

    /**
     * Método chamado quando a atividade é resumida, iniciando o preview da câmera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onStart();
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