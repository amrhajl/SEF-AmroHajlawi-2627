package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vak {
    private static int volgendeId = 1;

    private int id;
    private String naam;
    // Compositie: Vak maakt de opdrachten zelf aan
    private List<Opdracht> opdrachten = new ArrayList<>();

    public Vak(String naam) {
        this.id = volgendeId;
        volgendeId++;
        this.naam = naam;
    }

    // Extra methodes nodig voor de compositie (het diagram toont ze niet)
    public Taak voegTaakToe(String titel, String beschrijving, LocalDate datum) {
        Taak taak = new Taak(titel, beschrijving, datum);
        opdrachten.add(taak);
        return taak;
    }

    public Test voegTestToe(String titel, String beschrijving, LocalDate datum, double maxScore) {
        Test test = new Test(titel, beschrijving, datum, maxScore);
        opdrachten.add(test);
        return test;
    }

    public List<Opdracht> getOpdrachten() {
        return opdrachten;
    }

    public int getId() { return id; }
    public String getNaam() { return naam; }
}