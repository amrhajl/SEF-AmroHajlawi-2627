package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudiesessieTest {

    private Studiesessie sessie;
    private Student student;
    private Document document;

    @BeforeEach
    void setUp() {
        Opdracht opdracht = new Taak("UML-diagram", "Klassendiagram tekenen", LocalDate.of(2026, 3, 20));
        sessie = new Studiesessie(LocalDateTime.of(2026, 3, 10, 14, 0), "Lokaal B201", opdracht);
        student = new Student("Lena Peeters", "lena@student.odisee.be", "geheim123");
        document = new Document("Samenvatting H1", "docs/h1.pdf", DocumentType.SAMENVATTING, student);
    }

    @Test
    void voegDeelnemerToe_nieuweStudent_staatInDeelnemers() {
        // Arrange (zie setUp)

        // Act
        sessie.voegDeelnemerToe(student);

        // Assert
        assertTrue(sessie.getDeelnemers().contains(student));
    }

    @Test
    void voegDeelnemerToe_bestaandeDeelnemer_voegtGeenDubbeleToe() {
        // Arrange
        sessie.voegDeelnemerToe(student);

        // Act
        sessie.voegDeelnemerToe(student);

        // Assert
        assertEquals(List.of(student), sessie.getDeelnemers());
    }

    @Test
    void voegDeelnemerToe_nullStudent_gooitException() {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> sessie.voegDeelnemerToe(null));
    }

    @Test
    void deelDocument_geldigDocument_staatInDocumenten() {
        // Arrange (zie setUp)

        // Act
        sessie.deelDocument(document);

        // Assert
        assertTrue(sessie.getDocumenten().contains(document));
    }

    @Test
    void deelDocument_alGedeeldDocument_voegtGeenDubbeleToe() {
        // Arrange
        sessie.deelDocument(document);

        // Act
        sessie.deelDocument(document);

        // Assert
        assertEquals(List.of(document), sessie.getDocumenten());
    }

    @Test
    void deelDocument_nullDocument_gooitException() {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> sessie.deelDocument(null));
    }
}
