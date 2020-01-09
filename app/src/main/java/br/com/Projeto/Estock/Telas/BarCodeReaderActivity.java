package br.com.Projeto.Estock.Telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.camerakit.CameraKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

/**
 * Classe que extende BaseCameraActivity. Define os métodos e as funções necessárias para se realizar
 * a leitura de um código de barras através do MLkit.
 *
 * @Author André G. Theilacker <andretheilacker@gmail.com>
 * @Since 1.1.0
 */
public class BarCodeReaderActivity extends BaseCameraActivity {

    /**
     * Inicia a classe BarCodeReaderActivity.
     * <p>
     * Usa o método super.onCreate da classe BaseCameraActivity. Também define um onClickListener para
     * o botão btnScanCode.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setupBottonSheet(R.layout.activity_barcode_reader);

        btnScanCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                        final Bitmap bitmapImage = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.length);
                        getQRCodeDetails(bitmapImage);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                showPreview();
                                imagePreview.setImageBitmap(bitmapImage);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Define um onClickListener que funcionará ao clicar na Activity (qualquer lugar da tela).
     * <p>
     * Utiliza um método do CameraKit para capturar uma imagem, no formato byte[]. Então converte-se
     * essa imagem para um Bitmap, que é passado para o método getQRCodeDetails(Bitmap bitmap); em
     * seguida, o método showPreview() é chamado e a imagem capturada é definida como a imagem a ser
     * exibida no imagePreview.
     */
    @Override
    public void onClick(View view) {
        /*
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                final Bitmap bitmapImage = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.length);
                getQRCodeDetails(bitmapImage);
                runOnUiThread(new Runnable() {
                    public void run() {
                        showPreview();
                        imagePreview.setImageBitmap(bitmapImage);
                    }
                });
            }
        });
        */
    }

    /**
     * Escaneia o bitmap passado, utilizando o MLkit, para se extrair um código de barras.
     * <p>
     * É utilizado o FirebaseVisionBarcodeDetector, onde se define as opções (quais os tipos de
     * formato suportados); em seguida, pega-se uma instância do BarcodeDetector em si, passando as
     * opções definidas. Converte-se também o bitmap passado para o formato FirebaseVisionImage.
     * <p>
     * Então, usa-se o método .detectInImage do FirebaseVision para que o código de barras seja
     * procurado e adiciona-se listeners para situações de sucesso, falha e para quando a função é
     * completada.
     * <p>
     * O onSucessListener define o código de barras escaneado para o TextView codeData (que no momento
     * não é mais utilizado).
     * <p>
     * O onFailureListener imprime o erro encontrado e exibe ao usuário a mensagem de que algo deu
     * errado.
     * <p>
     * O onCompleteListener deveria retornar o código de barras encontrado para a Activity que o
     * chamou e finaliza a activity.
     */
    private void getQRCodeDetails(Bitmap bitmap) {
        FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build();

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
            @Override
            public void onSuccess(List<FirebaseVisionBarcode> barcodes) {

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Ops, algo de errado não está certo!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<FirebaseVisionBarcode>> task) {
                        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                        FirebaseVisionBarcode barcode = task.getResult().get(0);

                        if(barcode != null) {
                            String codigo = barcode.getDisplayValue();

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("codigo", codigo);
                            setResult(Activity.RESULT_OK, returnIntent);

                            finish();
                        }
                    }
                });
    }

}