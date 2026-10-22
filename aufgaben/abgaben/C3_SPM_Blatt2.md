Bedingungen:
- Der Quellcode des Produkts wird mit ausgeliefert.
- Die Anwendung wird in Java programmiert.
- Die Analyse der Daten soll durch die Data-Mining Software Weka erfolgen.
- Es wird eine war-Datei ausgeliefert.
- Die Software wird in javadoc kommentiert.
- Alle sonstigen Dokumente liegen im PDF-Format vor.
- Das Front-End der Analysesoftware soll eine Webseite sein, die auf dem firmeninternen TomCat Webserver laufen soll.
- Die Webseite wird hauptsächlich durch die jeweilige Marktleitung (keine IT-Fachleute) benutzt werden.
- Die Anwendung muss durch ein Passwort geschützt werden, jedoch ist keine Benutzerverwaltung nötig.

Muss-Anforderungen an die Webseite:
- csv-Datei hochladen können, Fehler dürfen nicht zum Systemabsturz führen
- Graphische Aufbereitung der Daten
- Liste für allgemeine Marketingmaßnahmen, diese muss erweiterbar sein

Pflichtanforderungen:
1. #1 10.000 Datensätze in 1-2 Sekunden, maximal 5 Sekunden
2. #12 Darstellung von Waren, die zusammen gekauft wurden (Apriori)
3. #5 Fehler in der csv-Datei sollten zu einer Fehlermeldung führen
4. #6 Umsatzstärkster Einkaufstag, Umsatzstärkste Einkaufsuhrzeit
5. #7 Stärkster Einkaufstag, stärkste Einkaufsuhrzeit, nach Kundenhäufigkeit
6. #8 Umsatzstärkste Einkaufsuhrzeit an den einzelnen Tagen

optional:
1. #9 Top und Flop - Artikel, nach Umsatz
2. #10 Top und Flop - Artikel, nach Deckungsbeitrag

## Aufgabe 1: Anforderungsanalyse

Der folgende Katalog strukturiert die oben ausgewählten Anforderungen. Die Bezeichner F, Q und R stehen für Funktionsanforderungen, Qualitätsmerkmale und Randbedingungen. Die ursprünglichen Nummern aus der PDF bleiben zur Rückverfolgbarkeit angegeben.

### a) Funktionsanforderungen

