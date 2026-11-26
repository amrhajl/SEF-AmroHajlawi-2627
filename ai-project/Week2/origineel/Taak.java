package org.example.model;

import java.time.LocalDate;

public class Taak extends Opdracht {
    public Taak(String titel, String beschrijving, LocalDate datum) {
        super(titel, beschrijving, datum);
    }
}
