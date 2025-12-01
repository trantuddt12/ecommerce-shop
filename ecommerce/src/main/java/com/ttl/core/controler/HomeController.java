package com.ttl.core.controler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String mvGoogleClientId;
	
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Nam");
        return "home";  // trả về home.html trong /templates
    }
    @GetMapping("/mylogin")
    public String loginPage(Model pModel) {
    	pModel.addAttribute("googleClientId", mvGoogleClientId);
    	return "login";
    }
}

