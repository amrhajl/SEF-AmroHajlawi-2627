package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Studiegroep {
    // Teller die telkens met 1 omhoog gaat zodat elke groep een uniek id krijgt
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
        this.id = volgendeId;
        volgendeId++;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.oprichter = oprichter;
        // Multipliciteit 1..* : de oprichter is meteen het eerste lid
        this.leden.add(oprichter);
    }

    // Voegt een lid toe, maar enkel als het nog geen lid is
    public void voegLidToe(Student student) {
        if (!leden.contains(student)) {
            leden.add(student);
        }
    }

    public void verwijderLid(Student student) {
        // Een groep moet minstens 1 lid hebben (1..*), dus het laatste lid
        // en de oprichter laten we niet verwijderen
        if (student == oprichter || leden.size() <= 1) {
            return;
        } else {
            leden.remove(student);
        }
    }

    // De groep maakt de sessie zelf aan en bewaart ze
    public Studiesessie planSessie(LocalDateTime datum, String locatie, Opdracht opdracht) {
        Studiesessie sessie = new Studiesessie(datum, locatie, opdracht);
        sessies.add(sessie);
        return sessie;
    }

    public int getId() { return id; }
    public String getNaam() { return naam; }
    public String getBeschrijving() { return beschrijving; }
    public Student getOprichter() { return oprichter; }
    public List<Student> getLeden() { return leden; }
    public List<Studiesessie> getSessies() { return sessies; }

    @Override
    public String toString() {
        return "Studiegroep " + id + ": " + naam + " (" + leden.size() + " leden)";
    }
}