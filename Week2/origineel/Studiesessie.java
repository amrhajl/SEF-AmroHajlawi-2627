package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Studiesessie {
    private static int volgendeId = 1;

    private int id;
    private LocalDateTime datum;
    private String locatie;
    private Opdracht opdracht;
    private List<Student> deelnemers = new ArrayList<>();
    private List<Document> documenten = new ArrayList<>();

    public Studiesessie(LocalDateTime datum, String locatie, Opdracht opdracht) {
        this.id = volgendeId;
        volgendeId++;
        this.datum = datum;
        this.locatie = locatie;
        this.opdracht = opdracht;
    }

    public void voegDeelnemerToe(Student student) {
        if (!deelnemers.contains(student)) {
            deelnemers.add(student);
        }
    }

    public void deelDocument(Document doc) {
        // Het diagram geeft het Document mee als parameter, dus het wordt hier
        // enkel toegevoegd aan de lijst van de sessie
        documenten.add(doc);
    }

    public int getId() { return id; }
    public LocalDateTime getDatum() { return datum; }
    public String getLocatie() { return locatie; }
    public Opdracht getOpdracht() { return opdracht; }
    public List<Student> getDeelnemers() { return deelnemers; }
    public List<Document> getDocumenten() { return documenten; }
}