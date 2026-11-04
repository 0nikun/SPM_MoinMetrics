package de.kaufdort.moinmetrics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String home(Model model) {
        model.addAttribute("message", "Willkommen bei MoinMetrics!");
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/import";
    }
}
