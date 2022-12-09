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
    private String estado;
    private String fotoDispUrl;
    private String tipoDisp;
    private String marcaDisp;
    private String nombrePersona;
    private String lugarRecojo;
    private String latitud;
    private String longitud;
    private String justificacionRechazo;

    public String getLugarRecojo() {
        return lugarRecojo;
    }

    public void setLugarRecojo(String lugarRecojo) {
        this.lugarRecojo = lugarRecojo;
    }

    public String getJustificacionRechazo() {
        return justificacionRechazo;
    }

    public void setJustificacionRechazo(String justificacionRechazo) {
        this.justificacionRechazo = justificacionRechazo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFotoDispUrl() {
        return fotoDispUrl;
    }

    public void setFotoDispUrl(String fotoDispUrl) {
        this.fotoDispUrl = fotoDispUrl;
    }

    public String getTipoDisp() {
        return tipoDisp;
    }

    public void setTipoDisp(String tipoDisp) {
        this.tipoDisp = tipoDisp;
    }

    public String getMarcaDisp() {
        return marcaDisp;
    }

    public void setMarcaDisp(String marcaDisp) {
        this.marcaDisp = marcaDisp;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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
