package com.example.legacyWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class DemoController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        Map<String, Object> returnValue = new HashMap<>();

        Enumeration<String> hearderNames = request.getHeaderNames();
        while(hearderNames.hasMoreElements())
        {
            String headerName = hearderNames.nextElement();
            returnValue.put(headerName, request.getHeader(headerName));
        }

        return "homePage";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "profilePage";
    }
}
