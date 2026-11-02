package org.example.model;

public enum DocumentType {
    SAMENVATTING,
    DOCUMENT;

    @Override
    public String toString() {
        // Geeft een leesbare tekst terug in plaats van hoofdletters
        if (this == SAMENVATTING) {
            return "Samenvatting";
        } else {
            return "Document";
        }
    }
}