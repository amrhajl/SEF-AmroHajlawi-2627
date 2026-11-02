# AI-logboek: Week 2 (Maven-project en domeincode)

## Opdracht
Een Maven-project opzetten, AI het klassendiagram van vorige week laten omzetten in domeincode en de domeinklassen in een package `model` plaatsen.

## Gebruikte AI
Claude AI

## Prompt 1: domeincode genereren

Schrijf de domeincode in Java voor dit klassendiagram
Regels:
- 1 bestand per klasse, abstracte klasse en enum.
- Private velden met de juiste types, constructors en getters.
- Overerving met extends, abstracte klasse waar het diagram dat aangeeft.
- Multipliciteit 0..* of 1..* wordt een List, multipliciteit 1 of een gewoon veld.
- Compositie: het geheel maakt de delen zelf aan. Aggregatie: de delen worden meegegeven.
- Voortgang is een associatieklasse: laat ze verwijzen naar zowel Student als Opdracht.
- Implementeer de methodes met een simpele logische body en zet commentaar waar de logica onduidelijk is.
- Gebruik geen extra klasse buiten het domein.

[@startuml StudyBuddy
skinparam classAttributeIconSize 0

enum Status {
TE_DOEN
BEZIG
AFGEROND
}

enum DocumentType {
SAMENVATTING
DOCUMENT
}

class AccountService {
+registreer(naam: String, email: String, wachtwoord: String): Student
+aanmelden(email: String, wachtwoord: String): Student
}

class Student {
-id: int
-naam: String
-email: String
-wachtwoord: String
+voegVakToe(vak: Vak): void
+richtStudiegroepOp(naam: String): Studiegroep
+sluitAanBijGroep(groep: Studiegroep): void
+sluitAanBijSessie(sessie: Studiesessie): void
+bekijkVoortgang(opdracht: Opdracht): Status
}

class Vak {
-id: int
-naam: String
+getOpdrachten(): List<Opdracht>
}

abstract class Opdracht {
-id: int
-titel: String
-beschrijving: String
-datum: LocalDate
}

class Taak {
}

class Test {
-maxScore: double
}

class Voortgang {
-status: Status
-score: double
+updateStatus(status: Status): void
}

class Studiegroep {
-id: int
-naam: String
-beschrijving: String
+voegLidToe(student: Student): void
+verwijderLid(student: Student): void
+planSessie(datum: LocalDateTime, locatie: String, opdracht: Opdracht): Studiesessie
}

class Studiesessie {
-id: int
-datum: LocalDateTime
-locatie: String
+voegDeelnemerToe(student: Student): void
+deelDocument(doc: Document): void
}

class Document {
-id: int
-titel: String
-bestandspad: String
-type: DocumentType
-uploadDatum: LocalDateTime
}

' Overerving
Opdracht <|-- Taak
Opdracht <|-- Test

' Account
AccountService ..> Student : maakt aan >

' Vakken en opdrachten
Student "0..*" -- "0..*" Vak : volgt >
Vak "1" *-- "0..*" Opdracht : bevat >

' Voortgang als associatieklasse
Student "0..*" -- "0..*" Opdracht
(Student, Opdracht) .. Voortgang

' Studiegroepen
Studiegroep "0..*" o-- "1..*" Student : leden
Studiegroep "0..*" --> "1" Student : oprichter

' Studiesessies
Studiegroep "1" *-- "0..*" Studiesessie : plant >
Studiesessie "0..*" --> "1" Opdracht : voor >
Studiesessie "0..*" -- "0..*" Student : deelnemers
Studiesessie "1" *-- "0..*" Document : bevat >
Document "0..*" --> "1" Student : gedeeld door >

@enduml]

## Resultaat
Claude gaf 12 Java-bestanden, één per klasse:
- Enums: `Status`, `DocumentType`
- Abstracte klasse: `Opdracht` met subklassen `Taak` en `Test`
- Overige klassen: `AccountService`, `Student`, `Vak`, `Voortgang`, `Studiegroep`, `Studiesessie`, `Document`


Keuzes van de AI:
- Id's worden gegenereerd met een static teller per klasse.
- `Vak` kreeg extra methodes `voegTaakToe` en `voegTestToe`, omdat bij compositie het geheel de delen zelf aanmaakt.
- `Voortgang` verwijst naar `Student` en `Opdracht`, met standaardstatus `TE_DOEN`.
- Bij `Studiegroep` is de oprichter meteen het eerste lid (multipliciteit 1..*).

## Controle van de code

