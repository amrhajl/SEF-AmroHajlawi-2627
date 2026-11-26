package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoortgangTest {

    private Student student;
    private Opdracht opdracht;
    private Voortgang voortgang;

    @BeforeEach
    void setUp() {
        student = new Student("Lena Peeters", "lena@student.odisee.be", "geheim123");
        opdracht = new Taak("UML-diagram", "Klassendiagram tekenen", LocalDate.of(2026, 3, 20));
        voortgang = new Voortgang(student, opdracht);
    }

    @Test
    void constructor_nieuweVoortgang_heeftStatusTeDoenEnScoreNul() {
        // Arrange (zie setUp)

        // Act
        Voortgang nieuw = new Voortgang(student, opdracht);

        // Assert
        assertAll(
                () -> assertEquals(Status.TE_DOEN, nieuw.getStatus()),
                () -> assertEquals(0.0, nieuw.getScore())
        );
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"BEZIG", "AFGEROND"})
    void updateStatus_nieuweStatus_pastStatusAan(Status nieuweStatus) {
        // Arrange (zie setUp)

        // Act
        voortgang.updateStatus(nieuweStatus);

        // Assert
        assertEquals(nieuweStatus, voortgang.getStatus());
    }

    @Test
    void updateStatus_afgerondNaarTeDoen_zetStatusTerugOpTeDoen() {
        // Arrange
        voortgang.updateStatus(Status.AFGEROND);

        // Act
        voortgang.updateStatus(Status.TE_DOEN);

        // Assert
        assertEquals(Status.TE_DOEN, voortgang.getStatus());
    }

    @Test
    void updateStatus_null_gooitException() {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> voortgang.updateStatus(null));
    }

    @Test
    void updateStatus_null_behoudtVorigeStatus() {
        // Arrange
        voortgang.updateStatus(Status.BEZIG);

        // Act
        try {
            voortgang.updateStatus(null);
        } catch (IllegalArgumentException verwacht) {
            // De exception zelf wordt getest in updateStatus_null_gooitException
        }

        // Assert
        assertEquals(Status.BEZIG, voortgang.getStatus());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.1, 15.5})
    void updateScore_geldigeScore_pastScoreAan(double score) {
        // Arrange (zie setUp)

        // Act
        voortgang.updateScore(score);

        // Assert
        assertEquals(score, voortgang.getScore());
    }

    @Test
    void updateScore_netOnderNul_gooitException() {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> voortgang.updateScore(-0.1));
    }
}
