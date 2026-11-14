package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.InvalidCSVException;
import de.kaufdort.moinmetrics.repository.DataStorage;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ImportService {

    private final Map<String, Integer> EXPECTED_ATTRIBUTES = Map.ofEntries(
            Map.entry("Geschlecht", Attribute.NOMINAL),
            Map.entry("Alter", Attribute.NOMINAL),
            Map.entry("Kinder", Attribute.NOMINAL),
            Map.entry("Familienstand", Attribute.NOMINAL),
            Map.entry("Berufstaetig", Attribute.NOMINAL),
            Map.entry("Einkaufstag", Attribute.NOMINAL),
            Map.entry("Einkaufsuhrzeit", Attribute.NOMINAL),
            Map.entry("Wohnort", Attribute.NOMINAL),
            Map.entry("Haushaltsnettoeinkommen", Attribute.NOMINAL),
            Map.entry("Einkaufssumme", Attribute.NUMERIC),
            Map.entry("Fertiggerichte", Attribute.NUMERIC),
            Map.entry("Tiefkuehlware", Attribute.NUMERIC),
            Map.entry("Milchprodukte", Attribute.NUMERIC),
            Map.entry("Backwaren", Attribute.NUMERIC),
            Map.entry("Obst/Gemuese", Attribute.NUMERIC),
            Map.entry("Spirituosen", Attribute.NUMERIC),
            Map.entry("Tiernahrung", Attribute.NUMERIC),
            Map.entry("Bier", Attribute.NUMERIC),
            Map.entry("Frischfleisch", Attribute.NUMERIC),
            Map.entry("Drogerieartikel", Attribute.NUMERIC),
            Map.entry("Konserven", Attribute.NUMERIC),
            Map.entry("Kaffee/Tee", Attribute.NUMERIC),
            Map.entry("Suessigkeiten", Attribute.NUMERIC),
            Map.entry("Wurstwaren", Attribute.NUMERIC),
            Map.entry("Schreibwaren", Attribute.NUMERIC)
    );

    private final DataStorage session;

    public ImportService(DataStorage session) {
        this.session = session;
    }

    public void loadCSV(@NonNull MultipartFile file) throws IOException, InvalidCSVException {
        // TODO: Pflichtspalten, Datentypen und erlaubte Werte vor dem Speichern validieren
        // und Validierungsfehler als verständliche Fehlermeldung beim Import anzeigen.
        validateCSV(file);

        CSVLoader loader = new CSVLoader();
        loader.setSource(file.getInputStream());
        session.setInstances(loader.getDataSet());
    }

    private void validateCSV(@NonNull MultipartFile file) throws IOException, InvalidCSVException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(file.getInputStream());

        Instances structure;
        Instances data;
        try {
            structure = loader.getStructure();
            data = loader.getDataSet();
        } catch (IOException e) {
            throw new InvalidCSVException("Ungültige CSV-Struktur, Datei konnte nicht geladen werden.");
        } catch (IllegalArgumentException e) {
            throw new InvalidCSVException("Ungültige CSV-Struktur, prüfe bitte auf doppelte Attributsnamen oder eine fehlende Attributszeile.");
        }
        Set<String> attributes = new HashSet<>();
        Set<String> missing = new HashSet<>(EXPECTED_ATTRIBUTES.keySet());

        for (int i = 0; i < structure.numAttributes(); i++) {
            attributes.add(structure.attribute(i).name());
        }

        if (!attributes.containsAll(EXPECTED_ATTRIBUTES.keySet())) {
            missing.removeAll(attributes);
            throw new InvalidCSVException("Datei enthält nicht die erwarteten Attribute: " + missing);
        }

        int type;
        for (String attr : EXPECTED_ATTRIBUTES.keySet()) {
            System.out.println("Attribute: " + attr + " - Typ: " + data.attribute(attr).type());
            type = data.attribute(attr).type();
            if (type != EXPECTED_ATTRIBUTES.get(attr)) {
                throw new InvalidCSVException("Ungültiger Datentyp für Attribut: " + attr + " (" + Attribute.typeToString(type) + "), erwartet: " + Attribute.typeToString(EXPECTED_ATTRIBUTES.get(attr)));
            }
        }
    }

    /*
        Zu erkennende Fehler:
        - Falscher Dateityp ✅
        - leere Datei ✅

        - Attribute ✅
            - unvollständig ✅
            - falsch ✅ (zusätzliche Attribute werden nicht als Fehler erkannt, sofern erwartete Attribute vorhanden sind)
            - Datentypen falsch ✅
            - Wochentage


     */


}
