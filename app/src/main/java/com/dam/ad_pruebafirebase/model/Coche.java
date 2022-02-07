package com.dam.ad_pruebafirebase.model;

public class Coche {
    private String marca;
    private String modelo;

    public Coche() {}

    public Coche(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }
}
