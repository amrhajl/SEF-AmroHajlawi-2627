# Testcases StudyBuddy

## AccountService (stories 1 en 2)
| Story | Methode | Scenario | Verwacht gedrag |
|---|---|---|---|
| 1 | registreer | geldige naam, e-mail, wachtwoord | geeft een Student met die naam en e-mail |
| 1 | registreer | e-mail bestaat al | IllegalArgumentException |
| 1 | registreer | zelfde e-mail met andere hoofdletters | IllegalArgumentException |
| 1 | registreer | naam `null`, `""` of `" "` | IllegalArgumentException |
| 1 | registreer | e-mail `null`, `""` of `" "` | IllegalArgumentException |
| 1 | registreer | wachtwoord `null`, `""` of `" "` | IllegalArgumentException |
| 2 | aanmelden | juiste e-mail en wachtwoord | geeft de juiste Student |
| 2 | aanmelden | juiste e-mail, fout wachtwoord | IllegalArgumentException |
| 2 | aanmelden | onbekend e-mailadres | IllegalArgumentException |
| 2 | aanmelden | e-mail of wachtwoord `null` of `""` | IllegalArgumentException |

## Studiegroep (stories 3, 4 en 8)
| Story | Methode | Scenario | Verwacht gedrag |
|---|---|---|---|
| 3 | constructor | groep aangemaakt | oprichter is meteen lid (1 lid) |
| 4 | voegLidToe | nieuwe student | zit in de ledenlijst |
| 4 | voegLidToe | student is al lid | geen dubbele, aantal leden blijft gelijk |
| 4 | voegLidToe | `null` | IllegalArgumentException |
| geen story (klassendiagram) | verwijderLid | gewoon lid | lid is weg |
| geen story (klassendiagram) | verwijderLid | oprichter | blijft lid |
| geen story (klassendiagram) | verwijderLid | student die geen lid is | niets verandert |
| 8 | planSessie | geldige datum, locatie, opdracht | sessie teruggegeven en in de lijst van sessies |
| 8 | planSessie | datum `null` | IllegalArgumentException |
| 8 | planSessie | opdracht `null` | IllegalArgumentException |
| 8 | planSessie | locatie `null` of `""` | IllegalArgumentException |

## Studiesessie (stories 9 en 10)
| Story | Methode | Scenario | Verwacht gedrag |
|---|---|---|---|
| 9 | voegDeelnemerToe | nieuwe student | zit in de deelnemers |
| 9 | voegDeelnemerToe | student is al deelnemer | geen dubbele |
| 9 | voegDeelnemerToe | `null` | IllegalArgumentException |
| 10 | deelDocument | geldig document | zit in de lijst van documenten |
| 10 | deelDocument | document al gedeeld | geen dubbele |
| 10 | deelDocument | `null` | IllegalArgumentException |

## Voortgang (story 7)
| Story | Methode | Scenario | Verwacht gedrag |
|---|---|---|---|
| 7 | constructor | nieuwe voortgang | status TE_DOEN, score 0.0 |
| 7 | updateStatus | BEZIG of AFGEROND | status aangepast |
| 7 | updateStatus | AFGEROND naar TE_DOEN | status = TE_DOEN (taak heropenen mag) |
| 7 | updateStatus | `null` | IllegalArgumentException, status blijft |
| 7 | updateScore | 0.0 (op de grens) en 15.5 | score aangepast |
| 7 | updateScore | -0.1 (net onder de grens) | IllegalArgumentException |

## Nieuwe feature (TDD): maximum aantal deelnemers per studiesessie

User story 9: *Als groepslid wil ik aansluiten bij een studiesessie, zodat anderen weten dat ik erbij ben.*
Het maximum is geen aparte user story, maar een extra regel bij story 9. In de tests is het maximum 3.
De bestaande constructor zonder maximum blijft bestaan (geen maximum).

| Story | Klasse | Methode | Scenario | Verwacht gedrag |
|---|---|---|---|---|
| 9 (extra regel) | Studiesessie | voegDeelnemerToe | 1 deelnemer, maximum 3 (net onder het maximum na toevoegen) | student wordt toegevoegd (2 deelnemers) |
| 9 (extra regel) | Studiesessie | voegDeelnemerToe | 2 deelnemers, maximum 3 (op het maximum na toevoegen) | student wordt toegevoegd (3 deelnemers) |
| 9 (extra regel) | Studiesessie | voegDeelnemerToe | 3 deelnemers, maximum 3 (net boven het maximum) | IllegalStateException, 3 deelnemers blijven |
| 9 (extra regel) | Studiesessie | voegDeelnemerToe | sessie vol, student is al deelnemer | geen exception, geen dubbele (3 deelnemers) |
| 9 (extra regel) | Studiesessie | constructor met maximum | maximum 0 | IllegalArgumentException |
| 9 (extra regel) | Studiesessie | constructor met maximum | maximum -1 | IllegalArgumentException |