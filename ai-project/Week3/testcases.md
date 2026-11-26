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