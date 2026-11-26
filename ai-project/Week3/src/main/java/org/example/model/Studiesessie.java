package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Studiesessie {
    // Teller die telkens met 1 omhoog gaat zodat elke sessie een uniek id krijgt
    private static int volgendeId = 1;

    private final int id;
    private final LocalDateTime datum;
    private final String locatie;
    private final Opdracht opdracht;
    private final int maximum;
    private final List<Student> deelnemers = new ArrayList<>();
    private final List<Document> documenten = new ArrayList<>();

    // Zonder maximum: er is geen limiet op het aantal deelnemers
    public Studiesessie(LocalDateTime datum, String locatie, Opdracht opdracht) {
        this(datum, locatie, opdracht, Integer.MAX_VALUE);
    }

    public Studiesessie(LocalDateTime datum, String locatie, Opdracht opdracht, int maximum) {
        this.id = volgendeId;
        volgendeId++;
        this.datum = datum;
        this.locatie = locatie;
        this.opdracht = opdracht;
        this.maximum = maximum;
    }

    // Voegt een deelnemer toe, maar enkel als die nog niet meedoet
    public void voegDeelnemerToe(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student mag niet null zijn");
        }
        if (deelnemers.size() >= maximum) {
            throw new IllegalStateException("De sessie is vol");
        }
        if (!deelnemers.contains(student)) {
            deelnemers.add(student);
        }
    }

    public void deelDocument(Document doc) {
        // Het diagram geeft het Document mee als parameter, dus het wordt hier
        // enkel toegevoegd aan de lijst van de sessie
        if (doc == null) {
            throw new IllegalArgumentException("Document mag niet null zijn");
        }
        if (!documenten.contains(doc)) {
            documenten.add(doc);
        }
    }

    public int getId() { return id; }
    public LocalDateTime getDatum() { return datum; }
    public String getLocatie() { return locatie; }
    public Opdracht getOpdracht() { return opdracht; }
    public List<Student> getDeelnemers() { return deelnemers; }
    public List<Document> getDocumenten() { return documenten; }

    @Override
    public String toString() {
        return "Studiesessie " + id + ": " + datum + " in " + locatie;
    }
}