| Bezeichner | Funktion                                       | Beschreibung                                                                                                                      | Priorität / Bezug | Offene Abstimmungspunkte                                                                                                                                     |
| ---------- | ---------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| F01        | CSV-Import                                     | Die Anwendung muss eine CSV-Datei im vereinbarten Format hochladen und die enthaltenen Einkaufsdaten zur Analyse einlesen können. | Muss              | -                                                                                                                                                            |
| F02        | Fehlermeldung bei CSV-Fehlern                  | Die Anwendung muss bei einer fehlerhaften CSV-Datei eine Fehlermeldung anzeigen.                                                  | Pflicht, #5       | • Fehlerfälle<br>• Umgang mit teilweise gültigen Dateien                                                                                                     |
| F03        | Passwortschutz                                 | Die Anwendung muss vor dem Zugriff auf Daten und Auswertungen die Eingabe eines gültigen Passworts verlangen.                     | Muss              | -                                                                                                                                                            |
| F04        | Grafische Darstellung                          | Die Anwendung muss Analyseergebnisse grafisch darstellen.                                                                         | Muss              | • Diagrammarten<br>• Zuordnung zu Analysen                                                                                                                   |
| F05        | Marketingmaßnahmen anzeigen                    | Die Anwendung muss eine Liste allgemeiner Marketingmaßnahmen anzeigen. **???**                                                    | Muss              |                                                                                                                                                              |
| F06        | Marketingmaßnahmen erweitern                   | Die Liste allgemeiner Marketingmaßnahmen muss um zusätzliche Einträge erweitert werden können.                                    | Muss              | • Art der Erweiterung<br>• Zuständige Personen                                                                                                               |
| F07        | Gemeinsam gekaufte Waren                       | Die Anwendung muss mithilfe des Apriori-Verfahrens Waren ermitteln und darstellen, die zusammen gekauft wurden.                   | Pflicht, #12      | • Apriori-Parameter<br>• Umfang der Ergebnisdarstellung                                                                                                      |
| F08        | Umsatzstärkster Einkaufstag                    | Die Anwendung muss den Einkaufstag mit dem höchsten Umsatz ermitteln und anzeigen.                                                | Pflicht, #6       | • Auswertungszeitraum<br>• Tag: Datum oder Wochentag<br>• Umgang mit Gleichständen                                                                           |
| F09        | Umsatzstärkste Einkaufsuhrzeit                 | Die Anwendung muss die Einkaufsuhrzeit mit dem höchsten Umsatz ermitteln und anzeigen.                                            | Pflicht, #6       | • Auswertungszeitraum<br>• Zeitintervalle<br>• Umgang mit Gleichständen                                                                                      |
| F10        | Stärkster Einkaufstag nach Kundenhäufigkeit    | Die Anwendung muss den Einkaufstag mit der höchsten Kundenhäufigkeit ermitteln und anzeigen.                                      | Pflicht, #7       | • Auswertungszeitraum<br>• Tag: Datum oder Wochentag<br>• Umgang mit Gleichständen<br>• Kundenhäufigkeit: unterschiedliche Kund\*innen oder Einkaufsvorgänge |
| F11        | Stärkste Einkaufsuhrzeit nach Kundenhäufigkeit | Die Anwendung muss die Einkaufsuhrzeit mit der höchsten Kundenhäufigkeit ermitteln und anzeigen.                                  | Pflicht, #7       | • Auswertungszeitraum<br>• Zeitintervalle<br>• Umgang mit Gleichständen<br>• Kundenhäufigkeit: unterschiedliche Kund\*innen oder Einkaufsvorgänge            |
| F12        | Umsatzstärkste Einkaufsuhrzeit je Tag          | Die Anwendung muss für jeden betrachteten Tag die Einkaufsuhrzeit mit dem höchsten Umsatz ermitteln und anzeigen.                 | Pflicht, #8       | • Auswertungszeitraum<br>• Zeitintervalle<br>• Tag: Datum oder Wochentag<br>• Umgang mit Gleichständen                                                       |
| F13        | Top- und Flop-Artikel nach Umsatz              | Die Anwendung soll die Artikel mit dem höchsten und niedrigsten Umsatz ermitteln und anzeigen.                                    | Optional, #9      | • Auswertungszeitraum<br>• Umgang mit Gleichständen<br>• Anzahl der Top-/Flop-Ergebnisse<br>• Artikel oder Warengruppen                                      |
| F14        | Top- und Flop-Artikel nach Deckungsbeitrag     | Die Anwendung soll die Artikel mit dem höchsten und niedrigsten Deckungsbeitrag ermitteln und anzeigen.                           | Optional, #10     | • Auswertungszeitraum<br>• Umgang mit Gleichständen<br>• Anzahl der Top-/Flop-Ergebnisse<br>• Artikel oder Warengruppen                                      |


### b) Qualitätsmerkmale und Quantifizierung

| Bezeichner | Qualitätsmerkmal           | Messbares Kriterium                                                                                                                                                                         | Nachweis / Status                                                                                                                                                                                                  | Offene Abstimmungspunkte                                                                                                                                            |
| ---------- | -------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Q01        | Analysezeit                | Die Analyse von 10.000 Datensätzen muss spätestens nach 5 Sekunden abgeschlossen sein; 1-2 Sekunden sind der Zielwert.                                                                      | Bezug: #1. Vorschlag: Messung vom Start der Analyse bei bereits eingelesenen Daten bis zur vollständig sichtbaren Ergebnisausgabe; jede vereinbarte Analyse muss in 10 Testläufen die 5-Sekunden-Grenze einhalten. | • Referenzhardware und Testdatei<br>• Analyseparameter und einbezogene Analysen<br>• Messbeginn und Messende<br>• Messvorschlag und Anzahl der Testläufe bestätigen |
| Q02        | Robustheit beim CSV-Import | Bei 100 % der vereinbarten fehlerhaften CSV-Testdateien darf die Anwendung nicht abstürzen. Sie muss eine Fehlermeldung anzeigen und anschließend einen erneuten Upload ermöglichen.        | Vorschlag zur Quantifizierung. Testfälle mindestens: falsches Trennzeichen, fehlende Pflichtspalten, ungültige Zahlen-/Datumswerte und leere Datei.                                                                | • CSV-Format<br>• Fehlerfallkatalog<br>• Quantifizierung bestätigen                                                                                                 |
| Q03        | Bedienbarkeit              | **???**                                                                                                                                                                                     | Vorschlag zur Quantifizierung für die Zielgruppe Marktleitung.                                                                                                                                                     | • Quantifizierung und Testablauf                                                                                                                                    |
| Q04        | Zugriffsschutz             | Bei 100 % der vereinbarten Zugriffstests ohne gültige Anmeldung dürfen weder Einkaufsdaten noch Analyseergebnisse ausgeliefert werden. Mit gültigem Passwort muss der Zugriff möglich sein. | Vorschlag zur Quantifizierung von F03. Tests umfassen den Aufruf der Oberfläche und direkte Aufrufe der datenliefernden Endpunkte.                                                                                 | • Testumfang<br>• Sitzungsverhalten<br>• Quantifizierung bestätigen                                                                                                 |


