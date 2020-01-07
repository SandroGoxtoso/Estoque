package br.com.Projeto.Estock;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

public class CadastroProduto extends AppCompatActivity {

    private static final int CAMERA_RESULT = 12356;
    private final int GALERIA_IMAGENS = 1;
    private final int PERMISSAO_REQUEST = 2;
    /**
     * Criando os atributos da classe
     */

    private String nomeArquivo;
    private String codigo;
    private AlertDialog alerta;
    private AlertDialog alerta2;

    private EditText et_nomeProduto, et_valorProduto, et_qtdProduto, et_codigoBarra;
    private ImageView img_Produto;
    private Button btn_cadastrar, btn_codigoBarra, btn_carregarImagem, btn_carregarCamera;

    public static Bitmap rotationBitMap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return bmRotated;
    }

    /**
     * Aqui estamos criando um alerta, através de um método, que pde ser chamado a qualquer momento
     * Temos o alert que é 'Produto cadastrado'
     * E temos o alerta que é 'Dados inválidos'
     */


    private void alert() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("StockSys");
        //define a mensagem
        builder.setMessage("\n » Produto cadastrado!");
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void alerta() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        //define o titulo
        builder2.setTitle("StockSys");
        //define a mensagem
        builder2.setMessage("\n » Dados inválidos!");
        //cria o AlertDialog
        alerta2 = builder2.create();
        //Exibe
        alerta2.show();
    }

    /**
     * Setando o layout parecido com o da tela login
     * Setando o titulo da tela para Cadastrar produto
     * Transformando as views em objetos
     * Fazendo a verificação se já texto no campos
     * Fazendo o cadastro dos produtos
     * Limpando os campos, após inserção
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastrar produto");

        et_nomeProduto = findViewById(R.id.et_nomeProduto);
        et_valorProduto = findViewById(R.id.et_valorProduto);
        et_qtdProduto = findViewById(R.id.et_qtdProduto);
        et_codigoBarra = findViewById(R.id.et_codigoBarra);
        img_Produto = findViewById(R.id.img_produto);
        btn_codigoBarra = findViewById(R.id.btn_lerCodigoBarra);
        btn_carregarImagem = findViewById(R.id.btn_carregarImagem);
        btn_carregarCamera = findViewById(R.id.btn_carregarCamera);

        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                alert();
            }
        });

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((String.valueOf(et_valorProduto.getText()).length() > 0)
                        && (String.valueOf(et_valorProduto.getText()).length() > 0)
                        && (String.valueOf(et_qtdProduto.getText()).length() > 0)) {
                    btn_cadastrar.setActivated(true);
                    //cadastrarProduto();
                    alert();
                } else {
                    alerta();
                }
                limpaCampos();
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        img_Produto = findViewById(R.id.img_produto);
        Button galeria = findViewById(R.id.btn_carregarImagem);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_IMAGENS);
            }
        });

        btn_carregarCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                nomeArquivo = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg";
                File file = new File(nomeArquivo);
                Uri arquivoFoto = FileProvider.getUriForFile(getApplicationContext(),
                        getApplicationContext().getPackageName() + ".provider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, arquivoFoto);
                startActivityForResult(intent, CAMERA_RESULT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERIA_IMAGENS) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = rotationBitMap(thumbnail);
                img_Produto.setImageBitmap(thumbnail);
            }
        }
        if (requestCode == CAMERA_RESULT) {
            if (resultCode == RESULT_OK) {
                int targetW = img_Produto.getWidth();
                int targetH = img_Produto.getHeight();
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                File foto = new File(nomeArquivo);
                Uri fotoUri = FileProvider.getUriForFile(getApplicationContext(),
                        getApplicationContext().getPackageName() + ".provider", foto);
                Bitmap bitmap = BitmapFactory.decodeFile(fotoUri.toString(), bmOptions);
                bitmap = rotationBitMap(bitmap);
                img_Produto.setImageBitmap(bitmap);
            }
        }

        /**
         * Método que limpa os campos após o cadastro.
         */
    }

    public void limpaCampos() {
        et_nomeProduto.setText("");
        et_valorProduto.setText("");
        et_qtdProduto.setText("");
    }

    /**
     * Criando as variaveis que armazenam os dados e convertem para seu tipo primitivo.
     * Criando um novo produto com as caracteristicas.
     * O método retorna um produto.
     */

    public void cadastro() {
    }

    public void lerCodigo(View view) {
        Intent lerCodigodeBarra = new Intent(this, BarCodeReaderActivity.class);
        startActivityForResult(lerCodigodeBarra, 888);

        //et_codigoBarra = findViewById(R.id.et_codigoBarra);
        //codigo = String.valueOf(et_codigoBarra);
    }
}