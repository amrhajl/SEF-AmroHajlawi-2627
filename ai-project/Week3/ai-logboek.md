De eerste prompt:


### Eerste prompt:
Ik werk aan een Java-studentenproject (Maven, package org.example.model) voor de
applicatie StudyBuddy. Ik wil unit tests laten schrijven met JUnit 6 voor de klassen
AccountService, Studiegroep, Studiesessie en Voortgang.

De domeinklassen staan in src/main/java/org/example/model, lees ze zelf. Zet de tests in
src/test/java/org/example/model, één testklasse per klasse. Voeg junit-jupiter en
junit-jupiter-params toe aan de pom.xml (Maven van IntelliJ).
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

OORDEEL:
Meestal testen de asserts gedrag (toestand of exception). Zwakke punten: sommige tests lezen interne lijsten via `getLeden()`, `getSessies()` en `getDeelnemers()`, en `planSessie_geldigeGegevens` heeft 4 asserts in één test, terwijl één test één idee hoort te hebben.

### Naamgeving en structuur (AAA)
FACTEN:
- Alle tests volgen methode_scenario_verwachting.
- Alle tests hebben de commentaren Arrange, Act en Assert.
- updateStatus_null_behoudtVorigeStatus gebruikt een try/catch in de Act.
- OORDEEL:
  De naamgeving en de AAA-structuur zijn goed en consequent. Het enige probleem is `updateStatus_null_behoudtVorigeStatus`: de try/catch in de Act maakt de test onduidelijk, want Act en Assert lopen door elkaar.
### Ontbrekende scenario's
FACTEN:
- Alle scenario's uit mijn tabel hebben een test.
- OORDEEL:
  Alle scenario's uit mijn tabel hebben een test, omdat ik de tabel meegaf in de prompt. Scenario's die niet in de tabel stonden, kon AI niet bedenken. Daarom voegde ik later het maximum aantal deelnemers toe via TDD.
  

### Mocks gebruikt waar een echt object volstond, of omgekeerd
- Er zijn geen mocks gebruikt. Dat klopt, want de klassen hebben geen externe afhankelijkheden
  (repository of mail-service). Alle tests gebruiken echte domeinobjecten.
- OORDEEL:
  Dat er geen mocks zijn, is correct. De klassen hebben geen externe afhankelijkheden, dus echte objecten volstaan en Mockito zou hier enkel ruis geven.
### Bugs fixen
- De meeste errors kwamen door dat null aanvaard was, dus we hebben een IllegalArgumentException gevoegd aan de klasse.

### prompt1: rood

Ik bouw met TDD een nieuwe regel in mijn Java-project StudyBuddy (JUnit 6, package org.example.model): een Studiesessie kan een maximum aantal deelnemers hebben. Hieronder staat mijn testcase-tabel en de huidige klasse Studiesessie.

Schrijf enkel de test voor de eerste rij van de tabel (sessie vol, vierde student, IllegalStateException). Schrijf nog GEEN productiecode.
- Naamgeving: methode_scenario_verwachting
- Structuur: Arrange, Act, Assert
- Geen echte datum of tijd, gebruik vaste waarden

Zeg erbij waarom de test nu moet falen (en dat het om de juiste reden is, niet door een compilatiefout van iets anders).

### prompt2: groen

De test faalt nu zoals verwacht. 
Schrijf net genoeg productiecode in Studiesessie om deze test te laten slagen, en niet meer. 
Voeg geen extra functionaliteit toe die niet door een test gevraagd wordt. Leg kort uit wat je veranderd hebt.

### Prompt 3: refactor

De test is groen. Verbeter de code zonder het gedrag te veranderen (bv. duidelijke namen, geen dubbele code,
een kleine hulpmethode). 
Alle tests moeten groen blijven. 
Leg per wijziging kort uit waarom.

Gebruikte ik de testcase-tabel in de prompt? Ja, de tabel stond in de eerste prompt (voor de tests) en de tabel voor story 9 hoorde bij de TDD-prompts.

Cyclus 2 en 3 deed ik stap per stap met hulp van Claude in de chat, zonder vaste prompt per stap.

### Verloop van de cycli
| Cyclus | Rood (falende test) | Groen (minimale code) | Refactor |
|---|---|---|---|
| 1 | `voegDeelnemerToe_sessieIsVol_gooitException` | Constructor met `maximum`, `IllegalStateException` bij vol | Constante `GEEN_MAXIMUM` i.p.v. `Integer.MAX_VALUE` |
| 2 | `voegDeelnemerToe_sessieVolEnStudentIsAlDeelnemer_voegtGeenDubbeleToe` faalde met `De sessie is vol` | Eerst `contains` controleren, dan `isVol` | Hulpmethode `isVol()` |
| 3 | `constructor_ongeldigMaximum_gooitException` (0 en -1) | `maximum <= 0` gooit `IllegalArgumentException` | Controle in `controleerMaximum()` |

De grenstest `voegDeelnemerToe_derdeStudentBijMaximumDrie_wordtToegelaten` was meteen groen, want de code kon dit al. Ik voegde hem toch toe om de grens (precies 3 van 3) vast te leggen.

Wat goed ging: de rode test faalde telkens om de juiste reden, en bij de refactor bleven alle tests groen.
Wat lastig was: een test belandde eerst per ongeluk in de productieklasse, en een test belandde binnen een andere test. Dat zag ik doordat het niet compileerde of niet draaide, en ik corrigeerde het.

## Mutatietesten

Ik brak de productiecode (`Studiesessie.java`) op 3 plaatsen en draaide telkens alle tests. Daarna draaide ik de mutatie terug met Git Rollback. Mutaties zijn niet gecommit.

| # | Mutatie | Resultaat | Test die rood werd |
|---|---|---|---|
| 1 | In `isVol()` `>=` vervangen door `>` | Gedood | `voegDeelnemerToe_sessieIsVol_gooitException` |
| 2 | Null-controle in `voegDeelnemerToe` in commentaar gezet | Gedood | `voegDeelnemerToe_nullStudent_gooitException` |
| 3 | Dubbel-controle (`contains` + `return`) in commentaar gezet | Gedood | `voegDeelnemerToe_bestaandeDeelnemer_voegtGeenDubbeleToe` en `voegDeelnemerToe_sessieVolEnStudentIsAlDeelnemer_voegtGeenDubbeleToe` |

## Aanpassingen aan de productiecode na de tests

Na de eerste fixes heb ik de productiecode nog verbeterd, samen met AI (Claude). Ik gaf aanwijzingen en bracht zelf een deel van de ideeën aan. AI schreef de code. Ik heb de aanpassingen niet alleen gemaakt.

| Klasse | Aanpassing |
|---|---|
| `AccountService` | E-mails opgeslagen in een `Map` met kleine letters als sleutel, `trim()` op naam en e-mail, wachtwoord gehasht met SHA-256 in plaats van `hashCode()` |
| `Studiegroep` | Constructor controleert `naam` en `oprichter`, `verwijderLid` vergelijkt met `equals`, `planSessie` gebruikt `isBlank()` en `trim()`, `getLeden()` en `getSessies()` geven een niet-aanpasbare lijst terug |
| `Studiesessie` | `getDeelnemers()` en `getDocumenten()` geven een niet-aanpasbare lijst terug. De constructor zonder maximum liet ik bewust ongewijzigd. |
| `Voortgang` | Constructor controleert `student` en `opdracht`, `updateScore` weigert ook `NaN` |


## Dekking

Ik heb de dekking niet gemeten met JaCoCo.