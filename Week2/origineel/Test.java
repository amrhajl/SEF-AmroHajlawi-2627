package org.example.model;

import java.time.LocalDate;

public class Test extends Opdracht {
    private double maxScore;

    public Test(String titel, String beschrijving, LocalDate datum, double maxScore) {
        super(titel, beschrijving, datum);
        this.maxScore = maxScore;
    }

    public double getMaxScore() { return maxScore; }
}