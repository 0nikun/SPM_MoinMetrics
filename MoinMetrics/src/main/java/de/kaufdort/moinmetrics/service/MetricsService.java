package de.kaufdort.moinmetrics.service;

import de.kaufdort.moinmetrics.repository.DataStorage;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.*;

@Service
public class MetricsService {

    private final DataStorage session;

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

    private Set<String> times = new TreeSet<>();

    public MetricsService(DataStorage session) {
        this.session = session;
    }

    public void init() {
        setTimes();
    }


    public Map<String, Integer> getDayRevenue() {
        Instances data = session.getInstances();

        return mapAttributeRevenue(data,"Einkaufstag");
    }

    public void getDayCustomer() {

    }

    // liefert für jeden Tag für jeden Zeitraum den Umsatz
    public Map<String, Map<String, Integer>> mapTimeRevenueDay() {
        Instances data = session.getInstances();

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

        System.out.println(result);
        return result;
    }

    private Map<String, Integer> mapAttributeRevenue(Instances data, String attribute) {
        Map<String, Integer> result = new HashMap<>();

        Attribute revenueAttribute = data.attribute("Einkaufssumme");
        Attribute attributeToMap = data.attribute(attribute);

        for (Instance row : data) {
            result.merge(row.stringValue(attributeToMap), (int) row.value(revenueAttribute), Integer::sum);
        }

        System.out.println(result);
        return result;
    }

    // liefert für jeden Zeitraum den Umsatz
    public Map<String, Integer> mapTimeRevenue() {
        Instances data = session.getInstances();

        return mapAttributeRevenue(data, "Einkaufsuhrzeit");
    }

    public void setTimes() {
        Instances data = session.getInstances();

        Attribute attr = data.attribute("Einkaufsuhrzeit");

        Enumeration<Object> enumeration = attr.enumerateValues();
        while (enumeration.hasMoreElements()) {
            times.add((String) enumeration.nextElement());
        }
    }

    public void getTimeCustomer() {
    }

    public void getItemsBoughtTogether() {
    }

    // Umsatz pro Artikel
    public Map<String, Integer> mapItemRevenue() {
        Instances data = session.getInstances();

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

    public void getItemsMargin() {
    }

}
