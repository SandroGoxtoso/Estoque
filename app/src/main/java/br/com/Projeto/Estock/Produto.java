package br.com.Projeto.Estock;

import java.text.DecimalFormat;

public class Produto {

    private String nomeProduto, codigoBarra;
    private Float valorProduto, somaProduto;
    private int imgProduto, qtdProduto;

    public Produto() {
    }

    public Produto(String nomeProduto, float valorProduto, float somaProduto, int qtdProduto, String codigoBarra, int imgProduto) {
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.qtdProduto = qtdProduto;
        this.imgProduto = imgProduto;
        this.codigoBarra = codigoBarra;
        this.somaProduto = somaProduto;
    }

    public static String decimalFormat(Float num) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,##0.00");
        return df.format(num);
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
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
        somaProduto = valorProduto * qtdProduto;
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
