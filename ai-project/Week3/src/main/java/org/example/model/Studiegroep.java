package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Studiegroep {
    private static int volgendeId = 1;

    private final int id;
    private final String naam;
    private final String beschrijving;
    private final Student oprichter;
    // Aggregatie: leden worden meegegeven
    private final List<Student> leden = new ArrayList<>();
    // Compositie: Studiegroep maakt de sessies zelf aan
    private final List<Studiesessie> sessies = new ArrayList<>();

    public Studiegroep(String naam, String beschrijving, Student oprichter) {
        if (naam == null || naam.isBlank()) {
            throw new IllegalArgumentException("Naam mag niet leeg zijn");
        }
        if (oprichter == null) {
            throw new IllegalArgumentException("Oprichter mag niet null zijn");
        }
        this.id = volgendeId;
        volgendeId++;
        this.naam = naam.trim();
        this.beschrijving = beschrijving;
        this.oprichter = oprichter;
        // Multipliciteit 1..*: de oprichter is meteen het eerste lid
        this.leden.add(oprichter);
    }

    // Voegt een lid toe, maar enkel als het nog geen lid is
    public void voegLidToe(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student mag niet null zijn");
        }
        if (!leden.contains(student)) {
            leden.add(student);
        }
    }

    // De oprichter blijft altijd lid (1..*), dus die wordt nooit verwijderd
    public void verwijderLid(Student student) {
        if (oprichter.equals(student)) {
            return;
        }
        leden.remove(student);
    }

    // De groep maakt de sessie zelf aan en bewaart ze
    public Studiesessie planSessie(LocalDateTime datum, String locatie, Opdracht opdracht) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum mag niet null zijn");
        }
        if (locatie == null || locatie.isBlank()) {
            throw new IllegalArgumentException("Locatie mag niet leeg zijn");
        }
        if (opdracht == null) {
            throw new IllegalArgumentException("Opdracht mag niet null zijn");
        }
        Studiesessie sessie = new Studiesessie(datum, locatie.trim(), opdracht);
        sessies.add(sessie);
        return sessie;
    }

    public int getId() { return id; }
    public String getNaam() { return naam; }
    public String getBeschrijving() { return beschrijving; }
    public Student getOprichter() { return oprichter; }

    // Niet-aanpasbare weergave: wijzigen kan enkel via voegLidToe / verwijderLid
    public List<Student> getLeden() { return Collections.unmodifiableList(leden); }
    public List<Studiesessie> getSessies() { return Collections.unmodifiableList(sessies); }

    @Override
    public String toString() {
        return "Studiegroep " + id + ": " + naam + " (" + leden.size() + " leden)";
    }
}