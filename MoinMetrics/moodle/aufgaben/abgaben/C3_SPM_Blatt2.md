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

| Bezeichner | Funktion | Beschreibung | Priorität / Bezug |
| ---------- | ---------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ----------------- |
| F01 | CSV-Import | Die Anwendung muss eine CSV-Datei im vereinbarten Format hochladen und die enthaltenen Einkaufsdaten zur Analyse einlesen können. | Muss |
| F02 | Fehlermeldung bei CSV-Fehlern | Die Anwendung muss bei einer fehlerhaften CSV-Datei eine Fehlermeldung anzeigen. | Pflicht, #5 |
| F03 | Passwortschutz | Die Anwendung muss vor dem Zugriff auf Daten und Auswertungen die Eingabe eines gültigen Passworts verlangen. | Muss |
| F04 | Grafische Darstellung | Die Anwendung muss Analyseergebnisse grafisch darstellen. | Muss |
| F05 | Marketingmaßnahmen anzeigen | Die Anwendung muss eine Liste allgemeiner Marketingmaßnahmen anzeigen. **???** | Muss |
| F06 | Marketingmaßnahmen erweitern | Die Liste allgemeiner Marketingmaßnahmen muss um zusätzliche Einträge erweitert werden können. | Muss |
| F07 | Gemeinsam gekaufte Waren | Die Anwendung muss mithilfe des Apriori-Verfahrens Waren ermitteln und darstellen, die zusammen gekauft wurden. | Pflicht, #12 |
| F08 | Umsatzstärkster Einkaufstag | Die Anwendung muss den Einkaufstag mit dem höchsten Umsatz ermitteln und anzeigen. | Pflicht, #6 |
| F09 | Umsatzstärkste Einkaufsuhrzeit | Die Anwendung muss die Einkaufsuhrzeit mit dem höchsten Umsatz ermitteln und anzeigen. | Pflicht, #6 |
| F10 | Stärkster Einkaufstag nach Kundenhäufigkeit | Die Anwendung muss den Einkaufstag mit der höchsten Kundenhäufigkeit ermitteln und anzeigen. | Pflicht, #7 |
| F11 | Stärkste Einkaufsuhrzeit nach Kundenhäufigkeit | Die Anwendung muss die Einkaufsuhrzeit mit der höchsten Kundenhäufigkeit ermitteln und anzeigen. | Pflicht, #7 |
| F12 | Umsatzstärkste Einkaufsuhrzeit je Tag | Die Anwendung muss für jeden betrachteten Tag die Einkaufsuhrzeit mit dem höchsten Umsatz ermitteln und anzeigen. | Pflicht, #8 |
| F13 | Top- und Flop-Artikel nach Umsatz | Die Anwendung soll die Artikel mit dem höchsten und niedrigsten Umsatz ermitteln und anzeigen. | Optional, #9 |
| F14 | Top- und Flop-Artikel nach Deckungsbeitrag | Die Anwendung soll die Artikel mit dem höchsten und niedrigsten Deckungsbeitrag ermitteln und anzeigen. | Optional, #10 |


### b) Qualitätsmerkmale und Quantifizierung

| Bezeichner | Qualitätsmerkmal | Messbares Kriterium | Nachweis / Status |
| ---------- | -------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Q01 | Analysezeit | Die Analyse von 10.000 Datensätzen muss spätestens nach 5 Sekunden abgeschlossen sein; 1-2 Sekunden sind der Zielwert. | Bezug: #1. Vorschlag: Messung vom Start der Analyse bei bereits eingelesenen Daten bis zur vollständig sichtbaren Ergebnisausgabe; jede vereinbarte Analyse muss in 10 Testläufen die 5-Sekunden-Grenze einhalten. |
| Q02 | Robustheit beim CSV-Import | Bei 100 % der vereinbarten fehlerhaften CSV-Testdateien darf die Anwendung nicht abstürzen. Sie muss eine Fehlermeldung anzeigen und anschließend einen erneuten Upload ermöglichen. | Vorschlag zur Quantifizierung. Testfälle mindestens: falsches Trennzeichen, fehlende Pflichtspalten, ungültige Zahlen-/Datumswerte und leere Datei. |
| Q03 | Bedienbarkeit | **???** | Vorschlag zur Quantifizierung für die Zielgruppe Marktleitung. |
| Q04 | Zugriffsschutz | Bei 100 % der vereinbarten Zugriffstests ohne gültige Anmeldung dürfen weder Einkaufsdaten noch Analyseergebnisse ausgeliefert werden. Mit gültigem Passwort muss der Zugriff möglich sein. | Vorschlag zur Quantifizierung von F03. Tests umfassen den Aufruf der Oberfläche und direkte Aufrufe der datenliefernden Endpunkte. |


### c) Randbedingungen

