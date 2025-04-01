package com.simple.ecommerce.Controller;

import com.simple.ecommerce.Entity.User;
import com.simple.ecommerce.Enum.Role;
import com.simple.ecommerce.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ContentController {


    //once the app runs people will be redirected to login page
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/req/login";
    }

    // login.html file will be rendered once we go to /req/login
    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    // signup.html file will be rendered
    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

}