### c) Randbedingungen

| Bezeichner | Merkmal                       | Beschreibung                                                                                                                                                             | Offene Abstimmungspunkte           |
| ---------- | ----------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------- |
| R01        | Programmiersprache            | Die Anwendung muss in Java implementiert werden.                                                                              | • Java-Version                     |
| R02        | Analysewerkzeug               | Die Datenanalyse muss mit der Data-Mining-Software Weka erfolgen.                                                                           | • Weka-Version                     |
| R03        | Weboberfläche und Betrieb     | Das Front-End muss als Webseite auf dem firmeninternen Tomcat-Webserver betrieben werden können. | • Tomcat-Version<br>• Zielumgebung |
| R04        | Auslieferungsformat           | Die Anwendung muss als WAR-Datei ausgeliefert werden.                                                                    | -                                  |
| R05        | Quellcodeauslieferung         | Der Quellcode des Produkts muss mit ausgeliefert werden.                                                                                  | -                                  |
| R06        | Quellcodedokumentation        | Die Software muss mit Javadoc kommentiert werden. Vorschlag: alle öffentlichen Klassen und Methoden.       | • Umfang der Javadoc-Kommentierung |
| R07        | Dokumentformate               | Alle sonstigen Dokumente müssen im PDF-Format vorliegen.                                                                                  | -                                  |
| R08        | Eingabedaten                  | Die Einkaufsdaten müssen aus CSV-Dateien im festgelegten Format übernommen werden.          | -                                  |
| R09        | Umfang der Benutzerverwaltung | Eine Benutzerverwaltung ist nicht erforderlich und gehört nicht zum vereinbarten Funktionsumfang. Der Passwortschutz nach F03 bleibt erforderlich.                                  | -                                  |


### d) Eigenschaften gut formulierter Anforderungen

- **Eindeutig:** Die Anforderung lässt nur eine fachliche Interpretation zu; Begriffe wie „schnell“ oder „benutzerfreundlich“ werden konkretisiert.
- **Verständlich:** Auftraggebende und Entwicklungsteam verstehen die verwendeten Begriffe gleich.
- **Vollständig:** Auslöser, Eingaben, erwartetes Verhalten und relevante Fehlerfälle sind beschrieben.
- **Widerspruchsfrei:** Die Anforderung widerspricht weder anderen Anforderungen noch den Randbedingungen.
- **Notwendig:** Die Anforderung trägt zu einem vereinbarten Projektziel bei.
- **Realisierbar:** Sie lässt sich innerhalb der verfügbaren Zeit, Mittel und technischen Umgebung umsetzen.
- **Einzeln formuliert:** Jede Anforderung beschreibt einen separat prüfbaren Sachverhalt.
- **Prüfbar:** Ein Test oder eine Prüfung kann eindeutig feststellen, ob sie erfüllt ist; Qualitätsmerkmale besitzen messbare Grenzwerte und definierte Testbedingungen.
- **Identifizierbar und nachvollziehbar:** Ein stabiler Bezeichner wie F01, Q01 oder R01 ermöglicht die Zuordnung zu Quelle, Umsetzung und Test.
- **Priorisiert und abgestimmt:** Pflichtanforderungen und optionale Anforderungen sind gekennzeichnet und mit der beauftragenden Firma vereinbart.


