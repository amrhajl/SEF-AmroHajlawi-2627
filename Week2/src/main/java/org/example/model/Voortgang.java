package org.example.model;

public class Voortgang {
    // Associatieklasse: verwijst naar zowel Student als Opdracht
    private final Student student;
    private final Opdracht opdracht;
    private Status status;
    private double score;

    public Voortgang(Student student, Opdracht opdracht) {
        this.student = student;
        this.opdracht = opdracht;
        this.status = Status.TE_DOEN;
        this.score = 0.0;
    }

    // Past de status aan (bv. van TE_DOEN naar bezig of afgewerkt)
    public void updateStatus(Status status) {
        this.status = status;
    }

    // Past de score aan
    public void updateScore(double score) {
        this.score = score;
    }

    public Student getStudent() { return student; }
    public Opdracht getOpdracht() { return opdracht; }
    public Status getStatus() { return status; }
    public double getScore() { return score; }

    @Override
    public String toString() {
        return student.getNaam() + " - " + opdracht.getTitel() + ": " + status + " (score: " + score + ")";
    }
}