package de.kaufdort.moinmetrics.repository;

import de.kaufdort.moinmetrics.model.MeasureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureRepository extends JpaRepository<MeasureEntity, Long> {
}
