# Dokumentenregeln für MoinMetrics

Stand: 24.10.2026

Diese Regeln halten die vereinbarten Maßstäbe für zukünftige Projektdokumente fest. Sie dienen als Grundlage für die Erstellung und abschließende Prüfung von TeX-Quellen und PDFs. Abweichende Vorgaben aus der jeweiligen Aufgabenstellung oder ausdrücklich vereinbarte Ausnahmen haben Vorrang.

## 1. Umfang der Prüfung

- Formale Prüfung und inhaltliche Prüfung werden getrennt ausgewiesen.
- Eine formale Prüfung umfasst Gliederung, Typografie, Tabellen, Abstände, Seitenumbrüche und das sachliche Erscheinungsbild.
- Die Aussage „formal geprüft“ bestätigt keine fachliche oder rechnerische Richtigkeit und keine vollständige Erfüllung der Aufgabenstellung.
- Bei einer rein formalen Überarbeitung werden Aussagen, Schätzwerte und Berechnungen nicht ohne gesonderten Auftrag verändert.

## 2. Gliederung und Überschriften

- Die Hauptkapitel entsprechen den Aufgaben der jeweiligen Aufgabenstellung. Bei Übungsblättern lautet die Form beispielsweise „Aufgabe 1: Anforderungsanalyse“.
- Aufgabenbezeichnungen und automatische Kapitelnummern dürfen keine widersprüchliche oder doppelte Nummerierung erzeugen. Für ausdrücklich ausgeschriebene Aufgabenbezeichnungen können unnummerierte LaTeX-Überschriften verwendet werden.
- Gleichrangige Abschnitte erhalten dieselbe Überschriftenebene und Formatierung.
- Untergeordnete Themen werden auch in der Überschriftenhierarchie untergeordnet. Beispiel: Aufgabe 1 → Offene Abstimmungspunkte → Funktionsanforderungen, Qualitätsmerkmale und Randbedingungen.
- Persönliche Begründungen werden gleichartig und mit Namen überschrieben, beispielsweise „Begründung Alexander B.“, „Begründung Lukas P.“ und „Begründung Ino W.“.
- Übernommene schriftliche Anforderungen werden durch eine eindeutige Überschrift kenntlich gemacht, beispielsweise „Schriftliche Anforderungen des Auftraggebers“.
- Überschriften stehen zusammen mit dem Beginn des zugehörigen Textes und nicht allein am Seitenende.

## 3. Tabellen und Beschriftungen

- Große Anforderungstabellen und Tabellen zu offenen Abstimmungspunkten benötigen keine zusätzliche Beschriftung, wenn die unmittelbar vorausgehende Abschnittsüberschrift ihren Zweck eindeutig benennt. Doppelte Überschriften werden vermieden.
- Die beiden Schätztabellen erhalten aussagekräftige, nummerierte Beschriftungen, beispielsweise „Tabelle 1: Aufwandsschätzung pro Gruppenmitglied (in Stunden)“.
- Tabellenbeschriftungen stehen einheitlich oberhalb der Tabelle. Schriftgröße und Abstand werden im gesamten Dokument einheitlich eingestellt.
- Nur beschriftete Tabellen werden nummeriert. Die Nummerierung beginnt bei 1 und enthält keine unbeabsichtigten Lücken. Unbeschriftete `longtable`-Tabellen dürfen die Nummerierung der beschrifteten Tabellen nicht erhöhen.
- Unterschiedliche Tabellenstile sind ausdrücklich erlaubt. Linien, Ausrichtungen und Spaltenaufbau müssen nicht dokumentweit vereinheitlicht werden.
- Innerhalb einer Tabelle bleiben die Formatierung vergleichbarer Werte und die Darstellung von Einheiten und Dezimalzahlen einheitlich.
- Eine ergänzte Beschriftung darf eine zuvor vollständig auf eine Seite passende Tabelle nicht unbemerkt auf eine weitere Seite verdrängen. Dies ist insbesondere bei der ersten großen Anforderungstabelle zu prüfen.
- Wenn eine Tabelle auf einer Seite bleiben soll, werden zunächst Abstände und Zeilenhöhen behutsam angepasst. Die Lesbarkeit darf dabei nicht durch stark verkleinerte Schrift leiden.
- Mehrseitige Tabellen erhalten wiederholte Spaltenüberschriften. Tabellenzeilen, Beschriftungen und Tabelleninhalt dürfen nicht abgeschnitten oder überlagert werden.

