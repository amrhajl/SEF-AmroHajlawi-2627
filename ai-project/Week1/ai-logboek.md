#AI-logboek.md -Week1

# 1.Gebruikte prompt
Maak op basis van de onderstaande user stories een UML-klassendiagram als PlantUML-code
voor de applicatie StudyBuddy. Voeg klassen, attributen, methodes en relaties toe. Gebruik waar nodig aggregatie, overerving, compositie of associatie.

-Als student wil ik me registreren met mijn naam en e-mailadres, zodat ik StudyBuddy kan gebruiken.
-Als student wil ik me aanmelden met mijn e-mailadres en wachtwoord, zodat alleen ik toegang heb tot mijn profiel. 
-Als student wil ik een studiegroep oprichten, zodat ik met medestudenten kan samenwerken.
-Als student wil ik aansluiten bij een studiegroep, zodat ik samen met anderen kan studeren.
-Als student wil ik vakken toevoegen aan mijn profiel, zodat ik mijn studiewerk per vak kan organiseren.
-Als student wil ik per vak de taken en de testen zien, zodat ik weet wat ik moet doen. 
-Als student wil ik mijn voortgang van testen en taken zien, zodat ik weet hoever ik sta.
-Als groepslid wil ik een studiesesie plannen met een datum en locatie, zodat ik samen met andere studenten kan studeren.
-Als groepslid wil ik aansluiten bij een studiesessie, zodat anderen weten dat ik erbij ben. 
-Als groepslid wil ik samenvattingen en documenten delen bij een studiesessie, zodat wie afwezig was ook kan meevolgen.


# 2.Wat AI opleverde
De AI gaf ons een klassendiagram in PlantUml (zie orignieel.puml en origineel.png) met de klassen Student, Vak, Opdracht, Taak, Test, Voortang, Studiegroep, Studiesessie en Document, plus een enum Status.

- Overerving: Taak en Test erven van de abstracte klasse Opdracht
- Compositie: een Vak bevat opdrachten, een studiegroep plant sessies en een Studiesessie bevat documenten. 
- Aggregatie: een Studiegroep heeft leden, een Student volgt vakken en een Studiesessie heeft deelnemers
- Voortgang is een assiciatieklasse tussen Student en Opdracht
# 3.Ontbrekende klassen, attributen of methodes
- een studiesessie wordt gepland voor een taak of een toets, maar er was geen relatie tussen Studiesessie en Opdracht
- Onze user stories spreken over samenvattinge, maar die zaten er niet in. Document had alleen een type: String
- Studiegroep wist niet wie de oprichter was
- Er waren dubbele attributen: Opdracht had datum, maar Taak had ook deadline en Test had testdatum

# 4.Multipliciteit
- Studiegroep "1..*" o-- "1..*" Student klopte niet: dat
betekent dat elke student minstens in 1 groep moet zitten. Een student kan ook in geen enkele groep zitten, dus aan die kant moet het 0..* zijn.
- De andere multipliciteiten (bv. een Vak bevat 0..* opdrachten, een studiegroep plant 0..* sessies) kloppen wel

# 5.Accociatie, aggregatie, compositie, overerving
- Student - Vak was een aggregatie, maar een vak is geen onderdeel van een student. Een gewone associatie past beter.
- Studiesessie -Student (deelnemers) was ook een aggregatie.
Deelnemers zijn geen onderdeel van een sessie, dus ook hier een associatie.
- Voortgang stond er dubbel in: als twee losse associaties en als associatieklasse. 1 van de 2 is genoeg.
- Overerving van Taak en Test naar Opdracht en de composities (Vak - Opdracht, Studiegroep - Studiesesie, Studiesessie - Document) waren wel correct.

# 6.Sluit het aan bij de requirements
Grotendeels wel: registreren, aanmelden, groepen, vakken, toetsen/taken, voortgang, sessies en documenten zitten er allemaal in.
Maar: 
- registreer() en aanmelden() zaten in de klasse Student. Je kan niet registreren als je nog geen Student-object bent, dus dat hoort in een aparte klasse.
- bekijkVoortgang() gaf een double terug, terwijl getVoortgang() een Status teruggaf. Dat was consistent.
- Het verband tussen een sessie en de taak/toets waarover gestudeerd wordt (user story 8) ontbrak.

# 7.Aanpassingen en vervolgprompts
We hebben het originele diagram gecontroleerd op de punten hierboven (hierbij ook met AI fouten verlopen waar we twijfelden)
en daarna aangepast en verbeterd.
Resultaat: zie herwerkt.puml. Aanpassingen:
- Nieuwe klasse AccountService met registreer() en aanmelden(), weg van Student.
- Student - Vak is nu een gewone associatie. 
- Voortgang staat er nog maar 1 keer in, als associatieklasse tussen Student en Opdracht. 
-Dubbele datems verwijderd: alleen nog datum in Opdracht.
- bekijkVoortgang() geeft nu een Status terug.
- Een student zit nu in 0..* studiegroepen. 
- Relatie oprichter toegevoegd tussen Studiegroep en Student.
- Relatie Studiesessie -> Opdracht toegevoegd: een sesie is voor een taak of toets.
- Studiesessie - Student(deelnemers) is nu een gewone associatie.
Enum DocumentTtpe(SAMENVATTING, DOCUMENT) toegevoegd.

We hebben de herwerkte versie nagekeken en vergeleken met onze user stories voordat we ze overnamen. 