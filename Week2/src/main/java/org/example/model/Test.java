package org.example.model;

import java.time.LocalDate;

public class Test extends Opdracht {
    private final double maxScore;

    public Test(String titel, String beschrijving, LocalDate datum, double maxScore) {
        super(titel, beschrijving, datum);
        this.maxScore = maxScore;
    }

    public double getMaxScore() { return maxScore; }

    @Override
    public String toString() {
        // Gebruikt de toString van Opdracht en vult die aan met de maximumscore
        return super.toString() + ", max score: " + maxScore;
    }
}