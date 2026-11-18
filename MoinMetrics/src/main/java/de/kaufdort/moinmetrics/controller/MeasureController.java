package de.kaufdort.moinmetrics.controller;


import de.kaufdort.moinmetrics.model.MeasureEntity;
import de.kaufdort.moinmetrics.service.MeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam("beschreibung") String beschreibung
    ) {
        measureService.saveMeasure(name, beschreibung);
        return "redirect:/measures";
    }

    @PostMapping("/api/measures/delete")
    public String deleteMeasure(
            @RequestParam("id") Long id
    ) {
        measureService.deleteMeasure(id);
        return "redirect:/measures";
    }

    @PostMapping("/api/measures/update")
    public String updateMeasure(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("beschreibung") String beschreibung
    ) {
        measureService.updateMeasure(id, name, beschreibung);
        return "redirect:/measures";
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
