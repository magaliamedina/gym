package com.example.gimnasio_unne;

public class Horarios {
    private String id, hora_inicio, hora_fin;

    public Horarios() {}

    public Horarios(String id, String hora_inicio, String hora_fin) {
        this.id =id;
        this.hora_inicio=hora_inicio;
        this.hora_fin=hora_fin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoraInicio() {
        return hora_inicio;
    }

    public void setHoraInicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHoraFin() {
        return hora_fin;
    }

    public void setHoraFin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    @Override
    public String toString() {
        return "De " + hora_inicio + " a " + hora_fin;
    }
}
