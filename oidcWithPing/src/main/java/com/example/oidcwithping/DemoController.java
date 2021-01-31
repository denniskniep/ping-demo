package com.example.oidcwithping;

import com.example.oidcwithping.client.FooModel;
import com.example.oidcwithping.client.FooResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.annotation.RequestScope;

import java.security.Principal;

@Controller
public class DemoController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private Integer proxyPort;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        String accessToken = extractAccessToken();
        FooResourceClient fooResourceClient = new FooResourceClient(accessToken, proxyHost, proxyPort);
        FooModel normalFoo = fooResourceClient.getNormalFoo();

        model.addAttribute("name", principal.getName());
        model.addAttribute("normalFooId", normalFoo.getId());
        model.addAttribute("normalFooName", normalFoo.getName());
        return "profile";
    }

    public String extractAccessToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken =(OAuth2AuthenticationToken) authentication;
            String clientRegistrationId =oauthToken.getAuthorizedClientRegistrationId();
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
            accessToken = client.getAccessToken().getTokenValue();
        }
        return accessToken;
    }
}
