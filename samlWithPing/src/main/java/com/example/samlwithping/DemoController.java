package com.example.samlwithping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class DemoController {

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "profile";
    }
}
