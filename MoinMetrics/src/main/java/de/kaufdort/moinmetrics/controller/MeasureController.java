package de.kaufdort.moinmetrics.controller;


import de.kaufdort.moinmetrics.model.MeasureEntity;
import de.kaufdort.moinmetrics.service.MeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
public class MeasureController {
    private final MeasureService measureService;

    public MeasureController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @GetMapping("/measures")
    public String showMeasures(Model model) {
        model.addAttribute("measures", measureService.getAllMeasures());
        return "/measures";
    }

    @PostMapping("/api/measures/create")
    public String createMeasure(
            @RequestParam("name") String name,
            @RequestParam("beschreibung") String beschreibung,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
            Model model
    ) {
        measureService.saveMeasure(name, beschreibung);
        return measuresResponse(requestedWith, model);
    }

    @PostMapping("/api/measures/update")
    public String updateMeasure(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("beschreibung") String beschreibung,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
            Model model
    ) {
        measureService.updateMeasure(id, name, beschreibung);
        return measuresResponse(requestedWith, model);
    }

    @PostMapping("/api/measures/delete")
    public String deleteMeasure(
            @RequestParam("id") Long id,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
            Model model
    ) {
        measureService.deleteMeasure(id);
        return measuresResponse(requestedWith, model);
    }

    private String measuresResponse(String requestedWith, Model model) {
        if ("XMLHttpRequest".equals(requestedWith)) {
            model.addAttribute("measures", measureService.getAllMeasures());
            return "measures :: massnahmen";
        }
        return "redirect:/metrics#massnahmen";
    }

    @GetMapping("/api/measures/get")
    public MeasureEntity getMeasure(
            @RequestParam("id") Long id
    ) {
        return measureService.getMeasureById(id);
    }

    @PostMapping("/api/measures/getAll")
    public List<MeasureEntity> getAllMeasures() {
        return measureService.getAllMeasures();
    }
}
