package de.kaufdort.moinmetrics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Willkommen bei MoinMetrics!");
        return "home";
    }

    @PostMapping("/begruessen")
    public String begruessen(@RequestParam("name") String name, Model model) {
        model.addAttribute("message", "Willkommen bei MoinMetrics!");
        model.addAttribute("ergebnis", "Hallo, " + name + "!");
        return "home";
    }
}
