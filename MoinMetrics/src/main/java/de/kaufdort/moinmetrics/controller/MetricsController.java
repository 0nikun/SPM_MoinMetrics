package de.kaufdort.moinmetrics.controller;

import de.kaufdort.moinmetrics.model.*;
import de.kaufdort.moinmetrics.service.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("api/metrics")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    // Stärkster Tag nach Umsatz
    @GetMapping("/dayByRevenue")
    public DayRevenueDTO dayByRevenue() {
        return metricsService.getDayRevenue();
    }

    // Stärkster Tag nach Kundenhäufigkeit
    @GetMapping("/dayByCustomers")
    public DayCustomerDTO dayByCustomers() {
        return metricsService.getDayCustomer();
    }

    // Stärkster Uhrzeit nach Umsatz
    @GetMapping("/timeByRevenue")
    public TimeRevenueDTO timeByRevenue() {
        return metricsService.getTimeRevenue();
    }

    // Stärkste Uhrzeit nach Kundenhäufigkeit
    @GetMapping("/timeByCustomers")
    public TimeCustomerDTO timeByCustomers() {
        return metricsService.getTimeCustomer();
    }

    // Stärkste Uhrzeit nach Umsatz pro Tag
    @GetMapping("/timeByRevenueEachDay")
    public TimeRevenueDayDTO timeByRevenueEachDay() {
        return metricsService.getTimeRevenueEachDay();
    }

    // Gemeinsam gekaufte Waren
    @GetMapping("/itemsBoughtTogether")
    public ItemsBoughtTogetherDTO itemsBoughtTogether() {
        return metricsService.getItemsBoughtTogether();
    }

    // Stärkster Artikel nach Umsatz
    @GetMapping("/bestItemByRevenue")
    public ItemsRevenueDTO bestItemsByRevenue() {
        return metricsService.getItemsRevenue();
    }

    // Schwächster Artikel nach Umsatz
    @GetMapping("/worstItemByRevenue")
    public ItemsRevenueDTO worstItemsByRevenue() {
        return metricsService.getItemsRevenue();
    }

    // Stärkster Artikel nach Deckungsbeitrag
    @GetMapping("/bestItemByMargin")
    public ItemsMarginDTO bestItemsByMargin() {
        return metricsService.getItemsMargin();
    }

    // Schwächster Artikel nach Deckungsbeitrag
    @GetMapping("/worstItemByMargin")
    public ItemsMarginDTO worstItemsByMargin() {
        return metricsService.getItemsMargin();
    }

}
