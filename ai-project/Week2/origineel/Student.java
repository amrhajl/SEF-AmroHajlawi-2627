package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private static int volgendeId = 1;

    private int id;
    private String naam;
    private String email;
    private String wachtwoord;
    private List<Vak> vakken = new ArrayList<>();
    private List<Voortgang> voortgangen = new ArrayList<>();

    public Student(String naam, String email, String wachtwoord) {
        this.id = volgendeId;
        volgendeId++;
        this.naam = naam;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public void voegVakToe(Vak vak) {
        if (!vakken.contains(vak)) {
            vakken.add(vak);
        }
    }

    public Studiegroep richtStudiegroepOp(String naam) {
        // Beschrijving is niet gekend bij het oprichten, dus leeg
        // De oprichter (this) wordt in de constructor ook als eerste lid toegevoegd
        return new Studiegroep(naam, "", this);
    }

    public void sluitAanBijGroep(Studiegroep groep) {
        groep.voegLidToe(this);
    }

    public void sluitAanBijSessie(Studiesessie sessie) {
        sessie.voegDeelnemerToe(this);
    }

    public Status bekijkVoortgang(Opdracht opdracht) {
        for (Voortgang voortgang : voortgangen) {
            if (voortgang.getOpdracht() == opdracht) {
                return voortgang.getStatus();
            }
        }
        // Nog geen voortgang voor deze opdracht: aanmaken met status TE_DOEN
        Voortgang nieuw = new Voortgang(this, opdracht);
        voortgangen.add(nieuw);
        return nieuw.getStatus();
    }

    public int getId() { return id; }
    public String getNaam() { return naam; }
    public String getEmail() { return email; }
    public String getWachtwoord() { return wachtwoord; }
    public List<Vak> getVakken() { return vakken; }
    public List<Voortgang> getVoortgangen() { return voortgangen; }
}