# Abgleich des aktuellen Funktionsstands

Stand: 19.11.2026. Grundlage: [Anforderungskatalog](moodle/aufgaben/abgaben/C3_SPM_Blatt2.md), aktueller Quellcode und die im Gespräch geklärte Bedeutung von F05/F06. Bewertet wird die Gesamtanwendung, unabhängig davon, ob Berechnungen in Java oder JavaScript stattfinden.

„Implementiert“ bedeutet im Code vorhanden, nicht durch einen vollständigen Abnahmetest bestätigt. Dieser Abgleich ist eine statische Prüfung; es wurden keine neuen Browser-, Last- oder Deploymenttests durchgeführt.

## Funktionsanforderungen

| Anforderung | Stand | Umsetzung / Einschränkung |
| --- | --- | --- |
| F01 CSV-Import | Implementiert | Upload, Weka-CSVLoader und Speicherung der eingelesenen Daten in der Sitzung. |
| F02 CSV-Fehlermeldungen (#5) | Teilweise abgesichert | Leere Datei, Endung, Struktur, Pflichtspalten und Datentypen werden geprüft; Fehler werden auf der Importseite angezeigt. Erlaubte Kategorien und fehlende Einzelwerte werden nicht umfassend validiert. |
| F03 Passwortschutz | Implementiert | Spring Security schützt Seiten und APIs; Login und Logout vorhanden. Zugriffstests fehlen. |
| F04 Grafische Darstellung | Implementiert | Advanced-Ansicht mit Diagrammen, Kaufregeltabelle und Heatmaps. |
| F05 Maßnahmen anzeigen | Implementiert | Liste mit Name und Beschreibung auf der Metrics-Seite. Keine vorbefüllten Vorschläge erforderlich; im Gespräch geklärt. |
| F06 Maßnahmen erweitern | Implementiert | Formular, Datenbankpersistenz und Aktualisierung des Maßnahmenbereichs. Löschen zusätzlich vorhanden. |
| F07 Apriori (#12) | Implementiert; fachlich zu prüfen | Weka Apriori liefert Regeln mit Confidence, Support und Lift; Tabelle und Kurzliste vorhanden. Vorverarbeitung setzt eine bestimmte Spaltenreihenfolge voraus, siehe Prüfpunkte. |
| F08 Umsatzstärkster Einkaufstag (#6) | Implementiert | Maximum nach Wochentag, einschließlich Gleichständen, in der einfachen Ansicht. Geldbeträge werden im Backend auf ganze Zahlen gekürzt. |
| F09 Umsatzstärkste Einkaufsuhrzeit (#6) | Implementiert | Maximum nach den vorgegebenen Zeitfenstern, einschließlich Gleichständen. Gleicher Geldbetrags-Prüfpunkt. |
| F10 Kundenstärkster Einkaufstag (#7) | Implementiert unter Datensatzannahme | Gezählt werden CSV-Zeilen/Einkaufsvorgänge je Wochentag, nicht identifizierte unterschiedliche Personen. |
| F11 Kundenstärkste Einkaufsuhrzeit (#7) | Implementiert unter Datensatzannahme | Gezählt werden CSV-Zeilen/Einkaufsvorgänge je Zeitfenster. |
| F12 Umsatzstärkste Uhrzeit je Tag (#8) | Implementiert | Einfache Ansicht zeigt die stärksten Zeitfenster je Wochentag; Advanced zeigt die Verteilungen mit gemeinsamer Skala. Geldbetrags-Prüfpunkt gilt auch hier. |
| F13 Top-/Flop-Artikel nach Umsatz (#9, optional) | Implementiert auf Warengruppenebene | Höchste/niedrigste Summen einschließlich Gleichständen; alle 15 hinterlegten Warengruppen werden berücksichtigt, auch mit Summe null. Keine einzelnen Artikel/SKUs. Geldbetrags-Prüfpunkt gilt. |
| F14 Top-/Flop-Artikel nach Deckungsbeitrag (#10, optional) | Implementiert auf Warengruppenebene | Umsatz je Warengruppe × hinterlegter Deckungsbeitragssatz; Min/Max in der einfachen Ansicht. Korrektheit der Sätze gegen die Aufgabenbasis prüfen. Leere separate Best-/Worst-Margin-Endpunkte verhindern diese Anzeige nicht. |

Belege: [ImportController](src/main/java/de/kaufdort/moinmetrics/controller/ImportController.java), [ImportService](src/main/java/de/kaufdort/moinmetrics/service/ImportService.java), [SecurityConfig](src/main/java/de/kaufdort/moinmetrics/security/SecurityConfig.java), [MetricsService](src/main/java/de/kaufdort/moinmetrics/service/MetricsService.java), [MeasureController](src/main/java/de/kaufdort/moinmetrics/controller/MeasureController.java), [einfache Ansicht](src/main/resources/static/js/metrics-summary.js), [Metrics-Template](src/main/resources/templates/metrics.html).

## Qualität und Randbedingungen

| Anforderung | Stand |
| --- | --- |
| Q01: 10.000 Datensätze in höchstens 5 Sekunden, Ziel 1–2 Sekunden | Nicht nachgewiesen. Importtimer endet vor den späteren API-Auswertungen. Der Diagrammtimer umfasst weder den vollständigen Import noch Apriori und Heatmaps. Eine durchgängige Messung aller geforderten Analysen fehlt. |
| Q02: Fehlerhafte CSV ohne Absturz, erneuter Import möglich | Teilweise implementiert; vollständiger Fehlerfalltest fehlt. Unzulässige Kategorien und fehlende Werte müssen berücksichtigt werden. |
| Q03: Bedienbarkeit für Marktleitung | Einfache/Advanced-Ansicht, klare Ergebnisse und Fehlermeldungen vorhanden; messbare Abnahmekriterien und Benutzertest fehlen. |
| Q04: Schutz aller Datenzugriffe | In der Security-Konfiguration vorgesehen; Tests mit und ohne Anmeldung sowie zum Sitzungsablauf fehlen. |
| R01: Java | Java-21-Backend vorhanden; Weboberfläche nutzt HTML/CSS/JavaScript. Ob die Vorgabe sämtliche Analyseberechnungen in Java verlangt, ist im Katalog nicht ausdrücklich präzisiert. |
| R02: Weka | Weka für Import/Datenhaltung und Apriori eingesetzt; Summen, Min/Max und Prozentwerte werden teilweise direkt in Java bzw. JavaScript berechnet. Falls sämtliche Analysen zwingend Weka-Verfahren nutzen müssen, ist das gesondert abzuklären. |
| R03: Webseite auf internem Tomcat | Weboberfläche, ServletInitializer und bereitgestellte Tomcat-Abhängigkeit vorhanden; Deployment auf dem Zielserver nicht nachgewiesen. |
| R04: WAR-Auslieferung | `packaging=war` konfiguriert; aktuelles Auslieferungsartefakt und Installation noch zu prüfen. |
| R05: Quellcodeauslieferung | Quellcode vorhanden; Vollständigkeit des endgültigen Abgabepakets noch zu prüfen. |
| R06: Javadoc | Unvollständig; insbesondere Controller und Services überwiegend ohne Javadoc. |
| R07: Dokumente als PDF | PDF-Abgaben im Repository vorhanden; Vollständigkeit und Aktualität des endgültigen Dokumentpakets noch zu prüfen. |
| R08: Festgelegtes CSV-Format | Import vorhanden; Kategorien, fehlende Werte und Spaltenreihenfolge noch abzusichern. |
| R09: Keine Benutzerverwaltung erforderlich | Erfüllt: einzelner konfigurierter Benutzer, keine Benutzerverwaltung nötig. |

Belege: [pom.xml](pom.xml), [Application](src/main/java/de/kaufdort/moinmetrics/MoinMetricsApplication.java), [vorhandener Test](src/test/java/de/kaufdort/moinmetrics/MoinMetricsApplicationTests.java). Unter `src/test` ist aktuell nur ein Context-Load-Test vorhanden; er weist fachliche Ergebnisse und Qualitätsziele nicht nach.

## Konkrete Prüfpunkte vor einer vollständigen Abnahme

1. **Geldbeträge:** `MetricsService` wandelt Einkaufssummen und Warengruppenwerte vor der Addition mit `(int)` um. Falls Nachkommastellen zulässig sind, gehen Centbeträge verloren; auch Rangfolgen und Gleichstände können dadurch falsch werden. Entweder ganzzahlige Eingaben verbindlich absichern oder dezimale Geldwerte korrekt verarbeiten.
2. **CSV-Inhalte:** Wochentage und Zeitfenster sind im Frontend fest vorgegeben. Nicht passende Werte können den Import passieren und anschließend in der Anzeige fehlen. Fehlende Einzelwerte brauchen ebenfalls eine klare Validierungsregel.
3. **Apriori-Spalten:** Die Vorverarbeitung entfernt die ersten zehn Spalten nach Position. Die Importvalidierung prüft jedoch nur das Vorhandensein und die Typen der Pflichtattribute, nicht deren Reihenfolge; zusätzliche Spalten sind zugelassen. Daher Spaltenvertrag validieren oder Artikelattribute gezielt nach Namen auswählen.
4. **Noch kein Import:** `DataStorage` startet mit `null`; `MetricsService.hasData()` ruft darauf unmittelbar `isEmpty()` auf. Ein direkter Aufruf von `/metrics` vor dem ersten Import ist damit im Code nicht abgesichert.
5. **Fachliche Begriffe:** Die Anwendung analysiert Wochentage und Zeitfenster über den gesamten Import. „Kunden“ sind Einkaufsvorgänge, „Artikel“ sind Warengruppen. Diese Interpretation muss zur Abnahme passen; der Katalog nennt diese Punkte noch als offen.
6. **Nachweise:** Ergebnisvergleich mit bekannten Referenzdaten, Fehlerfallkatalog, Zugriffstests und vollständige Laufzeitmessung durchführen. Der dokumentierte Vorschlag von zehn Laufzeitdurchgängen ist noch als Vorschlag gekennzeichnet.

## Einordnung der TODO-Liste

Die Verlagerung vorhandener Berechnungen vom Frontend ins Backend ist ein Architekturumbau, keine fehlende Benutzerfunktion. Ebenso sind Abs/%-Schalter, Advanced-Modus und zusätzliche Heatmaps Erweiterungen gegenüber dem funktionalen Mindestumfang. Gesamtumsatz und Gesamtanzahl als zusätzliche Übersichtswerte sind im Katalog nicht als eigenständige Muss-Funktionen gefordert.
