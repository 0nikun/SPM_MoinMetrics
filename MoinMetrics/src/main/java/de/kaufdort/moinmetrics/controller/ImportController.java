package de.kaufdort.moinmetrics.controller;

import de.kaufdort.moinmetrics.model.EinkaufDTO;
import de.kaufdort.moinmetrics.repository.StorageSession;
import de.kaufdort.moinmetrics.service.ImportService;
import de.kaufdort.moinmetrics.service.WekaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import weka.core.Instances;

import java.io.IOException;
import java.util.List;

@Controller
@SessionScope
public class ImportController {

    private final ImportService importService;
    private final WekaService wekaService;
    private final StorageSession session;

    public ImportController(ImportService importService, StorageSession session, WekaService wekaService) {
        this.importService = importService;
        this.session = session;
        this.wekaService = wekaService;
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
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute(
                    "importErgebnis", "Fehler beim Importieren der Datei.");
            return "redirect:/import";
        }
        long end = System.nanoTime();
        long duration = (end - start) / 1000000; // Dauer in Millisekunden

        redirectAttributes.addFlashAttribute(
                "importErgebnis",
                "Datei empfangen: " + file.getOriginalFilename() + " (" + duration + "ms) (" + session.getEinkaeufeSize() + " Einkäufe geladen)");


        return "redirect:/metrics";
    }
}
