package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.model.AprioriRuleDTO;
import de.kaufdort.moinmetrics.repository.DataStorage;
import org.springframework.stereotype.Service;
import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericCleaner;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.util.*;

@Service
public class MetricsService {

    private final DataStorage storage;

    private final List<String> attributes = List.of(
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

    private final List<String> artikel = List.of(
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

    private final Map<String, Integer> deckungsbeitraege = Map.ofEntries(
            Map.entry("Fertiggerichte", 25),
            Map.entry("Tiefkuehlware", 28),
            Map.entry("Milchprodukte", 18),
            Map.entry("Backwaren", 30),
            Map.entry("Obst/Gemuese", 40),
            Map.entry("Spirituosen", 75),
            Map.entry("Tiernahrung", 23),
            Map.entry("Bier", 37),
            Map.entry("Frischfleisch", 24),
            Map.entry("Drogerieartikel", 95),
            Map.entry("Konserven", 33),
            Map.entry("Kaffee/Tee", 56),
            Map.entry("Suessigkeiten", 67),
            Map.entry("Wurstwaren", 38),
            Map.entry("Schreibwaren", 73)
    );

    private Set<String> times = new TreeSet<>();

    public MetricsService(DataStorage session) {
        this.storage = session;
    }

    public void init() {
        setTimes();
    }


    public Map<String, Integer> getDayRevenue() {
        Instances data = storage.getInstances();

        return mapAttributeRevenue(data, "Einkaufstag");
    }

    // Kundenzahl pro Tag
    public Map<String, Integer> mapDayCustomer() {
        Instances data = storage.getInstances();

        return mapAttributeCustomer(data, "Einkaufstag");
    }

    // Stärkster Tag nach Kundenhäufigkeit
    public Map.Entry<String, Integer> getBestDayCustomer() {
        return mapDayCustomer().entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow();
    }

    // liefert für jeden Tag für jeden Zeitraum den Umsatz
    public Map<String, Map<String, Integer>> mapTimeRevenueDay() {
        Instances data = storage.getInstances();

        Map<String, Map<String, Integer>> result = new HashMap<>();

        Attribute dayAttribute = data.attribute("Einkaufstag");
        Attribute timeAttribute = data.attribute("Einkaufsuhrzeit");
        Attribute revenueAttribute = data.attribute("Einkaufssumme");

        for (Instance row : data) {
            if (!result.containsKey(row.stringValue(dayAttribute))) {
                result.put(row.stringValue(dayAttribute), new HashMap<>());
            }
            result.get(row.stringValue(dayAttribute)).merge(row.stringValue(timeAttribute), (int) row.value(revenueAttribute), Integer::sum);
        }

        return result;
    }

    // Mapped den Umsatz für ein beliebiges Attribut
    private Map<String, Integer> mapAttributeRevenue(Instances data, String attribute) {
        Map<String, Integer> result = new HashMap<>();

        Attribute revenueAttribute = data.attribute("Einkaufssumme");
        Attribute attributeToMap = data.attribute(attribute);

        for (Instance row : data) {
            result.merge(row.stringValue(attributeToMap), (int) row.value(revenueAttribute), Integer::sum);
        }

        return result;
    }

    // liefert für jeden Zeitraum den Umsatz
    public Map<String, Integer> mapTimeRevenue() {
        Instances data = storage.getInstances();

        return mapAttributeRevenue(data, "Einkaufsuhrzeit");
    }

    public void setTimes() {
        Instances data = storage.getInstances();

        Attribute attr = data.attribute("Einkaufsuhrzeit");

        Enumeration<Object> enumeration = attr.enumerateValues();
        while (enumeration.hasMoreElements()) {
            times.add((String) enumeration.nextElement());
        }
    }

    private Map<String, Integer> mapAttributeCustomer(Instances data, String attribute) {
        Map<String, Integer> result = new HashMap<>();

        Attribute attributeToMap = data.attribute(attribute);

        for (Instance row : data) {
            result.merge(row.stringValue(attributeToMap), 1, Integer::sum);
        }

        return result;
    }

    public Map<String, Integer> getTimeCustomer() {
        Instances data = storage.getInstances();

        return mapAttributeCustomer(data, "Einkaufsuhrzeit");
    }

    public List<AprioriRuleDTO> getItemsBoughtTogether() {
        // Arbeiten auf einer Kopie um Storagedaten nicht zu verändern
        Instances data = new Instances(storage.getInstances());

        // Attribute die keine Artikel sind löschen
        for (int i = 0; i < 10; i++) {
            data.deleteAttributeAt(0);
        }

        // umwandeln in gekauft / nicht gekauft (0/1)
        NumericCleaner nc = new NumericCleaner();
        nc.setMaxThreshold(1.0); // Schwellwert auf 1 setzen
        nc.setMaxDefault(1.0); // alles ueber Schwellwert durch 1 ersetzen
        try {
            nc.setInputFormat(data);
            data = Filter.useFilter(data, nc); // Filter anwenden.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // nicht gekaufte Artikel aus Analyse rausnehmen
        for (Instance row : data) {
            for (int i = 0; i < data.numAttributes(); i++) {
                if (row.value(i) == 0) {
                    row.setMissing(i);
                }
            }
        }

        // Die Daten als nominale und nicht als numerische Daten setzen
        NumericToNominal num2nom = new NumericToNominal();
        num2nom.setAttributeIndices("first-last");
        try {
            num2nom.setInputFormat(data);
            data = Filter.useFilter(data, num2nom);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Apriori apriori = new Apriori();
        List<AprioriRuleDTO> aprioriRules = new ArrayList<>();
        try {
            apriori.buildAssociations(data);

            List<AssociationRule> rules = apriori.getAssociationRules().getRules();

            for (AssociationRule rule : rules) {
                aprioriRules.add(
                        new AprioriRuleDTO(
                                rule.getPremise().toString(),
                                rule.getConsequence().toString(),
                                rule.getNamedMetricValue("Confidence"),
                                (double) rule.getTotalSupport() / rule.getTotalTransactions(),
                                rule.getNamedMetricValue("Lift")
                        )
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return aprioriRules;
    }

    // Umsatz pro Artikel
    public Map<String, Integer> mapItemRevenue() {
        Instances data = storage.getInstances();

        Map<String, Integer> result = new HashMap<>();

        for (String article : artikel) {
            Attribute attribute = data.attribute(article);
            int sum = 0;
            for (Instance row : data) {
                sum += (int) row.value(attribute);
            }
            result.put(article, sum);
        }

        return result;
    }

    public Map.Entry<String, Integer> getMostRevenueItem() {
        Map<String, Integer> result = mapItemRevenue();
        return result.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get();
    }

    public Map.Entry<String, Integer> getLeastRevenueItem() {
        Map<String, Integer> result = mapItemRevenue();
        return result.entrySet().stream()
                .min(Map.Entry.comparingByValue()).get();
    }

    // Deckungsbeiträge für jeden Artikel
    public Map<String, Double> mapItemMargin() {
        Instances data = storage.getInstances();

        Map<String, Double> margins = new HashMap<>();

        mapItemRevenue().forEach((article, revenue) -> {
            margins.put(article, revenue * deckungsbeitraege.get(article) / 100.0);
        });

        return margins;
    }

    public void getItemsMargin() {
    }

}
