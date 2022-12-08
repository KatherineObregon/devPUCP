package com.example.devpucp.Entities;

import java.io.Serializable;

public class Solicitud implements Serializable {
    private String key;
    private String motivo;
    private String curso;
    private String tiempoReserva;
    private String programas;
    private String otros;
    private String fotoUrl;
    private String personaKey ;
    private String dispositivoKey;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTiempoReserva() {
        return tiempoReserva;
    }

    public void setTiempoReserva(String tiempoReserva) {
        this.tiempoReserva = tiempoReserva;
    }

    public String getProgramas() {
        return programas;
    }

    public void setProgramas(String programas) {
        this.programas = programas;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getPersonaKey() {
        return personaKey;
    }

    public void setPersonaKey(String personaKey) {
        this.personaKey = personaKey;
    }

    public String getDispositivoKey() {
        return dispositivoKey;
    }

    public void setDispositivoKey(String dispositivoKey) {
        this.dispositivoKey = dispositivoKey;
    }
}
