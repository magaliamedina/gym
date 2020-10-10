package com.example.gimnasio_unne;

public class Grupos {

    private String id, prof1,prof2, horario, cupototal, descripcion;

    public Grupos() {}

    public Grupos(String id, String prof1, String prof2, String horario, String cupototal, String descripcion) {
        this.id = id;
        this.prof1 = prof1;
        this.prof2 = prof2;
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

    public String getProf1() {
        return prof1;
    }

    public void setProf1(String prof1) {
        this.prof1 = prof1;
    }

    public String getProf2() {
        return prof2;
    }

    public void setProf2(String prof2) {
        this.prof2 = prof2;
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
