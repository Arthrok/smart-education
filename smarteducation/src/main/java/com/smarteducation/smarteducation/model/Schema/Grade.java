package com.smarteducation.smarteducation.model.Schema;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Grade {
    private String dia;
    private String horario;

    public Grade(String dia, String horario) {
        this.dia = dia;
        this.horario = horario;
    }
    public String getDia() {
        return dia;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }

    
}
