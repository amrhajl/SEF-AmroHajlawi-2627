# Samenwerkingsafspraken

**Team:** Amro Hajlawi en Ayoub Knidil

Vanaf week 4 commit niemand nog rechtstreeks op `main`. Elke wijziging komt binnen via een branch en een pull request (PR), wordt gereviewd door het andere teamlid en wordt pas gemerged als de pipeline groen is.

## 1. Branches

| Prefix | Voor | Voorbeeld |
|---|---|---|
| `feature/` | nieuwe functionaliteit | `feature/inschrijven-sessie` |
| `fix/` | een fout herstellen | `fix/voortgang-score` |
| `test/` | tests toevoegen of aanpassen | `test/studiegroep` |
| `ci/` | pipeline en workflows | `ci/github-actions` |
| `docs/` | documentatie en logboeken | `docs/logboek-week4` |

- Namen in kleine letters, woorden gescheiden door `-`.
- Eén branch per onderwerp. Na de merge wordt de branch verwijderd.
- Je vertrekt altijd van de nieuwste `main` (`git switch main` en `git pull`).

## 2. Merge-methode

We gebruiken **Create a merge commit**.

Waarom:
- Elke commit van een branch blijft zichtbaar in `main`. Dat is nodig voor de TDD-cycli van week 3 (`rood:`, `groen:`, `refactor:`) en voor de rode en daarna groene pipeline van week 4. Squash zou die stappen samenvoegen tot één commit.
- De merge-commit toont duidelijk wanneer een PR binnenkwam en van wie.
- Rebase herschrijft commits. Dat doen we enkel lokaal op een eigen branch die nog niet gepusht is.

## 3. Wat een PR minstens bevat

- **Titel:** kort en in de gebiedende wijs, bv. *Voeg CI-workflow toe*.
- **Wat:** welke wijzigingen.
- **Waarom:** welk probleem of welke user story.
- **Hoe getest:** welke tests, en het aantal `Tests run:` lokaal.
- **AI:** of AI gebruikt werd, en hoe de output nagekeken werd.
- Het andere teamlid als reviewer.

## 4. Review

De auteur merget nooit zijn eigen PR. De reviewer merget, na een **Approve** en een groene pipeline.

### Approve als alles hiervan klopt
- [ ] De pipeline is groen.
- [ ] Het aantal tests in de pipeline is gelijk aan lokaal, en niet lager dan op `main`.
- [ ] Nieuwe of gewijzigde code heeft tests: minstens één happy path en één randgeval per publieke methode.
- [ ] Testnamen volgen `methode_scenario_verwachting`, met een werkwoord in de verwachting.
- [ ] Elke test volgt Arrange, Act, Assert, met één Act en één idee per test.
- [ ] Tests zijn onafhankelijk en deterministisch: geen vaste volgorde en geen echte datum of tijd (vaste `Clock`).
- [ ] Mockito enkel voor afhankelijkheden (bv. een repository), nooit voor domeinklassen.
- [ ] Geen wachtwoorden, tokens of andere geheimen in de code.
- [ ] Geen `target/`, `.idea/` of andere gegenereerde bestanden in de PR.
- [ ] De beschrijving bevat wat, waarom, hoe getest en het AI-gebruik.

### Request changes als
- er één punt van de checklist niet klopt, of
- de code iets anders doet dan de user story vraagt, of
- je de code niet begrijpt zonder uitleg.

Schrijf bij Request changes altijd **wat** er niet klopt en **waarom**, bij de regel waarover het gaat.

## 5. Merge-conflicten

Na het oplossen van een conflict:
1. `grep -rn "<<<<<<<" .` toont niets meer.
2. Het project compileert.
3. `Tests run:` is gelijk aan vóór het conflict, plus de nieuwe tests.