## 4. Typografie und Schreibweisen

- Schriftfamilie, Schriftgrößen, Seitenränder, Fußzeilen und Seitenzahlen werden einheitlich verwendet.
- Umlaute, ß, Anführungszeichen und mathematische Zeichen müssen in der fertigen PDF korrekt erscheinen. Ein korrekter Quelltext allein genügt als Nachweis nicht.
- Falls der verwendete Compiler ein direkt eingegebenes ß falsch ausgibt, kann im TeX-Quelltext `\ss{}` verwendet werden. Maßgeblich bleibt die sichtbare Darstellung als ß.
- Deutsche Anführungszeichen werden korrekt gesetzt; im TeX-Quelltext sind beispielsweise `\glqq{}` und `\grqq{}` möglich.
- Schreibweisen wie CSV-Datei, WAR-Datei, Javadoc, Tomcat-Webserver und Data-Mining-Software werden einheitlich verwendet.
- Überschriften beginnen mit einem Großbuchstaben. Abkürzungen und Prozentangaben werden einheitlich gesetzt, beispielsweise `z.\,B.` und `10\,\%` in LaTeX.
- Tippfehler, versehentlich getrennte Wörter und unnötige Leerzeichen werden behoben, beispielsweise „Z unächst“ → „Zunächst“.
- Aufzählungen verwenden innerhalb eines Abschnitts eine einheitliche Großschreibung und Zeichensetzung.

## 5. Absätze, Abstände und Seitenumbrüche

- Absätze werden im Fließtext durch reguläre Absatzwechsel und einen einheitlichen Absatzabstand getrennt. Wiederholte manuelle Zeilenumbrüche oder Leerzeilen werden nicht als Abstandshalter verwendet.
- Feste Seitenumbrüche werden gezielt eingesetzt, wenn sie die Lesbarkeit verbessern oder eine Tabelle beziehungsweise einen zusammengehörigen Abschnitt schützen.
- Ein neues Hauptkapitel muss nicht grundsätzlich auf einer neuen Seite beginnen. Eine deutlich erkennbare Überschrift mit angemessenem Abstand kann als Trennung ausreichen.
- Große vermeidbare Leerflächen werden reduziert. Dabei wird geprüft, ob ein fester Umbruch statt der Textmenge die Lücke verursacht.
- Kurze Aufzählungen und persönliche Begründungen werden nach Möglichkeit zusammengehalten. Einzelne Listenpunkte oder wenige abgetrennte Schlusszeilen sollen nicht auf einer Folgeseite stehen.
- Längere Abschnitte dürfen sinnvoll über mehrere Seiten laufen. Das vollständige Zusammenhalten eines Abschnitts darf nicht selbst unnötig große Lücken erzeugen.
- Nach jeder Änderung eines Seitenumbruchs werden alle nachfolgenden betroffenen Seiten geprüft. Eine beseitigte Lücke darf nicht lediglich auf eine spätere Seite verschoben werden.
- Eine geringere Seitenzahl ist kein eigenständiges Qualitätsziel; Lesbarkeit und sinnvolle Zusammenhänge gehen vor.

## 6. Sachliches Erscheinungsbild und offene Punkte

- Der Text wird sachlich formuliert. Persönliche Schätzungen können einer Person zugeordnet sein, ohne in der Ich-Form geschrieben zu werden.
- Umgangssprachliche Formulierungen und interne Bearbeitungsnotizen sollen in einer Abgabe vermieden werden.
- Fragezeichenfolgen wie „???“ sind keine geeignete Kennzeichnung für offene Fragen.
- Noch fehlende Angaben können neutral mit „noch offen“ bezeichnet werden. Offene Klärungsfragen werden im Abschnitt „Offene Abstimmungspunkte“ aufgeführt.
- Offene Punkte erhalten den Bezeichner der zugehörigen Anforderung. Bei unklaren Begriffen wird die erforderliche Bedeutungsklärung ausdrücklich benannt.
- Beispiel: Die Fragezeichen bei F05 werden aus der Anforderung entfernt; stattdessen wird unter F05 die Bedeutung von „allgemeinen Marketingmaßnahmen“ zur Klärung aufgeführt.
- Eine offene Angabe wird bei einer formalen Überarbeitung nicht durch eine erfundene fachliche Festlegung ersetzt.

