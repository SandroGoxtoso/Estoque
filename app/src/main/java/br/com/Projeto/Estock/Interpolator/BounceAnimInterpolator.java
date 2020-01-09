package br.com.Projeto.Estock.Interpolator;

import android.view.animation.Interpolator;

public class BounceAnimInterpolator implements Interpolator {

    private double amplitude = 1;
    private double frequencia = 10;

    public BounceAnimInterpolator(double amplitude, double frequencia) {
        this.amplitude = amplitude;
        this.frequencia = frequencia;
    }

    @Override
    public float getInterpolation(float tempo) {
        return (float) (-1 * Math.pow(Math.E, -tempo / amplitude) * Math.cos(frequencia * tempo) + 1);
    }
}
