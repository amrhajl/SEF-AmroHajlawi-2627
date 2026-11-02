package org.example.model;

import java.time.LocalDate;

public abstract class Opdracht {
    // Teller die telkens met 1 omhoog gaat zodat elke opdracht een uniek id krijgt
    private static int volgendeId = 1;

    private final int id;
    private final String titel;
    private final String beschrijving;
    private final LocalDate datum;

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

    @Override
    public String toString() {
        return "Opdracht " + id + ": " + titel + " (" + datum + ")";
    }
}