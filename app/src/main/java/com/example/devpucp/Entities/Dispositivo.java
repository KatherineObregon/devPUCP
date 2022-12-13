package com.example.devpucp.Entities;

import java.io.Serializable;

public class Dispositivo implements Serializable {
    private String key;
    private String tipo;
    private String marca;
    private String fotoUrl;
    private String caracteristicas;
    private String accesorios;
    private String stock;

    private String stockPrestados;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(String accesorios) {
        this.accesorios = accesorios;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStockPrestados() {
        return stockPrestados;
    }

    public void setStockPrestados(String stockPrestados) {
        this.stockPrestados = stockPrestados;
    }
}
