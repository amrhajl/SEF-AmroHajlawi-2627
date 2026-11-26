De eerste prompt:


### Eerste prompt:
Ik werk aan een Java-studentenproject (Maven, package org.example.model) voor de
applicatie StudyBuddy. Ik wil unit tests laten schrijven met JUnit 6 voor de klassen
AccountService, Studiegroep, Studiesessie en Voortgang.

De domeinklassen staan in src/main/java/org/example/model, lees ze zelf. Zet de tests in
src/test/java/org/example/model, één testklasse per klasse. Voeg junit-jupiter en
junit-jupiter-params toe aan de pom.xml (JDK 27, Maven van IntelliJ).
Pas de productiecode NIET aan.

Belangrijk: ik heb zelf een testcase-tabel gemaakt op basis van mijn user stories. Die
tabel bepaalt wat het juiste gedrag is. Test dus wat de code zou moeten doen volgens de
tabel, niet wat ze nu toevallig doet. Als de code in strijd is met de tabel, laat de test
dan falen en zeg me dat erbij.

Afspraken voor de tests:
- Naamgeving: methode_scenario_verwachting, met een werkwoord in de verwachting
  (bv. voegLidToe_nullStudent_gooitException).
- Structuur: Arrange, Act, Assert (met de commentaren // Arrange, // Act, // Assert).
  Eén Act en één idee per test. Gebruik @BeforeEach voor gedeelde voorbereiding.
- Test het gedrag (returnwaarde, toestand of exception), niet de interne werking.
- Tests zijn onafhankelijk en deterministisch: geen vaste volgorde, geen gedeelde
  toestand en geen afhankelijkheid van de echte datum of tijd. Gebruik vaste datums, en
  een vaste Clock waar dat nodig is. Test niets op de automatische id (static teller).
- Gebruik Mockito alleen voor afhankelijkheden, nooit voor domeinklassen. Is er geen
  afhankelijkheid, gebruik dan geen mocks.
- Gebruik @ParameterizedTest waar meerdere cases dezelfde verwachting hebben
  (bv. null, "" en " ").
- Elke publieke methode krijgt minstens één test voor het happy path en één voor een
  randgeval (lege of ongeldige invoer, of een grenswaarde: net onder, op en net boven
  de grens).
- Importeer org.junit.jupiter.api.Test expliciet (de domeinklasse Test botst anders).
- Geef één testklasse per klasse, elk in een apart bestand, met de juiste imports en package.

Testcase-tabel:

AccountService (stories 1 en 2)
| Methode | Scenario | Verwacht gedrag |
|---|---|---|
| registreer | geldige naam, e-mail, wachtwoord | geeft een Student met die naam en e-mail |
| registreer | e-mail bestaat al | IllegalArgumentException |
| registreer | zelfde e-mail met andere hoofdletters | IllegalArgumentException |
| registreer | naam null, "" of " " | IllegalArgumentException |
| registreer | e-mail null, "" of " " | IllegalArgumentException |
| registreer | wachtwoord null, "" of " " | IllegalArgumentException |
| aanmelden | juiste e-mail en wachtwoord | geeft de juiste Student |
| aanmelden | juiste e-mail, fout wachtwoord | IllegalArgumentException |
| aanmelden | onbekend e-mailadres | IllegalArgumentException |
| aanmelden | e-mail of wachtwoord null of "" | IllegalArgumentException |

Studiegroep (stories 3, 4 en 8)
| Methode | Scenario | Verwacht gedrag |
|---|---|---|
| constructor | groep aangemaakt | oprichter is meteen lid (1 lid) |
| voegLidToe | nieuwe student | zit in de ledenlijst |
| voegLidToe | student is al lid | geen dubbele, aantal leden blijft gelijk |
| voegLidToe | null | IllegalArgumentException |
| verwijderLid | gewoon lid | lid is weg |
| verwijderLid | oprichter | blijft lid |
| verwijderLid | student die geen lid is | niets verandert |
| planSessie | geldige datum, locatie, opdracht | sessie teruggegeven en in de lijst van sessies |
| planSessie | datum null | IllegalArgumentException |
| planSessie | opdracht null | IllegalArgumentException |
| planSessie | locatie null of "" | IllegalArgumentException |

Studiesessie (stories 9 en 10)
| Methode | Scenario | Verwacht gedrag |
|---|---|---|
| voegDeelnemerToe | nieuwe student | zit in de deelnemers |
| voegDeelnemerToe | student is al deelnemer | geen dubbele |
| voegDeelnemerToe | null | IllegalArgumentException |
| deelDocument | geldig document | zit in de lijst van documenten |
| deelDocument | document al gedeeld | geen dubbele |
| deelDocument | null | IllegalArgumentException |

Voortgang (story 7)
| Methode | Scenario | Verwacht gedrag |
|---|---|---|
| constructor | nieuwe voortgang | status TE_DOEN, score 0.0 |
| updateStatus | BEZIG of AFGEROND | status aangepast |
| updateStatus | AFGEROND naar TE_DOEN | status = TE_DOEN |
| updateStatus | null | IllegalArgumentException, status blijft |
| updateScore | 0.0 (op de grens) en 15.5 | score aangepast |
| updateScore | -0.1 (net onder de grens) | IllegalArgumentException |

Geef me ook kort aan:
- hoeveel tests geslaagd en gefaald zijn, en per falende test de reden
  (bug in de code of fout in de test);
- welke scenario's uit de tabel je niet kon testen en waarom;
- waar de code afwijkt van de tabel;
- welke aannames je maakte waar de tabel niets over zegt.


### Wat AI opleverde
- 4 testklassen: AccountServiceTest, StudiegroepTest, StudiesessieTest, VoortgangTest.
- 47 tests: 36 geslaagd, 11 gefaald (zie bugs hierboven).
- Mockito werd niet gebruikt.
- Originele versie bewaard in map originele-tests (als .java.txt).

### Testen asserts het gedrag of de implementatie?
FACTEN:
- De meeste asserts controleren toestand of exception (assertThrows, assertEquals).
- Sommige tests lezen interne lijsten via getLeden(), getSessies(), getDeelnemers().
- planSessie_geldigeGegevens heeft 4 asserts in één test (assertAll).
  

### Naamgeving en structuur (AAA)
FACTEN:
- Alle tests volgen methode_scenario_verwachting.
- Alle tests hebben de commentaren Arrange, Act en Assert.
- updateStatus_null_behoudtVorigeStatus gebruikt een try/catch in de Act.
 
### Ontbrekende scenario's
FACTEN:
- Alle scenario's uit mijn tabel hebben een test.
  

### Mocks gebruikt waar een echt object volstond, of omgekeerd
- Er zijn geen mocks gebruikt. Dat klopt, want de klassen hebben geen externe afhankelijkheden
  (repository of mail-service). Alle tests gebruiken echte domeinobjecten.

### Bugs fixen
- De meeste errors kwamen door dat null aanvaard was, dus we hebben een IllegalArgumentException gevoegd aan de klasse.