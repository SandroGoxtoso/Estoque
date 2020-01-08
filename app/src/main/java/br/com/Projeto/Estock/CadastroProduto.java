package br.com.Projeto.Estock;

import android.Manifest;
import android.app.Activity;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class CadastroProduto extends AppCompatActivity {

    private static final int CAMERA_RESULT = 12356;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2000;
    private static final int GALERIA_IMAGENS = 111;
    private final int CAPTURAR_IMAGEM = 222;
    private final int PERMISSAO_REQUEST = 2;
    private final int LEITOR_DE_CODIGO_DE_BARRA = 888;
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
    private ImageView ibt_foto;
    private Uri uri;
    private Activity thisActivity;
    private String nomeImagem;

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
        thisActivity = this;
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

        btn_carregarCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("DEBUG","onclick antes");
                    ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_CAMERA_PERMISSION
                            );
                    Log.d("DEBUG","onclick dps");
                }else{
                    boolean temCamera = getPackageManager()
                            .hasSystemFeature(PackageManager.FEATURE_CAMERA);
                    if (temCamera) {
                       tirarFoto();
                    }
                }
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

        /*btn_carregarCamera.setOnClickListener(new View.OnClickListener() {
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
        });*/

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

        if (requestCode == CAPTURAR_IMAGEM) {
            if (resultCode == RESULT_OK) {;

                Bitmap thumbnail = (BitmapFactory.decodeFile(nomeImagem));
                thumbnail = rotationBitMap(thumbnail);
                img_Produto.setImageBitmap(thumbnail);
                mostrarMensagem("Imagem capturada!");
                adicionarNaGaleria();
            } else {
                mostrarMensagem("Imagem não capturada!");
            }
        }

        if (requestCode == LEITOR_DE_CODIGO_DE_BARRA) {
            if (resultCode == RESULT_OK) {
                String codigoDeBarras = data.getStringExtra("codigo");
                et_codigoBarra = findViewById(R.id.et_codigoBarra);
                et_codigoBarra.setText(codigoDeBarras);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tirarFoto();
            }else{
                Log.d("DEBUG","PERMISSÃO NEGADA");
            }
        }
    }

    private void tirarFoto() {
        File diretorio = Environment
                .getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
        nomeImagem = diretorio.getPath() + "/" +
                System.currentTimeMillis() +
                ".jpg";
        //uri = Uri.fromFile(new File(nomeImagem));
        uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", new File(nomeImagem));

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAPTURAR_IMAGEM);
    }

    private void mostrarMensagem(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_LONG)
                .show();
    }
    private void adicionarNaGaleria() {
        Intent intent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        this.sendBroadcast(intent);
    }

    public void capturarImagem(View v){

    }
    public void visualizarImagem(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/jpeg");
        startActivity(intent);
    }

    /**
     * Método que limpa os campos após o cadastro.
     */
    public void limpaCampos() {
        et_nomeProduto.setText("");
        et_valorProduto.setText("");
        et_qtdProduto.setText("");
    }

    public void lerCodigo(View view) {
        Intent lerCodigodeBarra = new Intent(this, BarCodeReaderActivity.class);
        startActivityForResult(lerCodigodeBarra, LEITOR_DE_CODIGO_DE_BARRA);
    }
}