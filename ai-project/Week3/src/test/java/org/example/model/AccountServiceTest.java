package org.example.model;

import org.example.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    private static final String NAAM = "Lena Peeters";
    private static final String EMAIL = "lena@student.odisee.be";
    private static final String WACHTWOORD = "geheim123";

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
    }

    @Test
    void registreer_geldigeGegevens_geeftStudentMetNaamEnEmail() {
        // Arrange (zie setUp)

        // Act
        Student student = accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Assert
        assertAll(
                () -> assertEquals(NAAM, student.getNaam()),
                () -> assertEquals(EMAIL, student.getEmail())
        );
    }

    @Test
    void registreer_bestaandeEmail_gooitException() {
        // Arrange
        accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.registreer("Andere Lena", EMAIL, "anders456"));
    }

    @Test
    void registreer_zelfdeEmailAndereHoofdletters_gooitException() {
        // Arrange
        accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.registreer("Andere Lena", "LENA@Student.Odisee.BE", "anders456"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void registreer_ongeldigeNaam_gooitException(String naam) {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.registreer(naam, EMAIL, WACHTWOORD));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void registreer_ongeldigeEmail_gooitException(String email) {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.registreer(NAAM, email, WACHTWOORD));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void registreer_ongeldigWachtwoord_gooitException(String wachtwoord) {
        // Arrange (zie setUp)

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.registreer(NAAM, EMAIL, wachtwoord));
    }

    @Test
    void aanmelden_juisteGegevens_geeftJuisteStudent() {
        // Arrange
        Student geregistreerd = accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Act
        Student aangemeld = accountService.aanmelden(EMAIL, WACHTWOORD);

        // Assert
        assertSame(geregistreerd, aangemeld);
    }

    @Test
    void aanmelden_foutWachtwoord_gooitException() {
        // Arrange
        accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.aanmelden(EMAIL, "fout999"));
    }

    @Test
    void aanmelden_onbekendeEmail_gooitException() {
        // Arrange
        accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.aanmelden("onbekend@student.odisee.be", WACHTWOORD));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void aanmelden_ongeldigeEmail_gooitException(String email) {
        // Arrange
        accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.aanmelden(email, WACHTWOORD));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void aanmelden_ongeldigWachtwoord_gooitException(String wachtwoord) {
        // Arrange
        accountService.registreer(NAAM, EMAIL, WACHTWOORD);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountService.aanmelden(EMAIL, wachtwoord));
    }
}
