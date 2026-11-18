package de.kaufdort.moinmetrics.model;

import jakarta.persistence.*;

@Entity
@Table(name = "measures")
public class MeasureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String beschreibung;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
