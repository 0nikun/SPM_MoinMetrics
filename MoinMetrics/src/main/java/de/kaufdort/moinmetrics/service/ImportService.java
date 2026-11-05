package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.repository.DataStorage;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.converters.CSVLoader;

import java.io.IOException;

@Service
public class ImportService {

    private final DataStorage session;

    public ImportService(DataStorage session) {
        this.session = session;
    }

    public void loadCSV(@NonNull MultipartFile file) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(file.getInputStream());
        session.setInstances(loader.getDataSet());
    }


}
