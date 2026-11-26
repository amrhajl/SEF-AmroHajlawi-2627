package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudiegroepTest {

    private static final LocalDateTime SESSIE_DATUM = LocalDateTime.of(2026, 3, 10, 14, 0);
    private static final String LOCATIE = "Lokaal B201";

    private Student oprichter;
    private Student student;
    private Studiegroep groep;
    private Opdracht opdracht;

    @BeforeEach
    void setUp() {
        oprichter = new Student("Lena Peeters", "lena@student.odisee.be", "geheim123");
        student = new Student("Bram Janssens", "bram@student.odisee.be", "wachtwoord1");
        groep = new Studiegroep("SEF-groep", "Samen leren voor SEF", oprichter);
        opdracht = new Taak("UML-diagram", "Klassendiagram tekenen", LocalDate.of(2026, 3, 20));
    }

    @Test
    void constructor_nieuweGroep_bevatOprichterAlsEnigLid() {
        // Arrange
        Student nieuweOprichter = new Student("Sara Claes", "sara@student.odisee.be", "pw123");

        // Act
        Studiegroep nieuweGroep = new Studiegroep("Java-groep", "Oefenen", nieuweOprichter);

        // Assert
        assertEquals(List.of(nieuweOprichter), nieuweGroep.getLeden());
    }

    @Test
    void voegLidToe_nieuweStudent_staatInLedenlijst() {
        // Arrange (zie setUp)

        // Act
        groep.voegLidToe(student);

        // Assert
        assertTrue(groep.getLeden().contains(student));
    }

    @Test
    void voegLidToe_bestaandLid_behoudtAantalLeden() {
        // Arrange
        groep.voegLidToe(student);

        // Act
        groep.voegLidToe(student);

        // Assert
        assertEquals(2, groep.getLeden().size());
    }

    @Test
    void voegLidToe_nullStudent_gooitException() {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> groep.voegLidToe(null));
    }

    @Test
    void verwijderLid_gewoonLid_verwijdertLid() {
        // Arrange
        groep.voegLidToe(student);

        // Act
        groep.verwijderLid(student);

        // Assert
        assertFalse(groep.getLeden().contains(student));
    }

    @Test
    void verwijderLid_oprichter_behoudtOprichter() {
        // Arrange
        groep.voegLidToe(student);

        // Act
        groep.verwijderLid(oprichter);

        // Assert
        assertTrue(groep.getLeden().contains(oprichter));
    }

    @Test
    void verwijderLid_geenLid_wijzigtLedenNiet() {
        // Arrange
        groep.voegLidToe(student);
        Student buitenstaander = new Student("Tom Maes", "tom@student.odisee.be", "pw123");

        // Act
        groep.verwijderLid(buitenstaander);

        // Assert
        assertEquals(List.of(oprichter, student), groep.getLeden());
    }

    @Test
    void planSessie_geldigeGegevens_geeftSessieEnBewaartZe() {
        // Arrange (zie setUp)

        // Act
        Studiesessie sessie = groep.planSessie(SESSIE_DATUM, LOCATIE, opdracht);

        // Assert
        assertAll(
                () -> assertEquals(SESSIE_DATUM, sessie.getDatum()),
                () -> assertEquals(LOCATIE, sessie.getLocatie()),
                () -> assertSame(opdracht, sessie.getOpdracht()),
                () -> assertEquals(List.of(sessie), groep.getSessies())
        );
    }

    @Test
    void planSessie_datumNull_gooitException() {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> groep.planSessie(null, LOCATIE, opdracht));
    }

    @Test
    void planSessie_opdrachtNull_gooitException() {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> groep.planSessie(SESSIE_DATUM, LOCATIE, null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void planSessie_ongeldigeLocatie_gooitException(String locatie) {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> groep.planSessie(SESSIE_DATUM, locatie, opdracht));
    }
}
