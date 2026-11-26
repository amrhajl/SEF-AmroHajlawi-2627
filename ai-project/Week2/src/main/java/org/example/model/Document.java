package org.example.model;

import java.time.LocalDateTime;

public class Document {
    // Teller die telkens met 1 omhoog gaat zodat elk document een uniek id krijgt
    private static int volgendeId = 1;

    private final int id;
    private final String titel;
    private final String bestandspad;
    private final DocumentType type;
    private final LocalDateTime uploadDatum;
    private final Student gedeeldDoor;

    public Document(String titel, String bestandspad, DocumentType type, Student gedeeldDoor) {
        this.id = volgendeId;
        volgendeId++;
        this.titel = titel;
        this.bestandspad = bestandspad;
        this.type = type;
        this.uploadDatum = LocalDateTime.now(); // moment van aanmaken
        this.gedeeldDoor = gedeeldDoor;
    }

    public int getId() { return id; }
    public String getTitel() { return titel; }
    public String getBestandspad() { return bestandspad; }
    public DocumentType getType() { return type; }
    public LocalDateTime getUploadDatum() { return uploadDatum; }
    public Student getGedeeldDoor() { return gedeeldDoor; }

    @Override
    public String toString() {
        return "Document " + id + ": " + titel + " (" + type + ")";
    }
}