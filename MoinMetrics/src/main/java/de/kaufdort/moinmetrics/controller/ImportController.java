package de.kaufdort.moinmetrics.controller;

import de.kaufdort.moinmetrics.repository.DataStorage;
import de.kaufdort.moinmetrics.service.ImportService;
import de.kaufdort.moinmetrics.service.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@SessionScope
public class ImportController {

    private final ImportService importService;
    private final MetricsService metricsService;
    private final DataStorage session;

    public ImportController(ImportService importService, MetricsService metricsService, DataStorage session) {
        this.importService = importService;
        this.metricsService = metricsService;
        this.session = session;
    }

    @GetMapping("/import")
    public String showImport(Model model) {
        model.addAttribute("message", "Willkommen bei MoinMetrics!");
        return "import";
    }

    @PostMapping("/api/import")
    public String importCSV(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        long start = System.nanoTime();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "importErgebnis", "Bitte eine nicht leere Datei auswählen.");
            return "redirect:/import";
        }


        try {
            importService.loadCSV(file);
            metricsService.init();
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute(
                    "importErgebnis", "Fehler beim Importieren der Datei.");
            return "redirect:/import";
        }
        long end = System.nanoTime();
        long duration = (end - start) / 1_0_0_0_0_0_0; // Dauer in Millisekunden

        redirectAttributes.addFlashAttribute(
                "importErgebnis",
                "Datei empfangen: " + file.getOriginalFilename() + " (" + duration + "ms) (" + session.getEinkaeufeSize() + " Einkäufe geladen)");


        return "redirect:/metrics";
    }
}
