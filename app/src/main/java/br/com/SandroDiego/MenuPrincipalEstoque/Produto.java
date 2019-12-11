package br.com.SandroDiego.MenuPrincipalEstoque;

public class Produto {

    private String nomeProduto, qtdProduto, codigoBarra;
    private Float valorProduto, somaProduto;
    private int imgProduto;

    public Produto() {
    }

    public Produto(String nomeProduto, float valorProduto, float somaProduto, String qtdProduto, String codigoBarra, int imgProduto) {
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.qtdProduto = qtdProduto;
        this.imgProduto = imgProduto;
        this.codigoBarra = codigoBarra;
        this.somaProduto = somaProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(String qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public float getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(float valorProduto) {
        this.valorProduto = valorProduto;
    }

    public void setValorProduto(Float valorProduto) {
        this.valorProduto = valorProduto;
    }

    public int getImgProduto() {
        return imgProduto;
    }

    public void setImgProduto(int imgProduto) {
        this.imgProduto = imgProduto;
    }

    public float getSomaProduto() {
        somaProduto = valorProduto * Float.valueOf(qtdProduto);
        return somaProduto;
    }

    public void setSomaProduto(Float somaProduto) {
        this.somaProduto = somaProduto;
    }

    public void setSomaProduto(float somaProduto) {
        this.somaProduto = somaProduto;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }
}
