package com.example.gimnasio_unne.model;

public class Grupos {

    private String id, prof, horario, cupototal, descripcion;

    public Grupos() {}

    public Grupos(String id, String prof, String horario, String cupototal, String descripcion) {
        this.id = id;
        this.prof = prof;
        this.horario = horario;
        this.cupototal = cupototal;
        this.descripcion= descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof1) {
        this.prof = prof;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCupototal() {
        return cupototal;
    }

    public void setCupototal(String cupototal) {
        this.cupototal = cupototal;
    }


}
