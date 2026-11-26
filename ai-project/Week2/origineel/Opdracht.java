package org.example.model;

import java.time.LocalDate;

public abstract class Opdracht {
    private static int volgendeId = 1;

    private int id;
    private String titel;
    private String beschrijving;
    private LocalDate datum;

    public Opdracht(String titel, String beschrijving, LocalDate datum) {
        this.id = volgendeId;
        volgendeId++;
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.datum = datum;
    }

    public int getId() { return id; }
    public String getTitel() { return titel; }
    public String getBeschrijving() { return beschrijving; }
    public LocalDate getDatum() { return datum; }
}