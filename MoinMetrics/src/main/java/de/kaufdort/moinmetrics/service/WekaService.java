package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.repository.StorageSession;
import org.springframework.stereotype.Service;


@Service
public class WekaService {

    private final StorageSession session;

    public WekaService(StorageSession session) {
        this.session = session;
    }


}
