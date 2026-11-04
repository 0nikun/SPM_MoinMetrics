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
    public DayRevenueDTO dayByRevenue() {
        return metricsService.getDayRevenue();
    }

    // Stärkster Tag nach Kundenhäufigkeit
    @ResponseBody
    @GetMapping("/api/metrics/dayByCustomers")
    public DayCustomerDTO dayByCustomers() {
        return metricsService.getDayCustomer();
    }

    // Stärkster Uhrzeit nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/timeByRevenue")
    public TimeRevenueDTO timeByRevenue() {
        return metricsService.getTimeRevenue();
    }

    // Stärkste Uhrzeit nach Kundenhäufigkeit
    @ResponseBody
    @GetMapping("/api/metrics/timeByCustomers")
    public TimeCustomerDTO timeByCustomers() {
        return metricsService.getTimeCustomer();
    }

    // Stärkste Uhrzeit nach Umsatz pro Tag
    @ResponseBody
    @GetMapping("/api/metrics/timeByRevenueEachDay")
    public TimeRevenueDayDTO timeByRevenueEachDay() {
        return metricsService.getTimeRevenueEachDay();
    }

    // Gemeinsam gekaufte Waren
    @ResponseBody
    @GetMapping("/api/metrics/itemsBoughtTogether")
    public ItemsBoughtTogetherDTO itemsBoughtTogether() {
        return metricsService.getItemsBoughtTogether();
    }

    // Stärkster Artikel nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/bestItemByRevenue")
    public ItemsRevenueDTO bestItemsByRevenue() {
        return metricsService.getItemsRevenue();
    }

    // Schwächster Artikel nach Umsatz
    @ResponseBody
    @GetMapping("/api/metrics/worstItemByRevenue")
    public ItemsRevenueDTO worstItemsByRevenue() {
        return metricsService.getItemsRevenue();
    }

    // Stärkster Artikel nach Deckungsbeitrag
    @ResponseBody
    @GetMapping("/api/metrics/bestItemByMargin")
    public ItemsMarginDTO bestItemsByMargin() {
        return metricsService.getItemsMargin();
    }

    // Schwächster Artikel nach Deckungsbeitrag
    @ResponseBody
    @GetMapping("/api/metrics/worstItemByMargin")
    public ItemsMarginDTO worstItemsByMargin() {
        return metricsService.getItemsMargin();
    }

}
