package de.kaufdort.moinmetrics.controller;

import de.kaufdort.moinmetrics.model.AprioriRuleDTO;
import de.kaufdort.moinmetrics.service.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller()
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics")
    public String metrics() {
        return "metrics";
    }

    // Stärkster Tag nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/dayByRevenue")
    public Map<String, Integer> dayByRevenue() {
        return metricsService.getDayRevenue();
    }

    // Stärkster Tag nach Kundenhäufigkeit
    @ResponseBody
    @GetMapping("/api/metrics/dayByCustomers")
    public Map<String, Integer> dayByCustomers() {
        return metricsService.mapDayCustomer();
    }

    // Stärkste Uhrzeit nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/timeByRevenue")
    public Map<String, Integer> timeByRevenue() {
        return metricsService.mapTimeRevenue();
    }

    // Stärkste Uhrzeit nach Kundenhäufigkeit
    @ResponseBody
    @GetMapping("/api/metrics/timeByCustomers")
    public Map<String, Integer> timeByCustomers() {
        return metricsService.getTimeCustomer();
    }

    // Stärkste Uhrzeit nach Umsatz pro Tag
    @ResponseBody
    @GetMapping("/api/metrics/timeByRevenueEachDay")
    public Map<String, Map<String, Integer>> timeByRevenueEachDay() {
        return metricsService.mapTimeRevenueDay();
    }

    // Gemeinsam gekaufte Waren
    @ResponseBody
    @GetMapping("/api/metrics/itemsBoughtTogether")
    public List<AprioriRuleDTO> itemsBoughtTogether() {
        return metricsService.getItemsBoughtTogether();
    }

    @ResponseBody
    @GetMapping("/api/metrics/itemsSupportHeatmap")
    public Map<String, Map<String, Double>> itemsSupportHeatmap() {
        return metricsService.getItemsSupportMatrix();
    }

    @ResponseBody
    @GetMapping("/api/metrics/itemsConfidenceHeatmap")
    public Map<String, Map<String, Double>> itemsConfidenceHeatmap() {
        return metricsService.getItemsConfidenceMatrix();
    }


    // Artikel nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/itemByRevenue")
    public Map<String, Integer> itemByRevenue() {
        return metricsService.mapItemRevenue();
    }

    // Stärkster Artikel nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/bestItemByRevenue")
    public Map.Entry<String, Integer> bestItemsByRevenue() {
        return metricsService.getMostRevenueItem();
    }

    // Schwächster Artikel nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/worstItemByRevenue")
    public Map.Entry<String, Integer> worstItemsByRevenue() {
        return metricsService.getLeastRevenueItem();
    }

    // Artikel nach Deckungsbeitrag
    @ResponseBody
    @GetMapping("/api/metrics/itemByMargin")
    public Map<String, Double> itemByMargin() {
        return metricsService.mapItemMargin();
    }

    // Stärkster Artikel nach Deckungsbeitrag
    @ResponseBody
    @GetMapping("/api/metrics/bestItemByMargin")
    public void bestItemsByMargin() {
        metricsService.getItemsMargin();
    }

    // Schwächster Artikel nach Deckungsbeitrag
    @ResponseBody
    @GetMapping("/api/metrics/worstItemByMargin")
    public void worstItemsByMargin() {
        metricsService.getItemsMargin();
    }

}
