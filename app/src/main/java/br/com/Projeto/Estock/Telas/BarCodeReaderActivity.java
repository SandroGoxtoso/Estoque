package br.com.Projeto.Estock.Telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
     * Chama o método super.onCreate da classe BaseCameraActivity e também define um onClickListener
     * param o botão btnScanCode.
     * <p>
     * Ao extendermos essa classe da BaseCameraActivity, podemos ter uma
     * atividade especializada em ler códigos de barra, ao mesmo tempo mantendo uma classe genérica
     * BaseCameraActivity, para outras atividades que envolvam o uso da câmera.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnScanCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("onClickBtnScanCode", "Clicked");
                captureImage();
                Log.i("onClickBtnScanCode", "Concluded");
            }
        });
    }

    /**
     * Detecta cliques em qualquer lugar da tela para iniciar a captura de uma imagem.
     * <p>
     * Utiliza um método do CameraKit para capturar uma imagem, no formato byte[]. A imagem é
     * convertida para um Bitmap, que é passado para o método getQRCodeDetails(Bitmap bitmap); em
     * seguida, o método showPreview() é chamado e a imagem capturada é definida como a imagem a ser
     * exibida no imagePreview.
     */
    @Override
    public void onClick(View view) {
        Log.i("onClickBtnScanCode", "Clicked");
        captureImage();
        Log.i("onClickBtnScanCode", "Concluded");
    }

    private void captureImage() {
        Log.i("imageCapture", "Started");
        cameraKitView.captureImage((cameraKitView, capturedImage) -> {
            Log.i("imageCapture", "Processing...");

            final Bitmap bitmapImage = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.length);
            getQRCodeDetails(bitmapImage);

            runOnUiThread(new Runnable() {
                public void run() {
                    Log.i("imageCapture", "Running...");
                    showPreview();
                    imagePreview.setImageBitmap(bitmapImage);
                }
            });
            Log.i("imageCapture", "Ending...");
        });
        Log.i("imageCapture", "Ended");
    }

    /**
     * Escaneia o bitmap passado como parâmetro, utilizando o MLkit, para se extrair um código de barras.
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
        Log.i("CodeScanner", "Started");

        FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build();

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
            @Override
            public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                Log.i("Código de Barras", "Lido com sucesso.");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.i("Código de Barras", "Leitura falhou.");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<FirebaseVisionBarcode>> task) {
                        Log.i("Código de Barras", "Leitura completada.");

                        if (task.getResult().size() == 0) {
                            Log.i("Código de Barras", "Código não enviado.");
                            setResult(RESULT_CANCELED);
                        } else {
                            Log.i("Código de Barras", "Código enviado.");
                            FirebaseVisionBarcode barcode = task.getResult().get(0);
                            String codigo = barcode.getDisplayValue();

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("codigo", codigo);
                            setResult(RESULT_OK, returnIntent);
                        }

                        finish();
                    }
                });
    }

}