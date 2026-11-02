package org.example.model;

public class Voortgang {
    // Associatieklasse: verwijst naar zowel Student als Opdracht
    private Student student;
    private Opdracht opdracht;
    private Status status;
    private double score;

    public Voortgang(Student student, Opdracht opdracht) {
        this.student = student;
        this.opdracht = opdracht;
        this.status = Status.TE_DOEN;
        this.score = 0.0;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public Student getStudent() { return student; }
    public Opdracht getOpdracht() { return opdracht; }
    public Status getStatus() { return status; }
    public double getScore() { return score; }
}