package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.repository.StorageSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.converters.CSVLoader;

import java.io.IOException;

@Service
public class ImportService {

    private final StorageSession session;

    public ImportService(StorageSession session) {
        this.session = session;
    }

    public void loadCSV(MultipartFile file) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(file.getInputStream());
        session.setInstances(loader.getDataSet());
    }


}