| Bezeichner | Merkmal | Beschreibung |
| ---------- | ----------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| R01 | Programmiersprache | Die Anwendung muss in Java implementiert werden. |
| R02 | Analysewerkzeug | Die Datenanalyse muss mit der Data-Mining-Software Weka erfolgen. |
| R03 | Weboberfläche und Betrieb | Das Front-End muss als Webseite auf dem firmeninternen Tomcat-Webserver betrieben werden können. |
| R04 | Auslieferungsformat | Die Anwendung muss als WAR-Datei ausgeliefert werden. |
| R05 | Quellcodeauslieferung | Der Quellcode des Produkts muss mit ausgeliefert werden. |
| R06 | Quellcodedokumentation | Die Software muss mit Javadoc kommentiert werden. Vorschlag: alle öffentlichen Klassen und Methoden. |
| R07 | Dokumentformate | Alle sonstigen Dokumente müssen im PDF-Format vorliegen. |
| R08 | Eingabedaten | Die Einkaufsdaten müssen aus CSV-Dateien im festgelegten Format übernommen werden. |
| R09 | Umfang der Benutzerverwaltung | Eine Benutzerverwaltung ist nicht erforderlich und gehört nicht zum vereinbarten Funktionsumfang. Der Passwortschutz nach F03 bleibt erforderlich. |


### d) Eigenschaften gut formulierter Anforderungen

Eine Anforderungsspezifikation soll folgende Eigenschaften erfüllen:

- **Eindeutig:** Die sprachliche Formulierung lässt nur eine Interpretation zu. Unklare Begriffe wie „gut“ oder „schnell“ werden konkretisiert.
- **Vollständig:** Die Spezifikation enthält alle erforderlichen Anforderungen und alle Bestandteile, die der zugrunde gelegte Standard verlangt.
- **Überprüfbar:** Für jede Forderung lässt sich durch einen Test oder eine Prüfung feststellen, ob sie erfüllt ist. Qualitätsmerkmale erhalten dazu messbare Kriterien und definierte Testbedingungen.
- **Konsistent:** Begriffe werden einheitlich verwendet; Synonyme für denselben Sachverhalt werden vermieden. Die Anforderungen stimmen mit den geltenden Standards überein und enthalten keine logischen, zeitlichen oder ursächlichen Widersprüche.
- **Änderbar:** Eine klare Gliederung, ein Inhaltsverzeichnis, ein Index und Querverweise erleichtern Änderungen. Redundante Beschreibungen werden vermieden, damit eine Änderung nicht an mehreren Stellen nachgeführt werden muss.
- **Verfolgbar:** Entscheidungen und ihre Begründungen sind nachvollziehbar dokumentiert. Eindeutige Bezeichner und Verweise ermöglichen die Zuordnung der Anforderungen zu ihren Quellen, ihrer Umsetzung und ihren Tests.

## Offene Abstimmungspunkte

### Funktionsanforderungen

- **F02:**
  - Fehlerfälle
  - Umgang mit teilweise gültigen Dateien
- **F04:**
  - Diagrammarten
  - Zuordnung zu Analysen
- **F06:**
  - Art der Erweiterung
  - Zuständige Personen
- **F07:**
  - Apriori-Parameter
  - Umfang der Ergebnisdarstellung
- **F08:**
  - Auswertungszeitraum
  - Tag: Datum oder Wochentag
  - Umgang mit Gleichständen
- **F09:**
  - Auswertungszeitraum
  - Zeitintervalle
  - Umgang mit Gleichständen
- **F10:**
  - Auswertungszeitraum
  - Tag: Datum oder Wochentag
  - Umgang mit Gleichständen
  - Kundenhäufigkeit: unterschiedliche Kund\*innen oder Einkaufsvorgänge
- **F11:**
  - Auswertungszeitraum
  - Zeitintervalle
  - Umgang mit Gleichständen
  - Kundenhäufigkeit: unterschiedliche Kund\*innen oder Einkaufsvorgänge
- **F12:**
  - Auswertungszeitraum
  - Zeitintervalle
  - Tag: Datum oder Wochentag
  - Umgang mit Gleichständen
- **F13:**
  - Auswertungszeitraum
  - Umgang mit Gleichständen
  - Anzahl der Top-/Flop-Ergebnisse
  - Artikel oder Warengruppen
- **F14:**
  - Auswertungszeitraum
  - Umgang mit Gleichständen
  - Anzahl der Top-/Flop-Ergebnisse
  - Artikel oder Warengruppen

### Qualitätsmerkmale

- **Q01:**
  - Referenzhardware und Testdatei
  - Analyseparameter und einbezogene Analysen
  - Messbeginn und Messende
  - Messvorschlag und Anzahl der Testläufe bestätigen
- **Q02:**
  - CSV-Format
  - Fehlerfallkatalog
  - Quantifizierung bestätigen
- **Q03:**
  - Quantifizierung und Testablauf
- **Q04:**
  - Testumfang
  - Sitzungsverhalten
  - Quantifizierung bestätigen

### Randbedingungen

- **R01:**
  - Java-Version
- **R02:**
  - Weka-Version
- **R03:**
  - Tomcat-Version
  - Zielumgebung
- **R06:**
  - Umfang der Javadoc-Kommentierung