### Zijn er SOLID-schendingen?
Bijna niet. Elke klasse doet één ding, en `Taak` en `Test` kunnen overal gebruikt worden waar een `Opdracht` verwacht wordt. Twee kleine dingen:
- `AccountService` doet wat meer dan de andere klassen (registreren, aanmelden én het wachtwoord hashen). Voor dit project is dat prima.
- `Vak` heeft aparte methodes voor een taak en een test. Komt er een nieuw soort opdracht bij, dan moet `Vak` ook aangepast worden. Dat komt door de compositie in het diagram.

### Zitten de attributen en methodes in de juiste klasse?
Ja. Een groep beheert zijn eigen leden en sessies, een sessie zijn deelnemers en documenten, en een vak zijn opdrachten. Eén opmerking: `bekijkVoortgang` in `Student` maakt een `Voortgang` aan als die nog niet bestaat. Dat is een beetje vreemd voor een "bekijk"-methode, maar zo blijft alles in `Student`.

### Komt de code overeen met het klassendiagram?
Grotendeels wel. Dit is anders of extra:
- De AI vergat `Document`. Die klasse hebben we zelf gemaakt.
- Getters en id's (met een teller) staan niet in het diagram, maar waren nodig.
- `Vak` heeft extra methodes om taken en tests aan te maken, omdat bij compositie het geheel de delen zelf maakt.
- `Voortgang` heeft een extra methode `updateScore`, anders bleef de score altijd 0.0.
- `Studiegroep.verwijderLid` laat de oprichter en het laatste lid niet weg, vanwege `1..*`.
- `richtStudiegroepOp` krijgt geen beschrijving mee, dus de groep krijgt een lege beschrijving.
- `uploadDatum` van een `Document` wordt automatisch op het moment van aanmaken gezet.
- `deelDocument` krijgt een bestaand document mee, terwijl het diagram compositie toont. Dat is een tegenstrijdigheid in het diagram zelf. We hebben het niet aangepast.

### Wat hebben we aangepast, en waarom?
1. **`Document.java` gemaakt en een losse `ù-` bij `Taak` verwijderd.** Anders kon de code niet compileren.
2. **Alle klassen in `org.example.model` gezet.** Zo staat het domein apart van `Main`.
3. **`final` toegevoegd bij velden die nooit veranderen.** Build Project gaf er gele waarschuwingen voor. Bij `Voortgang` enkel bij `student` en `opdracht`, want `status` en `score` veranderen wel.
4. **`toString()` toegevoegd.** Zo zie je bij het testen gewone tekst in plaats van een rare code. Het wachtwoord staat er bewust niet in.
5. **`updateScore` toegevoegd.** Anders kon de score nooit veranderen.
6. **`AccountService` verbeterd.**
    - Twee studenten met hetzelfde e-mailadres kunnen niet meer, want dan klopte het aanmelden niet.
    - Exceptions in plaats van `null`, want een exception kan je niet vergeten te controleren.
    - Het wachtwoord wordt niet in platte tekst bewaard. We gebruikten `hashCode()` omdat dat simpel is. Het is niet veilig genoeg voor een echte app, maar het toont het idee.
### prompt voor aanpassingen:
Je bent een ervaren software-tester die code reviewt voor een studentenproject.

Context:
- Het is een domeinmodel in Java (Maven-project, package `org.example.model`) voor een studieapp met studenten, vakken, opdrachten, studiegroepen en studiesessies.
- De code komt uit een klassendiagram (UML) en wordt gemaakt voor een opleiding Toegepaste Informatica. Het niveau is eerstejaars/tweedejaars.

Opdracht:
Kijk de onderstaande klasse na en zeg wat je zou aanpassen als we deze applicatie echt voor school willen gebruiken, en hoe we dat op een veilige manier doen.

Controleer op:
1. Veiligheid en invoer: ongeldige of lege waarden, dubbele gegevens, hoe wachtwoorden bewaard worden.
2. Fouten: code die niet compileert, methodes of velden die nooit gebruikt of aangepast kunnen worden.
3. Codekwaliteit: velden die `final` kunnen zijn, ontbrekende `toString()`, onduidelijke namen of ontbrekend commentaar.
4. SOLID: heeft de klasse meer dan één verantwoordelijkheid, en zit alles in de juiste klasse?
5. Overeenstemming met het klassendiagram: pas niets aan dat afwijkt van het diagram, tenzij je uitlegt waarom.

Regels:
- Hou het simpel: geen advanced niveau, geen externe libraries.
- Leg bij elke aanpassing kort uit waarom je die doet.
- Zet de volledige aangepaste code in één codeblok, met extra commentaar bij de wijzigingen.


