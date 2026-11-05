package de.kaufdort.moinmetrics.controller;

import de.kaufdort.moinmetrics.model.*;
import de.kaufdort.moinmetrics.service.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public void dayByRevenue() {
        metricsService.getDayRevenue();
    }

    // Stärkster Tag nach Kundenhäufigkeit
    @ResponseBody
    @GetMapping("/api/metrics/dayByCustomers")
    public void dayByCustomers() {
        metricsService.getDayCustomer();
    }

    // Stärkste Uhrzeit nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/timeByRevenue")
    public void timeByRevenue() {
        metricsService.getTimeRevenue();
    }

    // Stärkste Uhrzeit nach Kundenhäufigkeit
    @ResponseBody
    @GetMapping("/api/metrics/timeByCustomers")
    public void timeByCustomers() {
        metricsService.getTimeCustomer();
    }

    // Stärkste Uhrzeit nach Umsatz pro Tag
    @ResponseBody
    @GetMapping("/api/metrics/timeByRevenueEachDay")
    public void timeByRevenueEachDay() {
        metricsService.getTimeRevenueEachDay();
    }

    // Gemeinsam gekaufte Waren
    @ResponseBody
    @GetMapping("/api/metrics/itemsBoughtTogether")
    public void itemsBoughtTogether() {
        metricsService.getItemsBoughtTogether();
    }

    // Stärkster Artikel nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/bestItemByRevenue")
    public void bestItemsByRevenue() {
        metricsService.getItemsRevenue();
    }

    // Schwächster Artikel nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/worstItemByRevenue")
    public void worstItemsByRevenue() {
        metricsService.getItemsRevenue();
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
