package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.repository.DataStorage;
import org.springframework.stereotype.Service;


@Service
public class WekaService {

    private final DataStorage session;

    public WekaService(DataStorage session) {
        this.session = session;
    }


}
