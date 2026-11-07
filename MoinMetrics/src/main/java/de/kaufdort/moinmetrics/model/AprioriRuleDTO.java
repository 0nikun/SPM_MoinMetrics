package de.kaufdort.moinmetrics.model;

public record AprioriRuleDTO(
        String premise,
        String conclusion,
        double confidence,
        double support,
        double lift
) {
}