## 7. Arbeitsablauf und PDF-Prüfung

1. Die aktuelle TeX-Datei lesen und vorhandene Änderungen anderer Gruppenmitglieder erhalten.
2. Die beauftragten Änderungen im Quelltext vornehmen.
3. Wenn eine aktualisierte PDF beauftragt ist, diese aus dem aktuellen Quelltext kompilieren. Bei der ausdrücklichen Vorgabe „noch nicht rendern“ bleibt die bestehende PDF unverändert.
4. Die neu erstellte PDF visuell prüfen. Nach einer umfassenden Überarbeitung werden alle Seiten geprüft; nach einer lokalen Änderung mindestens die geänderten und alle durch Umbruchverschiebungen betroffenen Seiten.
5. Beschriftungen, Nummerierung, Sonderzeichen, Abstände und Seitenumbrüche kontrollieren. Textextraktion oder eine erfolgreiche Kompilierung ersetzen die visuelle Prüfung nicht.
6. Auffälligkeiten korrigieren, erneut kompilieren und die betroffenen Seiten erneut prüfen.
7. Die endgültige PDF unter dem vereinbarten Dateinamen speichern. Quelltext und PDF müssen denselben Bearbeitungsstand abbilden, sofern nicht ausdrücklich nur der Quelltext geändert werden sollte.
8. Im Abschluss kurz nennen, was geändert und geprüft wurde. Verbleibende Auffälligkeiten und der Umfang der Prüfung werden ausdrücklich angegeben.

## 8. Prüfliste vor der Abgabe

- [ ] Die Aufgabenüberschriften und ihre Reihenfolge entsprechen der Aufgabenstellung.
- [ ] Die Überschriftenhierarchie ist logisch und gleichrangige Abschnitte sind gleich formatiert.
- [ ] Persönliche Begründungen sind eindeutig zugeordnet und einheitlich überschrieben.
- [ ] Jede Tabelle ist durch ihre Abschnittsüberschrift oder eine eigene Beschriftung eindeutig benannt; die beschrifteten Tabellen sind ab 1 fortlaufend nummeriert.
- [ ] Die unterschiedlichen Tabellenstile sind bewusst gewählt und innerhalb der einzelnen Tabellen konsistent.
- [ ] Tabellen, die auf einer Seite stehen sollen, passen einschließlich Beschriftung vollständig darauf.
- [ ] Sonderzeichen, insbesondere ß und Anführungszeichen, erscheinen korrekt.
- [ ] Schreibweisen, Abkürzungen, Prozentangaben und Einheiten sind einheitlich gesetzt.
- [ ] Es gibt keine versehentlich getrennten Wörter oder auffälligen Tippfehler.
- [ ] Absatz- und Überschriftenabstände sind einheitlich; manuelle Leerzeilen erzeugen keine Lücken.
- [ ] Es gibt keine alleinstehenden Überschriften, abgetrennten einzelnen Listenpunkte oder vermeidbaren großen Leerflächen.
- [ ] Offene Angaben sind neutral gekennzeichnet und Klärungsfragen eindeutig zugeordnet.
- [ ] Fußzeilen und Seitenzahlen sind korrekt; Text und Tabellen sind nicht abgeschnitten oder überlagert.
- [ ] Alle durch die letzten Änderungen verschobenen Seiten wurden visuell geprüft.
- [ ] Die finale PDF wurde aus der aktuellen TeX-Datei erstellt, sofern ein Neubau beauftragt war.
- [ ] Im Prüfvermerk wird deutlich, ob nur die Form oder zusätzlich der Inhalt geprüft wurde.
