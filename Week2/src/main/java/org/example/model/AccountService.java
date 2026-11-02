package org.example.service;

import org.example.model.Student;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    // Lijst met alle geregistreerde studenten (final: de lijst zelf wordt nooit vervangen)
    private final List<Student> studenten = new ArrayList<>();

    /**
     * Maakt een nieuwe student aan en bewaart die in de lijst.
     * Gooit een exception als de input leeg is of het e-mailadres al bestaat.
     */
    public Student registreer(String naam, String email, String wachtwoord) {
        // Stap 1: controleren of alle velden ingevuld zijn
        if (isLeeg(naam) || isLeeg(email) || isLeeg(wachtwoord)) {
            throw new IllegalArgumentException("Naam, e-mail en wachtwoord zijn verplicht");
        }

        // Stap 2: controleren of het e-mailadres al in gebruik is
        if (zoekOpEmail(email) != null) {
            throw new IllegalArgumentException("E-mailadres bestaat al");
        }

        // Stap 3: de service maakt de Student zelf aan
        // Het wachtwoord wordt gehasht bewaard, nooit in platte tekst
        Student student = new Student(naam, email, hash(wachtwoord));

        // Stap 4: student bewaren en teruggeven
        studenten.add(student);
        return student;
    }

    /**
     * Zoekt de student met dit e-mailadres en controleert het wachtwoord.
     * Gooit een exception als er geen match is.
     */
    public Student aanmelden(String email, String wachtwoord) {
        // Stap 1: controleren of de velden ingevuld zijn
        if (isLeeg(email) || isLeeg(wachtwoord)) {
            throw new IllegalArgumentException("E-mail en wachtwoord zijn verplicht");
        }

        // Stap 2: student zoeken op e-mail
        Student student = zoekOpEmail(email);

        // Stap 3: controleren of de student bestaat en het gehashte wachtwoord klopt
        // Altijd dezelfde melding, zodat je niet verklapt of het e-mailadres bestaat
        if (student == null || !student.getWachtwoord().equals(hash(wachtwoord))) {
            throw new IllegalArgumentException("Ongeldige e-mail of wachtwoord");
        }

        // Stap 4: alles klopt, student teruggeven
        return student;
    }

    // Hulpmethode: zoekt een student op e-mail (hoofdletters maken niet uit)
    // Geeft null terug als er geen student gevonden is
    private Student zoekOpEmail(String email) {
        for (Student student : studenten) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null;
    }

    // Hulpmethode: controleert of een tekst null of leeg is
    private boolean isLeeg(String tekst) {
        return tekst == null || tekst.isBlank();
    }

    // Hulpmethode: zet een wachtwoord om naar een getal (als tekst)
    // Eenvoudige versie voor de oefening, niet veilig genoeg voor een echte applicatie
    private String hash(String wachtwoord) {
        return String.valueOf(wachtwoord.hashCode());
    }
}