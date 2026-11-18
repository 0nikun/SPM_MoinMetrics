package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.model.MeasureEntity;
import de.kaufdort.moinmetrics.repository.MeasureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureService {

    private final MeasureRepository measureRepository;

    public MeasureService(MeasureRepository measureRepository) {
        this.measureRepository = measureRepository;
    }

    public void saveMeasure(String name, String beschreibung) {
        MeasureEntity measureEntity = new MeasureEntity();
        measureEntity.setName(name);
        measureEntity.setBeschreibung(beschreibung);
        measureRepository.save(measureEntity);
    }

    public MeasureEntity getMeasureById(Long id) {
        return measureRepository.findById(id).orElse(null);
    }

    public List<MeasureEntity> getAllMeasures() {
        return measureRepository.findAll();
    }

    public void updateMeasure(Long id, String name, String beschreibung) {
        MeasureEntity measureEntity = measureRepository.findById(id).orElse(null);
        if (measureEntity != null) {
            measureEntity.setName(name);
            measureEntity.setBeschreibung(beschreibung);
            measureRepository.save(measureEntity);
        }
    }

    public void deleteMeasure(Long id) {
        measureRepository.deleteById(id);
    }

    public void deleteAllMeasures() {
        measureRepository.deleteAll();
    }
}
