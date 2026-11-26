package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private List<Student> studenten = new ArrayList<>();

    public Student registreer(String naam, String email, String wachtwoord) {
        // "maakt aan": de service maakt de Student zelf aan
        Student student = new Student(naam, email, wachtwoord);
        studenten.add(student);
        return student;
    }

    public Student aanmelden(String email, String wachtwoord) {
        for (Student student : studenten) {
            if (student.getEmail().equals(email) && student.getWachtwoord().equals(wachtwoord)) {
                return student;
            }
        }
        // Geen match: null teruggeven (alternatief: een exception gooien)
        return null;
    }
}