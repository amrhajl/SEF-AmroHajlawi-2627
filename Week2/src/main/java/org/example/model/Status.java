package org.example.model;

public enum Status {
    TE_DOEN,
    BEZIG,
    AFGEROND;

    @Override
    public String toString() {
        // Geeft een leesbare tekst terug in plaats van de naam met underscore
        if (this == TE_DOEN) {
            return "Te doen";
        } else if (this == BEZIG) {
            return "Bezig";
        } else {
            return "Afgerond";
        }
    }
}