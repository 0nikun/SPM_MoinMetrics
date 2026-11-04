package de.kaufdort.moinmetrics.controller;

import de.kaufdort.moinmetrics.service.ImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ImportController {

    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/api/import")
    public String importCSV(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "importErgebnis", "Bitte eine nicht leere Datei auswählen.");
            return "redirect:/import";
        }

        // Hier kannst du die CSV über file.getInputStream()
        // einlesen und an einen Service zur Verarbeitung übergeben.

        redirectAttributes.addFlashAttribute(
                "importErgebnis",
                "Datei empfangen: " + file.getOriginalFilename());

        return "redirect:/metrics";
    }
}
