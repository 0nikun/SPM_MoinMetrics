# Offene Aufgaben

Aktueller Funktionsabgleich: [ANFORDERUNGSABGLEICH.md](ANFORDERUNGSABGLEICH.md). Die geforderten Auswertungen sind in der Oberfläche implementiert; fachliche Prüfpunkte und Qualitätsnachweise bleiben offen. Die unten aufgeführte Backend-Verlagerung ist ein Architekturumbau, kein fehlender Funktionsumfang.

## Anforderungen absichern und Abnahme vorbereiten

- [ ] Geldbeträge prüfen: `(int)`-Kürzungen vor dem Summieren vermeiden, sofern Nachkommastellen im Eingabeformat zulässig sind; Referenzergebnisse für Umsatz und Deckungsbeitrag vergleichen.
- [ ] CSV-Prüfung um erlaubte Wochentage/Zeitfenster und fehlende Einzelwerte ergänzen; Apriori-Spaltenauswahl gegenüber geänderter Reihenfolge und Zusatzspalten absichern.
- [ ] Aufruf von Metrics und Analyse-APIs vor dem ersten Import abfangen (`DataStorage` enthält zunächst `null`).
- [ ] Interpretation von Kunden als Einkaufsvorgänge, Artikeln als Warengruppen und Tagen als Wochentagen gegen die Abnahmebasis bestätigen; hinterlegte Deckungsbeitragssätze prüfen.
- [ ] F01–F14 mit Referenzdaten und im Browser prüfen; CSV-Fehlerfälle, Zugriffsschutz und Sitzungsablauf testen.
- [ ] Q01 nachweisen: 10.000 Datensätze einschließlich aller geforderten Analysen und sichtbarer Ergebnisse durchgängig messen; Ziel 1–2 Sekunden, höchstens 5 Sekunden. Messbedingungen festlegen.
- [ ] Javadoc vervollständigen; WAR auf Ziel-Tomcat testen und vollständiges Abgabepaket aus WAR, Quellcode und aktuellen PDF-Dokumenten prüfen.

## Fachliche Auswertung aus dem Frontend ins Backend verlagern

Die folgenden Entscheidungen und Berechnungen liegen aktuell in JavaScript. Vorhandene Service-Methoden und API-Endpunkte zuerst auf Wiederverwendbarkeit prüfen.

- [ ] Stärksten Wochentag und stärkste Uhrzeit nach Umsatz sowie Kundenhäufigkeit im Backend bestimmen und einschließlich aller Gleichstände liefern. Aktuell: `metricExtrema` und `showMetricSummary` in `metrics-summary.js`.
- [ ] Umsatzstärkstes Zeitfenster je Wochentag im Backend bestimmen, einschließlich Gleichständen und fehlenden Daten. Aktuell: Auswertung der Tageswerte in `metrics-summary.js`.
- [ ] Top-/Flop-Artikel nach Umsatz und Deckungsbeitrag sowie die Artikelrangfolge zentral bereitstellen. Gleichstände und die Berücksichtigung von Artikeln ohne Verkäufe festlegen. Aktuell: Min-/Max-Auswahl in `metrics-summary.js` und Sortierung nach Wert bzw. Artikelname in `metrics.html`.
- [ ] Relative Werte mit ihren Bezugsgrößen im Backend berechnen: Gesamtumsatz, Tagesumsatz und Anzahl der Kunden bzw. Einkaufsvorgänge fachlich eindeutig definieren. Prozentberechnung liegt derzeit in `metrics-display.js` und zusätzlich für die Tagesdiagramme in `metrics.html`.
- [ ] Regeln für die Verfügbarkeit relativer Werte zentral festlegen und über die API liefern: Umgang mit negativen Werten, einer Summe von null und ungültigen Werten; pauschalen Ausschluss des Deckungsbeitrags prüfen. Aktuell entscheidet `metrics-display.js`, ob auf absolute Werte zurückgefallen wird.
- [ ] Apriori-Kurzliste im Backend auswählen: derzeit höchstens fünf Regeln, absteigend nach Lift und danach Confidence. Auswahlkriterium, Anzahl und Gleichstände festlegen. Aktuell: `showRulesSummary` in `metrics-summary.js`; die Apriori-Berechnung selbst erfolgt bereits im Backend.
- [ ] Apriori-Prämisse und -Folge als strukturierte Artikellisten liefern. Das Frontend entfernt derzeit per String-Ersetzung Klammern und `=1` aus der serialisierten Darstellung (`metrics.html` und `metrics-summary.js`).
- [ ] Wochentage, Zeitfenster und Warengruppen einschließlich ihrer Reihenfolge zentral definieren und über die API bereitstellen. Aktuell sind diese Listen mehrfach in `metrics.html` fest codiert.
- [ ] Fehlende Werte und leere Ergebnisse fachlich eindeutig modellieren: fehlende Tageswerte werden aktuell mit `0` ergänzt, fehlende Support-Zellen mit `0` und Confidence-Zellen mit `null`; Selbstvergleiche werden ausgeblendet. Die API soll zwischen keinem Vorkommen, nicht berechenbar und fehlenden Daten unterscheiden, ohne dass das Frontend diese Bedeutung ableiten muss.
- [ ] Einfache Ergebnisansicht von der Diagrammerzeugung entkoppeln: strukturierte Zusammenfassung aus dem Backend laden, statt sie beim Aufruf von `createMetricsChart` aus Chart-Konfigurationen zu berechnen. Einfache Ergebnisse müssen auch ohne erfolgreiche Diagrammerzeugung verfügbar sein.
- [ ] Backend-Tests für diese Auswertungen ergänzen: Gleichstände, leere Daten, Nullsummen, negative Deckungsbeiträge, fehlende Zeitfenster und nicht berechenbare Confidence abdecken.

## Darstellungslogik sauber abgrenzen

- [ ] Gemeinsame Skalierung der Tagesdiagramme aus dem Inline-Skript in eine zentrale Frontend-Funktion auslagern. Fachliche Prozentwerte aus der API verwenden; gemeinsame Achsengrenzen, Rundung der Skala und Schrittweite bleiben Darstellungslogik. Aktuell: `skalaMin`, `skalaMax`, `schritt` und `prozentMax` in `metrics.html`.

Farben, Layout, Umschalter, Zahlenformatierung, Tooltips und Chart.js-Aktualisierungen bleiben im Frontend.
