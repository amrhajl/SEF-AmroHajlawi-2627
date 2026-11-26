package org.example.model;

import org.example.model.Student;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

public class AccountService {
    // Sleutel = e-mailadres in kleine letters, zo maken hoofdletters niets uit
    private final Map<String, Student> studenten = new HashMap<>();

    public Student registreer(String naam, String email, String wachtwoord) {
        if (isLeeg(naam) || isLeeg(email) || isLeeg(wachtwoord)) {
            throw new IllegalArgumentException("Naam, e-mail en wachtwoord zijn verplicht");
        }
        String sleutel = normaliseer(email);
        if (studenten.containsKey(sleutel)) {
            throw new IllegalArgumentException("E-mailadres bestaat al");
        }
        // Het wachtwoord wordt enkel gehasht bewaard
        Student student = new Student(naam.trim(), email.trim(), hash(wachtwoord));
        studenten.put(sleutel, student);
        return student;
    }

    public Student aanmelden(String email, String wachtwoord) {
        if (isLeeg(email) || isLeeg(wachtwoord)) {
            throw new IllegalArgumentException("E-mail en wachtwoord zijn verplicht");
        }
        Student student = studenten.get(normaliseer(email));
        // Altijd dezelfde melding, zodat je niet verklapt of het e-mailadres bestaat
        if (student == null || !student.getWachtwoord().equals(hash(wachtwoord))) {
            throw new IllegalArgumentException("Ongeldige e-mail of wachtwoord");
        }
        return student;
    }

    private String normaliseer(String email) {
        return email.trim().toLowerCase();
    }

    private boolean isLeeg(String tekst) {
        return tekst == null || tekst.isBlank();
    }

    // SHA-256 zonder salt: beter dan hashCode(), maar nog niet genoeg voor een echte app
    private String hash(String wachtwoord) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(wachtwoord.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is niet beschikbaar", e);
        }
    }
}