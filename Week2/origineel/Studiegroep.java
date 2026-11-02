package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Studiegroep {
    private static int volgendeId = 1;

    private int id;
    private String naam;
    private String beschrijving;
    private Student oprichter;
    // Aggregatie: leden worden meegegeven
    private List<Student> leden = new ArrayList<>();
    // Compositie: Studiegroep maakt de sessies zelf aan
    private List<Studiesessie> sessies = new ArrayList<>();

    public Studiegroep(String naam, String beschrijving, Student oprichter) {
        this.id = volgendeId;
        volgendeId++;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.oprichter = oprichter;
        // Multipliciteit 1..* : de oprichter is meteen het eerste lid
        this.leden.add(oprichter);
    }

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
}