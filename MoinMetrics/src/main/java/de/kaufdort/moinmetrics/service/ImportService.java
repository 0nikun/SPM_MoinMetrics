package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.repository.DataStorage;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.IOException;
import java.util.Locale;

@Service
public class ImportService {

    private final DataStorage session;

    public ImportService(DataStorage session) {
        this.session = session;
    }

    public void loadCSV(@NonNull MultipartFile file) throws IOException {
        // TODO: Pflichtspalten, Datentypen und erlaubte Werte vor dem Speichern validieren
        // und Validierungsfehler als verständliche Fehlermeldung beim Import anzeigen.
        validateFileFormat(file);
        validateFileContent(file);

        CSVLoader loader = new CSVLoader();
        loader.setSource(file.getInputStream());
        session.setInstances(loader.getDataSet());
    }

    public void validateCSVFile(@NonNull MultipartFile file) {

    }

    private void validateFileFormat(@NonNull MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        } else if (!file.getOriginalFilename().toLowerCase(Locale.ROOT).endsWith(".csv")) {
            throw new IllegalArgumentException("File format is not supported");
        } else if (!file.getContentType().equals("text/csv")) {
            throw new IllegalArgumentException("File format is not supported");
        }
    }

    private void validateFileContent(@NonNull MultipartFile file) {
        CSVLoader loader = new CSVLoader();
        try {
            loader.setSource(file.getInputStream());
            System.out.println("HEADER?: " + loader.getNoHeaderRowPresent());
            Instances instances = loader.getStructure();
            System.out.println("VALIDATION: " + instances + "||");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
