package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    // Teller die telkens met 1 omhoog gaat zodat elke student een uniek id krijgt
    private static int volgendeId = 1;

    private final int id;
    private final String naam;
    private final String email;
    private final String wachtwoord;
    private final List<Vak> vakken = new ArrayList<>();
    private final List<Voortgang> voortgangen = new ArrayList<>();

    public Student(String naam, String email, String wachtwoord) {
        this.id = volgendeId;
        volgendeId++;
        this.naam = naam;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    // Voegt een vak toe, maar enkel als de student dat vak nog niet heeft
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

    // Geeft de status van een opdracht terug
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

    @Override
    public String toString() {
        return "Student " + id + ": " + naam + " (" + email + ")";
    }
}