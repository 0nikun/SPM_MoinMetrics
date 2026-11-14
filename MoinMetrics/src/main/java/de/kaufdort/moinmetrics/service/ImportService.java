package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.InvalidCSVException;
import de.kaufdort.moinmetrics.repository.DataStorage;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ImportService {

    private final Set<String> EXPECTED_ATTRIBUTES = Set.of(
            "Geschlecht",
            "Alter",
            "Kinder",
            "Familienstand",
            "Berufstaetig",
            "Einkaufstag",
            "Einkaufsuhrzeit",
            "Wohnort",
            "Haushaltsnettoeinkommen",
            "Einkaufssumme",
            "Fertiggerichte",
            "Tiefkuehlware",
            "Milchprodukte",
            "Backwaren",
            "Obst/Gemuese",
            "Spirituosen",
            "Tiernahrung",
            "Bier",
            "Frischfleisch",
            "Drogerieartikel",
            "Konserven",
            "Kaffee/Tee",
            "Suessigkeiten",
            "Wurstwaren",
            "Schreibwaren"
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

    // prüft bisher Header auf vorhandensein und erwartete Attribute TODO: Typen
    private void validateCSV(@NonNull MultipartFile file) throws IOException, InvalidCSVException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(file.getInputStream());

        Instances structure;
        try {
            structure = loader.getStructure();
        } catch (IOException e) {
            throw new InvalidCSVException("Ungültige CSV-Struktur, Datei konnte nicht geladen werden.");
        } catch (IllegalArgumentException e) {
            throw new InvalidCSVException("Ungültige CSV-Struktur, prüfe bitte auf doppelte Attributsnamen oder eine fehlende Attributszeile.");
        }
        Set<String> attributes = new HashSet<>();
        Set<String> missing = new HashSet<>(EXPECTED_ATTRIBUTES);

        for (int i = 0; i < structure.numAttributes(); i++) {
            attributes.add(structure.attribute(i).name());
        }

        if (!attributes.equals(EXPECTED_ATTRIBUTES)) {
            missing.removeAll(attributes);
            throw new InvalidCSVException("Datei enthält nicht die erwarteten Attribute: " + missing);
        }

    }

    /*
        Zu erkennende Fehler:
        - Falscher Dateityp
        - leere Datei

        - Attribute
            - unvollständig
            - falsch
            - Datentypen falsch
        - Wochentage


     */


}
